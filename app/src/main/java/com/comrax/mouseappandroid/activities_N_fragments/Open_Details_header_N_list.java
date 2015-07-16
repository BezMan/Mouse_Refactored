package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.adapters.OpenDetailsCustomAdapter;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.app.HelperMethods;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.model.ListModel;
import com.comrax.mouseappandroid.model.MapMarkerModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list extends MyBaseDrawerActivity {

    TextView boneText;
    Cursor cursor;
    DBTools dbTools = new DBTools(this);
    private SlidingLayer mSlidingLayer;
    private GoogleMap map;
    private ArrayList<MapMarkerModel> markerArray;
    String[] icon = {"hotel", "shop", "rest", "tour"};
    List<Marker> markers = new ArrayList<>();
    Marker currentMarker;
    String prevIcon;
    private ListView mListView;
    TextView resultsTxtView;

    double lon, lat;

    OpenDetailsCustomAdapter adapter;
    public ArrayList<ListModel> customListViewValuesArr;

    private RadioGroup radioGroup;

    ViewPager pager;
    MyPageAdapter pageAdapter;

    Intent placeActivity;

    int pos;
    boolean setMap;

    public void mapButtonClicked(View v) {
        if (mSlidingLayer.isOpened()) {
            mSlidingLayer.closeLayer(true);
        } else {
            mSlidingLayer.openLayer(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();

        setBoneTitleAndColor();

        setupInitCityMap();

        customListViewValuesArr = new ArrayList<>();

        setRadioGroupFilter();

        String data = myInstance.get_cityFolderName() + "/" + myInstance.get_cityId() + "_" + myInstance.get_boneId() + "_ArticalsList.json";
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(data);

        addPagerData(jsonData);

        setStikkyHeader();

        populateListView();

        adapter = new OpenDetailsCustomAdapter(this, customListViewValuesArr, getResources());
        mListView.setAdapter(adapter);


    }


    private void setStikkyHeader() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, frameLayout)
                .minHeightHeader(160)
                .build();
    }


    private void setRadioGroupFilter() {
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
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


    }


    private void initVars() {
        resultsTxtView = (TextView) findViewById(R.id.txtResultsCount);
        mListView = (ListView) findViewById(R.id.listview);
        pager = (ViewPager) findViewById(R.id.viewpager);
        placeActivity = new Intent(this, PlaceActivity.class);

    }


    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.titles);
        indicator.setViewPager(pager);
    }


    private List<Fragment> getFragmentsFromJson(JSONObject jsonData) {
        List<Fragment> fList = new ArrayList<>();
        try {
            JSONArray articlesArray = jsonData.getJSONArray("articles");

            //lets add items thru loop
            for (int i = 0; i < 5; i++) {   //we want only 5 first arurlContent = item.getJSONObject("urlContent").toString();tem.getString("image");
                JSONObject item = articlesArray.getJSONObject(i);
                String urlContent = item.getJSONObject("urlContent").toString();
                String image = item.getString("image").toString();

                fList.add(MyPagerArticleFragment.newInstance(urlContent, image));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fList;
    }




    private void screenListView(int price) {

        int index = mListView.getFirstVisiblePosition();
        View v = mListView.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - mListView.getPaddingTop());

        if (price == 0) {

            adapter = new OpenDetailsCustomAdapter(this, customListViewValuesArr, getResources());
            mListView.setAdapter(adapter);

        } else {

            ArrayList<ListModel> screenedListViewValuesArr = new ArrayList<>();

            for (int i = 0; i < customListViewValuesArr.size(); i++) {
                if (customListViewValuesArr.get(i).getPrice() == price) {
                    screenedListViewValuesArr.add(customListViewValuesArr.get(i));
                }
            }
            adapter = new OpenDetailsCustomAdapter(this, screenedListViewValuesArr, getResources());
            mListView.setAdapter(adapter);
        }

        mListView.setSelectionFromTop(index, top);

    }


    private void populateListView() {

        String cityId = myInstance.get_cityId();
        String boneId = myInstance.get_boneId();
        Cursor cursor = new DBTools(this).getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, cityId, DBConstants.boneId, boneId);

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
            lm.setNsId(cursor.getString(cursor.getColumnIndex(DBConstants.nsId)));


            float[] results = new float[1];

            Location.distanceBetween(lat, lon,
                    cursor.getDouble(cursor.getColumnIndex(DBConstants.centerCoordinateLat)), cursor.getDouble(cursor.getColumnIndex(DBConstants.centerCoordinateLon)), results);

            lm.setDistance(results[0]);
            //my lon and lat are 0//
            /////////

            customListViewValuesArr.add(lm);

        }


    }


    private void setBoneTitleAndColor() {
        boneText = (TextView) findViewById(R.id.bone_title);
        String boneTitle = myInstance.get_boneIdTitle();
        boneText.setText(boneTitle);

        int pos = myInstance.getBonePosition();
        boneText.setBackgroundColor(GlobalVars.boneColors[pos]);
    }


    private void setupInitCityMap() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Move the camera instantly to current city with a zoom of 10.
        String cityLat = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.centerCoordinateLat, DBConstants.cityId, myInstance.get_cityId());
        String cityLon = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.centerCoordinateLon, DBConstants.cityId, myInstance.get_cityId());
        LatLng zoomCamera = new LatLng(Double.parseDouble(cityLat), Double.parseDouble(cityLon));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomCamera, 10));


        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);

        mSlidingLayer.setOnInteractListener(new SlidingLayer.OnInteractListener() {
            @Override
            public void onOpen() {
            }

            @Override
            public void onShowPreview() {
            }

            @Override
            public void onClose() {
                mSlidingLayer.setSlidingEnabled(true);
            }

            @Override
            public void onOpened() {
                if (!setMap) {
                    setupMapData(dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId()));
                    setMap = true;
                }
                mSlidingLayer.setSlidingEnabled(false);
            }

            @Override
            public void onPreviewShowed() {
            }

            @Override
            public void onClosed() {
            }
        });
    }


    private void setupMapData(Cursor cursor) {
        markerArray = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); cursor.moveToNext(), i++) {
            final MapMarkerModel mapItem = new MapMarkerModel();
            mapItem.setBoneId(cursor.getString(cursor.getColumnIndex(DBConstants.boneId)));
            mapItem.setPlaceName(cursor.getString(cursor.getColumnIndex(DBConstants.name)));

            String itemLatitude = cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLat));
            String itemLongitude = cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLon));

            if (!itemLatitude.equals("")) {//latitude not empty val//

                // inside your loop:
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(itemLatitude), Double.parseDouble(itemLongitude)))
                        .title(mapItem.getPlaceName()));

                String currentIcon = getIconByBoneId(mapItem.getBoneId());

                marker.setIcon((BitmapDescriptorFactory
                        .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + currentIcon + "_blank", null, null))));

                markers.add(marker);
                markerArray.add(mapItem);
            }

        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                int currIndex = markers.indexOf(marker);
                String currBoneId = markerArray.get(currIndex).getBoneId();

                String currentIcon = getIconByBoneId(currBoneId);

                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory
                            .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + prevIcon + "_blank", null, null)));
                }
                currentMarker = marker;
                prevIcon = currentIcon;
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + currentIcon, null, null)));

                return false;
            }
        });


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                // Getting view from the layout file map_info_window
                View v = getLayoutInflater().inflate(R.layout.map_info_window, null);

                // Setting the title
                TextView infoWindow = (TextView) v.findViewById(R.id.tv_place_name);
                infoWindow.setText(marker.getTitle() + "   ");

                // Returning the view containing InfoWindow contents
                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });


        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Cursor cursor = dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.name, marker.getTitle(), DBConstants.cityId, myInstance.get_cityId());

                Bundle bundle = new Bundle();
                bundle.putString(DBConstants.name, cursor.getString(cursor.getColumnIndex(DBConstants.name)));
                bundle.putString(DBConstants.objId, cursor.getString(cursor.getColumnIndex(DBConstants.objId)));
                myInstance.set_boneIdTitle(cursor.getString(cursor.getColumnIndex(DBConstants.boneCategoryName)));
                myInstance.setBonePosition(pos);

                Log.wtf("pos:", " " + pos);

                placeActivity.putExtras(bundle);
                startActivity(placeActivity);


                closeSlidingMapPanel();
            }
        });
    }

    private void closeSlidingMapPanel() {
        if (mSlidingLayer.isOpened()) {
            mSlidingLayer.closeLayer(true);
        }
    }


    private String getIconByBoneId(String boneId) {
        if (boneId.equals(myInstance.getBoneHotel())) {
            pos = 0;
        } else if (boneId.equals(myInstance.getBoneShop())) {
            pos = 1;
        } else if (boneId.equals(myInstance.getBoneRest())) {
            pos = 2;
        } else {
            pos = 3;
        }
        return icon[pos];
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mSlidingLayer.isOpened()) {
                    mSlidingLayer.closeLayer(true);
                    return true;
                }

            default:
                return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;

    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    public void onPlaceItemClick() {
//        Log.wtf("item id's: ", "city: " + myInstance.get_cityId() +
//                        ", bone: " + myInstance.get_boneId() +
//                        ", obj: " + myInstance.get_objId() +
//                        ", nsId: " + myInstance.get_nsId()
//                );

        cursor = new DBTools(this).getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId(), DBConstants.boneId, myInstance.get_boneId(), DBConstants.objId, myInstance.get_objId());

        Bundle bundle = new Bundle();
        bundle.putString(DBConstants.name, cursor.getString(cursor.getColumnIndex(DBConstants.name)));
        bundle.putString(DBConstants.objId, cursor.getString(cursor.getColumnIndex(DBConstants.objId)));

        placeActivity.putExtras(bundle);
        startActivity(placeActivity);
    }



    public void onNavigationClick() {

        //tel aviv test coord:
//        String latitude = "32.111767";
//        String longitude = "34.801361";

        cursor = new DBTools(this).getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId(), DBConstants.boneId, myInstance.get_boneId(), DBConstants.objId, myInstance.get_objId());

        String latitude = cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLat));
        String longitude = cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLon));

        try {
            String url = "geo:" + latitude + "," + longitude;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        }

    }


}




