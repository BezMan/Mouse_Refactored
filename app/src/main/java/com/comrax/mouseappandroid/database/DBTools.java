package com.comrax.mouseappandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DBTools extends SQLiteOpenHelper {



    public DBTools(Context applicationContext) {
        super(applicationContext, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ARTICLE_TABLE = "CREATE TABLE "
                + DBConstants.ARTICLE_TABLE_NAME + " ("
                + DBConstants.cityId + " TEXT, "
                + DBConstants.boneId + " TEXT, "
                + DBConstants.nsId + " TEXT, "
                + DBConstants.objId + " TEXT, "
                + DBConstants.menuItemId + " TEXT, "
                + DBConstants.rating + " REAL, "
                + DBConstants.ratingCount + " INTEGER, "
                + DBConstants.title + " TEXT, "
                + DBConstants.image + " TEXT, "
                + DBConstants.urlContent + " TEXT, "
                + DBConstants.url + " TEXT " + ");";


        String CREATE_PLACE_TABLE = "CREATE TABLE "
                + DBConstants.PLACE_TABLE_NAME + " ("
                +  DBConstants.id + " integer PRIMARY KEY autoincrement, "
                + DBConstants.centerCoordinateLat + " TEXT, "
                + DBConstants.centerCoordinateLon + " TEXT, "
                + DBConstants.price + " INTEGER, "
                + DBConstants.boneId + " TEXT, "
                + DBConstants.boneCategoryName + " TEXT, "
                + DBConstants.cityId + " TEXT, "
                + DBConstants.nsId + " TEXT, "
                + DBConstants.objId + " TEXT, "
                + DBConstants.address + " TEXT, "
                + DBConstants.description + " TEXT, "
                + DBConstants.fullDescriptionBody + " TEXT, "
                + DBConstants.hebrewName + " TEXT, "
                + DBConstants.name + " TEXT, "
                + DBConstants.rating + " REAL, "
                + DBConstants.ratingCount + " INTEGER, "
                + DBConstants.type + " TEXT, "
                + DBConstants.urlString + " TEXT, "
                + DBConstants.image + " TEXT, "
                + DBConstants.phone + " TEXT, "
                + DBConstants.activityHours + " TEXT, "
                + DBConstants.publicTransportation + " TEXT, "
                + DBConstants.userComments + " TEXT "
                + ");";


        String CREATE_CITY_TABLE = "CREATE TABLE "
                + DBConstants.CITY_TABLE_NAME + " ("
                + DBConstants.centerCoordinateLat + " TEXT, "
                + DBConstants.centerCoordinateLon + " TEXT, "
                + DBConstants.cityFolderPath + " TEXT, "
                + DBConstants.dateUpdated + " TEXT, "
                + DBConstants.cityId + " TEXT, "
                + DBConstants.hebrewName + " TEXT, "
                + DBConstants.name + " TEXT, "
                + DBConstants.stopsArticle + " TEXT, "
                + DBConstants.touristArticlesList + " TEXT "
                + ");";


        String CREATE_FAVORITE_TABLE = "CREATE TABLE "
                + DBConstants.FAVORITE_TABLE_NAME + " ("

                + DBConstants.cityId + " TEXT, "
                + DBConstants.boneId + " TEXT, "
                + DBConstants.boneCategoryName + " TEXT, "
                + DBConstants.objId+ " TEXT, "
                + DBConstants.type + " TEXT, "
                + DBConstants.name + " TEXT, "
                + DBConstants.hebrewName + " TEXT, "
                + DBConstants.description + " TEXT, "
                + DBConstants.address + " TEXT, "
                + DBConstants.phone + " TEXT, "
                + DBConstants.activityHours + " TEXT, "
                + DBConstants.publicTransportation + " TEXT, "
                + DBConstants.responses + " TEXT, "

                + DBConstants.imagePath + " TEXT "
                + ");";

        try {
            db.execSQL(CREATE_CITY_TABLE);
            db.execSQL(CREATE_FAVORITE_TABLE);
            db.execSQL(CREATE_PLACE_TABLE);
            db.execSQL(CREATE_ARTICLE_TABLE);

        } catch (SQLiteException e) {
            Log.e("Create table exception:", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.ARTICLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.CITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.PLACE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.FAVORITE_TABLE_NAME);

        onCreate(db);
    }


    public void insertCityTable(JSONObject item) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(DBConstants.cityId, item.getString(DBConstants.cityId));
            values.put(DBConstants.hebrewName, item.getString(DBConstants.hebrewName));
            values.put(DBConstants.name, item.getString(DBConstants.name));
            values.put(DBConstants.centerCoordinateLat, item.getString(DBConstants.centerCoordinateLat));
            values.put(DBConstants.centerCoordinateLon, item.getString(DBConstants.centerCoordinateLon));
            values.put(DBConstants.stopsArticle, item.getString(DBConstants.stopsArticle));
            values.put(DBConstants.dateUpdated, item.getString(DBConstants.dateUpdated));
            values.put(DBConstants.cityFolderPath, item.getString(DBConstants.cityFolderPath));
            values.put(DBConstants.touristArticlesList, item.getString(DBConstants.touristArticlesList));

            database.insert(DBConstants.CITY_TABLE_NAME, null, values);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        database.close();

    }

    public void insertArticleTable(JSONObject item) {

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
            values.put(DBConstants.image, item.getString(DBConstants.image));
            values.put(DBConstants.rating, item.getString(DBConstants.rating));
            values.put(DBConstants.ratingCount, item.getString(DBConstants.ratingCount));

            values.put(DBConstants.urlContent, item.getString(DBConstants.urlContent));

            database.insert(DBConstants.ARTICLE_TABLE_NAME, null, values);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        database.close();

    }


    public void insertPlaceTable(JSONObject item) {

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

            JSONObject jsonUrlContentFullPlace = item.getJSONObject(DBConstants.urlContent);
            values.put(DBConstants.fullDescriptionBody, jsonUrlContentFullPlace.getString(DBConstants.description));
            values.put(DBConstants.hebrewName, jsonUrlContentFullPlace.getString(DBConstants.hebrewName));
            values.put(DBConstants.price, jsonUrlContentFullPlace.getString(DBConstants.price));
            values.put(DBConstants.image, jsonUrlContentFullPlace.getString(DBConstants.image));
            values.put(DBConstants.activityHours, jsonUrlContentFullPlace.getString(DBConstants.activityHours));
            values.put(DBConstants.publicTransportation, jsonUrlContentFullPlace.getString(DBConstants.publicTransportation));
            values.put(DBConstants.userComments, jsonUrlContentFullPlace.getString(DBConstants.userComments));

            database.insert(DBConstants.PLACE_TABLE_NAME, null, values);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        database.close();

    }


    public void insertFavorite(Cursor cursor) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String sql = "SELECT 1 FROM " + DBConstants.FAVORITE_TABLE_NAME + " WHERE " + DBConstants.name + "='" + cursor.getString(cursor.getColumnIndex(DBConstants.name)) +"' AND "+ DBConstants.cityId +"='"+ cursor.getString(cursor.getColumnIndex(DBConstants.cityId)) + "'";
        Cursor checkCursor = database.rawQuery(sql, null);

        if(checkCursor.getCount() == 0){
            values.put(DBConstants.cityId, cursor.getString(cursor.getColumnIndex(DBConstants.cityId)));
            values.put(DBConstants.boneId, cursor.getString(cursor.getColumnIndex(DBConstants.boneId)));
            values.put(DBConstants.objId, cursor.getString(cursor.getColumnIndex(DBConstants.objId)));
            values.put(DBConstants.boneCategoryName, cursor.getString(cursor.getColumnIndex(DBConstants.boneCategoryName)));
            values.put(DBConstants.type, cursor.getString(cursor.getColumnIndex(DBConstants.type)));
            values.put(DBConstants.name, cursor.getString(cursor.getColumnIndex(DBConstants.name)));
            values.put(DBConstants.hebrewName, cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
            values.put(DBConstants.description, cursor.getString(cursor.getColumnIndex(DBConstants.fullDescriptionBody)));
            values.put(DBConstants.address, cursor.getString(cursor.getColumnIndex(DBConstants.address)));
            values.put(DBConstants.phone, cursor.getString(cursor.getColumnIndex(DBConstants.phone)));
            values.put(DBConstants.activityHours, cursor.getString(cursor.getColumnIndex(DBConstants.activityHours)));
            values.put(DBConstants.publicTransportation, cursor.getString(cursor.getColumnIndex(DBConstants.publicTransportation)));
            values.put(DBConstants.responses, cursor.getString(cursor.getColumnIndex(DBConstants.responses)));
            values.put(DBConstants.image, cursor.getString(cursor.getColumnIndex(DBConstants.image)));

            database.insert(DBConstants.FAVORITE_TABLE_NAME, null, values);
        }

        database.close();
    }




    public boolean isDataAlreadyInDB(String TableName, String colName, String rowValue) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT " + colName + " FROM " + TableName + " WHERE " + colName + "=" + rowValue;
        Cursor cursor = database.rawQuery(sql, null);

        return cursor.getCount() > 0;
    }


    public void addDataToTable(String TableName, JSONObject item, String boneId) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {

            ContentValues newValues = new ContentValues();
            newValues.put(DBConstants.centerCoordinateLat, item.getString(DBConstants.centerCoordinateLat));
            newValues.put(DBConstants.centerCoordinateLon, item.getString(DBConstants.centerCoordinateLon));
            newValues.put(DBConstants.boneCategoryName, item.getString(DBConstants.boneCategoryName));
            String condition = DBConstants.objId + "=" + item.getString(DBConstants.objId) +" AND "+ DBConstants.boneId +"="+ boneId;
            database.update(TableName, newValues, condition, null);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        database.close();

    }


    public String getData(String TableName, String ColumnReturned, String checkColumn, String checkVal) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT " + ColumnReturned + " FROM " + TableName + " WHERE " + checkColumn + "=" + checkVal;
        Cursor cursor = database.rawQuery(sql, null);
        String res = null;
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(ColumnReturned));
        }
        return res;
    }


    public Cursor getData(String TableName, String checkColumn1, String checkVal1) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TableName + " WHERE " + checkColumn1 + "='" + checkVal1 + "'";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor;
    }


    public Cursor getData(String TableName, String checkColumn1, String checkVal1, String checkColumn2, String checkVal2) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TableName + " WHERE " + checkColumn1 + "='" + checkVal1 + "' AND " + checkColumn2 + "='" + checkVal2 + "'" ;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor;
    }



    public Cursor getData(String TableName, String checkColumn1, String checkVal1, String checkColumn2, String checkVal2, String checkColumn3, String checkVal3) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TableName + " WHERE " + checkColumn1 + "='" + checkVal1 +"' AND "+ checkColumn2+ "='" + checkVal2 +"' AND "+ checkColumn3+ "='" + checkVal3 + "'";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        return cursor;
    }





    public Cursor getFavorites(String TableName, String checkColumn1, String checkVal1, String checkColumn2, String checkVal2) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TableName + " WHERE " + checkColumn1 + "=" + checkVal1 +" AND "+ checkColumn2+ "= '" + checkVal2 + "' ";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        return cursor;
    }


    public int deleteRow(String TableName, String KEY_NAME, String VALUE) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TableName, KEY_NAME + "='" + VALUE + "'", null);

    }


    public Cursor fetchItemsByDesc(String inputText) throws SQLException {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor mCursor = database.query(true, DBConstants.PLACE_TABLE_NAME,
                new String[]{DBConstants.id, DBConstants.name, DBConstants.hebrewName},
                DBConstants.name + " like '%" + inputText + "%'" + " OR " + DBConstants.hebrewName + " like '%" + inputText + "%'",
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }



}
