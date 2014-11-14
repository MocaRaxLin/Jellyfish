package com.doodlejump;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LimitPointActivity extends Activity{
	private Button btnbackchoice;
	private Button btnagain;
	private Intent intent = new Intent();
	private SQLiteDatabase pointdata;
	private TextView mEdtList;
	private static final String DB_FILE = "point.db" , DB_TABLE = "point";
	private Cursor c;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.point_layout);
		setId();
		
		btnagain.setOnClickListener(GamePage);
		btnbackchoice.setOnClickListener(ChoicePage);
		
		DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(), DB_FILE,null, 1);
        
        pointdata = databaseHelper.getWritableDatabase();
		
		c = pointdata.query(true, DB_TABLE, new String[]{"name", "score"},
				null, null, null, null, null, null);
		
//		c.moveToFirst();
//		score1.setText(c.getString(0) + c.getString(1));
		
		if (c.getCount() == 0) {
			Toast.makeText(LimitPointActivity.this, "沒有資料", Toast.LENGTH_LONG)
				.show();
		}
		else {
			c.moveToLast();
			mEdtList.setText(c.getString(0) + " , " + c.getString(1));
			while (c.moveToPrevious())
				mEdtList.append("\n" + c.getString(0) + " , " + c.getString(1));
		}
		
	}
	
	//重新開始遊戲(尚未完成)
	private Button.OnClickListener GamePage = new Button.OnClickListener(){
		@Override
		public void onClick(View v) { 
			intent.setClass(LimitPointActivity.this,LimitMode.class);
			startActivity(intent);
			finish();
		}
	};

	//返回選擇模式
	private Button.OnClickListener ChoicePage = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			intent.setClass(LimitPointActivity.this,DifficultyActivity.class);
			startActivity(intent);
			finish();
		}
	};
	
	private void setId() {
		btnbackchoice = (Button)findViewById(R.id.btnbackchoice);
		btnagain = (Button)findViewById(R.id.btnagain);
		mEdtList = (TextView)findViewById(R.id.edittext);
		
		
	}
}