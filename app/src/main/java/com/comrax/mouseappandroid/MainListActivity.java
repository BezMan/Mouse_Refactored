package com.comrax.mouseappandroid;

import android.os.Bundle;

/**
 * Created by bez on 10/05/2015.
 */
public class MainListActivity extends MyDrawerLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);


    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

}