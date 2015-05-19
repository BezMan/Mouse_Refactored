package com.comrax.mouseappandroid.activities;

import android.content.res.Configuration;
import android.os.Bundle;
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

        for (int i = 0; i < GlobalVars.Nav10_imageList.length; i++) {

            final DrawerModel item = new DrawerModel();

            /******* Firstly take data in model object ******/
//            item.setBtnTitle(GlobalVars.Nav10_textList[i]);
            item.setBtnImage(GlobalVars.Nav10_imageList[i]);

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
        DrawerModel tempValues = customDrawerItemsArr.get(mPosition);
        Toast.makeText(this, "" + tempValues.getBtnImage() + " \n" + mPosition + " \n", Toast.LENGTH_LONG).show();

//        Intent detailsIntent = new Intent(this, ConfListActivity.class);
//        detailsIntent.putExtra("NavSelect", mPosition);
//        detailsIntent.putExtra("NavTitle", tempValues.getBtnTitle());

//        if (mPosition == 0) {
//            search();
//        } else if (mPosition == 1) {
//            showFavorites();
////        } else if (mPosition >= 2 && mPosition <= 6) {
////            startActivity(detailsIntent);
//        } else if (mPosition == 7) {
//            contactUs();
//        } else if (mPosition == 8) {
//            _getHelpData();
//        } else if (mPosition == 9) {
//            logout();
//        }
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
//
//
//    private void search() {
//    }
//
//    private void showFavorites() {
//    }
//
//    private void contactUs() {
//    }
//
//    private void _getHelpData() {
//    }
//
//    private void logout() {
//    }

    }


    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }
    }


}
