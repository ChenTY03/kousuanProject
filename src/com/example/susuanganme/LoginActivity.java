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

public class LoginActivity extends Activity {
	MyDataHelper mydatahelper;
	
	Button btnlogin,btnreg;
	EditText ed_L_user,ed_L_pwd;
	
	String userid,userpwd,username;
	
	boolean flag=true;
	String uid,upwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnlogin = (Button)findViewById(R.id.btn_login);
        btnreg = (Button)findViewById(R.id.btn_Reg);
        ed_L_user = (EditText)findViewById(R.id.ed_L_user);
        ed_L_pwd = (EditText)findViewById(R.id.ed_L_pwd); 
        
        mydatahelper = new MyDataHelper(this, "user.db", null, 2);
        
        btnreg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
        btnlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userid = ed_L_user.getText().toString().trim();
				 userpwd = ed_L_pwd.getText().toString().trim();
				
				
				Cursor cursor = mydatahelper.getWritableDatabase().query("user", new String[]{"user_id","user_name","user_pwd"}, null, null, null, null, null);
				while(cursor.moveToNext()){
					uid = cursor.getString(cursor.getColumnIndex("user_id"));
					upwd = cursor.getString(cursor.getColumnIndex("user_pwd"));
					username = cursor.getString(cursor.getColumnIndex("user_name"));
					if(uid.equals(userid)&&upwd.equals(userpwd)){
						
						Intent intent = new Intent(LoginActivity.this,MainActivity.class);
						intent.putExtra("loginName", username);
						startActivity(intent);
						flag=false;
						break;
					}
				}
				if(flag){
					Toast.makeText(LoginActivity.this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
					ed_L_user.setText("");
					ed_L_pwd.setText("");
				}
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
