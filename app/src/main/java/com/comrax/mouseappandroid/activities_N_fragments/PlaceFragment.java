package com.comrax.mouseappandroid.activities_N_fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comrax.mouseappandroid.App;
import com.comrax.mouseappandroid.R;

import java.io.File;

public class PlaceFragment extends Fragment {

    Button b1, b2, b3, b4;
    ImageView image1, image2, image3, image4;
    LinearLayout layout1, layout2, layout3, layout4;



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

        String imagePath = bundle.getString("image", null);

        String title = bundle.getString("title", null);
        String hebTitle = bundle.getString("hebTitle", null);

        String fullDescription = new StringBuilder().append("<![CDATA[")
                .append("<html><head><style>")
                .append("body{font-family:arial;font-size:17px;direction:rtl;background:none;}")
                .append("</style></head><body>")
                .append(bundle.getString("fullDescription", null))
                .append("</body></html>")
                .append("]]>")
                .toString();

        String address = bundle.getString("address", null);



//        bundle.getString("fullDescription", null);
//
//        try {
//            JSONObject jsonObject = new JSONObject(fullDescription);
//            String txt = jsonObject.get
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        ImageView headImageView = (ImageView)getActivity().findViewById(R.id.detailed_place_head_imageView);


        File file = new File(App.getInstance().get_cityFolderName() + "/" + imagePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            headImageView.setImageBitmap(bitmap);
        }

        TextView titleView = (TextView)getActivity().findViewById(R.id.detailed_place_english_title);
        titleView.setText(title);

        TextView hebTitleView = (TextView)getActivity().findViewById(R.id.detailed_place_hebrew_title);
        hebTitleView.setText(hebTitle);

        TextView addressView = (TextView)getActivity().findViewById(R.id.detailed_place_address);
        addressView.setText(address);

        TextView mainDetailedText = (TextView)getActivity().findViewById(R.id.detailed_place_main_text);
        String html = Html.fromHtml(fullDescription).toString();
        String html2 = Html.fromHtml(html).toString();

        mainDetailedText.setText(Html.fromHtml(html2));


        setServiceItems();
//        Toast.makeText(getActivity().getApplicationContext(), fullDescription, Toast.LENGTH_LONG).show();

        setFooterAd();

        setGrayExtras();
    }

    private void setGrayExtras() {

    }

    private void setFooterAd() {
        ImageView imageButton = (ImageView)getActivity().findViewById(R.id.footer_item_ad);
        int rand = (int) (Math.random()*10 + 1);

        File file = new File("/sdcard/Mouse_App/Default_master/Images/MenuIcons/" + "320x50_" + rand + ".jpg");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageButton.setImageBitmap(bitmap);
        }

    }


    private void setServiceItems(){

        TextView[] textViews = {b1, b2, b3, b4};
        ImageView[] images = {image1, image2, image3, image4};
        LinearLayout[] layouts = {layout1, layout2, layout3, layout4};



        for (int i=0; i< MainListActivity.BannersArray.size(); i++){

                int buttonID = getActivity().getResources().getIdentifier("footer_item_title_" + (i + 1), "id", getActivity().getPackageName());
                textViews[i] = (TextView) getActivity().findViewById(buttonID);
                textViews[i].setText(MainListActivity.BannersArray.get(i).getText());


                int imageID = getResources().getIdentifier("footer_item_image_" + (i + 1), "id", getActivity().getPackageName());
                images[i] = (ImageView) getActivity().findViewById(imageID);

                File file = new File("/sdcard/Mouse_App/Default_master/" + MainListActivity.BannersArray.get(i).getImageBIG());
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    images[i].setImageBitmap(bitmap);
                }

                int layoutID = getResources().getIdentifier("footer_item_layout_" + (i + 1), "id", getActivity().getPackageName());
                layouts[i] = (LinearLayout) getActivity().findViewById(layoutID);
                layouts[i].setOnClickListener(new OnBannerClick(i));
            }


    }

    private class OnBannerClick implements View.OnClickListener {
        private int mPosition;

        OnBannerClick(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainListActivity.BannersArray.get(mPosition).getUrlAndroid()));
            startActivity(browserIntent);
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        populateDetails();

    }




    private void populateDetails() {

    }

}
