package com.comrax.mouseappandroid.favorites;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities_N_fragments.MyDrawerLayoutActivity;
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;
import com.comrax.mouseappandroid.helpers.AmazingAdapter;
import com.comrax.mouseappandroid.helpers.AmazingListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SectionDemoActivity extends MyDrawerLayoutActivity {
    AmazingListView lsComposer;
    SectionComposerAdapter adapter;

    DBTools dbTools = new DBTools(this);

    @Override
    protected int getLayoutResourceId() {
        return R.layout.favorites_activity_section_demo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lsComposer = (AmazingListView) findViewById(R.id.lsComposer);
        lsComposer.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.favorites_item_composer_header, lsComposer, false));
        lsComposer.setAdapter(adapter = new SectionComposerAdapter());
    }

    class SectionComposerAdapter extends AmazingAdapter {
        List<Pair<String, List<FavoritesModel>>> all = getAllData();


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

            TextView titleTxtView = (TextView) view.findViewById(R.id.favorite_title);
            TextView typeTxtView = (TextView) view.findViewById(R.id.favorite_type);

            FavoritesModel favoritesModel = getItem(position);
            titleTxtView.setText(favoritesModel.title);
            typeTxtView.setText(favoritesModel.type);

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

        for (int i = 0; i < 5; i++) {
            pairList.add(getOneSection(i));
        }
        return pairList;
    }


    public Pair<String, List<FavoritesModel>> getOneSection(int index) {
//		String[] titles = {"מלונות", "קניות", "מסעדות וחיי לילה", "אטרקציות", "כתבות"};
        String[] titles = new String[GlobalVars.detailMenuItems.size()];
        titles = GlobalVars.detailMenuItems.toArray(titles);

        Cursor cursor = dbTools.getFavorites(DBConstants.FAVORITE_TABLE_NAME, DBConstants.cityId, App.getInstance().get_cityId(), DBConstants.categoryName, titles[index]  );
//need column for bone name//
        FavoritesModel[][] allItems = {
                {
                        new FavoritesModel(cursor),

                },
                {
                        new FavoritesModel(cursor),

                },
                {
                        new FavoritesModel(cursor),

                },
                {
                        new FavoritesModel(cursor),

                },
                {
                        new FavoritesModel(cursor),

                },
        };
        return new Pair<String, List<FavoritesModel>>(titles[index], Arrays.asList(allItems[index]));
    }

}