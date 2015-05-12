package com.comrax.mouseappandroid.activities;

import android.os.Bundle;

import com.comrax.mouseappandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by bez on 10/05/2015.
 */
public class MainListActivity extends MyDrawerLayoutActivity {

//    public ListView _listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        _listView = (ListView) findViewById(R.id.dummy_list);

        openCities(loadJsonDataFromFile("/sdcard/Mouse_App/cities.json"));
    }

    private void openCities(JSONObject jsonObj) {
        // Getting data JSON Array nodes
        JSONArray data = null;
        try {
            data = jsonObj.getJSONArray("cities");
            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                String id = c.getString("id");
                String title = c.getString("boneId");
                String duration = c.getString("duration");
                //use >  int id = c.getInt("duration"); if you want get an int


                // tmp hashmap for single node
                HashMap<String, String> parsedData = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                parsedData.put("id", id);
                parsedData.put("title", title);
                parsedData.put("duration", duration);


                // do what do you want on your interface
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_drawer_layout;
    }


    public JSONObject loadJsonDataFromFile(String FILENAME) {
        File yourFile = new File(FILENAME);
        FileInputStream stream = null;
        String jsonStr = null;
        JSONObject jsonObject = null;

        try {
            stream = new FileInputStream(yourFile);
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            jsonStr = Charset.defaultCharset().decode(bb).toString();
            stream.close();

            try {
                jsonObject = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;


    }
//            JSONObject jsonObj = new JSONObject(jsonStr);


}