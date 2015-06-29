package com.comrax.mouseappandroid.favorites;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities_N_fragments.MyDrawerLayoutActivity;
import com.comrax.mouseappandroid.app.GlobalVars;
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
//        Cursor cursor = dbTools.getFavorites(DBConstants.PLACE_FAVORITE_TABLE_NAME, DBConstants.cityId, App.getInstance().get_cityId(), DBConstants.boneId, App.getInstance().get_boneId());


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
            View res = convertView;
            if (res == null)
                res = getLayoutInflater().inflate(R.layout.favorites_item_composer, null);

            TextView lName = (TextView) res.findViewById(R.id.lName);
            TextView lYear = (TextView) res.findViewById(R.id.lYear);

            FavoritesModel favoritesModel = getItem(position);
            lName.setText(favoritesModel.name);
            lYear.setText(favoritesModel.year);

            return res;
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


    public static List<Pair<String, List<FavoritesModel>>> getAllData() {
        List<Pair<String, List<FavoritesModel>>> res = new ArrayList<Pair<String, List<FavoritesModel>>>();

        for (int i = 0; i < 5; i++) {
            res.add(getOneSection(i));
        }
        return res;
    }


    public static Pair<String, List<FavoritesModel>> getOneSection(int index) {
//		String[] titles = {"מלונות", "קניות", "מסעדות וחיי לילה", "אטרקציות", "כתבות"};
        String[] titles = new String[GlobalVars.detailMenuItems.size()];
        titles = GlobalVars.detailMenuItems.toArray(titles);

//        Cursor cursor = new DBTools(null).getFavorites(DBConstants.PLACE_FAVORITE_TABLE_NAME, DBConstants.cityId, App.getInstance().get_cityId(), DBConstants.boneId, App.getInstance().get_boneId() );
        FavoritesModel[][] composerses = {
                {
                        new FavoritesModel("Thomas Tallis", "1510-1585"),
                        new FavoritesModel("Josquin Des Prez", "1440-1521"),
                        new FavoritesModel("Pierre de La Rue", "1460-1518"),
                },
                {
                        new FavoritesModel("Johann Sebastian Bach", "1685-1750"),
                        new FavoritesModel("George Frideric Handel", "1685-1759"),
                        new FavoritesModel("Antonio Vivaldi", "1678-1741"),
                        new FavoritesModel("George Philipp Telemann", "1681-1767"),
                },
                {
                        new FavoritesModel("Franz Joseph Haydn", "1732-1809"),
                        new FavoritesModel("Wolfgang Amadeus Mozart", "1756-1791"),
                        new FavoritesModel("Barbara of Portugal", "1711�1758"),
                        new FavoritesModel("Frederick the Great", "1712�1786"),
                        new FavoritesModel("John Stanley", "1712�1786"),
                        new FavoritesModel("Luise Adelgunda Gottsched", "1713�1762"),

                },
                {
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("Ludwig van Beethoven", "1770-1827"),
                        new FavoritesModel("Fernando Sor", "1778-1839"),
                        new FavoritesModel("Johann Strauss I", "1804-1849"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),


                },
                {
                        new FavoritesModel("Carl Philipp Emanuel Bach", "1714�1788"),
                        new FavoritesModel("Christoph Willibald Gluck", "1714�1787"),
                        new FavoritesModel("Gottfried August Homilius", "1714�1785"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),
                        new FavoritesModel("טקסט ראשי", "טקסט משני"),


                },
        };
        return new Pair<String, List<FavoritesModel>>(titles[index], Arrays.asList(composerses[index]));
    }

}