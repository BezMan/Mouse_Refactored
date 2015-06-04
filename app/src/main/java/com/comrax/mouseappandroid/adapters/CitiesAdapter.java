package com.comrax.mouseappandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities.MainListActivity;
import com.comrax.mouseappandroid.model.CitiesModel;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by betzalel on 30/03/2015.
 */
public class CitiesAdapter extends BaseAdapter /*implements View.OnClickListener*/ {

    private static LayoutInflater inflater = null;
    CitiesModel tempValues = null;
    /**
     * ******** Declare Used Variables ********
     */
    private Activity _activity;
    private ArrayList _listModelList;
    private int _existingCityCounter;
    private Resources _resources;


    /* **********  CustomAdapter Constructor ****************
    */
    public CitiesAdapter(Activity activity, ArrayList arrayList, int existingCityCounter, Resources resLocal) {

        /********** Take passed values **********/
        _activity = activity;
        _listModelList = arrayList;
        _existingCityCounter = existingCityCounter;
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

        public RelativeLayout backgroundLayout;
        public TextView nameCity;
        public ImageView imageCity, imageArrow;

    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (CitiesModel) _listModelList.get(position);


        if (tempValues.getId().equals("greenBlankItem")) {
            view = inflater.inflate(R.layout.green_title, null);
        }
else if (tempValues.getId().equals("greenYesDownloaded")) {
                view = inflater.inflate(R.layout.green_title, null);

                TextView greenTv = (TextView)view.findViewById(R.id.greenTextView);
                greenTv.setText("מדריכי ערים שהורדתי");

            }

        else if (tempValues.getId().equals("greenNotDownloaded")) {
            view = inflater.inflate(R.layout.green_title, null);

            TextView greenTv = (TextView)view.findViewById(R.id.greenTextView);
            greenTv.setText("מדריכי ערים להורדה");

        }
        else if(tempValues.getId().equals("blankCityItem")){
            view = inflater.inflate(R.layout.city_layout, null);
            holder.backgroundLayout = (RelativeLayout) view.findViewById(R.id.cityBackground);
            holder.backgroundLayout.setBackgroundColor(_resources.getColor(R.color.Achbar_gray_default_background));

        }

        else {


            view = inflater.inflate(R.layout.city_layout, null);

            holder.nameCity = (TextView) view.findViewById(R.id.cityNameText);
            holder.imageCity = (ImageView) view.findViewById(R.id.cityImageView);
            holder.imageArrow = (ImageView) view.findViewById(R.id.cityImageArrow);

            /************  Set Model values in Holder elements ***********/
            holder.nameCity.setText(tempValues.getName());

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Mouse_App/Default_master/" + tempValues.getImage());
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.imageCity.setImageBitmap(bitmap);
            }


            if (position < _existingCityCounter+2) {
                holder.imageArrow.setImageResource(_resources.getIdentifier("com.comrax.mouseappandroid:drawable/" + "side_arrow", null, null));
            } else {
                holder.imageArrow.setImageResource(_resources.getIdentifier("com.comrax.mouseappandroid:drawable/" + "down_arrow", null, null));

            }
            /******** Set Item Click Listner for LayoutInflater for each row ***********/
            view.setOnClickListener(new OnItemClickListener(position));


        }
        return view;
    }


    /**
     * ****** Called when Item click in ListView ***********
     */
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MainListActivity cla = (MainListActivity) _activity;
            cla.onListItemClick(mPosition);
        }
    }

}
