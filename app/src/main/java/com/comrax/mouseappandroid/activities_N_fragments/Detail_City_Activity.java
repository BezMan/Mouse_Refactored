package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.App;
import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.helpers.AnimatedExpandableListView;
import com.comrax.mouseappandroid.helpers.HelperMethods;
import com.comrax.mouseappandroid.model.GlobalVars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Detail_City_Activity extends MyDrawerLayoutActivity {

    MyPageAdapter pageAdapter;
    public String CITY_FOLDER_NAME, CITY_UPDATE_DATE, cityId;
    int infoItemPosition;
    DBTools dbTools = new DBTools(this);

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    ViewPager pager;
    View pagerLayout;

    List<GroupItem> items;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listView = (AnimatedExpandableListView) findViewById(R.id.details_list);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        pagerLayout = layoutInflater.inflate(R.layout.view_pager, null);

        pager = (ViewPager) pagerLayout.findViewById(R.id.viewpager);

        listView.addHeaderView(pagerLayout);


        setDetailsListItems();
        App.getInstance().setCityId(cityId);

        setTitle();

        listView.setAdapter(adapter);
    }


    private void setExpandableList() {

        items = new ArrayList<GroupItem>();

        JSONObject jsonMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_menu.json");
        JSONObject jsonServiceMenuData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_serviceMenu.json");
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
                    JSONObject menuItem = menuArray.getJSONObject(m++);
                    listItem.title = (menuItem.getString("name"));
                    listItem.imagePath = ("/sdcard/Mouse_App/" + menuItem.getString("icon"));
                    listItem.boneId = (menuItem.getString("boneId"));
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

        else if (mPosition == infoItemPosition + 1) {   //pos 7

        }

        else if (mPosition == infoItemPosition + 2) {   //pos 8

        }

        else if (mPosition == infoItemPosition + 3) {   //pos 9

        }

        else if (mPosition == infoItemPosition + 4) {   //pos 10

        }

        else {                                         //pos 1-4
            Intent intent = new Intent(this, Open_Details_header_N_list_Activity.class);
            App.getInstance().setBoneId(items.get(mPosition).boneId);

            String title = items.get(mPosition).title;
            intent.putExtra("title", title );
            startActivity(intent);

        }
    }


    public void onInfoChildItemClick(int mPosition) {
        Toast.makeText(getApplicationContext(), "" + mPosition, Toast.LENGTH_SHORT).show();
    }


    private void setTitle() {
        String title = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.hebrewName, DBConstants.cityId, cityId);
        TextView titleTextView = (TextView) findViewById(R.id.title_text);
        titleTextView.setText(title);
    }


    private void setDetailsListItems() {
        Intent dataFileIntent = getIntent();
        CITY_FOLDER_NAME = dataFileIntent.getStringExtra("cityFolderName");
        CITY_UPDATE_DATE = dataFileIntent.getStringExtra("cityUpdateDate");
        cityId = CITY_FOLDER_NAME.substring(CITY_FOLDER_NAME.length() - 4, CITY_FOLDER_NAME.length());
        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(CITY_FOLDER_NAME + "/" + cityId + "_mainPageArticles.json");

        addPagerData(jsonData);

        setExpandableList();

    }


    private void addPagerData(JSONObject jsonData) {
        List<Fragment> fragments = getFragmentsFromJson(jsonData);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

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
