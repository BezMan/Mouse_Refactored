package com.comrax.mouseappandroid.adapters;

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
import com.comrax.mouseappandroid.activities_N_fragments.MyDrawerLayoutActivity;
import com.comrax.mouseappandroid.model.DetailsListModel;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by betzalel on 30/03/2015.
 */
public class DetailsListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    DetailsListModel tempValues = null;
    /**
     * ******** Declare Used Variables ********
     */
    private Activity _activity;
    private ArrayList _listModelList;
    private Resources _resources;


     /* **********  CustomAdapter Constructor ****************
     */
    public DetailsListAdapter(Activity activity, ArrayList arrayList, Resources resLocal) {

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

        public TextView title;
        public ImageView image;

    }

    /**
     * ******** Depends upon _arrayList size called for each row , Create each ListView row **********
     */
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        tempValues = null;
        tempValues = (DetailsListModel) _listModelList.get(position);

        view = inflater.inflate(R.layout.city_details_item, null);

        holder.title = (TextView) view.findViewById(R.id.details_item_title);
        holder.image = (ImageView) view.findViewById(R.id.details_item_image);


        /************  Set Model values in Holder elements ***********/
        holder.title.setText(tempValues.getBtnTitle());

        File file = new File(tempValues.getBtnImage());
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.image.setImageBitmap(bitmap);
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
            MyDrawerLayoutActivity activity = (MyDrawerLayoutActivity) _activity;
            activity.onListItemClick(mPosition);
        }
    }

}
