package com.comrax.mouseappandroid.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CitiesAdapter;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.BannersModel;
import com.comrax.mouseappandroid.model.CitiesModel;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
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

    View bannerLayout;
    Button b1, b2, b3, b4;
    ImageView image1,image2,image3,image4;
    LinearLayout layout1, layout2, layout3, layout4;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_drawer_layout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVarsAndHeaders();

        setBanners(HelperMethods.loadJsonDataFromFile("/sdcard/Mouse_App/Default_master/banners.json"));
        setCities(HelperMethods.loadJsonDataFromFile("/sdcard/Mouse_App/Default_master/cities.json"));

        citiesAdapter = new CitiesAdapter(this, CitiesArray, getResources());
        gridView.setAdapter(citiesAdapter);

    }


    private void initVarsAndHeaders() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.main_grid);
        bannerLayout = layoutInflater.inflate(R.layout.banner_layout, null);
        gridView.addHeaderView(bannerLayout);


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
                buttons[i] = (Button) bannerLayout.findViewById(buttonID);
                buttons[i].setText(BannersArray.get(i).getText());


                int imageID = getResources().getIdentifier("banner_image"+ (i+1), "id", getPackageName());
                images[i] = (ImageView) bannerLayout.findViewById(imageID);

                File file = new File("/sdcard/Mouse_App/Default_master/" + BannersArray.get(i).getImageBIG());
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    images[i].setImageBitmap(bitmap);
                }

                int layoutID = getResources().getIdentifier("banner_layout"+ (i+1), "id", getPackageName());
                layouts[i]= (LinearLayout) bannerLayout.findViewById(layoutID);
                layouts[i].setOnClickListener(new OnBannerClick(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class OnBannerClick implements View.OnClickListener {
        private int mPosition;

        OnBannerClick(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BannersArray.get(mPosition).getUrlAndroid()));
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

                String s =  GlobalVars.initDataModelArrayList.get(i+1).getFile();
                String s2 =  s.substring(0, s.lastIndexOf('.'));
                String cityFolder = s2.substring(s2.lastIndexOf("/")+1, s2.length());

                File file = new File("/sdcard/Mouse_App/" + cityFolder);
                if(!file.exists() && CitiesArray.size()>0) {
                    CitiesArray.add(CitiesArray.size(), cityItem);
                }
                else {
                    CitiesArray.add(0, cityItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    public void onListItemClick(int mPosition) {
        CitiesModel tempValues = CitiesArray.get(mPosition);

//        Toast.makeText(this, tempValues.getName() + " \n" + tempValues.getId() + " \n" + tempValues.getImage() + " \n" + tempValues.getBoneId(), Toast.LENGTH_LONG).show();
        for (int i=0; i< GlobalVars.initDataModelArrayList.size(); i++){
            if(GlobalVars.initDataModelArrayList.get(i).getCityId().equals(tempValues.getId())){
                new DownloadFileAsync().execute(GlobalVars.initDataModelArrayList.get(i).getFile());

            }
        }
    }






    class DownloadFileAsync extends AsyncTask<String, String, String> {

        private ProgressDialog mProgressDialog;
        String fileName;
        File sourceZipFile, destinationFolder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainListActivity.this);
            mProgressDialog.setMessage("Downloading file..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DownloadFileAsync.this.cancel(true);
                    dialog.dismiss();
                }
            });
                    mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            fileName = aurl[0].substring(aurl[0].lastIndexOf("/") + 1);
            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                sourceZipFile = new File("/sdcard/Mouse_App/" + fileName);    //download to here//
                //only continue if non-existant.
                if (!sourceZipFile.exists()) {

                    OutputStream output = new FileOutputStream(sourceZipFile);
                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);

                        if (isCancelled())
                            break;
                    }

                    output.flush();
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();
            try {
                destinationFolder = new File("/sdcard/Mouse_App/" + fileName.substring(0, fileName.indexOf('.'))); //without .zip//
                HelperMethods.unzip(sourceZipFile, destinationFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            nextActivity(destinationFolder);
        }

        @Override
        protected void onCancelled(String s) {
//            delete downloaded zip file on cancel:
            new File("/sdcard/Mouse_App/" + fileName).delete();

        }
    }




    private void nextActivity(File file) {
        Intent cityFolderNameIntent = new Intent(MainListActivity.this, Detail_City_Activity.class);
        cityFolderNameIntent.putExtra("cityFolderName", file.toString());
        startActivity(cityFolderNameIntent);
    }




}