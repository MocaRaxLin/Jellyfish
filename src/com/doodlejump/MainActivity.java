package com.doodlejump;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private ImageButton startButton;
	private ImageButton aboutButton;
	LinearLayout layout;
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setId();
		aboutButton.setOnClickListener(aboutPage);
		startButton.setOnClickListener(nextPage);
	}
	private ImageButton.OnClickListener nextPage = new ImageButton.OnClickListener(){
		@Override
		public void onClick(View v) {
			intent.setClass(MainActivity.this,DifficultyActivity.class);
			startActivity(intent);
			//MainActivity.this.finish();
		}
	};
	private ImageButton.OnClickListener aboutPage = new ImageButton.OnClickListener(){
		@Override
		public void onClick(View v) {
			intent.setClass(MainActivity.this,AboutPrograme.class);
			startActivity(intent);
			//MainActivity.this.finish();
		}
	};
	private void setId() {
		layout = (LinearLayout)findViewById(R.layout.activity_main);
		startButton = (ImageButton)findViewById(R.id.start_button);
		aboutButton = (ImageButton)findViewById(R.id.about_button);
	}
	/** 翴牟北ㄆン矪瞶
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 眔牟北翴计
		int pointTouched = event.getPointerCount();
		Toast.makeText(getApplicationContext(),""+pointTouched,Toast.LENGTH_SHORT).show();
		float x, y;
		int j = 0;
		for (int i = 0; i < pointTouched; i++) {
			// 眔牟北翴ぇ牟北竚畒夹
			x = event.getX();
			y = event.getY();
			j++;
			//Toast.makeText(getApplicationContext(),x+" , "+y+" , "+j+"/"+pointTouched,Toast.LENGTH_SHORT).show();
		}
		return true;
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
