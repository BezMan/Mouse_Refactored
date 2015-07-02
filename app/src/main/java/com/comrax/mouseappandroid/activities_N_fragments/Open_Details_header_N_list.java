package com.comrax.mouseappandroid.activities_N_fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list extends MyDrawerLayoutActivity implements MyBaseFragment.MyFragmentDelegate {

    TextView boneTitle;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleStikkyFragment fragment = new SimpleStikkyFragment();
        fragment.setDelegate(this);
        initLoadFragment(fragment, "ListTag");


        boneTitle = (TextView) findViewById(R.id.bone_title);
        boneTitle.setText(App.getInstance().get_boneIdTitle());


    }



    private void initLoadFragment(final Fragment fragment, String fragTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
//                .addToBackStack(fragTag)
                .commit();

    }


    public void loadFragmentWithBackStack(final Fragment fragment, String fragTag) {

        getSupportFragmentManager().popBackStack(fragTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
                .addToBackStack(fragTag)
                .commit();


    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;

    }

    @Override
    protected String getTextForAppBar() {
        return  App.getInstance().getCityName();
    }


    public void onListItemClick() {
//        startActivity();
        App myData = App.getInstance();
        Log.wtf("myClick", " " + myData.get_objId() + " " + myData.get_boneId() + " " + myData.get_cityId());

        cursor = new DBTools(this).getPlaceItem(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myData.get_cityId(), DBConstants.boneId, myData.get_boneId(), DBConstants.objId, myData.get_objId());

        Bundle bundle = new Bundle();
        bundle.putString("name", cursor.getString(cursor.getColumnIndex(DBConstants.name)));
        bundle.putString("hebName", cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
        bundle.putString("fullDescription", cursor.getString(cursor.getColumnIndex(DBConstants.fullDescriptionBody)));
        bundle.putString("address", cursor.getString(cursor.getColumnIndex(DBConstants.address)));
        bundle.putString("image", cursor.getString(cursor.getColumnIndex(DBConstants.image)));

        bundle.putString("phone", cursor.getString(cursor.getColumnIndex(DBConstants.phone)));
        bundle.putString("activityHours", cursor.getString(cursor.getColumnIndex(DBConstants.activityHours)));
        bundle.putString("publicTransportation", cursor.getString(cursor.getColumnIndex(DBConstants.publicTransportation)));
        bundle.putString("responses", cursor.getString(cursor.getColumnIndex(DBConstants.responses)));

        bundle.putString("type", cursor.getString(cursor.getColumnIndex(DBConstants.type)));


        PlaceFragment placeFragment = new PlaceFragment();
        placeFragment.setDelegate(this);

        placeFragment.setArguments(bundle);
        loadFragmentWithBackStack(placeFragment, "placeTag");
    }


    @Override
    public void onResumeAction() {
        //change your title
        String str = getTextForAppBar();
        setupTextView(str);
    }
}




