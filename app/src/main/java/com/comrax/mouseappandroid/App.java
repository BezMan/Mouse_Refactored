package com.comrax.mouseappandroid;

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

    private String AppBarTitle;

    private boolean MyFragVal=false;
    private boolean InFragActivity=false;

    private static App _instance;

    private App() {

    }

    public static App getInstance() {
        if(_instance == null) {
            _instance = new App();
        }
        return _instance;
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

    public boolean isInStaticPage() {
        return MyFragVal;
    }

    public void setInStaticPage(boolean myFragVal) {
        MyFragVal = myFragVal;
    }


    public String getAppBarTitle() {
        return AppBarTitle;
    }

    public void setAppBarTitle(String appBarTitle) {
        AppBarTitle = appBarTitle;
    }



}
