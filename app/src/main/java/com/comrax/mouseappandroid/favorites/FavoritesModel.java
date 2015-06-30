package com.comrax.mouseappandroid.favorites;

import android.database.Cursor;

import com.comrax.mouseappandroid.database.DBConstants;

public class FavoritesModel {
	public static final String TAG = FavoritesModel.class.getSimpleName();
	
	public String name;
	public String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public FavoritesModel(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public FavoritesModel(Cursor cursor) {
		this.name = cursor.getString(cursor.getColumnIndex(DBConstants.name));
		this.type = cursor.getString(cursor.getColumnIndex(DBConstants.type));
	}
}
