package com.comrax.mouseappandroid.activities_N_fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bez on 14/07/2015.
 */
public class StopArticleActivity extends MyBaseDrawerActivity{

DBTools dbTools = new DBTools(this);

    @Override
    protected int getLayoutResourceId() {
        return R.layout.stop_article_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView stopTxt = (TextView)findViewById(R.id.stop_textview);
        String wholeTxt = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.stopsArticle, DBConstants.cityId, myInstance.get_cityId());

        try {
            JSONObject jsonObject = new JSONObject(wholeTxt);
            JSONArray jsonArray = jsonObject.getJSONArray("article");
            JSONObject item = (JSONObject) jsonArray.get(0);
            String content = item.getString("content");
            stopTxt.setText(Html.fromHtml(Html.fromHtml(content).toString()));
            stopTxt.setMovementMethod(LinkMovementMethod.getInstance());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
