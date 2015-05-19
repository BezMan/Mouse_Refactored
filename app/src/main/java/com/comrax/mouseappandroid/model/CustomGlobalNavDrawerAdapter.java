package com.comrax.mouseappandroid.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.activities.MainListActivity;

import java.util.ArrayList;

/**
 * Created by betzalel on 20/04/2015.
 */
public class CustomGlobalNavDrawerAdapter extends BaseAdapter /*implements View.OnClickListener*/ {

    private static LayoutInflater inflater = null;
    DrawerModel tempValues = null;
    private Activity _activity;
    private ArrayList _listModelList;
    private Resources _resources;



    /**
     * **********  CustomAdapter Constructor ****************
     */
    public CustomGlobalNavDrawerAdapter(Activity activity, ArrayList arrayList, Resources resLocal) {

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





    public View getView(final int position, View view, ViewGroup parent) {

        NavDrawerViewHolder holder = new NavDrawerViewHolder();
        tempValues = null;
        tempValues = (DrawerModel) _listModelList.get(position);

        view = inflater.inflate(R.layout.details_nav_drawer_item_layout, null);

//        holder.textView = (TextView) view.findViewById(R.id.nav_drawer_text);
        holder.imageViewIcon = (ImageView) view.findViewById(R.id.nav_drawer_imageView);

        /************  Set Model values in Holder elements ***********/
//        holder.textView.setText(tempValues.getBtnTitle());
        holder.imageViewIcon.setImageResource(_resources.getIdentifier("com.comrax.mouseappandroid:drawable/" + tempValues.getBtnImage(), null, null));

//        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        view.setOnClickListener(new OnItemClickListener(position));
//
//
        return view;
    }



    /**
     * ****** Create a holder to contain inflated xml file elements **********
     */
    public static class NavDrawerViewHolder {

//        public TextView textView;
        public ImageView imageViewIcon;

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

        if(_activity instanceof MainListActivity)
            ((MainListActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof ConfListActivity)
//            ((ConfListActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof HelpActivity)
//            ((HelpActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof ContactUsActivity)
//            ((ContactUsActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof FavoritesListActivity)
//            ((FavoritesListActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof SearchActivity)
//            ((SearchActivity) _activity).onNavDrawerItemClick(mPosition);
//        else if(_activity instanceof SearchExtendedActivity)
//            ((SearchExtendedActivity) _activity).onNavDrawerItemClick(mPosition);
//
        }
    }

}
