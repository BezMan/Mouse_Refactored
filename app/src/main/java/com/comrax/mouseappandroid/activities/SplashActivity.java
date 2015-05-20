package com.comrax.mouseappandroid.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.comrax.mouseappandroid.R;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class SplashActivity extends Activity {

    enum Request {
        PRIMARY, ZIP
    }

    public static Request _request;

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;


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

    private void getZip(String zipUrl) {
        _request = Request.ZIP;
        new DownloadFileAsync().execute(zipUrl);
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
            compareDataDates(result);
        }
    }


    public void compareDataDates(String result) {

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


            getZip(GlobalVars.initDataModelArrayList.get(0).getFile());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                File file = new File("/sdcard/Default_master.zip");
                //only continue if non-existant.
                if (!file.exists()) {

                    OutputStream output = new FileOutputStream(file);
                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);
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
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

            try {
                unzip(new File("/sdcard/Default_master.zip"), new File("/sdcard/Mouse_App/Default_master.zip"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            nextActivity();
        }
    }


    private void nextActivity() {
        startActivity(new Intent(SplashActivity.this, MainListActivity.class));
        finish();

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


}
