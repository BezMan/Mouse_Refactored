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
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.fragments.MyFragment;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.DetailsListModel;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

    }


    private void setDetailsListItems() {
        Intent dataFileIntent = getIntent();
        CITY_FOLDER_NAME = dataFileIntent.getStringExtra("cityFolderName");
        CITY_UPDATE_DATE = dataFileIntent.getStringExtra("cityUpdateDate");
        cityId = CITY_FOLDER_NAME.substring(CITY_FOLDER_NAME.length() - 4, CITY_FOLDER_NAME.length());
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_mainPageArticles.json");

        addPagerData(jsonData);

        setListItems();

        if (!dbTools.isDataAlreadyInDB(DBConstants.ARTICLE_TABLE_NAME, "cityId", cityId)) {
            setDBdata();
        }

    }

    private void setDBdata() {

        JSONObject cityObject = new JSONObject();
        File dir = new File(CITY_FOLDER_NAME);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            try {
                for (File child : directoryListing) {

                    // loop thru json files in city directory//

                    if (child.toString().contains("CityCoordinates")) {
                        JSONObject jsonCityCoordinates = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONObject fullObject = jsonCityCoordinates.getJSONObject("cityCoordinates");
                        cityObject.put(DBConstants.cityId, cityId);
                        cityObject.put(DBConstants.hebrewName, fullObject.getString("name"));
                        cityObject.put(DBConstants.name, fullObject.getString("EnglishName"));
                        cityObject.put(DBConstants.centerCoordinateLat, fullObject.getString("latitude"));
                        cityObject.put(DBConstants.centerCoordinateLon, fullObject.getString("longitude"));
                    }
                    else if (child.toString().contains("StopsArticle")) {
                        JSONObject jsonStopsArticle = HelperMethods.loadJsonDataFromFile(child.toString());
                        cityObject.put(DBConstants.stopsArticle, jsonStopsArticle.toString());
                    }

                    else if (child.toString().contains("TuristArticalsList")) {
                        JSONObject jsonTuristArticalsList = HelperMethods.loadJsonDataFromFile(child.toString());
                        cityObject.put(DBConstants.touristArticlesList, jsonTuristArticalsList.toString());
                    }

                    else if (child.toString().contains("PlacesCoordinatesList")) {
                        JSONObject jsonPlacesCoordinatesList = HelperMethods.loadJsonDataFromFile(child.toString());
                        cityObject.put(DBConstants.placesCoordinatesList, jsonPlacesCoordinatesList.toString());
                    }

                    else if (child.toString().contains("PlacesList")) {
                        JSONObject jsonPlace = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonPlace.getJSONArray("places");
                        // looping through All nodes if json file:
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            item.put(DBConstants.cityId, cityId);
                            dbTools.insertPlaceTable(item);
                        }
                    } else if (child.toString().contains("ArticalsList")) {

                        JSONObject jsonArticle = HelperMethods.loadJsonDataFromFile(child.toString());
                        JSONArray data = jsonArticle.getJSONArray("articles");
                        // looping through All nodes if json file:
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            item.put(DBConstants.cityId, cityId);
                            dbTools.insertArticleTable(item);
                        }
                    }


                    }

                //add extra important data//
                cityObject.put(DBConstants.dateUpdated, CITY_UPDATE_DATE);
                cityObject.put(DBConstants.cityFolderPath, CITY_FOLDER_NAME);

                dbTools.insertCityTable(cityObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void setListItems() {
        ArrayList<DetailsListModel> myDetailsArray = new ArrayList<>();
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_menu.json");
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
