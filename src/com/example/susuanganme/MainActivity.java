package com.example.susuanganme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{

	Button btingame,btchaxun,btexit;
	
	String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        btingame = (Button) findViewById(R.id.bt_ingame);
        btchaxun = (Button) findViewById(R.id.bt_chaxun);
        btexit = (Button) findViewById(R.id.bt_exit);
        
        btingame.setOnClickListener(this);
        btchaxun.setOnClickListener(this);
        btexit.setOnClickListener(this);
        
        Intent intent = getIntent();
        username = intent.getStringExtra("loginName");
        Toast.makeText(MainActivity.this, "»¶Ó­ÓÃ»§"+username+"µÇÂ¼", Toast.LENGTH_SHORT).show();
        
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_ingame:
			Intent in = new Intent();
			in.setClass(MainActivity.this, GameActivitu.class);
			in.putExtra("username", username);
			startActivity(in);
			break;
		case R.id.bt_chaxun:
			Intent inchaxun = new Intent();
			inchaxun.setClass(MainActivity.this, JiFenActivity.class);
			startActivity(inchaxun);
			break;
		case R.id.bt_exit:	
			Intent MyIntent = new Intent(Intent.ACTION_MAIN); 
			MyIntent.addCategory(Intent.CATEGORY_HOME); 
			startActivity(MyIntent); 
			finish();
		default:
			break;
		}
	}


  
}
