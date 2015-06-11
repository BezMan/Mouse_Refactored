package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list_Activity extends MyDrawerLayoutActivity{

    String title;
    TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        if (savedInstanceState == null) {
            loadFragment(new SimpleStikkyFragment());
        }

        tvTitle = (TextView)findViewById(R.id.header_title);
        tvTitle.setText(title);


    }



    public void loadFragment(final Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragment.getClass().getName())
//                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;
    }
}

