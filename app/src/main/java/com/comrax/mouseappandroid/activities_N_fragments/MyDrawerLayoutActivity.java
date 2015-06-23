package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.model.CustomGlobalNavDrawerAdapter;
import com.comrax.mouseappandroid.model.DrawerModel;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public abstract class MyDrawerLayoutActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ArrayList<DrawerModel> customDrawerItemsArr = new ArrayList<>();


    protected abstract int getLayoutResourceId();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        setClickableIcons();

        mDrawerList = (ListView) findViewById(R.id.myNavList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mylist_drawer_layout);

        setNavDrawerData();


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
//                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle("regular");
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
//        DrawerModel tempValues = customDrawerItemsArr.get(mPosition);
//        Toast.makeText(this, "" + tempValues.getBtnImage() + " \n" + mPosition + " \n", Toast.LENGTH_LONG).show();


        if (mPosition == 0) {
            Intent cityIntent = new Intent(this, MainListActivity.class);
            cityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(cityIntent);

        } else if (mPosition == 1) {
        } else if (mPosition == 2) {
        } else if (mPosition == 3) {
        } else if (mPosition == 4) {
            for (int i = 0; i < GlobalVars.staticPagesArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalVars.staticPagesArray.getJSONObject(i);

                    if (jsonObject.getString("id").equals("3")) {
                        nextActivity(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (mPosition == 5) {
            for (int i = 0; i < GlobalVars.staticPagesArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalVars.staticPagesArray.getJSONObject(i);

                    if (jsonObject.getString("id").equals("2")) {
                        nextActivity(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (mPosition == 6) {
            for (int i = 0; i < GlobalVars.staticPagesArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = GlobalVars.staticPagesArray.getJSONObject(i);

                    if (jsonObject.getString("id").equals("1")) {
                        nextActivity(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
//
//        //finish any list activity (2-6), which isn't the initial list
////        Intent selectionIntent = getIntent();
////        int selectPos = selectionIntent.getIntExtra("NavSelect", -1);
////        if (selectPos != -1)
////            finish();
//
//        mDrawerLayout.closeDrawers();
//
//
//    }

    }

    private void nextActivity(JSONObject jsonObject) {
        Intent staticPageIntent = new Intent(this, StaticPageActivity.class);
        staticPageIntent.putExtra("data", jsonObject.toString());
        startActivity(staticPageIntent);

    }


//    searchOnClick

    public void searchOnClick(View view) {
        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {

        //close drawer if opened:
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mylist_drawer_layout);
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("placeTag");

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }
        //returning from place fragment restarts this activity, because viewPager layout needs to restart.
        else if (myFragment != null && myFragment.isVisible()) {
            startActivity(new Intent(this, getClass()));
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
