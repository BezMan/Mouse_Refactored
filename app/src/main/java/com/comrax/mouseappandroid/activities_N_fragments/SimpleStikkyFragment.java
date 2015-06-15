package com.comrax.mouseappandroid.activities_N_fragments;


import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.App;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CustomAdapter;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.ListModel;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class SimpleStikkyFragment extends Fragment {

    private ListView mListView;
    TextView resultsTxtView;

//    LocationManager mLocationManager;

    double lon, lat;

    CustomAdapter adapter;
    public ArrayList<ListModel> customListViewValuesArr = new ArrayList<>();

    private RadioGroup radioGroup;

    ViewPager pager;
    MyPageAdapter pageAdapter;

    public static String CITY_FOLDER_NAME = "/sdcard/Mouse_App/London_master_1146";

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final Location location) {
//            lat = location.getLatitude();
//            lon = location.getLongitude();
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };


    public SimpleStikkyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simplelistview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.myRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.btn1) {
                    screenListView(0);
                } else if (checkedId == R.id.btn2) {
                    screenListView(3);
                } else if (checkedId == R.id.btn3) {
                    screenListView(2);
                } else {
                    screenListView(1);
                }
            }

        });

        resultsTxtView = (TextView) getActivity().findViewById(R.id.txtResultsCount);

        mListView = (ListView) getActivity().findViewById(R.id.listview);

        pager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + "1146" + "_mainPageArticles.json");

        addPagerData(jsonData);




//        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000,
//                100, mLocationListener);

    }



    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getFragmentManager(), fragments);

        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        CirclePageIndicator indicator = (CirclePageIndicator)getActivity().findViewById(R.id.titles);
        indicator.setViewPager(pager);
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


    class MyPageAdapter extends FragmentPagerAdapter implements View.OnClickListener{
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

        @Override
        public void onClick(View v) {

        }
    }






    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeader(160)
                .build();


        populateListView();

        adapter = new CustomAdapter(getActivity(), customListViewValuesArr, getResources());
        mListView.setAdapter(adapter);


    }


    private void screenListView(int price) {

        if (price == 0) {

            adapter = new CustomAdapter(getActivity(), customListViewValuesArr, getResources());
            mListView.setAdapter(adapter);

        } else {

            ArrayList<ListModel> screenedListViewValuesArr = new ArrayList<>();

            for (int i = 0; i < customListViewValuesArr.size(); i++) {
                if (customListViewValuesArr.get(i).getPrice() == price) {
                    screenedListViewValuesArr.add(customListViewValuesArr.get(i));
                }
            }
            adapter = new CustomAdapter(getActivity(), screenedListViewValuesArr, getResources());
            mListView.setAdapter(adapter);
        }
    }


    private void populateListView() {

        String cityId = App.getInstance().getCityId();
        String boneId = App.getInstance().getBoneId();
        Cursor cursor = new DBTools(getActivity()).getCurrentCityPlacesTable(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, cityId, DBConstants.boneId, boneId);

        resultsTxtView.setText("התקבלו " + cursor.getCount() + " תוצאות");

        for (int i = 0; i < cursor.getCount(); cursor.moveToNext(), i++) {
            ListModel lm = new ListModel();

            lm.setTitleA(cursor.getString(cursor.getColumnIndex(DBConstants.name)));
            lm.setTitleB(cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
            lm.setTitleC(cursor.getString(cursor.getColumnIndex(DBConstants.type)));


            lm.setPrice(cursor.getInt(cursor.getColumnIndex(DBConstants.price)));
            lm.setAddress(cursor.getString(cursor.getColumnIndex(DBConstants.address)));

            lm.setRating(cursor.getFloat(cursor.getColumnIndex(DBConstants.rating)));

            lm.setObjId(cursor.getString(cursor.getColumnIndex(DBConstants.objId)));
//                lm.setLatitude();
//                lm.setLongitude();


// The computed distance is stored in results[0].
//If results has length 2 or greater, the initial bearing is stored in results[1].
//If results has length 3 or greater, the final bearing is stored in results[2].

            float[] results = new float[1];

            Location.distanceBetween(lat, lon,
                    cursor.getDouble(cursor.getColumnIndex(DBConstants.centerCoordinateLat)), cursor.getDouble(cursor.getColumnIndex(DBConstants.centerCoordinateLon)), results);

            lm.setDistance(results[0]);
            //my lon and lat are 0//
            /////////

            customListViewValuesArr.add(lm);

        }


    }

}
