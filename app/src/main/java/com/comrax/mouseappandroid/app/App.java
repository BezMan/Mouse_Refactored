package com.comrax.mouseappandroid.app;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by bez on 12/05/2015.
 */
public class App extends Application {
    private String _cityId;
    private String _boneId;
    private String _objId;
    private String _cityFolderName;
    private String _boneIdTitle;
    private int BoneCategoryName;

    private String CityName;

    private String BoneHotel;
    private String BoneRest;
    private String BoneShop;
    private String BoneTour;

    private static App _instance;

    private App() {

    }

    public static App getInstance() {
        if(_instance == null) {
            _instance = new App();
        }
        return _instance;
    }


    public int getBoneCategoryName() {
        return BoneCategoryName;
    }

    public void setBoneCategoryName(int boneCategoryName) {
        BoneCategoryName = boneCategoryName;
    }

    public String getBoneTour() {
        return BoneTour;
    }

    public void setBoneTour(String boneTour) {
        BoneTour = boneTour;
    }

    public String getBoneHotel() {
        return BoneHotel;
    }

    public void setBoneHotel(String boneHotel) {
        BoneHotel = boneHotel;
    }

    public String getBoneRest() {
        return BoneRest;
    }

    public void setBoneRest(String boneRest) {
        BoneRest = boneRest;
    }

    public String getBoneShop() {
        return BoneShop;
    }

    public void setBoneShop(String boneShop) {
        BoneShop = boneShop;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public void set_cityId(String cityId) {
        _cityId = cityId;
    }

    public String get_cityId() {
        return _cityId;
    }


    public void set_boneId(String boneId) {
        _boneId = boneId;
    }

    public String get_boneId() {
        return _boneId;
    }


    public String get_objId() {
        return _objId;
    }

    public void set_objId(String objId) {
        _objId = objId;
    }


    public String get_cityFolderName() {
        return _cityFolderName;
    }

    public void set_cityFolderName(String cityFolderName) {_cityFolderName = cityFolderName; }


    public String get_boneIdTitle() {
        return _boneIdTitle;
    }

    public void set_boneIdTitle(String boneIdTitle) {
        _boneIdTitle = boneIdTitle;
    }


    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }



}
