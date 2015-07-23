package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CitiesAdapter;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.app.HelperMethods;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.model.BannersModel;
import com.comrax.mouseappandroid.model.CitiesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by bez on 10/05/2015.
 */
public class MainGridActivity extends MyBaseDrawerActivity {

    GridViewWithHeaderAndFooter gridView;
    public ArrayList<CitiesModel> CitiesArray = new ArrayList<>();
    public static ArrayList<BannersModel> BannersArray;
    CitiesAdapter citiesAdapter;

    View bannerLayout;
    Button b1, b2, b3, b4;
    ImageView image1, image2, image3, image4;
    LinearLayout layout1, layout2, layout3, layout4;

    DBTools dbTools = new DBTools(this);
    boolean b;

    int existingCityCounter = 0;

    String fileName, updateDate;
    File sourceZipFile, destinationFolder;

    ArrayList<Pair<String, String>> boneId_boneName = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_drawer_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return "עכבר עולם";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myInstance.setCityName("עכבר עולם");
        myInstance.set_cityId("blankPage");
        GlobalVars.detailMenuItems = new ArrayList<>();
        initVarsAndHeaders();

        saveStaticPages(HelperMethods.loadJsonDataFromFile(GlobalVars.trialMethod(getApplicationContext(), "Default_master/staticPages.json")));

        setBanners(HelperMethods.loadJsonDataFromFile(GlobalVars.trialMethod(getApplicationContext(), "Default_master/banners.json")));
        setCities(HelperMethods.loadJsonDataFromFile(GlobalVars.trialMethod(getApplicationContext(), "Default_master/cities.json")));

        addDummyViews();

