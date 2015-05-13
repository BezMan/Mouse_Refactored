package com.comrax.mouseappandroid.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities.MainListActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by betzalel on 30/03/2015.
 */
public class citiesAdapter extends BaseAdapter /*implements View.OnClickListener*/ {

    private static LayoutInflater inflater = null;
    CitiesModel tempValues = null;
    /**
     * ******** Declare Used Variables ********
     */
    private Activity _activity;
    private ArrayList _listModelList;
    private Resources _resources;


     /* **********  CustomAdapter Constructor ****************
     */
    public citiesAdapter(Activity activity, ArrayList arrayList, Resources resLocal) {

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

        public TextView nameCity;
        public ImageView imageCity;

    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (CitiesModel) _listModelList.get(position);

        view = inflater.inflate(R.layout.city_layout, null);

        holder.nameCity = (TextView) view.findViewById(R.id.cityNameText);
        holder.imageCity = (ImageView) view.findViewById(R.id.cityImageView);


        /************  Set Model values in Holder elements ***********/
        holder.nameCity.setText(tempValues.getName());

        File image = new File("/sdcard/Mouse_App/" + tempValues.getImage());
        if (image.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            holder.imageCity.setImageBitmap(bitmap);
        }

        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        view.setOnClickListener(new OnItemClickListener(position));

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
