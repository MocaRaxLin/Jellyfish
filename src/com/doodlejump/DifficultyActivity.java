package com.doodlejump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DifficultyActivity extends Activity {
	private ImageView jellyfishIcon1;
	private ImageView jellyfishIcon2;
	private Animation am1;//上升動畫
	private Animation am2;
	private Animation am1b;//下降動畫
	private Animation am2b;
	private ImageButton difficulty1;//極限模式
	private ImageButton difficulty2;//道具模式
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.difficulty_layout);
		jellyfishIcon1 = (ImageView)findViewById(R.id.jellyfishicon);
		jellyfishIcon2 = (ImageView)findViewById(R.id.jellyfishicon1);
		difficulty1 = (ImageButton)findViewById(R.id.difficulty1);
		difficulty2 = (ImageButton)findViewById(R.id.difficulty2);
		difficulty1.setOnClickListener(play);
		difficulty2.setOnClickListener(play);
		//動畫路徑設定(x1,x2,y1,y2)
		am1 = new TranslateAnimation(0.0f,0.0f,0.0f,-550.0f);
		am2 = new TranslateAnimation(0.0f,0.0f,0.0f,-550.0f);
		am1b = new TranslateAnimation(0.0f,0.0f,-550.0f,0.0f);
		am2b = new TranslateAnimation(0.0f,0.0f,-550.0f,0.0f);
		
		//動畫開始到結束的時間
		am1.setDuration( 1500 );
		am2.setDuration( 1500 );
		am1b.setDuration( 1700 );
		am2b.setDuration( 1700 );
		
		//動畫監聽
		am1.setAnimationListener(aniListener);
		am1b.setAnimationListener(aniListener);
		//動畫是否有加減速度以及結束後是否留在原地
		am1.setInterpolator(new DecelerateInterpolator());
		am1.setFillEnabled(true);
		am1.setFillAfter(true);
		am2.setInterpolator(new DecelerateInterpolator());
		am2.setFillEnabled(true);
		am2.setFillAfter(true);
		am1b.setInterpolator(new AccelerateDecelerateInterpolator());
		am1b.setFillEnabled(true);
		am1b.setFillAfter(true);
		am2b.setInterpolator(new AccelerateDecelerateInterpolator());
		am2b.setFillEnabled(true);
		am2b.setFillAfter(true);
		//開始動畫
		jellyfishIcon1.startAnimation(am1);
		jellyfishIcon2.startAnimation(am2);
		
	}
	final private AnimationListener aniListener = new AnimationListener(){
		@Override//每個動畫結束後切換成另個動畫
		public void onAnimationEnd(Animation animation) {
			if(animation == am1){
				jellyfishIcon1.startAnimation(am1b);
				jellyfishIcon2.startAnimation(am2b);
			}
			if(animation == am1b){
				jellyfishIcon1.startAnimation(am1);
				jellyfishIcon2.startAnimation(am2);
			} 
		}
		@Override
		public void onAnimationRepeat(Animation animation) {
		}
		@Override
		public void onAnimationStart(Animation animation) {
		}	
	};
	private ImageButton.OnClickListener play = new ImageButton.OnClickListener(){
		@Override
		public void onClick(View v) {
			if(v== difficulty1){
				Intent it = new Intent();
				it.setClass(DifficultyActivity.this, LimitMode.class);
				startActivity(it);
				finish();
				//執行極限模式
			}else{
				Intent it = new Intent();
				it.setClass(DifficultyActivity.this, PropsMode.class);
				startActivity(it);
				finish();
				//執行道具模式
			}
		}
	};
}