        citiesAdapter = new CitiesAdapter(this, CitiesArray, existingCityCounter, getResources());
        gridView.setAdapter(citiesAdapter);

    }


    private void saveStaticPages(JSONObject jsonObject) {
        try {
            GlobalVars.staticPagesArray = jsonObject.getJSONArray("pages");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void addDummyViews() {

        final CitiesModel greenNotDownloaded = new CitiesModel();
        greenNotDownloaded.setId("greenNotDownloaded");

        final CitiesModel greenYesDownloaded = new CitiesModel();
        greenYesDownloaded.setId("greenYesDownloaded");

        final CitiesModel greenBlankItem = new CitiesModel();
        greenBlankItem.setId("greenBlankItem");

        final CitiesModel blankCityItem = new CitiesModel();
        blankCityItem.setId("blankCityItem");


        if (existingCityCounter > 0) {

            //2 greens at middle:
            CitiesArray.add(existingCityCounter, greenBlankItem);
            CitiesArray.add(existingCityCounter, greenNotDownloaded);
            if (existingCityCounter % 2 == 1) {
                //fill the missing gap:
                CitiesArray.add(existingCityCounter, blankCityItem);
                //2 blanks at the end, fixes bug:
                CitiesArray.add(CitiesArray.size(), blankCityItem);
                CitiesArray.add(CitiesArray.size(), blankCityItem);
            }
            //2 greens at beginning:
            CitiesArray.add(0, greenBlankItem);
            CitiesArray.add(0, greenYesDownloaded);
        } else {
            CitiesArray.add(0, greenBlankItem);
            CitiesArray.add(0, greenNotDownloaded);

        }
    }


    private void initVarsAndHeaders() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.main_grid);
        bannerLayout = layoutInflater.inflate(R.layout.banner_layout, null);
        gridView.addHeaderView(bannerLayout);


    }


    private void setBanners(JSONObject jsonObj) {
        Button[] buttons = {b1, b2, b3, b4};
        ImageView[] images = {image1, image2, image3, image4};
        LinearLayout[] layouts = {layout1, layout2, layout3, layout4};
        // Getting data JSON Array nodes
        BannersArray = new ArrayList<>();
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

                int buttonID = getResources().getIdentifier("banner_button" + (i + 1), "id", getPackageName());
                buttons[i] = (Button) bannerLayout.findViewById(buttonID);
                buttons[i].setText(BannersArray.get(i).getText());


                int imageID = getResources().getIdentifier("banner_image" + (i + 1), "id", getPackageName());
                images[i] = (ImageView) bannerLayout.findViewById(imageID);

                File file = new File(GlobalVars.trialMethod(getApplicationContext(), "Default_master/" + BannersArray.get(i).getImageBIG()));
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    images[i].setImageBitmap(bitmap);
                }

                int layoutID = getResources().getIdentifier("banner_layout" + (i + 1), "id", getPackageName());
                layouts[i] = (LinearLayout) bannerLayout.findViewById(layoutID);
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
        try {
            JSONArray data = jsonObj.getJSONArray("cities");
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

                //order the cities//
                boolean cityExists = dbTools.isDataAlreadyInDB(DBConstants.CITY_TABLE_NAME, "cityId", cityItem.getId());
                if (!cityExists) {
                    CitiesArray.add(CitiesArray.size(), cityItem);
                } else {
                    CitiesArray.add(0, cityItem);
                    existingCityCounter++;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void onLongCityItemClick(final int mPosition) {
        final CitiesModel tempValues = CitiesArray.get(mPosition);

        //only delete if existant.
        if (dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.cityId, tempValues.getId()).getCount() > 0) {

            //show dialog//
            final Dialog dialog = new Dialog(MainGridActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_city_delete_dialog);

            Button startDownloadButton = (Button) dialog.findViewById(R.id.start_delete_btn);
            startDownloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dbTools.deleteWholeCity(tempValues.getId());


//                    File[] files = getFilesDir().listFiles();
//                    for (File file : files) {
//                        if (file.getName().contains(tempValues.getId())) {
//                            b = file.delete();
//                            Log.wtf("delete: ", "" + b);
//                        }
//                    }

                    recreate();
                }

            });

            Button cancelDownloadButton = (Button) dialog.findViewById(R.id.cancel_delete_btn);
            cancelDownloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }


    public void onCityItemClick(final int mPosition) {
        final CitiesModel tempValues = CitiesArray.get(mPosition);

        for (int i = 0; i < GlobalVars.initDataModelArrayList.size(); i++) {
            if (GlobalVars.initDataModelArrayList.get(i).getCityId().equals(tempValues.getId())) {
                //save clicked cityId:
                myInstance.set_cityId(tempValues.getId());

                final String filePath = GlobalVars.initDataModelArrayList.get(i).getFile();
                updateDate = GlobalVars.initDataModelArrayList.get(i).getUpdate_date();
                fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

                sourceZipFile = new File(GlobalVars.trialMethod(getApplicationContext(), fileName));    //download to here//
                destinationFolder = new File(GlobalVars.trialMethod(getApplicationContext(),fileName.substring(0, fileName.indexOf('.')))); //without .zip//

                //only download if non-existant.
                if (dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.cityId, tempValues.getId()).getCount() == 0) {

                    //show dialog//
                    final Dialog dialog = new Dialog(MainGridActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_city_download_dialog);


                    Button startDownloadButton = (Button) dialog.findViewById(R.id.start_download_btn);
                    startDownloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                            new DownloadFileAsync().execute(filePath, updateDate);
                        }

                    });

                    Button cancelDownloadButton = (Button) dialog.findViewById(R.id.cancel_download_btn);
                    cancelDownloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }

                else {
                    //city exists:
                    if (!updateDate.equals(dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.dateUpdated, DBConstants.cityId, tempValues.getId()))) {

                        //show dialog, needs update//
                        final Dialog dialog = new Dialog(MainGridActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.update_city_dialog);


                        Button startDownloadButton = (Button) dialog.findViewById(R.id.update_download_btn);
                        startDownloadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                                dbTools.deleteWholeCity(tempValues.getId());


                                new DownloadFileAsync().execute(filePath, updateDate);
                            }
                        });

                        dialog.show();

                    } else {
                        nextActivity(destinationFolder, updateDate);
                    }
                }
                break;
            }
        }


    }


    class DownloadFileAsync extends AsyncTask<String, String, String> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainGridActivity.this, R.style.full_screen_dialog);
            mProgressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
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
        protected String doInBackground(String... initData) {//filepath + date//
            int count;
            try {

                URL url = new URL(initData[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(sourceZipFile);
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);

                    if (isCancelled()) {
                        output.flush();
                        output.close();
                        input.close();
                        return null;
                    }
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Bad connection \n try again", Toast.LENGTH_LONG).show();
                onCancelled();
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();
            new SavingFilesAsync().execute();

        }

        @Override
        protected void onCancelled() {
//            delete downloaded zip file on cancel:
            new File(GlobalVars.trialMethod(getApplicationContext(),fileName)).delete();
            new File(GlobalVars.trialMethod(getApplicationContext(), String.valueOf(destinationFolder))).delete();
            mProgressDialog.dismiss();

        }
    }


    class SavingFilesAsync extends AsyncTask<String, String, String> {

        private ProgressDialog mSavingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mSavingDialog = new ProgressDialog(MainGridActivity.this, R.style.full_screen_dialog);
            mSavingDialog.setMessage("Saving city data..");
            mSavingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSavingDialog.setCancelable(true);
            mSavingDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SavingFilesAsync.this.cancel(true);
                    dialog.dismiss();
                }
            });
            mSavingDialog.show();
        }

        @Override
        protected String doInBackground(String... initData) {//filepath + date//
            try {
                HelperMethods.unzip(sourceZipFile, destinationFolder);
                new File(GlobalVars.trialMethod(getApplicationContext(),fileName)).delete();
                setDBdata();
                mSavingDialog.dismiss();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error saving file", Toast.LENGTH_LONG).show();
                onCancelled();
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            mSavingDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            mSavingDialog.dismiss();
            startActivity(new Intent(MainGridActivity.this, MainGridActivity.class));
            finish();
        }

        @Override
        protected void onCancelled() {
//            delete downloaded zip file on cancel:
            mSavingDialog.show();
            new File(GlobalVars.trialMethod(getApplicationContext(),fileName)).delete();
            new File(GlobalVars.trialMethod(getApplicationContext(), String.valueOf(destinationFolder))).delete();
            mSavingDialog.dismiss();

        }
    }


    private void setDBdata() {

        JSONObject cityObject = new JSONObject();
        File dir = new File(destinationFolder.toString());
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            try {
                for (File child : directoryListing) {

                    // loop thru json files in city directory//

                    if (child.toString().contains("CityCoordinates")) {
                        JSONObject jsonCityCoordinates = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONObject fullObject = jsonCityCoordinates.getJSONObject("cityCoordinates");
                        cityObject.put(DBConstants.cityId, myInstance.get_cityId());
                        cityObject.put(DBConstants.hebrewName, fullObject.getString("name"));
                        cityObject.put(DBConstants.name, fullObject.getString("EnglishName"));
                        cityObject.put(DBConstants.centerCoordinateLat, fullObject.getString("latitude"));
                        cityObject.put(DBConstants.centerCoordinateLon, fullObject.getString("longitude"));
                    } else if (child.toString().contains("StopsArticle")) {
                        JSONObject jsonStopsArticle = HelperMethods.loadJsonDataFromFile(child.toString());
                        cityObject.put(DBConstants.stopsArticle, jsonStopsArticle.toString());
                    } else if (child.toString().contains("TuristArticalsList")) {
                        JSONObject jsonTuristArticalsList = HelperMethods.loadJsonDataFromFile(child.toString());
                        cityObject.put(DBConstants.touristArticlesList, jsonTuristArticalsList.toString());
                    } else if (child.toString().contains("PlacesList")) {
                        JSONObject jsonPlace = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonPlace.getJSONArray("places");
                        // looping through All nodes of json file:
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            item.put(DBConstants.cityId, myInstance.get_cityId());
                            dbTools.insertPlaceTable(item);
                        }
                    } else if (child.toString().contains("ArticalsList")) {
                        JSONObject jsonArticle = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonArticle.getJSONArray("articles");
                        // looping through All nodes of json file:
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            item.put(DBConstants.cityId, myInstance.get_cityId());
                            dbTools.insertArticleTable(item);
                        }
                    }


                }
                //on purpose after the whole first file loop, adding to the table data//
                for (File child : directoryListing) {

                    if (child.toString().contains("_menu")) {
                        JSONObject jsonMenuList = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonMenuList.getJSONArray("menu");
                        for (int i = 0; i < data.length() - 1; i++) {
                            JSONObject item = data.getJSONObject(i);

                            boneId_boneName.add(new Pair<>(item.getString("boneId"), item.getString("name")));

                        }
                    }
                }

                for (File child : directoryListing) {

                    if (child.toString().contains("PlacesCoordinatesList")) {
                        JSONObject jsonPlacesCoordinatesList = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonPlacesCoordinatesList.getJSONArray("cityPlcesCoordinates");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);

                            String boneId = child.toString();
                            boneId = boneId.substring(boneId.lastIndexOf("/") + 1);
                            boneId = boneId.substring(5, 9);


                            for (int j = 0; j < boneId_boneName.size(); j++) {
                                if (boneId.equals(boneId_boneName.get(j).first)) {
                                    item.put(DBConstants.boneCategoryName, boneId_boneName.get(j).second);
                                }
                            }


                            dbTools.addDataToTable(DBConstants.PLACE_TABLE_NAME, item, boneId);
                        }
                    }

                }

                //add extra important data//
                cityObject.put(DBConstants.dateUpdated, updateDate);
                cityObject.put(DBConstants.cityFolderPath, destinationFolder);

                dbTools.insertCityTable(cityObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "error with setDBdata", Toast.LENGTH_LONG).show();

            }

        }
    }


    private void nextActivity(File file, String updateDate) {
        Intent cityFolderNameIntent = new Intent(MainGridActivity.this, Detail_City_Activity.class);
        cityFolderNameIntent.putExtra("cityFolderName", file.toString());
//        cityFolderNameIntent.putExtra("cityUpdateDate", updateDate);
        startActivity(cityFolderNameIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        myInstance.setCityName(null);
        myInstance.set_cityId(null);
    }
}