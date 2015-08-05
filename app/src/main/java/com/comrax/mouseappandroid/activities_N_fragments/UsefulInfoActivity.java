package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 15/07/2015.
 */
public class UsefulInfoActivity extends MyBaseDrawerActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.useful_info_article_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String theTitle = intent.getStringExtra("articleTitle");
        String content = intent.getStringExtra("articleHtmlContent");

        TextView title = (TextView) findViewById(R.id.uesful_info_title);
        title.setText(theTitle);

//        TextView htmlContent = (TextView) findViewById(R.id.uesful_info_html_content);
//        htmlContent.setText(Html.fromHtml(Html.fromHtml(theContent).toString()));
//        htmlContent.setMovementMethod(LinkMovementMethod.getInstance());

        WebView mWebView = (WebView) findViewById(R.id.web_view_article);

        WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

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



    }
}
