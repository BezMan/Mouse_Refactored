package com.comrax.mouseappandroid.activities_N_fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list_Activity extends MyDrawerLayoutActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "agrt", Toast.LENGTH_SHORT).show();
    }
}
