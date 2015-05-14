package com.comrax.mouseappandroid.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CitiesAdapter;
import com.comrax.mouseappandroid.model.BannersModel;
import com.comrax.mouseappandroid.model.CitiesModel;

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

    GridViewWithHeaderAndFooter gridView;
    public ArrayList<CitiesModel> CitiesArray = new ArrayList<>();
    public ArrayList<BannersModel> BannersArray = new ArrayList<>();
    CitiesAdapter citiesAdapter;

    View headerView;
    Button b1, b2, b3, b4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVarsAndHeaders();

        setBanners(loadJsonDataFromFile("/sdcard/Mouse_App/banners.json"));
        setCities(loadJsonDataFromFile("/sdcard/Mouse_App/cities.json"));

        citiesAdapter = new CitiesAdapter(this, CitiesArray, getResources());
        gridView.setAdapter(citiesAdapter);

    }


    private void initVarsAndHeaders() {

        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.main_grid);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        headerView = layoutInflater.inflate(R.layout.banner_layout, null);
        gridView.addHeaderView(headerView);

//        b1 = (Button) headerView.findViewById(R.id.banner_button1);
//        b2 = (Button) headerView.findViewById(R.id.banner_button2);
//        b3 = (Button) headerView.findViewById(R.id.banner_button3);
//        b4 = (Button) headerView.findViewById(R.id.banner_button4);
    }


    private void setBanners(JSONObject jsonObj) {
        Button[] buttons = {b1,b2, b3, b4};
         // Getting data JSON Array nodes
        JSONArray data = null;
        try {
            data = jsonObj.getJSONArray("banners");
            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);

                String text = item.getString("text");
                String imageBIG = item.getString("imageBIG");
                String urlAndroid = item.getString("UrlAndroid");


                final BannersModel bannerItem = new BannersModel();

                bannerItem.setText(text);
                bannerItem.setImageBIG(imageBIG);
                bannerItem.setUrlAndroid(urlAndroid);

                BannersArray.add(bannerItem);

                int resID = getResources().getIdentifier("banner_button"+ (i+1),
                        "id", getPackageName());
                buttons[i] = (Button) headerView.findViewById(resID);

                buttons[i].setText(BannersArray.get(i).getText());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setCities(JSONObject jsonObj) {
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

                CitiesArray.add(cityItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    public void bannerSelected(View view) {
        switch (view.getId()) {
            case R.id.banner_button1:
                Toast.makeText(getApplicationContext(), "111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.banner_button2:
                Toast.makeText(getApplicationContext(), "222", Toast.LENGTH_SHORT).show();
                break;
            case R.id.banner_button3:
                Toast.makeText(getApplicationContext(), "333", Toast.LENGTH_SHORT).show();
                break;
            case R.id.banner_button4:
                Toast.makeText(getApplicationContext(), "444", Toast.LENGTH_SHORT).show();
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


    public void onListItemClick(int mPosition) {
        CitiesModel tempValues = CitiesArray.get(mPosition);

//        Intent detailsIntent = new Intent(this, ConfDetailsActivity.class);
//        detailsIntent.putExtra("NID", tempValues.getNid());
//        detailsIntent.putExtra("COLORPOS", mColorPos);
//        startActivity(detailsIntent);

        Toast.makeText(this, tempValues.getName() + " \n" + tempValues.getId() + " \n" + tempValues.getImage() + " \n" + tempValues.getBoneId(), Toast.LENGTH_LONG).show();
    }


}