package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.TextView;

import com.comrax.mouseappandroid.App;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list extends MyDrawerLayoutActivity {

    TextView tvTitle;

    Cursor cursor;

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            loadFragment(new SimpleStikkyFragment(), "simpleTag");
        }

        tvTitle = (TextView) findViewById(R.id.header_title);
        tvTitle.setText(App.getInstance().get_boneIdTitle());


    }


    public void loadFragment(final Fragment fragment, String fragTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
//                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;

    }


    public void onListItemClick() {
//        startActivity();
        App myData = App.getInstance();
        Log.wtf("myClick", " " + myData.get_objId() + " " + myData.get_boneId() + " " + myData.get_cityId());

        cursor = new DBTools(this).getPlaceItem(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myData.get_cityId(), DBConstants.boneId, myData.get_boneId(), DBConstants.objId, myData.get_objId());

        Bundle bundle = new Bundle();
        bundle.putString("title", cursor.getString(cursor.getColumnIndex(DBConstants.name)));
        bundle.putString("hebTitle", cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
        bundle.putString("fullDescription", cursor.getString(cursor.getColumnIndex(DBConstants.fullDescriptionBody)));
        bundle.putString("address", cursor.getString(cursor.getColumnIndex(DBConstants.address)));
        bundle.putString("image", cursor.getString(cursor.getColumnIndex(DBConstants.image)));

        bundle.putString("phone", cursor.getString(cursor.getColumnIndex(DBConstants.phone)));
        bundle.putString("activityHours", cursor.getString(cursor.getColumnIndex(DBConstants.activityHours)));
        bundle.putString("publicTransportation", cursor.getString(cursor.getColumnIndex(DBConstants.publicTransportation)));
        bundle.putString("responses", cursor.getString(cursor.getColumnIndex(DBConstants.responses)));


        Fragment placeFragment = new PlaceFragment();

        placeFragment.setArguments(bundle);
        loadFragment(placeFragment, "placeTag");
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
        }
        else {
            super.onBackPressed();
        }
    }
}




