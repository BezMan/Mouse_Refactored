package com.comrax.mouseappandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.DetailsListAdapter;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
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
    public String CITY_FOLDER_NAME, CITY_UPDATE_DATE, cityId;
    DBTools dbTools = new DBTools(this);
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDetailsListItems();
        setTitle();

    }

    private void setTitle() {
        String title = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.hebrewName, DBConstants.cityId, cityId);
        TextView titleTextView = (TextView)findViewById(R.id.title_text);
        titleTextView.setText(title);


//        getSupportActionBar().setTitle(title);
    }


    private void setDetailsListItems() {
        Intent dataFileIntent = getIntent();
        CITY_FOLDER_NAME = dataFileIntent.getStringExtra("cityFolderName");
        CITY_UPDATE_DATE = dataFileIntent.getStringExtra("cityUpdateDate");
        cityId = CITY_FOLDER_NAME.substring(CITY_FOLDER_NAME.length() - 4, CITY_FOLDER_NAME.length());
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_mainPageArticles.json");

        addPagerData(jsonData);

        setListItems();


    }



    private void setListItems() {
        ArrayList<DetailsListModel> myDetailsArray = new ArrayList<>();
        JSONObject jsonMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_menu.json");
        JSONObject jsonServiceMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_serviceMenu.json");
        int i=0, j=0, k=0, m=0;
        try {
            JSONArray menuArray = jsonMenuData.getJSONArray("menu");
            JSONArray serviceMenuArray = jsonServiceMenuData.getJSONArray("serviceMenu");

            int totalLength = menuArray.length()+serviceMenuArray.length();
            for (; i < totalLength+6; i++) {

                final DetailsListModel listItem = new DetailsListModel();

                if (i == 0 || i==menuArray.length()+1 || i >= totalLength+2) {
                    listItem.setBtnTitle(GlobalVars.detailsListTitles[k]);
                    listItem.setBtnImage(GlobalVars.detailsListImages[k]);
                    k++;
                } else {  //get From Json data//
                    if(i <= menuArray.length()) {
                        JSONObject menuItem = menuArray.getJSONObject(j++);
                        listItem.setBtnTitle(menuItem.getString("name"));
                        listItem.setBtnImage("/sdcard/Mouse_App/" + menuItem.getString("icon"));
                    }
                    else{ //we are up to the serviceMenu//
                        JSONObject serviceMenuItem = serviceMenuArray.getJSONObject(m++);
                        listItem.setBtnTitle(serviceMenuItem.getString("name"));
                    }
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
        List<Fragment> fList = new ArrayList<>();
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
