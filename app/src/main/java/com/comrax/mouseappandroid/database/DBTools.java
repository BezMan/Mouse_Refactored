package com.comrax.mouseappandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DBTools extends SQLiteOpenHelper {


    public DBTools(Context applicationContext){
        super(applicationContext, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ARTICLE_TABLE = "CREATE TABLE "
                + DBConstants.ARTICLE_TABLE_NAME + " ("
                + DBConstants.boneId + " INTEGER, "
                + DBConstants.cityId + " INTEGER, "
                + DBConstants.imagePath + " TEXT, "
                + DBConstants.menuItemId + " INTEGER, "
                + DBConstants.name + " TEXT, "
                + DBConstants.nsId + " INTEGER, "
                + DBConstants.objId + " INTEGER, "
                + DBConstants.rating + " TEXT, "
                + DBConstants.ratingCount + " TEXT, "
                + DBConstants.responses + " TEXT, "
                + DBConstants.title + " TEXT, "
                + DBConstants.url + " TEXT, "
                + DBConstants.content + " TEXT " + ");";


        String CREATE_PLACE_TABLE = "CREATE TABLE "
                + DBConstants.PLACE_TABLE_NAME + " ("
                + DBConstants.price + " INTEGER, "
                + DBConstants.boneId + " INTEGER, "
                + DBConstants.cityId + " INTEGER, "
                + DBConstants.nsId + " INTEGER, "
                + DBConstants.objId + " INTEGER, "
                + DBConstants.address + " TEXT, "
                + DBConstants.description + " TEXT, "
                + DBConstants.fullDescriptionBody + " TEXT, "
                + DBConstants.hebrewName + " TEXT, "
                + DBConstants.name + " TEXT, "
                + DBConstants.rating + " INTEGER, "
                + DBConstants.ratingCount + " INTEGER, "
                + DBConstants.phone + " TEXT, "
                + DBConstants.type + " TEXT, "
                + DBConstants.urlString + " TEXT, "
                + DBConstants.userComments + " TEXT "
                + ");";


        String CREATE_CITY_TABLE = "CREATE TABLE "
                + DBConstants.CITY_TABLE_NAME + " ("
                + DBConstants.centerCoordinateLat + " REAL, "
                + DBConstants.centerCoordinateLon + " REAL, "
                + DBConstants.cityFolderPath + " TEXT, "
                + DBConstants.coordinates + " TEXT, "
                + DBConstants.dateUpdated + " TEXT, "
                + DBConstants.imageName + " TEXT, "
                + DBConstants.index + " INTEGER, "
                + DBConstants.mainArticles + " TEXT, "
                + DBConstants.menu + " TEXT, "
                + DBConstants.name + " TEXT, "
                + DBConstants.serviceMenu + " TEXT, "
                + DBConstants.stopsArticle + " TEXT, "
                + DBConstants.touristArticles + " TEXT "
                + ");";


        String CREATE_PLACE_FAVORITE_TABLE = "CREATE TABLE "
                + DBConstants.PLACE_FAVORITE_TABLE_NAME + " ("
                + DBConstants.address + " TEXT, "
                + DBConstants.categoryId + " INTEGER, "
                + DBConstants.description + " TEXT, "
                + DBConstants.index + " INTEGER, "
                + DBConstants.name + " TEXT, "
                + DBConstants.phone + " TEXT, "
                + DBConstants.price + " INTEGER, "
                + DBConstants.rating + " INTEGER, "
                + DBConstants.type + " TEXT, "
                + DBConstants.urlString + " TEXT "
                + ");";

        try {
            db.execSQL(CREATE_PLACE_FAVORITE_TABLE);
            db.execSQL(CREATE_PLACE_TABLE);
            db.execSQL(CREATE_ARTICLE_TABLE);
            db.execSQL(CREATE_CITY_TABLE);

        } catch (SQLiteException e) {
            Log.e("Create table exception:", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ARTICLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PLACE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PLACE_FAVORITE_TABLE_NAME);

        onCreate(db);
    }



    public void insertCityTable(HashMap<String, String> queryValues){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBConstants.boneId, queryValues.get("nid"));
        values.put(DBConstants.cityId, queryValues.get("title"));
        values.put(DBConstants.imagePath, queryValues.get("country"));
        values.put(DBConstants.menuItemId, queryValues.get("country"));
        values.put(DBConstants.name, queryValues.get("country"));
        values.put(DBConstants.nsId, queryValues.get("country"));
        values.put(DBConstants.objId, queryValues.get("country"));
        values.put(DBConstants.rating, queryValues.get("country"));
        values.put(DBConstants.ratingCount, queryValues.get("country"));
        values.put(DBConstants.responses, queryValues.get("country"));
        values.put(DBConstants.title, queryValues.get("country"));
        values.put(DBConstants.url, queryValues.get("country"));
        values.put(DBConstants.urlContent, queryValues.get("country"));

        database.insert(DBConstants.CITY_TABLE_NAME, null, values);

        database.close();

    }

    public void insertArticleTable(JSONObject item){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(DBConstants.cityId, item.getString(DBConstants.cityId));
            values.put(DBConstants.nsId, item.getString(DBConstants.nsId));
            values.put(DBConstants.objId, item.getString(DBConstants.objId));
            values.put(DBConstants.boneId, item.getString(DBConstants.boneId));
            values.put(DBConstants.url, item.getString(DBConstants.url));
            values.put(DBConstants.title, item.getString(DBConstants.title));
            values.put(DBConstants.menuItemId, item.getString(DBConstants.menuItemId));
            values.put(DBConstants.imagePath, item.getString(DBConstants.imagePath));
            values.put(DBConstants.rating, item.getString(DBConstants.rating));
            values.put(DBConstants.ratingCount, item.getString(DBConstants.ratingCount));

            JSONObject urlContent = item.getJSONObject(DBConstants.urlContent);
            values.put(DBConstants.name, urlContent.getString(DBConstants.name));
            values.put(DBConstants.image, urlContent.getString(DBConstants.image));
            values.put(DBConstants.content, urlContent.getString(DBConstants.content));
            values.put(DBConstants.responses, urlContent.getString(DBConstants.responses));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        database.insert(DBConstants.ARTICLE_TABLE_NAME, null, values);
        database.close();

    }


    public void insertPlaceTable(JSONObject item){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(DBConstants.cityId, item.getString(DBConstants.cityId));
            values.put(DBConstants.name, item.getString(DBConstants.name));
            values.put(DBConstants.nsId, item.getString(DBConstants.nsId));
            values.put(DBConstants.objId, item.getString(DBConstants.objId));
            values.put(DBConstants.boneId, item.getString(DBConstants.boneId));
            values.put(DBConstants.urlString, item.getString(DBConstants.urlString));
            values.put(DBConstants.description, item.getString(DBConstants.description));
            values.put(DBConstants.address, item.getString(DBConstants.address));
            values.put(DBConstants.phone, item.getString(DBConstants.phone));
            values.put(DBConstants.type, item.getString(DBConstants.type));
            values.put(DBConstants.rating, item.getString(DBConstants.rating));
            values.put(DBConstants.ratingCount, item.getString(DBConstants.ratingCount));

            JSONObject jsonUrlContentFullPlace = item.getJSONObject(DBConstants.fullPlace);
            values.put(DBConstants.fullDescriptionBody, jsonUrlContentFullPlace.getString(DBConstants.description));
            values.put(DBConstants.hebrewName, jsonUrlContentFullPlace.getString(DBConstants.hebrewName));
            values.put(DBConstants.price, jsonUrlContentFullPlace.getString(DBConstants.price));
            values.put(DBConstants.userComments, jsonUrlContentFullPlace.getString(DBConstants.userComments));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        database.insert(DBConstants.PLACE_TABLE_NAME, null, values);
        database.close();

    }



}
