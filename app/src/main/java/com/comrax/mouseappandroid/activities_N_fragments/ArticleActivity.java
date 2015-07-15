package com.comrax.mouseappandroid.activities_N_fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by bez on 15/07/2015.
 */
public class ArticleActivity extends MyBaseDrawerActivity {

//    Intent articleIntent;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.article_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String info = getIntent().getStringExtra("articleData");

        try {
            JSONObject item = new JSONObject(info);
            JSONObject urlContent = item.getJSONObject("urlContent");

            ImageView topImage = (ImageView)findViewById(R.id.open_article_topImage);
            TextView descPhoto = (TextView)findViewById(R.id.open_article_descPhoto);
            TextView title = (TextView)findViewById(R.id.open_article_title);
            TextView dateAndCredit = (TextView)findViewById(R.id.open_article_dateAndCredit);
            TextView description = (TextView)findViewById(R.id.open_article_description);
            TextView htmlContent = (TextView)findViewById(R.id.open_article_html_content);


            File file = new File(App.getInstance().get_cityFolderName() + "/" + urlContent.getString("image"));
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                topImage.setImageBitmap(bitmap);
            }

            descPhoto.setText(urlContent.getString("descPhoto"));

            title.setText(urlContent.getString("nameTitle"));
            dateAndCredit.setText(urlContent.getString("dateAndCredit"));
            description.setText(urlContent.getString("description"));
            htmlContent.setText(Html.fromHtml(Html.fromHtml(urlContent.getString("content")).toString()));
            htmlContent.setMovementMethod(LinkMovementMethod.getInstance());



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}