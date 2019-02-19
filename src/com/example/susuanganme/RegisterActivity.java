package com.example.susuanganme;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	MyDataHelper myDataHelper;
	Button btnfinReg,btnback;
	EditText eduser,edname,edpwd;
	boolean flag=true;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		btnfinReg = (Button)findViewById(R.id.btn_finReg);
		btnback = (Button)findViewById(R.id.btn_back);

		eduser = (EditText)findViewById(R.id.ed_user);
		edname = (EditText)findViewById(R.id.ed_name);
		edpwd = (EditText)findViewById(R.id.ed_pwd);
		
		String dbName = "user.db";
		myDataHelper = new MyDataHelper(this, dbName, null, 2);
		
		btnfinReg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String str_user = eduser.getText().toString().trim();
				String str_name = edname.getText().toString().trim();
				String str_pwd = edpwd.getText().toString().trim();
				Cursor cursor = myDataHelper.getWritableDatabase().query("user", new String[]{"user_id"}, null, null, null, null, null);
				
				while(cursor.moveToNext()){
					String u = cursor.getString(cursor.getColumnIndex("user_id"));
					if(u.equals(str_user)){
						flag=false;
						Toast.makeText(RegisterActivity.this, "该账号已被注册", Toast.LENGTH_SHORT).show();
						eduser.setText("");
						edname.setText("");
						edpwd.setText("");
						break;
					}
					else {
						flag=true;
					}
				}
				if(flag){
					String sql_insert = "insert into user values(?,?,?)";
					myDataHelper.getReadableDatabase().execSQL(sql_insert, new String[]{str_user,str_name,str_pwd});
					Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
