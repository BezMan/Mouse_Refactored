package com.comrax.mouseappandroid.activities_N_fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.TiyulimListAdapter;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bez on 14/07/2015.
 */
public class TiyulimActivity extends MyBaseDrawerActivity{

    DBTools dbTools = new DBTools(this);
    ListView listView;

    TiyulimListAdapter adapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.tiulim_activity;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = (ListView)findViewById(R.id.tiyulim_listView);
        try {
            JSONObject jsonObject = new JSONObject(dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.touristArticlesList, DBConstants.cityId, myInstance.get_cityId()));
//            int jLength = jsonObject.length();
            Log.wtf("length", ""+jsonObject.length());
            JSONArray jsonArray = jsonObject.getJSONArray("articles");

            adapter = new TiyulimListAdapter(this, jsonArray, getResources());
            listView.setAdapter(adapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
