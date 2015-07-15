package com.comrax.mouseappandroid.activities_N_fragments;

import android.os.Bundle;
import android.util.Log;

import com.comrax.mouseappandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bez on 15/07/2015.
 */
public class ArticleActivity extends MyBaseDrawerActivity {

//    Intent articleIntent;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.article_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String info = getIntent().getStringExtra("articleData");

        try {
            JSONObject jsonObject = new JSONObject(info);
            Log.wtf("Article json: ", "" + jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}