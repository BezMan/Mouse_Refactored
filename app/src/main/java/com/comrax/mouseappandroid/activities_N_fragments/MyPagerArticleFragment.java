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
import com.comrax.mouseappandroid.app.App;
import com.comrax.mouseappandroid.database.DBConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by bez on 20/05/2015.
 */
public class MyPagerArticleFragment extends Fragment {

    public static final String EXTRA_CONTENT = "EXTRA_CONTENT", EXTRA_IMAGE = "EXTRA_IMAGE";

    public static final MyPagerArticleFragment newInstance(String urlContent, String image)
    {
        MyPagerArticleFragment f = new MyPagerArticleFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_CONTENT, urlContent);
        bdl.putString(EXTRA_IMAGE, image);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_fragment_layout, container, false);

        try {
            JSONObject jsonObject = new JSONObject(getArguments().getString(EXTRA_CONTENT));
            String title = jsonObject.getString(DBConstants.name);
            TextView titleText = (TextView)v.findViewById(R.id.pager_title);
            titleText.setText(title);

            String description = jsonObject.getString(DBConstants.description);
            TextView descriptionText = (TextView)v.findViewById(R.id.pager_description);
            descriptionText.setText(description);

            String image = getArguments().getString(EXTRA_IMAGE);

            ImageView imageText = (ImageView)v.findViewById(R.id.pager_image);

            File file = new File(App.getInstance().get_cityFolderName() + "/" + image);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageText.setImageBitmap(bitmap);
            }

            v.setOnClickListener(new OnPagerItemClicked(jsonObject.toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }


    private class OnPagerItemClicked implements View.OnClickListener {
        String mUrlContent;

        public OnPagerItemClicked(String urlContent) {

            mUrlContent=urlContent;
        }

        @Override
        public void onClick(View v) {

            Intent articleIntent = new Intent(getActivity(), ArticleActivity.class);
            articleIntent.putExtra("articleData", mUrlContent);
            startActivity(articleIntent);

        }
    }
}
