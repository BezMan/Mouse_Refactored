package com.comrax.mouseappandroid.activities_N_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;

public class PlaceFragment extends Fragment {



    public PlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String data = App.getInstance().get_cityFolderName() + "/"+ App.getInstance().get_cityId() + "_"+ App.getInstance().get_boneId()+ "_ArticalsList.json";
//        JSONObject jsonData = HelperMethods.loadJsonDataFromFile(data);

        Bundle bundle = this.getArguments();
        String title = bundle.getString("title", null);
        String hebTitle = bundle.getString("hebTitle", null);
//        String fullDescription = new StringBuilder().append("<html><head><style>").append("body{font-family: arial;font-size:17px;direction:rtl;background:none;}").append("</style>\ <script type='text/javascript'>\ function onLoad() {window.location.href = 'ready://' + document.body.offsetHeight;}\ </script>\ </head><body onload='onLoad()'>").toString();
        String address = bundle.getString("address", null);



        bundle.getString("fullDescription", null);
//
//        try {
//            JSONObject jsonObject = new JSONObject(fullDescription);
//            String txt = jsonObject.get
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        TextView titleView = (TextView)getActivity().findViewById(R.id.detailed_place_english_title);
        titleView.setText(title);

        TextView hebTitleView = (TextView)getActivity().findViewById(R.id.detailed_place_hebrew_title);
        hebTitleView.setText(hebTitle);

        TextView addressView = (TextView)getActivity().findViewById(R.id.detailed_place_address);
        addressView.setText(address);

//        TextView mainDetailedText = (TextView)getActivity().findViewById(R.id.detailed_place_main_text);
//        mainDetailedText.setText(Html.fromHtml(fullDescription));


//        Toast.makeText(getActivity().getApplicationContext(), fullDescription, Toast.LENGTH_LONG).show();

    }







    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateDetails();

    }




    private void populateDetails() {

    }

}
