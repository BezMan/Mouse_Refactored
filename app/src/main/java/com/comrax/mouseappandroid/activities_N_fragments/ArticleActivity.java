package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.database.DBConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by bez on 15/07/2015.
 */
public class ArticleActivity extends MyBaseDrawerActivity {

    JSONObject urlContent;

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
            urlContent = new JSONObject(info);

            ImageView topImage = (ImageView) findViewById(R.id.open_article_topImage);
            TextView descPhoto = (TextView) findViewById(R.id.open_article_descPhoto);
            TextView title = (TextView) findViewById(R.id.open_article_title);
            TextView dateAndCredit = (TextView) findViewById(R.id.open_article_dateAndCredit);
            TextView description = (TextView) findViewById(R.id.open_article_description);


            File file = new File(myInstance.get_cityFolderName() + "/" + urlContent.getString("image"));
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                topImage.setImageBitmap(bitmap);
            }

            descPhoto.setText(urlContent.getString("descPhoto"));
            title.setText(urlContent.getString("nameTitle"));
            dateAndCredit.setText(urlContent.getString("dateAndCredit"));
            description.setText(urlContent.getString("description"));


            WebView mWebView = (WebView) findViewById(R.id.web_view_article);
//            mWebView.getSettings().setAllowFileAccess(true);
//            mWebView.getSettings().setJavaScriptEnabled(true);
//            mWebView.getSettings().setBuiltInZoomControls(true);

            WebSettings settings = mWebView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");

            String content = urlContent.getString("content");

            String formattedContent = Html.fromHtml((String) content).toString();
            StringBuilder sb = new StringBuilder();
            sb.append("<!doctype html><html><head>");
            sb.append("<meta charset='utf-8'>");
            sb.append("<style>\n" +
                    "body{direction:rtl;font-family:arial !important;}\n" +
                    "h3{text-align:center;}\n" +
                    "iframe{display:none;}\n" +
                    ".map_widget clearfix{width:304px;}\n" +
                    "img{max-width:304px;border:none;}\n" +
                    "</style>\n" +
                    "<script>function onLoad(){document.getElementsByTagName('iframe')[0].setAttribute('width', '304');window.scrollTo(window.innerWidth, 0)}</script>\n" +
                    "</head>");

            String html = String.format("<body>%s</body></html>", formattedContent);

            String str = myInstance.get_cityFolderName() + "/Images/";

            String fromStr = "src=\"Images/";
            String toStr = String.format("src=\"file://%s", str);

            html = html.replace(fromStr, toStr);

            sb.append(html);

            mWebView.loadDataWithBaseURL("", sb.toString(), "text/html; charset=utf-8", "UTF-8", null);


            WebViewClient yourWebClient = new WebViewClient(){
                // you tell the webclient you want to catch when a url is about to load
                @Override
                public boolean shouldOverrideUrlLoading(WebView  view, String  url){
                    Log.wtf("url_clicked", url);
                    if(url.contains("world_place")){
                        String first = url.substring(url.indexOf(",") + 1);
                        String second = first.substring(first.indexOf(",") + 1);
                        String third = second.substring(second.indexOf(",") + 1);

//                        String boneId = first.substring(0,first.indexOf(","));
//                        String nsId = second.substring(0,second.indexOf(","));

                        myInstance.set_objId(third.substring(0,third.indexOf(",")));
//                        Log.wtf("id's: ", "bone "+boneId+" ns: "+ nsId+ " obj: "+ objId);

                        Bundle bundle = new Bundle();
                        bundle.putString(DBConstants.cityId, myInstance.get_cityId());
                        bundle.putString(DBConstants.objId, myInstance.get_objId());

                        Intent placeActivity = new Intent(getApplicationContext(), PlaceActivity.class);
                        placeActivity.putExtras(bundle);
                        startActivity(placeActivity);

                        return true;
                    }
                    return false;
                }
                // here you execute an action when the URL you want is about to load
                @Override
                public void onLoadResource(WebView  view, String  url){
                    //on init load webView with html links//
                }
            };

            mWebView.setWebViewClient(yourWebClient);

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
                final Dialog dialog = new Dialog(ArticleActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_fav_dialog);

                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                Button addFavButton = (Button) dialog.findViewById(R.id.add_fav_btn);
                addFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbTools.insertArticleFavorite(urlContent);
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
                startActivity(new Intent(ArticleActivity.this, AddCommentActivity.class));
//                Toast.makeText(getApplicationContext(), "btn", Toast.LENGTH_SHORT).show();
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