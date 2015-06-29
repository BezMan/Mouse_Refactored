package com.comrax.mouseappandroid.favorites;

import android.os.*;
import android.util.*;

import java.util.*;

public class Data {
	public static final String TAG = Data.class.getSimpleName();
	
	public static List<Pair<String, List<FavoritesModel>>> getAllData() {
		List<Pair<String, List<FavoritesModel>>> res = new ArrayList<Pair<String, List<FavoritesModel>>>();
		
		for (int i = 0; i < 4; i++) {
			res.add(getOneSection(i));
		}
		
		return res;
	}
	
	public static List<FavoritesModel> getFlattenedData() {
		 List<FavoritesModel> res = new ArrayList<FavoritesModel>();
		 
		 for (int i = 0; i < 4; i++) {
			 res.addAll(getOneSection(i).second);
		 }
		 
		 return res;
	}
	
	public static Pair<Boolean, List<FavoritesModel>> getRows(int page) {
		List<FavoritesModel> flattenedData = getFlattenedData();
		if (page == 1) {
			return new Pair<Boolean, List<FavoritesModel>>(true, flattenedData.subList(0, 5));
		} else {
			SystemClock.sleep(2000); // simulate loading
			return new Pair<Boolean, List<FavoritesModel>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
		}
	}
	
	public static Pair<String, List<FavoritesModel>> getOneSection(int index) {
		String[] titles = {"Renaissance", "Baroque", "Classical", "Romantic"};
		FavoritesModel[][] composerses = {
			{
				new FavoritesModel("Thomas Tallis", "1510-1585"),
				new FavoritesModel("Josquin Des Prez", "1440-1521"),
				new FavoritesModel("Pierre de La Rue", "1460-1518"),
			},
			{
				new FavoritesModel("Johann Sebastian Bach", "1685-1750"),
				new FavoritesModel("George Frideric Handel", "1685-1759"),
				new FavoritesModel("Antonio Vivaldi", "1678-1741"),
				new FavoritesModel("George Philipp Telemann", "1681-1767"),
			},
			{
				new FavoritesModel("Franz Joseph Haydn", "1732-1809"),
				new FavoritesModel("Wolfgang Amadeus Mozart", "1756-1791"),
				new FavoritesModel("Barbara of Portugal", "1711�1758"),
				new FavoritesModel("Frederick the Great", "1712�1786"),
				new FavoritesModel("John Stanley", "1712�1786"),
				new FavoritesModel("Luise Adelgunda Gottsched", "1713�1762"),

			},
			{
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
				new FavoritesModel("Ludwig van Beethoven", "1770-1827"),
				new FavoritesModel("Fernando Sor", "1778-1839"),
				new FavoritesModel("Johann Strauss I", "1804-1849"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("Johann Ludwig Krebs", "1713�1780"),
					new FavoritesModel("Carl Philipp Emanuel Bach", "1714�1788"),
					new FavoritesModel("Christoph Willibald Gluck", "1714�1787"),
					new FavoritesModel("Gottfried August Homilius", "1714�1785"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),
					new FavoritesModel("טקסט ראשי", "טקסט משני"),


			},
		};
		return new Pair<String, List<FavoritesModel>>(titles[index], Arrays.asList(composerses[index]));
	}
}
