package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.app.GlobalVars;
import com.comrax.mouseappandroid.database.DBConstants;
import com.comrax.mouseappandroid.http.RequestTask;
import com.comrax.mouseappandroid.http.RequestTaskDelegate;
import com.comrax.mouseappandroid.http.RequestTaskGet;

import java.io.File;

/**
 * Created by bez on 12/07/2015.
 */
public class PlaceActivity extends MyBaseDrawerActivity implements RequestTaskDelegate {

    int myRating;
    TextView boneTextView, dareg;

    String imagePath, name, hebName, description, address, type,
            phone, activityHours, publicTransportation, responses;


    Button b1, b2, b3, b4;
    ImageView image1, image2, image3, image4;
    LinearLayout layout1, layout2, layout3, layout4;

    RatingBar rating;

    Bundle bundle;
    Cursor cursor;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.place_full_layout;
    }

    @Override
    protected String getTextForAppBar() {
        return myInstance.getCityName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        cursor = dbTools.getData(DBConstants.PLACE_TABLE_NAME, DBConstants.name, bundle.getString(DBConstants.name, null), DBConstants.objId, bundle.getString(DBConstants.objId, null));

        setBoneTitleAndColor();

        setUpperData();

        setGraysWithExtras();

        set2GreenButtons();

        setServiceItems();

        setFooterAd();
    }


    private void set2GreenButtons() {

        Button btnComment = (Button) findViewById(R.id.detailed_place_comment_btn);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceActivity.this, AddCommentActivity.class));
