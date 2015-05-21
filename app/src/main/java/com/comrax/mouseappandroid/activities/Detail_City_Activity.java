package com.comrax.mouseappandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.DetailsListAdapter;
import com.comrax.mouseappandroid.fragments.MyFragment;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.DetailsListModel;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Detail_City_Activity extends MyDrawerLayoutActivity {

    MyPageAdapter pageAdapter;
    DetailsListAdapter detailsListAdapter;

    public String CITY_FOLDER_NAME, strNum;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMainPageArticles();

    }


    private void getMainPageArticles() {
        Intent dataFileIntent = getIntent();
        CITY_FOLDER_NAME = dataFileIntent.getStringExtra("cityFolderName");
        strNum = CITY_FOLDER_NAME.substring(CITY_FOLDER_NAME.length() - 4, CITY_FOLDER_NAME.length());
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/"+ strNum +"_mainPageArticles.json");

        addPagerData(jsonData);

        setMainList();

    }


    private void setMainList() {
        ArrayList<DetailsListModel> myDetailsArray = new ArrayList<DetailsListModel>();
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/"+ strNum+"_menu.json");
        try {
            JSONArray articlesArray = jsonData.getJSONArray("menu");

            for (int i = 0, j = 0; i < GlobalVars.detailsListTitles.length; i++) {

                final DetailsListModel listItem = new DetailsListModel();

                if (i < 1 || i > 5) {
                    listItem.setBtnTitle(GlobalVars.detailsListTitles[i]);
                    listItem.setBtnImage(GlobalVars.detailsListImages[i]);
                } else {  //get From Json data//
                    JSONObject item = articlesArray.getJSONObject(j++);
                    listItem.setBtnTitle(item.getString("name"));
                    listItem.setBtnImage("/sdcard/Mouse_App/" + item.getString("icon"));

                }
                myDetailsArray.add(listItem);
            }
            detailsListAdapter = new DetailsListAdapter(this, myDetailsArray, getResources());
            ListView listView = (ListView) findViewById(R.id.details_list);
            listView.setAdapter(detailsListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }


    private List<Fragment> getFragmentsFromJson(JSONObject jsonData) {
        List<Fragment> fList = new ArrayList<Fragment>();
        try {
            JSONArray articlesArray = jsonData.getJSONArray("articles");

            //lets add items thru loop
            for (int i = 0; i < articlesArray.length(); i++) {
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
