package com.comrax.mouseappandroid;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by bez on 12/05/2015.
 */
public class App extends Application {
    private String _cityId;
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


    public void setCityId(String cityId) {
        _cityId = cityId;
    }

    public String getCityId() {
        return _cityId;
    }


}
