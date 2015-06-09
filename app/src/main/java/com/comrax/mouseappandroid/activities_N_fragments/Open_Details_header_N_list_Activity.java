package com.comrax.mouseappandroid.activities_N_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list_Activity extends MyDrawerLayoutActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            loadFragment(new SimpleStikkyFragment());
        }

    }



    public void loadFragment(final Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;
    }
}

