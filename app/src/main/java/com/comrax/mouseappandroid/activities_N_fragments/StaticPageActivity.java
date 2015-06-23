package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bez on 23/06/2015.
 */
public class StaticPageActivity extends MyDrawerLayoutActivity {

    TextView title, mainTxt;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.static_page_layout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (TextView)findViewById(R.id.static_page_title);
        mainTxt = (TextView)findViewById(R.id.static_page_main_text);

        Intent staticPageIntent = getIntent();
        try {
            TextView titleTextView = (TextView) findViewById(R.id.title_text);
            titleTextView.setText(staticPageIntent.getStringExtra("barTitle"));

            JSONObject jsonObject = new JSONObject(staticPageIntent.getStringExtra("data"));

            title.setText(jsonObject.getString("Title"));
            mainTxt.setText(jsonObject.getString("Content"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
