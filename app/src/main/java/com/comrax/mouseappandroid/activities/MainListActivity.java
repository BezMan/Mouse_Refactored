package com.comrax.mouseappandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ImageView image1,image2,image3,image4;
    LinearLayout layout1, layout2, layout3, layout4;


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
    }


    private void setBanners(JSONObject jsonObj) {
        Button[] buttons = {b1,b2, b3, b4};
        ImageView[] images = {image1, image2, image3, image4};
        LinearLayout[] layouts = {layout1, layout2, layout3, layout4};
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

                int buttonID = getResources().getIdentifier("banner_button"+ (i+1), "id", getPackageName());
                buttons[i] = (Button) headerView.findViewById(buttonID);
                buttons[i].setText(BannersArray.get(i).getText());


                int imageID = getResources().getIdentifier("banner_image"+ (i+1), "id", getPackageName());
                images[i] = (ImageView) headerView.findViewById(imageID);

                File file = new File("/sdcard/Mouse_App/" + BannersArray.get(i).getImageBIG());
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    images[i].setImageBitmap(bitmap);
                }


                int layoutID = getResources().getIdentifier("banner_layout"+ (i+1), "id", getPackageName());
                layouts[i]= (LinearLayout) headerView.findViewById(layoutID);
                layouts[i].setOnClickListener(new OnBannerClick(i));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class OnBannerClick implements View.OnClickListener {
        private int mposition;

        OnBannerClick(int position) {
            mposition = position;
        }

        @Override
        public void onClick(View arg0) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BannersArray.get(mposition).getUrlAndroid()));
            startActivity(browserIntent);
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



//    public void bannerSelected(View view) {
////        switch (view.getId()) {
//
////            case R.id.layouttt:
//                                Toast.makeText(getApplicationContext(), "layout", Toast.LENGTH_SHORT).show();
//
////            case R.id.banner_button1:
////                Toast.makeText(getApplicationContext(), "111", Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.banner_button2:
////                Toast.makeText(getApplicationContext(), "222", Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.banner_button3:
////                Toast.makeText(getApplicationContext(), "333", Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.banner_button4:
////                Toast.makeText(getApplicationContext(), "444", Toast.LENGTH_SHORT).show();
////        }
//    }

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