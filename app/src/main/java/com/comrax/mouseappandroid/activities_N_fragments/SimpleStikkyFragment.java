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

import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.CustomAdapter;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.app.HelperMethods;
import com.comrax.mouseappandroid.model.ListModel;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class SimpleStikkyFragment extends MyBaseFragment {

    private ListView mListView;
    TextView resultsTxtView;
    //public SimpleFragmentDelegate delegate;

//    LocationManager mLocationManager;

    double lon, lat;

    CustomAdapter adapter;
    public ArrayList<ListModel> customListViewValuesArr;

    private RadioGroup radioGroup;

    ViewPager pager;
    MyPageAdapter pageAdapter;

//    public static String BASE_FOLDER = "/sdcard/Mouse_App/";

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

        customListViewValuesArr = new ArrayList<>();

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

        String data = App.getInstance().get_cityFolderName() + "/"+ App.getInstance().get_cityId() + "_"+ App.getInstance().get_boneId()+ "_ArticalsList.json";
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(data);

        addPagerData(jsonData);




//        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000,
//                100, mLocationListener);

    }



    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments);

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
            for (int i = 0; i < 5; i++) {   //we want only 5 first articles//
                JSONObject item = articlesArray.getJSONObject(i);

                String title = item.getString("title");
                String boneId = item.getString("boneId");
                String nsId = item.getString("nsId");
                String url = item.getString("url");
                String image = item.getString("image");

                JSONObject urlContent = item.getJSONObject("urlContent");


                String folderName = App.getInstance().get_cityFolderName();

                fList.add(MyFragment.newInstance(folderName, title, "", image));
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
                .minHeightHeader(180)
                .build();


        populateListView();

        adapter = new CustomAdapter(getActivity(), customListViewValuesArr, getResources());
        mListView.setAdapter(adapter);


    }


    private void screenListView(int price) {

        int index = mListView.getFirstVisiblePosition();
        View v = mListView.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - mListView.getPaddingTop());

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

        mListView.setSelectionFromTop(index, top);

    }


    private void populateListView() {

        String cityId = App.getInstance().get_cityId();
        String boneId = App.getInstance().get_boneId();
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
