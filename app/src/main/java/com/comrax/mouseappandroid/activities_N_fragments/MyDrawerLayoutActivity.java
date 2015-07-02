package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CustomGlobalNavDrawerAdapter;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.favorites.SectionDemoActivity;
import com.comrax.mouseappandroid.model.DrawerModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public abstract class MyDrawerLayoutActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ArrayList<DrawerModel> customDrawerItemsArr = new ArrayList<>();
    protected String mTitle;
    protected TextView appBarTextView;

    private static final String STATIC_PAGE_TAG = "staticPageTag";

    protected abstract int getLayoutResourceId();
    protected abstract String setAppBarTextView();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        setClickableIcons();

        mDrawerList = (ListView) findViewById(R.id.myNavList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mylist_drawer_layout);

        setNavDrawerData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        String str = setAppBarTextView();
        appBarTextView = (TextView) findViewById(R.id.title_text);
        appBarTextView.setText(str);
    }

    private void setClickableIcons() {
        ImageButton imageButtonInfo = (ImageButton) findViewById(R.id.image_info);
        imageButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "info Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.image_burger);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseNavDrawer();
            }
        });
    }


    public void setNavDrawerData() {

        for (int i = 0; i < GlobalVars.Drawer_imageList.length; i++) {

            final DrawerModel item = new DrawerModel();

            /******* Firstly take data in model object ******/
            item.setBtnTitle(GlobalVars.Drawer_textList[i]);
            item.setBtnImage(GlobalVars.Drawer_imageList[i]);

            /******** Add Model Object in ArrayList **********/
            customDrawerItemsArr.add(item);
        }

        addDrawerItems();
        setupDrawer();
    }


    private void addDrawerItems() {
        CustomGlobalNavDrawerAdapter mAdapter = new CustomGlobalNavDrawerAdapter(this, customDrawerItemsArr, getResources());
        mDrawerList.setAdapter(mAdapter);

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setAppBarTextView("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getSupportActionBar().setAppBarTextView("regular");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void openCloseNavDrawer() {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.END))
            mDrawerLayout.openDrawer(GravityCompat.START);

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
    }


    public void onNavDrawerItemClick(int mPosition) {
//        TextView barTitleTextView = (TextView) findViewById(R.id.title_text);
//        App.getInstance().setCityName(barTitleTextView.getText().toString());


        if (mPosition == 0) {
            allCities();

        } else if (mPosition == 1) {
            openFavorites();

        } else if (mPosition == 2) {
            emailShareApp();

        } else if (mPosition == 3) {
            rateApp();

        } else {
            openStaticPage(mPosition);
        }
        mDrawerLayout.closeDrawers();
    }




    private void allCities() {
        Intent cityIntent = new Intent(this, MainListActivity.class);
        cityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cityIntent);
    }



    private void openFavorites() {
        Fragment staticPageFragment = getSupportFragmentManager().findFragmentByTag(STATIC_PAGE_TAG);

        //if frag open, just close it...
        if (this instanceof SectionDemoActivity && staticPageFragment!=null && staticPageFragment.isVisible()) { //if in static page:
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(staticPageFragment)
//                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }
        else {
            Intent intent = new Intent(this, SectionDemoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }

    }


    private void emailShareApp() {
        String websiteAddress = "http://www.mouse.co.il/";
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "חבר/ה שלך הוריד/ה את אפליקציית עכבר עולם.");
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("הורד את האפליקציה באמצעות הלינק הבא: \n\n  <a href=\"" + websiteAddress + "\"> " + "להורדה" + "</a>"));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }



    private void rateApp() {
    }

    private void openStaticPage(int mPosition) {
        DrawerModel tempValues = customDrawerItemsArr.get(mPosition);
        for (int i = 0; i < GlobalVars.staticPagesArray.length(); i++) {
            try {

                JSONObject jsonObject = GlobalVars.staticPagesArray.getJSONObject(i);
                if ((mPosition == 4 && jsonObject.getString("id").equals("3")) || (mPosition == 5 && jsonObject.getString("id").equals("2")) || (mPosition == 6 && jsonObject.getString("id").equals("1"))) {

                    Bundle bundle = new Bundle();
                    bundle.putString("data", jsonObject.toString());
                    bundle.putString("barTitle", tempValues.getBtnTitle());

                    Fragment staticPageFragment = new StaticPageFragment();
                    staticPageFragment.setArguments(bundle);
                    loadFragment(staticPageFragment, STATIC_PAGE_TAG);

                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}




    public void loadFragment(final Fragment fragment, String fragTag) {

        Fragment staticPageFragment = getSupportFragmentManager().findFragmentByTag(STATIC_PAGE_TAG);

        if (staticPageFragment!=null && staticPageFragment.isVisible()) { //if in static page:
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_container, fragment, fragTag)
//                    .addToBackStack(fragTag)
                    .commit();

        } else {   //inside static page :
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_container, fragment, fragTag)
                .addToBackStack(fragTag)
                    .commit();

        }

    }

//    searchOnClick

    public void searchOnClick(View view) {
        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mylist_drawer_layout);
//        Fragment placeFragment = getSupportFragmentManager().findFragmentByTag("placeTag");
        Fragment staticPageFragment = getSupportFragmentManager().findFragmentByTag(STATIC_PAGE_TAG);

        //close drawer if opened:
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }

        else {
            if (staticPageFragment!=null && staticPageFragment.isVisible()) { //if in static page:
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(staticPageFragment)
//                    .addToBackStack(fragment.getClass().getName())
                        .commit();
            }



            super.onBackPressed();

        }
    }
}
