package com.doodlejump;

import android.app.Activity;
import android.os.Bundle;

public class AboutPrograme extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);

	}
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
	
}
