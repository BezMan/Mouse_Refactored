package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.GlobalVars;
import com.comrax.mouseappandroid.model.InitDataModel;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class SplashActivity extends Activity {

    enum Request {
        PRIMARY, ZIP
    }

    public static Request _request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getInitialData();
    }

    private void getInitialData() {
        _request = Request.PRIMARY;
        new RequestTask().execute("http://www.mouse.co.il/appService.ashx?appName=master@mouse.co.il");

    }


    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            saveInitCitiesData(result);
        }
    }


    public void saveInitCitiesData(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray files = jsonObject.getJSONArray("files");
            GlobalVars.initDataModelArrayList = new ArrayList<>();

            for (int i = 0; i < files.length(); i++) {
                JSONObject jsonItem = (JSONObject) files.get(i);

                final InitDataModel arrayItem = new InitDataModel();

                /******* Firstly take data in model object ******/
                arrayItem.setCityId(jsonItem.getString("CityId"));
                arrayItem.setFile(jsonItem.getString("File"));
                arrayItem.setUpdate_date(jsonItem.getString("Update_date"));

                /******** Add Model Object in ArrayList **********/
                GlobalVars.initDataModelArrayList.add(arrayItem);
            }

            _request = Request.ZIP;
            File mouseFolder = new File("/sdcard/Mouse_App");
            if(mouseFolder.isDirectory()==false)
                mouseFolder.mkdirs();
            new DownloadFileAsync().execute(GlobalVars.initDataModelArrayList.get(0).getFile());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    class DownloadFileAsync extends AsyncTask<String, String, String> {

        private ProgressDialog mProgressDialog;
        String fileName;
        File sourceZipFile, destinationFolder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SplashActivity.this);
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

                int lengthOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                sourceZipFile = new File("/sdcard/Mouse_App/" + fileName);    //download to here//
                destinationFolder = new File("/sdcard/Mouse_App/" + fileName.substring(0, fileName.indexOf('.'))); //without .zip//

                //only continue if non-existant.
                if (!sourceZipFile.exists()) {

                    OutputStream output = new FileOutputStream(sourceZipFile);
                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lengthOfFile));
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
                HelperMethods.unzip(sourceZipFile, destinationFolder);
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


    private void nextActivity() {
        startActivity(new Intent(SplashActivity.this, MainListActivity.class));
        finish();

    }


}
