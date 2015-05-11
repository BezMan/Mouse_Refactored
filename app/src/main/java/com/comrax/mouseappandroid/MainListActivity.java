package com.comrax.mouseappandroid;

import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by bez on 10/05/2015.
 */
public class MainListActivity extends MyDrawerLayoutActivity {

    public ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main_list;
    }


    private void initVars() {
        _listView = (ListView) findViewById(R.id.dummy_list);
    }

}