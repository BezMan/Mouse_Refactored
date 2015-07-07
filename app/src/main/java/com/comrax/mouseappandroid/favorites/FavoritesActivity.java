package com.comrax.mouseappandroid.favorites;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities_N_fragments.MyBaseDrawerActivity;
import com.comrax.mouseappandroid.activities_N_fragments.PlaceFragment;
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.helpers.AmazingAdapter;
import com.comrax.mouseappandroid.helpers.AmazingListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FavoritesActivity extends MyBaseDrawerActivity implements PlaceFragment.MyFragmentDelegate {
    AmazingListView lsComposer;
    FavoritesModel[][] allItems = new FavoritesModel[4][];
    DBTools dbTools = new DBTools(this);
    FavoritesAdapter adapter;

    TextView editPage;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.favorites_activity_section_demo;
    }

    @Override
    protected String getTextForAppBar() {
        return "המועדפים שלי";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lsComposer = (AmazingListView) findViewById(R.id.lsComposer);
        lsComposer.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.favorites_item_composer_header, lsComposer, false));
        adapter = new FavoritesAdapter(false);
        lsComposer.setAdapter(adapter);
    }

    @Override
    public void onResumeAction() {

    }

    class FavoritesAdapter extends AmazingAdapter {
        boolean mEditable;
        List<Pair<String, List<FavoritesModel>>> all;

        public FavoritesAdapter(boolean editable) {
            mEditable = editable;
            all = getAllData();
        }


        @Override
        public int getCount() {
            int res = 0;
            for (int i = 0; i < all.size(); i++) {
                res += all.get(i).second.size();
            }

            return res;

        }

        @Override
        public FavoritesModel getItem(int position) {
            int c = 0;
            for (int i = 0; i < all.size(); i++) {
                if (position >= c && position < c + all.get(i).second.size()) {
                    return all.get(i).second.get(position - c);
                }
                c += all.get(i).second.size();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        protected void onNextPageRequested(int page) {
        }

        @Override
        protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
            if (displaySectionHeader) {
                view.findViewById(R.id.header).setVisibility(View.VISIBLE);
                TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
                lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
            } else {
                view.findViewById(R.id.header).setVisibility(View.GONE);
            }
        }


        //like getView of adapter//
        @Override
        public View getAmazingView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.favorites_item_composer, null);
            FavoritesModel favoritesModel = getItem(position);

            if (favoritesModel != null) {

                editPage = (TextView) findViewById(R.id.favorites_edit_page);

                TextView nameTxtView = (TextView) view.findViewById(R.id.favorite_name);
                TextView typeTxtView = (TextView) view.findViewById(R.id.favorite_type);

                LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.favorite_main_layout);

                if (mEditable) {
                    ImageView deleteBtn = (ImageView) view.findViewById(R.id.favorite_delete_image);
                    deleteBtn.setVisibility(View.VISIBLE);
                    deleteBtn.setOnClickListener(new FavoritesLayoutClickListener(favoritesModel));

                } else {
                    mainLayout.setOnClickListener(new FavoritesLayoutClickListener(favoritesModel));
                }

                nameTxtView.setText(favoritesModel.getName());
                typeTxtView.setText(favoritesModel.getType());

                editPage.setOnClickListener(new FavoritesLayoutClickListener(favoritesModel));
            }


            return view;
        }

        @Override
        public void configurePinnedHeader(View header, int position, int alpha) {
            TextView lSectionHeader = (TextView) header;
            lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
//			lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
//			lSectionHeader.setTextColor(alpha << 24 | (0x222222));
        }

        @Override
        public int getPositionForSection(int section) {
            if (section < 0) section = 0;
            if (section >= all.size()) section = all.size() - 1;
            int c = 0;
            for (int i = 0; i < all.size(); i++) {
                if (section == i) {
                    return c;
                }
                c += all.get(i).second.size();
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            int c = 0;
            for (int i = 0; i < all.size(); i++) {
                if (position >= c && position < c + all.get(i).second.size()) {
                    return i;
                }
                c += all.get(i).second.size();
            }
            return -1;
        }

        @Override
        public String[] getSections() {
            String[] res = new String[all.size()];
            for (int i = 0; i < all.size(); i++) {
                res[i] = all.get(i).first;
            }
            return res;
        }

    }


    public List<Pair<String, List<FavoritesModel>>> getAllData() {
        List<Pair<String, List<FavoritesModel>>> pairList = new ArrayList<Pair<String, List<FavoritesModel>>>();

        for (int i = 0; i < 4; i++) {
            pairList.add(getOneSection(i));
        }
        return pairList;
    }


    public Pair<String, List<FavoritesModel>> getOneSection(int index) {
//		String[] titles = {"מלונות", "קניות", "מסעדות וחיי לילה", "אטרקציות", "כתבות"};
        int mySize = GlobalVars.detailMenuItems.size();
        if (mySize <= 0) {
            return new Pair<String, List<FavoritesModel>>("", Arrays.<FavoritesModel>asList());
        }
        String[] titles = new String[mySize];
        titles = GlobalVars.detailMenuItems.toArray(titles);

        Cursor cursor = dbTools.getFavorites(DBConstants.FAVORITE_TABLE_NAME, DBConstants.cityId, App.getInstance().get_cityId(), DBConstants.categoryName, titles[index]);

        allItems[index] = new FavoritesModel[cursor.getCount()];

        for (int j = 0; j < cursor.getCount(); j++, cursor.moveToNext()) {

            allItems[index][j] = new FavoritesModel(cursor);
        }
        return new Pair<String, List<FavoritesModel>>(titles[index], Arrays.asList(allItems[index]));

    }


    private class FavoritesLayoutClickListener implements View.OnClickListener {
        FavoritesModel mFavoritesModel;

        public FavoritesLayoutClickListener(FavoritesModel favoritesModel) {
            mFavoritesModel = favoritesModel;
        }

        @Override
        public void onClick(View v) {

            if (v instanceof LinearLayout) {
//                Toast.makeText(getApplicationContext(), mFavoritesModel.getName() + "\n" + mFavoritesModel.getType(), Toast.LENGTH_SHORT).show();
                Cursor cursor = dbTools.getData(DBConstants.FAVORITE_TABLE_NAME, DBConstants.name, mFavoritesModel.getName(), DBConstants.objId, mFavoritesModel.getObjId());

                Bundle bundle = new Bundle();
                bundle.putString("name", cursor.getString(cursor.getColumnIndex(DBConstants.name)));
                bundle.putString("hebName", cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName)));
                bundle.putString("fullDescription", cursor.getString(cursor.getColumnIndex(DBConstants.description)));
                bundle.putString("address", cursor.getString(cursor.getColumnIndex(DBConstants.address)));
                bundle.putString("image", cursor.getString(cursor.getColumnIndex(DBConstants.image)));

                bundle.putString("phone", cursor.getString(cursor.getColumnIndex(DBConstants.phone)));
                bundle.putString("activityHours", cursor.getString(cursor.getColumnIndex(DBConstants.activityHours)));
                bundle.putString("publicTransportation", cursor.getString(cursor.getColumnIndex(DBConstants.publicTransportation)));
                bundle.putString("responses", cursor.getString(cursor.getColumnIndex(DBConstants.responses)));

                bundle.putString("type", cursor.getString(cursor.getColumnIndex(DBConstants.type)));


                PlaceFragment placeFragment = new PlaceFragment();
                //placeFragment.setDelegate(FavoritesActivity.this);
                placeFragment.setArguments(bundle);
                loadFragmentWithBackStack(placeFragment, "placeTag");

            } else if (v instanceof ImageView) {//item delete btn:
//                Toast.makeText(getApplicationContext(), "delete \n" + mFavoritesModel.getName() + "\n" + mFavoritesModel.getType(), Toast.LENGTH_SHORT).show();
                dbTools.deleteRow(DBConstants.FAVORITE_TABLE_NAME, DBConstants.name, mFavoritesModel.getName());

                adapter = new FavoritesAdapter(true);
                lsComposer.setAdapter(adapter);

            } else {// edit btn clicked:
//                Toast.makeText(getApplicationContext(), "EDIT", Toast.LENGTH_SHORT).show();
                if (editPage.getText().equals("עריכה")) {//start editing//
                    editPage.setText("סיים");
                    adapter = new FavoritesAdapter(true);
                    lsComposer.setAdapter(adapter);


                } else {    //end editing//
                    editPage.setText("עריכה");
                    adapter = new FavoritesAdapter(false);
                    lsComposer.setAdapter(adapter);


                }

            }
        }
    }

    public void loadFragmentWithBackStack(final Fragment fragment, String fragTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment, fragTag)
                .addToBackStack(fragTag)
                .commit();

    }


}