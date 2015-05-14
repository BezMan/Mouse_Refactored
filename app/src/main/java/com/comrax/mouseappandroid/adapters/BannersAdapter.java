package com.comrax.mouseappandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities.MainListActivity;
import com.comrax.mouseappandroid.model.BannersModel;

import java.util.ArrayList;

/**
 * Created by betzalel on 30/03/2015.
 */
public class BannersAdapter extends BaseAdapter /*implements View.OnClickListener*/ {

    private static LayoutInflater inflater = null;
    BannersModel tempValues = null;
    /**
     * ******** Declare Used Variables ********
     */
    private Activity _activity;
    private ArrayList _listModelList;
    private Resources _resources;


     /* **********  CustomAdapter Constructor ****************
     */
    public BannersAdapter(Activity activity, ArrayList arrayList, Resources resLocal) {

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

        public TextView textBanner;
        public ImageView imageBanner;
        public String url;
    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (BannersModel) _listModelList.get(position);

        view = inflater.inflate(R.layout.banner_layout, null);

//        holder.textBanner = (TextView) view.findViewById(R.id.cityNameText);
//        holder.imageBanner = (ImageView) view.findViewById(R.id.cityImageView);
//
//
//        /************  Set Model values in Holder elements ***********/
//        holder.textBanner.setText(tempValues.getName());
//
//        File image = new File("/sdcard/Mouse_App/" + tempValues.getImage());
//        if (image.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//            holder.imageBanner.setImageBitmap(bitmap);
//        }
//
//        /******** Set Item Click Listner for LayoutInflater for each row ***********/
//        view.setOnClickListener(new OnItemClickListener(position));

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
