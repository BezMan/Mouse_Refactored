package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

        String wholeTxt = dbTools.getCellData(DBConstants.CITY_TABLE_NAME, DBConstants.stopsArticle, DBConstants.cityId, myInstance.get_cityId());

        try {
            JSONObject jsonObject = new JSONObject(wholeTxt);
            JSONArray jsonArray = jsonObject.getJSONArray("article");
            JSONObject item = (JSONObject) jsonArray.get(0);
            String content = item.getString("content");


            WebView mWebView = (WebView) findViewById(R.id.web_view_article);
//            mWebView.getSettings().setAllowFileAccess(true);
//            mWebView.getSettings().setJavaScriptEnabled(true);
//            mWebView.getSettings().setBuiltInZoomControls(true);

            WebSettings settings = mWebView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");

//            String content = urlContent.getString("content");

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




            mWebView.setWebViewClient(new WebViewClient(){
                // you tell the webclient you want to catch when a url is about to load
                @Override
                public boolean shouldOverrideUrlLoading(WebView  view, String  url){
                    Log.wtf("url_clicked", url);

                    if(url.contains("CM.world_place")){
                        String first = url.substring(url.indexOf(",") + 1);
                        String second = first.substring(first.indexOf(",") + 1);
                        String third = second.substring(second.indexOf(",") + 1);

                        myInstance.set_objId(third.substring(0, third.indexOf(",")));

                        Bundle bundle = new Bundle();
                        bundle.putString(DBConstants.cityId, myInstance.get_cityId());
                        bundle.putString(DBConstants.objId, myInstance.get_objId());

                        Intent placeActivity = new Intent(getApplicationContext(), PlaceActivity.class);
                        placeActivity.putExtras(bundle);
                        startActivity(placeActivity);

                        return true;
                    }
                    else if(url.contains("CM.world_articles_item")){
                        String first = url.substring(url.indexOf(",") + 1);
                        String second = first.substring(first.indexOf(",") + 1);
                        String third = second.substring(second.indexOf(",") + 1);

                        String article_obj = third.substring(0,third.indexOf(","));


                        String articleContent = dbTools.getCellData(DBConstants.ARTICLE_TABLE_NAME, DBConstants.urlContent, DBConstants.cityId, myInstance.get_cityId(), DBConstants.objId, article_obj);

                        if(articleContent!=null) {
                            Intent articleIntent = new Intent(getApplicationContext(), ArticleActivity.class);
                            articleIntent.putExtra("articleData", articleContent);
                            startActivity(articleIntent);
                        }
                        else{
                            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }
                    }
                    else if(url.contains("CM.city")) {

                        String first = url.substring(url.indexOf(",") + 1);
                        String cityId = first.substring(0,first.indexOf(","));

                        if(cityId.equals(myInstance.get_cityId())){
                            Intent activityIntent = new Intent(getApplicationContext(), Detail_City_Activity.class);
                            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(activityIntent);
                        }
                        else{
                            Intent activityIntent = new Intent(getApplicationContext(), MainGridActivity.class);
                            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Toast.makeText(getApplicationContext(), "בחר/הורד עיר", Toast.LENGTH_LONG).show();
                            startActivity(activityIntent);
                        }
                    }

                    else{
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }

                    return true;
                }

                @Override
                public void onLoadResource(WebView  view, String  url){
                    //on init load webView with html links//
                }
            });

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
