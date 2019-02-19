package com.example.susuanganme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class JiFenActivity extends Activity {
	
	MyDatabaseHelper mydata;
	
	ListView lv;
	Button btfanhui,btagain;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.jifen_activity);
        
        Intent out = getIntent();
        
        String dbName = "fenshu.db3";
		 mydata = new MyDatabaseHelper(this,dbName, null, 1);
        
        lv = (ListView) findViewById(R.id.lv_fenshu);
        btfanhui = (Button) findViewById(R.id.bt_fanhui);
        btagain = (Button) findViewById(R.id.bt_again);
        
        lv = SelectData();
        
        btfanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inexit = new Intent();
				inexit.setClass(JiFenActivity.this, MainActivity.class);
				startActivity(inexit);
				finish();
			}
		});
        btagain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(JiFenActivity.this, GameActivitu.class);
				startActivity(in);
				finish();
			}
		});
	}
	private ListView SelectData(){
		try{
		
		String select = "SELECT * FROM fenshu";
		Cursor cur = mydata.getReadableDatabase().rawQuery(select, null);
		if(cur!=null){
			ListAdapter adapter = new SimpleCursorAdapter(this,R.layout.jifen_item, cur, new String[] {"username","fenshu_date","fenshu_suanfa","fenshu_jishi","fenshu_jifen"}, 
					new int[] {R.id.tv_iusername,R.id.tv_idate,R.id.tv_isuanfa,R.id.tv_ijishi,R.id.tv_ijifen});
			
			lv.setAdapter(adapter);
			
		}
		}catch(Exception e){
			
		}
		return lv;
		
	}
}
