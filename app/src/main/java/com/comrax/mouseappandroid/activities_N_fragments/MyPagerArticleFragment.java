package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

import java.io.File;

/**
 * Created by bez on 20/05/2015.
 */
public class MyPagerArticleFragment extends Fragment {

    public static final String EXTRA_CITY_FOLDER_NAME = "EXTRA_CITY_FOLDER_NAME", EXTRA_TITLE = "EXTRA_TITLE", EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION", EXTRA_IMAGE = "EXTRA_IMAGE";

    public static final MyPagerArticleFragment newInstance(String cityFolderName, String title, String description, String image)
    {
        MyPagerArticleFragment f = new MyPagerArticleFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_CITY_FOLDER_NAME, cityFolderName);
        bdl.putString(EXTRA_TITLE, title);
        bdl.putString(EXTRA_DESCRIPTION, description);
        bdl.putString(EXTRA_IMAGE, image);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_fragment_layout, container, false);

        String title = getArguments().getString(EXTRA_TITLE);
        TextView titleText = (TextView)v.findViewById(R.id.pager_title);
        titleText.setText(title);

        String description = getArguments().getString(EXTRA_DESCRIPTION);
        TextView descriptionText = (TextView)v.findViewById(R.id.pager_description);
        descriptionText.setText(description);

        String image = getArguments().getString(EXTRA_IMAGE);
        ImageView imageText = (ImageView)v.findViewById(R.id.pager_image);

        String folderName = getArguments().getString(EXTRA_CITY_FOLDER_NAME);
        File file = new File(folderName + "/" + image);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageText.setImageBitmap(bitmap);
        }

        v.setOnClickListener(new OnPagerItemClicked(title));

        return v;
    }


    private class OnPagerItemClicked implements View.OnClickListener {
        String mTitle;

        public OnPagerItemClicked(String title) {
            mTitle=title;
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity().getApplicationContext(), ""+mTitle, Toast.LENGTH_SHORT ).show();
            startActivity(new Intent(getActivity(), ArticleActivity.class));
        }
    }
}
