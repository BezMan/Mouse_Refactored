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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Detail_City_Activity extends MyDrawerLayoutActivity {

    MyPageAdapter pageAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPagerData();
        getMainPageArticles();

    }


    private void addPagerData(){
        List<Fragment> fragments = getFragments();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }


    private void getMainPageArticles(){
        Intent dataFileIntent = getIntent();
        String fileData = dataFileIntent.getStringExtra("cityFolderName");
        JSONObject jsonData= HelperMethods.loadJsonDataFromFile(fileData+"/1146_mainPageArticles.json");

    }



    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        //lets add items thru loop
        fList.add(MyFragment.newInstance("Fragment 1"));
        fList.add(MyFragment.newInstance("Fragment 2"));
        fList.add(MyFragment.newInstance("Fragment 3"));
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
