package com.comrax.mouseappandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.comrax.mouseappandroid.MyFragment;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.helpers.HelperMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Detail_City_Activity extends MyDrawerLayoutActivity {

    MyPageAdapter pageAdapter;
    public String CITY_FOLDER_NAME;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMainPageArticles();

    }



    private void getMainPageArticles(){
        Intent dataFileIntent = getIntent();
        CITY_FOLDER_NAME = dataFileIntent.getStringExtra("cityFolderName");
        JSONObject jsonData= HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME +"/1146_mainPageArticles.json");

        addPagerData(jsonData);

    }


    private void addPagerData(JSONObject jsonData){
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }





    private List<Fragment> getFragmentsFromJson(JSONObject jsonData){
        List<Fragment> fList = new ArrayList<Fragment>();
        try {
            JSONArray articlesArray = jsonData.getJSONArray("articles");

            //lets add items thru loop
            for (int i=0; i<articlesArray.length(); i++){
                JSONObject item = articlesArray.getJSONObject(i);
                String title = item.getString("name");
                String description = item.getString("description");
                String image = item.getString("image");
                String folderName = CITY_FOLDER_NAME;

                fList.add(MyFragment.newInstance(folderName, title, description, image));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fList;
    }


    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

    }


}
