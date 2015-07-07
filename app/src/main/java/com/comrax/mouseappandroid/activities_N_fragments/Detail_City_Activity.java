package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.app.HelperMethods;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.helpers.AnimatedExpandableListView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Detail_City_Activity extends MyBaseDrawerActivity {

    MyPageAdapter pageAdapter;
    public String CITY_FOLDER_PATH, CITY_UPDATE_DATE, cityId;
    int infoItemPosition;
    DBTools dbTools = new DBTools(this);

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    ViewPager pager;
    View pagerLayout;

    List<GroupItem> items;


    private SlidingLayer mSlidingLayer;

    private App myInstance = App.getInstance();

    private GoogleMap map;

    private ArrayList<MapMarkerModel> markerArray;

    String[] icon = {"hotel","rest","shop","tour"};
    private int j;

    // before loop:
    List<Marker> markers = new ArrayList<>();

    Marker currentMarker;
    String myMarkerId;

    public void buttonClicked(View v) {
        if (mSlidingLayer.isOpened()) {
            mSlidingLayer.closeLayer(true);
        }else{
            mSlidingLayer.openLayer(true);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected String getTextForAppBar() {
        return  myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

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
                mSlidingLayer.setSlidingEnabled(false);
            }

            @Override
            public void onPreviewShowed() {
            }

            @Override
            public void onClosed() {
            }
        });

        GlobalVars.detailMenuItems = new ArrayList<>();
//        GlobalVars.detailMenuItems.add("כתבות");
        listView = (AnimatedExpandableListView) findViewById(R.id.details_list);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        pagerLayout = layoutInflater.inflate(R.layout.view_pager, null);
        pager = (ViewPager) pagerLayout.findViewById(R.id.viewpager);
        listView.addHeaderView(pagerLayout);


        setDetailsListItems();

        myInstance.setCityName(dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.hebrewName, DBConstants.cityId, cityId));

        listView.setAdapter(adapter);


        setupMapData(dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId()));



    }

    private void setupMapData(Cursor cursor) {

        String lat = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.centerCoordinateLat, DBConstants.cityId, cityId);
        String lon = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.centerCoordinateLon, DBConstants.cityId, cityId);
        LatLng zoomCamera = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomCamera, 10));

        markerArray = new ArrayList<>();

        for (int i=0; i<cursor.getCount(); cursor.moveToNext(), i++) {
            final MapMarkerModel mapItem = new MapMarkerModel();
            mapItem.setBoneId(cursor.getString(cursor.getColumnIndex(DBConstants.boneId)));
            mapItem.setPlaceName(cursor.getString(cursor.getColumnIndex(DBConstants.name)));
            mapItem.setLatitude(cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLat)));
            mapItem.setLongitude(cursor.getString(cursor.getColumnIndex(DBConstants.centerCoordinateLon)));

            if (!mapItem.getLatitude().equals("")) {

                // inside your loop:
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(mapItem.getLatitude()), Double.parseDouble(mapItem.getLongitude())))
                        .title(mapItem.getPlaceName()));

                if (mapItem.getBoneId().equals(myInstance.getBoneHotel())) j = 0;
                else if (mapItem.getBoneId().equals(myInstance.getBoneRest())) j = 1;
                else if (mapItem.getBoneId().equals(myInstance.getBoneShop())) j = 2;
                else j = 3;

                marker.setIcon((BitmapDescriptorFactory
                        .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + icon[j] + "_blank", null, null))));

                markers.add(marker);
                markerArray.add(mapItem);

            }

            else{
                Toast.makeText(getApplicationContext(), ""+mapItem.getLatitude(), Toast.LENGTH_LONG).show();
            }
        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                int currIndex = markers.indexOf(marker);
                String currBoneId = markerArray.get(currIndex).getBoneId();
                String currName = markerArray.get(currIndex).getPlaceName();


                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory
                            .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + icon[j] + "_blank", null, null)));
                }
                currentMarker = marker;
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(getResources().getIdentifier("com.comrax.mouseappandroid:drawable/" + "pin_" + icon[j], null, null)));

                return false;
            }
        });


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Getting view from the layout file map_info_window
                View v = getLayoutInflater().inflate(R.layout.map_info_window, null);

                // Getting reference to the TextView
                TextView infoWindow = (TextView) v.findViewById(R.id.tv_place_name);

                // Setting the title
                infoWindow.setText(marker.getTitle());

                // Returning the view containing InfoWindow contents
                return v;

            }
        });


        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
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






    private void setExpandableList() {

        items = new ArrayList<GroupItem>();

        JSONObject jsonMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_PATH + "/" + cityId + "_menu.json");
        JSONObject jsonServiceMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_PATH + "/" + cityId + "_serviceMenu.json");
        int i = 0, j = 0, k = 0, m = 0;
        try {
            JSONArray menuArray = jsonMenuData.getJSONArray("menu");
            JSONArray serviceMenuArray = jsonServiceMenuData.getJSONArray("serviceMenu");
            infoItemPosition = menuArray.length() + 1;

            int totalLength = 1 + infoItemPosition + 4;
            for (; i < totalLength; i++) {

                GroupItem listItem = new GroupItem();

                if (i == 0 || i >= infoItemPosition) {
                    listItem.title = (GlobalVars.detailsListTitles[k]);
                    listItem.imagePath = (GlobalVars.detailsListImages[k]);
                    k++;

                    if (i == infoItemPosition) {

                        for (; j < serviceMenuArray.length(); j++) {
                            JSONObject serviceMenuItem = serviceMenuArray.getJSONObject(j);
                            ChildItem child = new ChildItem();
                            child.title = (serviceMenuItem.getString("name"));

                            listItem.items.add(child);
                        }
                    }
                }
                else {  //get From Json data//
                    JSONObject menuItem = menuArray.getJSONObject(m);
                    String boneId = menuItem.getString("boneId");
                    switch (m){
                        case 0:
                            myInstance.setBoneHotel(boneId);
                            break;
                        case 1:
                            myInstance.setBoneShop(boneId);
                            break;
                        case 2:
                            myInstance.setBoneRest(boneId);
                            break;
                        case 3:
                            myInstance.setBoneTour(boneId);
                            break;
                    }


                    listItem.title = (menuItem.getString("name"));
                    listItem.imagePath = ("/sdcard/Mouse_App/" + menuItem.getString("icon"));
                    listItem.boneId = (boneId);

                    GlobalVars.detailMenuItems.add(listItem.title);
                    m++;
                }
                items.add(listItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ExampleAdapter(this);
        adapter.setData(items);
//


        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (groupPosition == infoItemPosition) {
                    if (listView.isGroupExpanded(groupPosition)) {
                        listView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        listView.expandGroupWithAnimation(groupPosition);
                    }
                } else {
                    //will set up the switch-case//
                    onParentItemClick(groupPosition);

                }
                return true;
            }

        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onInfoChildItemClick(childPosition);
                return false;
            }
        });
    }


    //on city details list item clicked:
    public void onParentItemClick(int mPosition) {
        Toast.makeText(getApplicationContext(), "" + mPosition, Toast.LENGTH_SHORT).show();

        if (mPosition == 0) {                           //pos 0

        }

        else if (mPosition == infoItemPosition - 1) {   //pos 5

        }

        else if (mPosition > infoItemPosition) {   //pos 7
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainGridActivity.BannersArray.get(mPosition-infoItemPosition-1).getUrlAndroid()));
            startActivity(browserIntent);

        }

        else {                                         //pos 1-4
            Intent intent = new Intent(this, Open_Details_header_N_list.class);
            myInstance.set_boneId(items.get(mPosition).boneId);
            myInstance.set_boneIdTitle(items.get(mPosition).title);
            startActivity(intent);

        }
    }


    public void onInfoChildItemClick(int mPosition) {
        Toast.makeText(getApplicationContext(), "" + mPosition, Toast.LENGTH_SHORT).show();
    }



    private void setDetailsListItems() {
        Intent dataFileIntent = getIntent();
//        CITY_UPDATE_DATE = dataFileIntent.getStringExtra("cityUpdateDate");

        CITY_FOLDER_PATH = dataFileIntent.getStringExtra("cityFolderName");
        myInstance.set_cityFolderName(CITY_FOLDER_PATH);

        cityId = CITY_FOLDER_PATH.substring(CITY_FOLDER_PATH.length() - 4, CITY_FOLDER_PATH.length());
        myInstance.set_cityId(cityId);

        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_PATH + "/" + cityId + "_mainPageArticles.json");

        addPagerData(jsonData);

        setExpandableList();

    }


    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.titles);
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
                String folderName = CITY_FOLDER_PATH;

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


    private static class GroupItem {
        String title;
        String imagePath;
        String boneId;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
//        String hint;
    }

    private static class ChildHolder {
        TextView title;
        LinearLayout itemLayout;
        ImageView arrowIcon, imageViewBackground;
    }

    private static class GroupHolder {
        TextView title;
        ImageView imageView;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.city_details_item, parent, false);

                holder.title = (TextView) convertView.findViewById(R.id.details_item_title);
                holder.itemLayout = (LinearLayout) convertView.findViewById(R.id.details_item_layout);
                holder.imageViewBackground = (ImageView) convertView.findViewById(R.id.details_item_image);
                holder.arrowIcon = (ImageView) convertView.findViewById(R.id.details_item_arrow);

                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            holder.itemLayout.setBackgroundColor(getResources().getColor(R.color.Achbar_gray_light_background));
            holder.imageViewBackground.setBackgroundColor(getResources().getColor(R.color.Achbar_trial));
            holder.arrowIcon.setImageResource(getResources().getColor(R.color.Achbar_gray_light_background));


            return convertView;
        }


        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.city_details_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.details_item_title);
                holder.imageView = (ImageView) convertView.findViewById(R.id.details_item_image);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            File file = new File(item.imagePath);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.imageView.setImageBitmap(bitmap);
            }

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }


}
