package com.comrax.mouseappandroid.favorites;

import android.database.Cursor;

import com.comrax.mouseappandroid.database.DBConstants;

public class FavoritesModel {
	public static final String TAG = FavoritesModel.class.getSimpleName();
	
	public String title;
	public String type;
	
	public FavoritesModel(String title, String type) {
		this.title = title;
		this.type = type;
	}

	public FavoritesModel(Cursor cursor) {
		this.title = cursor.getString(cursor.getColumnIndex(DBConstants.title));
		this.type = cursor.getString(cursor.getColumnIndex(DBConstants.type));
	}
}
