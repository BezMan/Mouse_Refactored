package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    String title;
    TextView tvTitle;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = App.getInstance().get_boneIdTitle();

        if (savedInstanceState == null) {
            loadFragment(new SimpleStikkyFragment(), "simpleTag");
        }

        tvTitle = (TextView) findViewById(R.id.header_title);
        tvTitle.setText(title);


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
        Log.wtf("myClick", " " + App.getInstance().get_objId() + " " + App.getInstance().get_boneId() + " " + App.getInstance().get_cityId());

        App mydata = App.getInstance();
        cursor = new DBTools(this).getPlaceItem(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, mydata.get_cityId(), DBConstants.boneId, mydata.get_boneId(), DBConstants.objId, mydata.get_objId());

        loadFragment(new PlaceFragment(), "placeTag");
    }


    @Override
    public void onBackPressed() {
        //returning from place fragment restarts this activity, because viewPager layout needs to restart.
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("placeTag");
        if (myFragment != null && myFragment.isVisible()) {
            startActivity(new Intent(this, getClass()));
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}




