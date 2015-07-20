package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.database.DBTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by bez on 14/07/2015.
 */
public class StopArticleActivity extends MyBaseDrawerActivity{

DBTools dbTools = new DBTools(this);

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
        String wholeTxt = dbTools.getData(DBConstants.CITY_TABLE_NAME, DBConstants.stopsArticle, DBConstants.cityId, myInstance.get_cityId());

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

        setFooterFav();

        setFooterAd();


    }

    private void setFooterFav() {
        Button favoritesBtn = (Button) findViewById(R.id.footer_prefs_button);
        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(StopArticleActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_fav_dialog);

                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                Button addFavButton = (Button) dialog.findViewById(R.id.add_fav_btn);
                addFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dbTools.insertFavorite(cursor);
                        Toast.makeText(getApplicationContext(), "נשמר בהצלחה", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                Button cancelFavButton = (Button) dialog.findViewById(R.id.cancel_fav_btn);
                cancelFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

        });

    }

    private void setFooterComment() {
        Button btnComment = (Button) findViewById(R.id.footer_comment_btn);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StopArticleActivity.this, AddCommentActivity.class));
//                Toast.makeText(getApplicationContext(), "btn", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setFooterAd() {
        ImageView imageButton = (ImageView) findViewById(R.id.footer_item_ad);
        int rand = (int) (Math.random() * 10 + 1);

        File file = new File("/sdcard/Mouse_App/Default_master/Images/MenuIcons/" + "320x50_" + rand + ".jpg");
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
