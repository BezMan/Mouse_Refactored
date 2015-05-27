package com.comrax.mouseappandroid.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by bez on 20/05/2015.
 */
public class HelperMethods {


    public static JSONObject loadJsonDataFromFile(String FILENAME) {
        File yourFile = new File(FILENAME);
        JSONObject jsonObject = null;
        try {
            FileInputStream stream = new FileInputStream(yourFile);
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            String jsonStr = Charset.defaultCharset().decode(bb).toString();
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
