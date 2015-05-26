package com.comrax.mouseappandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class AppDataDbHelper extends SQLiteOpenHelper {

    public AppDataDbHelper(Context context, String name, CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ARTICLE_TABLE = "CREATE TABLE "
                + AppDataDbConstants.ARTICLE_TABLE_NAME + " ("
                + AppDataDbConstants.boneId + " INTEGER, "
                + AppDataDbConstants.cityId + " INTEGER, "
                + AppDataDbConstants.dictionary + " TEXT, "
                + AppDataDbConstants.imagePath + " TEXT, "
                + AppDataDbConstants.menuItemId + " INTEGER, "
                + AppDataDbConstants.name + " TEXT, "
                + AppDataDbConstants.nsId + " INTEGER, "
                + AppDataDbConstants.objId + " INTEGER, "
                + AppDataDbConstants.rating + " REAL, "
                + AppDataDbConstants.ratingCount + " INTEGER, "
                + AppDataDbConstants.responses + " TEXT, "
                + AppDataDbConstants.title + " TEXT, "
                + AppDataDbConstants.url + " TEXT, "
                + AppDataDbConstants.urlContent + " TEXT " + ");";


        String CREATE_CITY_TABLE = "CREATE TABLE "
                + AppDataDbConstants.CITY_TABLE_NAME + " ("
                + AppDataDbConstants.centerCoordinateLat + " REAL, "
                + AppDataDbConstants.centerCoordinateLon + " REAL, "
                + AppDataDbConstants.cityFolderPath + " TEXT, "
                + AppDataDbConstants.coordinates + " TEXT, "
                + AppDataDbConstants.dateUpdated + " TEXT, "
                + AppDataDbConstants.imageName + " TEXT, "
                + AppDataDbConstants.index + " INTEGER PRIMARY KEY, "
                + AppDataDbConstants.mainArticles + " TEXT, "
                + AppDataDbConstants.menu + " TEXT, "
                + AppDataDbConstants.name + " TEXT, "
                + AppDataDbConstants.serviceMenu + " TEXT, "
                + AppDataDbConstants.stopsArticle + " TEXT, "
                + AppDataDbConstants.touristArticles + " TEXT " + ")";


        String CREATE_PLACE_TABLE = "CREATE TABLE "
                + AppDataDbConstants.PLACE_TABLE_NAME + " ("
                + AppDataDbConstants.address + " TEXT, "
                + AppDataDbConstants.boneId + " INTEGER, "
                + AppDataDbConstants.cityId + " INTEGER, "
                + AppDataDbConstants.descriptionBody + " TEXT, "
                + AppDataDbConstants.fullDescriptionBody + " TEXT, "
                + AppDataDbConstants.fullPlace + " TEXT, "
                + AppDataDbConstants.hebrewName + " TEXT, "
                + AppDataDbConstants.name + " TEXT, "
                + AppDataDbConstants.nsId + " INTEGER, "
                + AppDataDbConstants.objId + " INTEGER, "
                + AppDataDbConstants.phone + " TEXT, "
                + AppDataDbConstants.price + " INTEGER, "
                + AppDataDbConstants.rating + " REAL, "
                + AppDataDbConstants.type + " TEXT, "
                + AppDataDbConstants.urlString + " TEXT, "
                + AppDataDbConstants.userComments + " TEXT " + ")";


        String CREATE_PLACE_FAVORITE_TABLE = "CREATE TABLE "
                + AppDataDbConstants.PLACE_TABLE_NAME + " ("
                + AppDataDbConstants.address + " TEXT, "
                + AppDataDbConstants.categoryId + " INTEGER, "
                + AppDataDbConstants.descriptionBody + " TEXT, "
                + AppDataDbConstants.index + " TEXT, "
                + AppDataDbConstants.name + " TEXT, "
                + AppDataDbConstants.phone + " TEXT, "
                + AppDataDbConstants.price + " INTEGER, "
                + AppDataDbConstants.rating + " REAL, "
                + AppDataDbConstants.type + " TEXT, "
                + AppDataDbConstants.urlString + " TEXT " + ")";

        try {
            db.execSQL(CREATE_ARTICLE_TABLE);
            db.execSQL(CREATE_CITY_TABLE);
            db.execSQL(CREATE_PLACE_TABLE);
            db.execSQL(CREATE_PLACE_FAVORITE_TABLE);

        } catch (SQLiteException e) {
            Log.e("Create table exception:", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppDataDbConstants.ARTICLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AppDataDbConstants.CITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AppDataDbConstants.PLACE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AppDataDbConstants.PLACE_FAVORITE_TABLE_NAME);

        onCreate(db);
    }



    public void insertArticleTable(HashMap<String, String> queryValues){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AppDataDbConstants.boneId, queryValues.get("nid"));
        values.put(AppDataDbConstants.cityId, queryValues.get("title"));
        values.put(AppDataDbConstants.dictionary, queryValues.get("date"));
        values.put(AppDataDbConstants.imagePath, queryValues.get("country"));
        values.put(AppDataDbConstants.menuItemId, queryValues.get("country"));
        values.put(AppDataDbConstants.name, queryValues.get("country"));
        values.put(AppDataDbConstants.nsId, queryValues.get("country"));
        values.put(AppDataDbConstants.objId, queryValues.get("country"));
        values.put(AppDataDbConstants.rating, queryValues.get("country"));
        values.put(AppDataDbConstants.ratingCount, queryValues.get("country"));
        values.put(AppDataDbConstants.responses, queryValues.get("country"));
        values.put(AppDataDbConstants.title, queryValues.get("country"));
        values.put(AppDataDbConstants.url, queryValues.get("country"));
        values.put(AppDataDbConstants.urlContent, queryValues.get("country"));

        database.insert(AppDataDbConstants.ARTICLE_TABLE_NAME, null, values);

        database.close();

    }


}
