package com.comrax.mouseappandroid.model;

/**
 * Created by betzalel on 30/03/2015.
 */
public class ListModel {
    private  String TitleA="", TitleB="", TitleC="", Address="";
    private  int Price;
    private  float Rating;

    public float getDistance() {
        return Distance;
    }

    public void setDistance(float distance) {
        Distance = distance;
    }

    private float Distance;
    private double Longitude, Latitude;


    public String getTitleA() {
        return TitleA;
    }

    public void setTitleA(String titleA) {
        TitleA = titleA;
    }

    public String getTitleB() {
        return TitleB;
    }

    public void setTitleB(String titleB) {
        TitleB = titleB;
    }

    public String getTitleC() {
        return TitleC;
    }

    public void setTitleC(String titleC) {
        TitleC = titleC;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }


    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}

