package com.comrax.mouseappandroid.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.comrax.mouseappandroid.R;

public class AmazingMainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amazing_activity_main);
	}
	
	public void bDemo1_click(View v) {
		startActivity(new Intent(this, SectionDemoActivity.class));
	}
	
	public void bDemo2_click(View v) {
		startActivity(new Intent(this, PaginationDemoActivity.class));
	}
}