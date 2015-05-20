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
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CitiesAdapter;
import com.comrax.mouseappandroid.model.BannersModel;
import com.comrax.mouseappandroid.model.CitiesModel;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVarsAndHeaders();

        setBanners(loadJsonDataFromFile("/sdcard/Mouse_App/Default_master.zip/banners.json"));
        setCities(loadJsonDataFromFile("/sdcard/Mouse_App/Default_master.zip/cities.json"));

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

                File file = new File("/sdcard/Mouse_App/Default_master.zip/" + BannersArray.get(i).getImageBIG());
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


    public void onListItemClick(int mPosition) {
        CitiesModel tempValues = CitiesArray.get(mPosition);

//        Intent detailsIntent = new Intent(this, ConfDetailsActivity.class);
//        detailsIntent.putExtra("NID", tempValues.getNid());
//        detailsIntent.putExtra("COLORPOS", mColorPos);
//        startActivity(detailsIntent);

        Toast.makeText(this, tempValues.getName() + " \n" + tempValues.getId() + " \n" + tempValues.getImage() + " \n" + tempValues.getBoneId(), Toast.LENGTH_LONG).show();
        for (int i=0; i< GlobalVars.initDataModelArrayList.size(); i++){
            if(GlobalVars.initDataModelArrayList.get(i).getCityId().equals(tempValues.getId())){
                new DownloadFileAsync().execute(GlobalVars.initDataModelArrayList.get(i).getFile());

            }
        }
    }






    class DownloadFileAsync extends AsyncTask<String, String, String> {

        String fileName;

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
                File file = new File("/sdcard/Mouse_App/" + fileName);    //download to here//
                //only continue if non-existant.
                if (!file.exists()) {

                    OutputStream output = new FileOutputStream(file);
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
                unzip(new File("/sdcard/Mouse_App/" + fileName), new File("/sdcard/Mouse_App/" + fileName.substring(0, fileName.indexOf('.'))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            nextActivity();
        }

        @Override
        protected void onCancelled(String s) {
//            delete downloaded zip file on cancel:
            new File("/sdcard/Mouse_App/" + fileName).delete();

        }
    }


    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        if (!targetDirectory.exists())  //non-existant:
            try {
                ZipEntry ze;
                int count;
                byte[] buffer = new byte[8192];
                while ((ze = zis.getNextEntry()) != null) {
                    File file = new File(targetDirectory, ze.getName());
                    File dir = ze.isDirectory() ? file : file.getParentFile();
                    if (!dir.isDirectory() && !dir.mkdirs())
                        throw new FileNotFoundException("Failed to ensure directory: " +
                                dir.getAbsolutePath());
                    if (ze.isDirectory())
                        continue;
                    FileOutputStream fout = new FileOutputStream(file);
                    try {
                        while ((count = zis.read(buffer)) != -1)
                            fout.write(buffer, 0, count);
                    } finally {
                        fout.close();
                    }
                }
            } finally {
                zis.close();
            }
    }



    private void nextActivity() {
//        startActivity(new Intent(SplashActivity.this, MainListActivity.class));
//        finish();

    }




}