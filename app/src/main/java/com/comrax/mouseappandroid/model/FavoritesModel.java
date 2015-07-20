package com.comrax.mouseappandroid.model;

import android.database.Cursor;

import com.comrax.mouseappandroid.database.DBConstants;

public class FavoritesModel {
//	public static final String TAG = FavoritesModel.class.getSimpleName();

//	public String objId;
	public String name;
	public String type;



//	public String getObjId() {
//		return objId;
//	}


	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}


	public FavoritesModel(Cursor cursor) {
		this.name = cursor.getString(cursor.getColumnIndex(DBConstants.name));
		this.type = cursor.getString(cursor.getColumnIndex(DBConstants.type));
//		this.objId = cursor.getString(cursor.getColumnIndex(DBConstants.objId));
	}
}
