package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comrax.mouseappandroid.R;
import com.comrax.mouseappandroid.http.RequestTask;
import com.comrax.mouseappandroid.http.RequestTaskDelegate;

/**
 * Created by bez on 13/07/2015.
 */
public class AddCommentActivity extends Activity implements RequestTaskDelegate{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_layout);


        Button btnClose = (Button) findViewById(R.id.close_comment_activity_btn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Button btnSend = (Button) findViewById(R.id.send_message_btn);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


        EditText editText = (EditText) findViewById(R.id.edit_message);

        editText.setHorizontallyScrolling(false);
        editText.setMaxLines(Integer.MAX_VALUE);

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void sendMessage() {
// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_fav_dialog);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        // set the custom dialog components - text, image and button

        Button addFavButton = (Button) dialog.findViewById(R.id.add_fav_btn);
        // if button is clicked, close the custom dialog
        addFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onTaskPOSTCompleted(String result, RequestTask task) {

    }

    @Override
    public void onTaskGETCompleted(String result, RequestTask task) {

    }

//    if (_place != nil) {
//        urlString = [NSString stringWithFormat:@"http://www.mouse.co.il/appMouseWorldServiceRequest.ashx?appName=master@mouse.co.il&method=addNewResponses&boneId=%@&nsId=%@&objId=%@", _place.boneId, _place.nsId, _place.objId];

//    postParams = String.format("{\"password\":\"%s\"" + "," + " \"phone\":\"%s\"}", editTextCode.getText(), editTextPhone.getText());

//    new RequestTaskPOST(this).execute(urlString, postParams);

}
