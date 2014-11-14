package com.doodlejump;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LimitFinalActivity extends Activity{
	private Button btnsave;
	private Button btnpoint;
	private Button btnbackchoice;
	private Button btnagain;
	private Intent intent = new Intent();
	private SQLiteDatabase pointdata;
	private int score = 0;
	private TextView scoreView;
	private static final String DB_FILE = "LimitPoint.db", DB_TABLE = "LimitPoint";


	private EditText mEdtName;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_layout);
		setId();
		
		Intent it = getIntent();
		Bundle bundle = it.getExtras();
		score = bundle.getInt("point");
		
		scoreView.setTextColor(Color.WHITE);
		scoreView.setText(""+score);
		btnsave.setOnClickListener(inClickBtnAdd);
		btnagain.setOnClickListener(GamePage);
		btnpoint.setOnClickListener(PointPage);
		btnbackchoice.setOnClickListener(DifficultyPage);
		
		DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(), DB_FILE,null, 1);
		databaseHelper.sCreateTableCommand = "CREATE TABLE " + DB_TABLE + "(_id INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "score INTEGER);";
        pointdata = databaseHelper.getWritableDatabase();
		
	}
	private void setId() {
		btnsave = (Button)findViewById(R.id.btnsave);
		btnpoint = (Button)findViewById(R.id.btnpoint);
		btnbackchoice = (Button)findViewById(R.id.backchoice);
		btnagain = (Button)findViewById(R.id.btnagain);
		scoreView = (TextView)findViewById(R.id.point);
		mEdtName = (EditText)findViewById(R.id.player_name);
	}
	//重新開始遊戲(尚未完成)
	private Button.OnClickListener GamePage = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			intent.setClass(LimitFinalActivity.this,LimitMode.class);
			startActivity(intent);
			finish();
		}
	};

	//查看排行榜
	private Button.OnClickListener PointPage = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			intent.setClass(LimitFinalActivity.this,LimitPointActivity.class);
			startActivity(intent);
			finish();
		}
	};
	
	//返回選擇模式
	private Button.OnClickListener DifficultyPage = new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			intent.setClass(LimitFinalActivity.this,DifficultyActivity.class);
			startActivity(intent);
			finish();
		}
	};
	
	
	private Button.OnClickListener inClickBtnAdd = new Button.OnClickListener(){
		@Override
		public void onClick(View v){
			//TODO Auto-generated constructor stub
			ContentValues newRow = new ContentValues();
			newRow.put("name", mEdtName.getText().toString());
			newRow.put("score", score);
			pointdata.insert(DB_TABLE, null, newRow);
			Toast.makeText(LimitFinalActivity.this, "儲存成功", Toast.LENGTH_LONG).show();
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
