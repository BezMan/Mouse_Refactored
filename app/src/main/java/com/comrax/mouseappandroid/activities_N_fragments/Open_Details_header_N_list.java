package com.comrax.mouseappandroid.activities_N_fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.model.MapMarkerModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bez on 07/06/2015.
 */
public class Open_Details_header_N_list extends MyBaseDrawerActivity implements MyBaseFragment.MyFragmentDelegate {

    TextView boneTitle;
    Cursor cursor;

    int infoItemPosition;
    DBTools dbTools = new DBTools(this);

    private SlidingLayer mSlidingLayer;

    private App myInstance = App.getInstance();

    private GoogleMap map;

    private ArrayList<MapMarkerModel> markerArray;

    String[] icon = {"hotel", "rest", "shop", "tour"};

    List<Marker> markers = new ArrayList<>();

    Marker currentMarker;
    String prevIcon;

    public void buttonClicked(View v) {
        if (mSlidingLayer.isOpened()) {
            mSlidingLayer.closeLayer(true);
        } else {
            mSlidingLayer.openLayer(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleStikkyFragment fragment = new SimpleStikkyFragment();
        fragment.setDelegate(this);
        initLoadFragment(fragment, "ListTag");


        boneTitle = (TextView) findViewById(R.id.bone_title);
        boneTitle.setText(myInstance.get_boneIdTitle());



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
                mSlidingLayer.setSlidingEnabled(false);
                setupMapData(dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId()));
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
//                String currName = markerArray.get(currIndex).getPlaceName();

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
//                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                Cursor cursor = dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.name, marker.getTitle(), DBConstants.cityId, myInstance.get_cityId());

                Bundle bundle = new Bundle();
                bundle.putString(DBConstants.name, cursor.getString(cursor.getColumnIndex(DBConstants.name)));
                bundle.putString(DBConstants.objId, cursor.getString(cursor.getColumnIndex(DBConstants.objId)));

                PlaceFragment placeFragment = new PlaceFragment();
                //placeFragment.setDelegate(FavoritesActivity.this);
                placeFragment.setArguments(bundle);
                loadFragmentWithBackStack(placeFragment, "placeTag");

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
            return icon[0];
        } else if (boneId.equals(myInstance.getBoneRest())) {
            return icon[1];
        } else if (boneId.equals(myInstance.getBoneShop())) {
            return icon[2];
        } else {
            return icon[3];
        }
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




    private void initLoadFragment(final Fragment fragment, String fragTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
//                .addToBackStack(fragTag)
                .commit();

    }


    public void loadFragmentWithBackStack(final Fragment fragment, String fragTag) {

        getSupportFragmentManager().popBackStack(fragTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
                .addToBackStack(fragTag)
                .commit();


    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.open_details_full_header;

    }

    @Override
    protected String getTextForAppBar() {
        return  myInstance.getCityName();
    }


    public void onListItemClick() {
        cursor = new DBTools(this).getData(DBConstants.PLACE_TABLE_NAME, DBConstants.cityId, myInstance.get_cityId(), DBConstants.boneId, myInstance.get_boneId(), DBConstants.objId, myInstance.get_objId());

        Bundle bundle = new Bundle();
        bundle.putString(DBConstants.name, cursor.getString(cursor.getColumnIndex(DBConstants.name)));
        bundle.putString(DBConstants.objId, cursor.getString(cursor.getColumnIndex(DBConstants.objId)));

        PlaceFragment placeFragment = new PlaceFragment();
        placeFragment.setDelegate(this);

        placeFragment.setArguments(bundle);
        loadFragmentWithBackStack(placeFragment, "placeTag");
    }


    @Override
    public void onResumeAction() {
        //change your title
        String str = getTextForAppBar();
        setupTextView(str);
    }
}