//                Toast.makeText(getApplicationContext(), "btn", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnEmailThem = (Button) findViewById(R.id.detailed_place_email_them_btn);
        btnEmailThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "customer-mouse@mouse.co.il", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "עדכנו אותנו - אפליקציית עכבר עולם");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "שם המקום: " + "\n" + name + "\n" + hebName + "\n\n" + "שם העיר: " + myInstance.getCityName());
                startActivity(Intent.createChooser(emailIntent, "Email עכבר העיר"));
            }
        });

    }


    private void setBoneTitleAndColor() {
        boneTextView = (TextView) findViewById(R.id.bone_title);
        String boneTitle = myInstance.get_boneIdTitle();
        boneTextView.setText(boneTitle);

        int pos = myInstance.getBonePosition();
        if(pos!=0)
            boneTextView.setBackgroundColor(GlobalVars.boneColors[pos-1]);
    }


    private void setUpperData() {
        imagePath = cursor.getString(cursor.getColumnIndex(DBConstants.image));
        name = cursor.getString(cursor.getColumnIndex(DBConstants.name));
        hebName = cursor.getString(cursor.getColumnIndex(DBConstants.hebrewName));
        description = cursor.getString(cursor.getColumnIndex(DBConstants.description));
        address = cursor.getString(cursor.getColumnIndex(DBConstants.address));
        type = cursor.getString(cursor.getColumnIndex(DBConstants.type));
        phone = cursor.getString(cursor.getColumnIndex(DBConstants.phone));
        activityHours = cursor.getString(cursor.getColumnIndex(DBConstants.activityHours));
        publicTransportation = cursor.getString(cursor.getColumnIndex(DBConstants.publicTransportation));
        responses = cursor.getString(cursor.getColumnIndex(DBConstants.responses));

        String fullDescription = new StringBuilder().append("<![CDATA[")
                .append("<html><head><style>")
                .append("body{font-family:arial;font-size:17px;direction:rtl;background:none;}")
                .append("</style></head><body>")
                .append(description)
                .append("</body></html>")
                .append("]]>")
                .toString();


        ImageView headImageView = (ImageView) findViewById(R.id.detailed_place_head_imageView);


        File file = new File(myInstance.get_cityFolderName() + "/" + imagePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            headImageView.setImageBitmap(bitmap);
        }

        TextView titleView = (TextView) findViewById(R.id.detailed_place_english_title);
        titleView.setText(name);

        TextView hebTitleView = (TextView) findViewById(R.id.detailed_place_hebrew_title);
        hebTitleView.setText(hebName);

        TextView addressView = (TextView) findViewById(R.id.detailed_place_address);
        addressView.setText(address);

        TextView mainDetailedText = (TextView) findViewById(R.id.detailed_place_main_text);
        String html = Html.fromHtml(fullDescription).toString();
        String html2 = Html.fromHtml(html).toString();

        mainDetailedText.setText(Html.fromHtml(html2));

        mainDetailedText.setMovementMethod(LinkMovementMethod.getInstance());


        rating = (RatingBar) findViewById(R.id.open_details_item_ratingBar);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(getApplicationContext(), "rating: " + ratingBar.getRating(), Toast.LENGTH_LONG).show();
                myRating = (int) ratingBar.getRating();
            }
        });


        dareg = (TextView) findViewById(R.id.open_details_item_dareg_textBtn);
        dareg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daregClicked();
            }
        });

        Button favoritesBtn = (Button) findViewById(R.id.detailed_place_prefs_button);
        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(PlaceActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_fav_dialog);

                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                Button addFavButton = (Button) dialog.findViewById(R.id.add_fav_btn);
                addFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbTools.insertPlaceFavorite(cursor);
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


    private void setGraysWithExtras() {

        TextView phoneTitle = (TextView) findViewById(R.id.detailed_place_phone_title);
        TextView activityHoursTitle = (TextView) findViewById(R.id.detailed_place_activity_hours_title);
        TextView publicTransportationTitle = (TextView) findViewById(R.id.detailed_place_public_transportation_title);
        TextView responsesTitle = (TextView) findViewById(R.id.detailed_place_responses_title);

        LinearLayout phoneLayout = (LinearLayout) findViewById(R.id.detailed_place_phone_layout);


        final TextView phoneView = (TextView) findViewById(R.id.detailed_place_phone_num);
        TextView activityHoursView = (TextView) findViewById(R.id.detailed_place_activity_hours);
        TextView publicTransportationView = (TextView) findViewById(R.id.detailed_place_public_transportation);
        TextView responsesView = (TextView) findViewById(R.id.detailed_place_responses);


        if (phone.length() > 8) {
            phoneTitle.setVisibility(View.VISIBLE);
            phoneLayout.setVisibility(View.VISIBLE);
            phoneView.setText(phone);

            phoneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:+" + phoneView.getText().toString().trim()));
                    startActivity(callIntent);
                }
            });

        }

        if (!activityHours.equals("")) {
            activityHoursTitle.setVisibility(View.VISIBLE);
            activityHoursView.setVisibility(View.VISIBLE);
            activityHoursView.setText(activityHours);

        }

        if (!publicTransportation.equals("")) {
            publicTransportationTitle.setVisibility(View.VISIBLE);
            publicTransportationView.setVisibility(View.VISIBLE);
            publicTransportationView.setText(publicTransportation);

        }

        if (!responses.equals("[]")) {
            responsesTitle.setVisibility(View.VISIBLE);
            responsesView.setVisibility(View.VISIBLE);
            responsesView.setText(responses);

        }
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


    private void setServiceItems() {

        TextView[] textViews = {b1, b2, b3, b4};
        ImageView[] images = {image1, image2, image3, image4};
        LinearLayout[] layouts = {layout1, layout2, layout3, layout4};


        for (int i = 0; i < MainGridActivity.BannersArray.size(); i++) {

            int buttonID = getResources().getIdentifier("footer_item_title_" + (i + 1), "id", getPackageName());
            textViews[i] = (TextView) findViewById(buttonID);
            textViews[i].setText(MainGridActivity.BannersArray.get(i).getText());


            int imageID = getResources().getIdentifier("footer_item_image_" + (i + 1), "id", getPackageName());
            images[i] = (ImageView) findViewById(imageID);

            File file = new File(GlobalVars.trialMethod(getApplicationContext(), "Default_master/" + MainGridActivity.BannersArray.get(i).getImageBIG()));
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                images[i].setImageBitmap(bitmap);
            }

            int layoutID = getResources().getIdentifier("footer_item_layout_" + (i + 1), "id", getPackageName());
            layouts[i] = (LinearLayout) findViewById(layoutID);
            layouts[i].setOnClickListener(new OnBannerClick(i));
        }


    }

    @Override
    public void onTaskPOSTCompleted(String result, RequestTask task) {

    }

    @Override
    public void onTaskGETCompleted(String result, RequestTask task) {
        Log.wtf("task", "" + task);
        dareg.setVisibility(View.GONE);

    }


    private class OnBannerClick implements View.OnClickListener {
        private int mPosition;

        OnBannerClick(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Intent fullAdIntent = new Intent(PlaceActivity.this, FullAdActivity.class);
            fullAdIntent.putExtra("adNum",mPosition);
            startActivity(fullAdIntent);
        }
    }


    public void daregClicked() {
        String url = String.format("http://www.mouse.co.il/appMouseWorldServiceRequest.ashx?appName=master@mouse.co.il&method=addNewRate" +
                        "&rate=%d" +
                        "&boneId=%s" +
                        "&nsId=%s" +
                        "&objId=%s",
                myRating, myInstance.get_boneId(), myInstance.get_nsId(), myInstance.get_objId()
        );
        if (myRating > 0)
            new RequestTaskGet(this).execute(url, null);
        //onGet, change the DAREG btn look.

    }


}
