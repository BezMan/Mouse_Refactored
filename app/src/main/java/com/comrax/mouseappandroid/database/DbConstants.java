package com.comrax.mouseappandroid.database;

import android.os.Environment;

public class DBConstants {
    public static final String DATABASE_NAME = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mouseAppData.db";
    public static final int DATABASE_VERSION = 1;


    public static final String
            ARTICLE_TABLE_NAME = "article",
            CITY_TABLE_NAME = "city",
            PLACE_TABLE_NAME = "place",
            PLACE_FAVORITE_TABLE_NAME = "favorites",

                boneId = "boneId",
                cityId = "cityId",
                imagePath = "imagePath",
                menuItemId = "menuItemId",
                name = "name",
                nsId = "nsId",
                objId = "objId",
                rating = "rating",
                ratingCount = "ratingCount",
                responses = "responses",
                title = "title",
                url = "url",
                urlContent = "urlContent",
                centerCoordinateLat = "centerCoordinateLat",
                centerCoordinateLon = "centerCoordinateLon",
                cityFolderPath = "cityFolderPath",
                coordinates = "coordinates",
                dateUpdated = "dateUpdated",
                image = "image",
                imageName = "imageName",
                index = "myIndex",
                mainArticles = "mainArticles",
                menu = "menu",
                serviceMenu = "serviceMenu",
                stopsArticle = "stopsArticle",
                touristArticles = "touristArticles",
                address = "address",
                description = "description",
                fullDescriptionBody = "fullDescriptionBody",
                fullPlace = "urlContent",
                hebrewName = "hebrewName",
                type = "type",
                urlString = "urlString",
                userComments = "responses",
                categoryId = "categoryId",
                phone = "phone",
                price = "areaRange";



}
