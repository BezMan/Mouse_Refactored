package com.comrax.mouseappandroid.activities;

import android.content.Intent;
import android.os.Bundle;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.helpers.HelperMethods;

import org.json.JSONObject;

public class Detail_City_Activity extends MyDrawerLayoutActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMainPageArticles();

    }



    private void getMainPageArticles(){
        Intent dataFileIntent = getIntent();
        String fileData = dataFileIntent.getStringExtra("cityFolderName");
        JSONObject jsonData= HelperMethods.loadJsonDataFromFile(fileData+"/1146_mainPageArticles.json");

    }




}
