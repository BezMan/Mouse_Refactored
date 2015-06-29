package com.comrax.mouseappandroid.favorites;

public class FavoritesModel {
	public static final String TAG = FavoritesModel.class.getSimpleName();
	
	public String name;
	public String year;
	
	public FavoritesModel(String name, String year) {
		this.name = name;
		this.year = year;
	}
}
