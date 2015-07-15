package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
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

    private GoogleMap map;

    private ArrayList<MapMarkerModel> markerArray;

    String[] icon = {"hotel","shop", "rest", "tour"};

    List<Marker> markers = new ArrayList<>();

    Marker currentMarker;
    String prevIcon;

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
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        setupInitCityMap();
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
                if(!setMap) {
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
                Log.wtf("pos:", " "+pos);


                myInstance.set_nsId(cursor.getString(cursor.getColumnIndex(DBConstants.nsId)));
                myInstance.set_objId(cursor.getString(cursor.getColumnIndex(DBConstants.objId)));

                Intent placeActivity = new Intent(Detail_City_Activity.this, PlaceActivity.class);
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
                            child.htmlContent = (serviceMenuItem.getJSONObject("urlContent").getString("content"));

                            listItem.items.add(child);
                        }

                    }
                } else {  //get From Json data//
                    JSONObject menuItem = menuArray.getJSONObject(m);
                    String boneId = menuItem.getString("boneId");
                    switch (m) {
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

        if (mPosition == 0) {                           //pos 0

            Intent stopArticleIntent = new Intent(this, StopArticleActivity.class);
            startActivity(stopArticleIntent);


        } else if (mPosition == infoItemPosition - 1) {   //pos 5
            Intent tiyulimIntent = new Intent(this, TiyulimActivity.class);
            startActivity(tiyulimIntent);


        } else if (mPosition > infoItemPosition) {   //pos 7-10
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainGridActivity.BannersArray.get(mPosition - infoItemPosition - 1).getUrlAndroid()));
            startActivity(browserIntent);

        } else {                                         //pos 1-4
            Intent intent = new Intent(this, Open_Details_header_N_list.class);
            myInstance.set_boneId(items.get(mPosition).boneId);
            String title = items.get(mPosition).title;
            myInstance.set_boneIdTitle(title);
            myInstance.setBonePosition(mPosition - 1);
            startActivity(intent);

        }
    }


    public void onInfoChildItemClick(int mPosition) {
        ChildItem childItem = items.get(infoItemPosition).items.get(mPosition);

        Intent usefulInfoIntent = new Intent(this, UsefulInfoActivity.class);
        usefulInfoIntent.putExtra("articleTitle", childItem.title);
        usefulInfoIntent.putExtra("articleHtmlContent", childItem.htmlContent);
        startActivity(usefulInfoIntent);
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
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.titles);
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

                fList.add(MyPagerArticleFragment.newInstance(folderName, title, description, image));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fList;
    }





    private static class GroupItem {
        String title;
        String imagePath;
        String boneId;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String htmlContent;
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

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
