package com.comrax.mouseappandroid.database;

import android.os.Environment;

public class DBConstants {
    public static final String DATABASE_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mouseAppData.db";
    public static final int DATABASE_VERSION = 1;


    public static final String
            ARTICLE_TABLE_NAME = "article",
            CITY_TABLE_NAME = "city",
            PLACE_TABLE_NAME = "place",
            PLACE_FAVORITE_TABLE_NAME = "favorites",

    boneId = "boneId",
            cityId = "cityId",
            imagePath = "image",
            menuItemId = "menuItemId",
            name = "name",
            nsId = "nsId",
            objId = "objId",
            rating = "rating",
            ratingCount = "ratingCount",
            responses = "responses",
            title = "title",
            url = "url",
            content = "content",
            urlContent = "urlContent",
            centerCoordinateLat = "latitude",
            centerCoordinateLon = "longitude",
            cityFolderPath = "cityFolderPath",
            coordinates = "coordinates",
            placesCoordinatesList = "placesCoordinatesList",
            dateUpdated = "dateUpdated",
            image = "image",
            imageName = "imageName",
            index = "myIndex",
            mainArticles = "mainArticles",
            menu = "menu",
            serviceMenu = "serviceMenu",
            stopsArticle = "stopsArticle",
            touristArticlesList = "touristArticlesList",
            address = "address",
            description = "description",
            fullDescriptionBody = "fullDescriptionBody",
            fullPlace = "urlContent",
            hebrewName = "hebrewName",
            type = "type",
            urlString = "url",
            userComments = "responses",
            categoryId = "categoryId",
            phone = "phone",
            price = "priceDegree";


}
