package com.comrax.mouseappandroid.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.model.CitiesModel;
import com.comrax.mouseappandroid.model.citiesAdapter;

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
import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by bez on 10/05/2015.
 */
public class MainListActivity extends MyDrawerLayoutActivity {

    private GridView gridView;
    public ArrayList<CitiesModel> customListViewValuesArr = new ArrayList<>();
    citiesAdapter adapter;
    View footerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gridView = (GridView)findViewById(R.id.main_grid);

        openCities(loadJsonDataFromFile("/sdcard/Mouse_App/cities.json"));

        addFooter();

        adapter = new citiesAdapter(this, customListViewValuesArr, getResources());
        gridView.setAdapter(adapter);

    }

    private void openCities(JSONObject jsonObj) {
        // Getting data JSON Array nodes
        JSONArray data = null;
        try {
            data = jsonObj.getJSONArray("cities");
            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);

                String id = item.getString("id");
                String boneId = item.getString("boneId");
                String image = item.getString("image");
                String name = item.getString("name");


                final CitiesModel cityItem = new CitiesModel();

                cityItem.setId(id);
                cityItem.setBoneId(boneId);
                cityItem.setImage(image);
                cityItem.setName(name);

                customListViewValuesArr.add(cityItem);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void addFooter() {
        GridViewWithHeaderAndFooter gridView = (GridViewWithHeaderAndFooter)findViewById(R.id.main_grid);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headerView = layoutInflater.inflate(R.layout.banner_layout, null);
//        View footerView = layoutInflater.inflate(R.layout.test_footer_view, null);
        gridView.addHeaderView(headerView);
//        gridView.addFooterView(footerView);

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


    public void onListItemClick(int mPosition) {
        CitiesModel tempValues = customListViewValuesArr.get(mPosition);

//        Intent detailsIntent = new Intent(this, ConfDetailsActivity.class);
//        detailsIntent.putExtra("NID", tempValues.getNid());
//        detailsIntent.putExtra("COLORPOS", mColorPos);
//        startActivity(detailsIntent);

        Toast.makeText(this, tempValues.getName() + " \n" + tempValues.getId() + " \n" + tempValues.getImage() + " \n" + tempValues.getBoneId(), Toast.LENGTH_LONG).show();
    }



}