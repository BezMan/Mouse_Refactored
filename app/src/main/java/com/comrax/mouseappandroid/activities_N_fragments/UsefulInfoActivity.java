package com.comrax.mouseappandroid.activities_N_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 15/07/2015.
 */
public class UsefulInfoActivity extends MyBaseDrawerActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.uesful_info_article_layout;
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
        String theContent = intent.getStringExtra("articleHtmlContent");

        TextView title = (TextView) findViewById(R.id.uesful_info_title);
        TextView htmlContent = (TextView) findViewById(R.id.uesful_info_html_content);

        title.setText(theTitle);
        htmlContent.setText(Html.fromHtml(Html.fromHtml(theContent).toString()));
        htmlContent.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
