package com.comrax.mouseappandroid.activities_N_fragments;

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

    TextView tvTitle;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void onResume() {
        super.onResume();

        initLoadFragment(new SimpleStikkyFragment(), "ListTag");


        tvTitle = (TextView) findViewById(R.id.header_title);
        tvTitle.setText(App.getInstance().get_boneIdTitle());

        App.getInstance().setInFragActivity(true);
    }


    private void initLoadFragment(final Fragment fragment, String fragTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.below_bone_title_container, fragment, fragTag)
//                .addToBackStack(fragTag)
                .commit();

    }


//    public void loadFragment(final Fragment fragment, String fragTag) {
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.below_bone_title_container, fragment, fragTag)
////                .addToBackStack(fragment.getClass().getName())
//                .commit();
//
//    }

    public void loadFragmentWithBackStack(final Fragment fragment, String fragTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.below_bone_title_container, fragment, fragTag)
                .addToBackStack(fragTag)
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
        loadFragmentWithBackStack(placeFragment, "placeTag");
    }



}




