package com.comrax.mouseappandroid.activities_N_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.comrax.mouseappandroid.R;

/**
 * Created by bez on 13/07/2015.
 */
public class AddCommentActivity extends Activity {

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
        Toast.makeText(getApplicationContext(), "senddd", Toast.LENGTH_SHORT).show();
    }



}
