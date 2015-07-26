package com.comrax.mouseappandroid.activities_N_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

public class StaticPageFragment extends Fragment {

    Bundle bundle;
    TextView title, mainTxt, barTitleTextView;
    String originalTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        barTitleTextView = (TextView) getActivity().findViewById(R.id.title_text);
        originalTitle = barTitleTextView.getText().toString();
            barTitleTextView.setText(bundle.getString("barTitle", null));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_static_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView)getActivity().findViewById(R.id.static_page_title);
        mainTxt = (TextView)getActivity().findViewById(R.id.static_page_main_text);

        try {
            JSONObject jsonObject = new JSONObject(bundle.getString("data", null));
            title.setText(jsonObject.getString("Title"));
//            mainTxt.setText(jsonObject.getString("Content"));

            mainTxt.setText(Html.fromHtml(Html.fromHtml(jsonObject.getString("Content")).toString()));
            mainTxt.setMovementMethod(LinkMovementMethod.getInstance());

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        barTitleTextView.setText(originalTitle);
    }
}
