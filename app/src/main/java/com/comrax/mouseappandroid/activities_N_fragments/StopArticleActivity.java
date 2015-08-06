package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.database.DBConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by bez on 14/07/2015.
 */
public class StopArticleActivity extends MyBaseDrawerActivity{

    @Override
    protected int getLayoutResourceId() {
        return R.layout.stop_article_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView stopTxt = (TextView)findViewById(R.id.stop_textview);
        String wholeTxt = dbTools.getCellData(DBConstants.CITY_TABLE_NAME, DBConstants.stopsArticle, DBConstants.cityId, myInstance.get_cityId());

        try {
            JSONObject jsonObject = new JSONObject(wholeTxt);
            JSONArray jsonArray = jsonObject.getJSONArray("article");
            JSONObject item = (JSONObject) jsonArray.get(0);
            String content = item.getString("content");
            stopTxt.setText(Html.fromHtml(Html.fromHtml(content).toString()));
            stopTxt.setMovementMethod(LinkMovementMethod.getInstance());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        setFooterComment();

        setFooterAd();


    }


    private void setFooterComment() {
        Button btnComment = (Button) findViewById(R.id.footer_comment_btn);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StopArticleActivity.this, AddCommentActivity.class));
            }
        });

    }


    private void setFooterAd() {
        ImageView imageButton = (ImageView) findViewById(R.id.footer_item_ad);
        int rand = (int) (Math.random() * 10 + 1);

        File file = new File(GlobalVars.trialMethod(getApplicationContext(), GlobalVars.IconFolder + "320x50_" + rand + ".jpg"));
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageButton.setImageBitmap(bitmap);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainGridActivity.BannersArray.get(0).getUrlAndroid()));
                startActivity(browserIntent);
            }
        });

    }

}
