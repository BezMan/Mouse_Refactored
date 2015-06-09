package com.comrax.mouseappandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.model.ListModel;

import java.util.ArrayList;

/**
 * Created by betzalel on 30/03/2015.
 */
public class CustomAdapter extends BaseAdapter /*implements View.OnClickListener*/ {

    private static LayoutInflater inflater = null;
    ListModel tempValues = null;
    /**
     * ******** Declare Used Variables ********
     */
    private Activity _activity;
    private ArrayList _listModelList;
    private Resources _resources;


    /**
     * **********  CustomAdapter Constructor ****************
     */
    public CustomAdapter(Activity activity, ArrayList arrayList, Resources resLocal) {

        /********** Take passed values **********/
        _activity = activity;
        _listModelList = arrayList;
        _resources = resLocal;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * ***** What is the size of Passed Arraylist Size ***********
     */
    public int getCount() {

        if (_listModelList.size() <= 0)
            return 1;
        return _listModelList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * ****** Create a holder to contain inflated xml file elements **********
     */
    public static class ViewHolder {

        public TextView title, date, country;
        public Button saveDateBtn, favoritesBtn;
        public View line1, line2, line3, backColor;
        public ImageView imageCal, imageStar, imageArrow;

    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

//        if (_listModelList.size() == 0) {
//            view = inflater.inflate(R.layout.no_results_layout, null);
//            return view;
//        }

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (ListModel) _listModelList.get(position);

//        if (selectPos == (-1) && position == 0) {   //only the main list has a special picture item at position 0//
//            view = inflater.inflate(R.layout.primary_list_item, null);
//
//            ImageView imgLarge = (ImageView) view.findViewById(R.id.largeImage);
//            ImageView imgSmall = (ImageView) view.findViewById(R.id.smallImage);
//        }
//
//        else {
//            view = inflater.inflate(R.layout.upcoming_list_item, null);
//        }
//
//        //do all these for all items:
//        holder.title = (TextView) view.findViewById(R.id.title);
//        holder.date = (TextView) view.findViewById(R.id.date);
//        holder.country = (TextView) view.findViewById(R.id.country);
//        holder.saveDateBtn = (Button) view.findViewById(R.id.save_date);
//        holder.favoritesBtn = (Button) view.findViewById(R.id.favorites);
//        holder.line1 = view.findViewById(R.id.line1);
//        holder.line2 = view.findViewById(R.id.line2);
//        holder.line3 = view.findViewById(R.id.line3);
//        holder.backColor = view.findViewById(R.id.backColor);
//        holder.imageCal = (ImageView) view.findViewById(R.id.imageCalendar);
//        holder.imageStar = (ImageView) view.findViewById(R.id.imageStar);
//        holder.imageArrow = (ImageView) view.findViewById(R.id.imageArrow);

        int modPosition = position % 8;
//        String tempCal = GlobalVars.ColorNames[modPosition] + "cal";
//        String tempStar = GlobalVars.ColorNames[modPosition] + "star";
//        String tempArrow = GlobalVars.ColorNames[modPosition] + "arrow";

        /************  Set Model values in Holder elements ***********/
        holder.title.setText(tempValues.getTitle());
        holder.date.setText(tempValues.getDate());
        holder.country.setText(tempValues.getCountry());

//        String myColor = GlobalVars.ColorCodes[modPosition];

//        holder.line1.setBackgroundColor(Color.parseColor(myColor));
//        holder.line2.setBackgroundColor(Color.parseColor(myColor));
//        holder.line3.setBackgroundColor(Color.parseColor(myColor));
//
//        holder.backColor.setBackgroundColor(Color.parseColor(myColor));
//
//        holder.imageCal.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempCal, null, null));
//        holder.imageStar.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempStar, null, null));
//        holder.imageArrow.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempArrow, null, null));

        holder.saveDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("save clicked ", "" + position);
//                ConfListActivity sct = (ConfListActivity) _activity;
//                sct.addCalendarEvent(position);

            }
        });

        holder.favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("favorites clicked ", "" + position);
//                ConfListActivity sct = (ConfListActivity) _activity;
//                sct.addToFavorites(position);

            }
        });


        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        view.setOnClickListener(new OnItemClickListener(position, modPosition));


        return view;
    }


    /**
     * ****** Called when Item click in ListView ***********
     */
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;
        private int mColorPos;

        OnItemClickListener(int position, int colorPos) {
            mPosition = position;
            mColorPos = colorPos;
        }

        @Override
        public void onClick(View arg0) {
//            ConfListActivity cla = (ConfListActivity) _activity;
//            cla.onListItemClick(mPosition, mColorPos);
        }
    }

}
