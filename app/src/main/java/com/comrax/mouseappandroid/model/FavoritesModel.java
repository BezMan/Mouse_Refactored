package com.comrax.mouseappandroid.model;

import android.database.Cursor;

import com.comrax.mouseappandroid.database.DBConstants;

public class FavoritesModel {

	public String name;
	public String type;



	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}


	public FavoritesModel(Cursor cursor) {
		this.name = cursor.getString(cursor.getColumnIndex(DBConstants.name));
		this.type = cursor.getString(cursor.getColumnIndex(DBConstants.type));
	}
}
