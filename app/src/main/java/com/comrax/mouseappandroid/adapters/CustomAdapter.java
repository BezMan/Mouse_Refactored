package com.comrax.mouseappandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
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

    /**
     * **********  CustomAdapter Constructor ****************
     */
    public CustomAdapter(Activity activity, ArrayList arrayList) {

        /********** Take passed values **********/
        _activity = activity;
        _listModelList = arrayList;

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

        public TextView titleA, titleB, titleC, distance, address, textPrice;
        public ImageView imagePrice;

    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (ListModel) _listModelList.get(position);

        view = inflater.inflate(R.layout.open_details_list_item, null);

        holder.titleA = (TextView) view.findViewById(R.id.open_details_item_title_A);
        holder.titleB = (TextView) view.findViewById(R.id.open_details_item_title_B);
        holder.titleC = (TextView) view.findViewById(R.id.open_details_item_title_C);

        holder.distance = (TextView) view.findViewById(R.id.open_details_item_Distance);
        holder.address = (TextView) view.findViewById(R.id.open_details_item_address);
        holder.textPrice = (TextView) view.findViewById(R.id.open_details_item_price_text);
        holder.imagePrice = (ImageView) view.findViewById(R.id.open_details_item_price_image);



        /************  Set Model values in Holder elements ***********/


        holder.titleA.setText(tempValues.getTitleA());
        holder.titleB.setText(tempValues.getTitleB());
        holder.titleC.setText(tempValues.getTitleC());

//        holder.imageCal.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempCal, null, null));
//        holder.imageStar.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempStar, null, null));
//        holder.imageArrow.setImageResource(_resources.getIdentifier("com.comrax.janssenconfinder:drawable/" + tempArrow, null, null));

//        holder.saveDateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("save clicked ", "" + position);
//                ConfListActivity sct = (ConfListActivity) _activity;
//                sct.addCalendarEvent(position);

//            }
//        });
//
//        holder.favoritesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("favorites clicked ", "" + position);
//                ConfListActivity sct = (ConfListActivity) _activity;
//                sct.addToFavorites(position);

//            }
//        });


        /******** Set Item Click Listner for LayoutInflater for each row ***********/
//        view.setOnClickListener(new OnItemClickListener(position, modPosition));


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
