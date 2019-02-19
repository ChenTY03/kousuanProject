package com.example.susuanganme;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivitu extends Activity {

	MyDatabaseHelper mydata;
	EditText edtime,edresult;
	Button btsuanfa,btweishu,btstart,btOK,bttuichu;
	TextView tvfuhao,tvshuzi1,tvshuzi2,tvjishi,tvjifen,tvdate;
	int t=0;
	Timer time = null;
	TimerTask timek = null;
	String str,fenshu,username;
	int x,y;
	int jifen=0;
	int result=0;
	final String[] fuhao = {"+","-","×","÷"};
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_activity);
        Intent out = getIntent();
        username = out.getStringExtra("username");
        
        AssetManager mgr = getAssets();
        Typeface tf = 	Typeface.createFromAsset(mgr, "font/hkwwt.ttf");
        
      //获取日期

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strdate = sDateFormat.format(new java.util.Date()); 
		 
		 String dbName = "fenshu.db3";
		 mydata = new MyDatabaseHelper(this,dbName, null, 1);
		 
        edtime = (EditText) findViewById(R.id.ed_time);
        edresult = (EditText) findViewById(R.id.ed_result);
        
        edtime.setTypeface(tf);
        edresult.setTypeface(tf);
        
        tvfuhao = (TextView) findViewById(R.id.tv_fuhao);
        tvshuzi1 = (TextView) findViewById(R.id.tv_shuzi1);
        tvshuzi2 = (TextView) findViewById(R.id.tv_shuzi2);
        tvjishi = (TextView) findViewById(R.id.tv_jishi);
        tvjifen = (TextView) findViewById(R.id.tv_jifen);
        tvdate = (TextView) findViewById(R.id.tv_date);
        
        tvdate.setTypeface(tf);
        tvfuhao.setTypeface(tf);
        tvshuzi1.setTypeface(tf);
        tvshuzi2.setTypeface(tf);
        tvjishi.setTypeface(tf);
        tvjifen.setTypeface(tf);
        
        btsuanfa = (Button) findViewById(R.id.bt_suanfa);
        btweishu = (Button) findViewById(R.id.bt_weishu);
        btstart = (Button) findViewById(R.id.bt_start);
        btOK = (Button) findViewById(R.id.bt_OK);
        bttuichu = (Button) findViewById(R.id.bt_tuichu);
        
        btsuanfa.setTypeface(tf);
        btweishu.setTypeface(tf);
        btstart.setTypeface(tf);
        btOK.setTypeface(tf);
        bttuichu.setTypeface(tf);
        
        tvdate.setText(strdate);
        
        
        btsuanfa.setOnClickListener(new OnClickListener() {
			
			@Override
			//点击跳出选择框选择运算符号
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(GameActivitu.this);
				builder.setTitle("算法选择");
				final String[] fuhao = {"+","-","×","÷"};
				builder.setSingleChoiceItems(fuhao,-1,
						new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(GameActivitu.this,fuhao[which], 1).show();
						
						tvfuhao.setText(fuhao[which]);
						
					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.show();
			}
		});
        
        //点击跳出选择框选择随即生成的数字的位数；
        btweishu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(GameActivitu.this);
				builder.setTitle("位数选择");
				final String[] weishu = {"个位数","十位数","百位数"};
				builder.setSingleChoiceItems(weishu,-1,
						new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(GameActivitu.this,weishu[which], 1).show();
						
						btweishu.setText(weishu[which]);
						
					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.show();
			}
		});
        //开始游戏
        btstart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String stredtime = edtime.getText().toString().trim();
				if(tvfuhao.getText().equals("+")||tvfuhao.getText().equals("-")||
				   tvfuhao.getText().equals("×")||tvfuhao.getText().equals("÷")){
					if(btweishu.getText().equals("个位数")||btweishu.getText().equals("十位数")||btweishu.getText().equals("百位数")){
					if(stredtime.equals("请输入时间")||stredtime.equals("")){
						Toast.makeText(GameActivitu.this, "还未输入时间", Toast.LENGTH_SHORT).show();
					}else{
				if(btweishu.getText().equals("个位数")){
					SuiJiShu1();
				}
				if(btweishu.getText().equals("十位数")){
					SuiJiShu2();
					}
				if(btweishu.getText().equals("百位数")){
					SuiJiShu3();
					}
				tvjishi.setText(edtime.getText().toString());
				t = Integer.parseInt(edtime.getText().toString());
				
				
					StartTime();
					}
					}else{
						Toast.makeText(GameActivitu.this, "请选择数字位数", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(GameActivitu.this, "请选择算法", Toast.LENGTH_SHORT).show();
				}

			}
		});
        //确定自己的计算结果并与程序计算的结果进行比较
        btOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					String shuru = edresult.getText().toString().trim();
					int result = jisuan();
					str = (result + "").trim();
					//edresult.setText(str);
					if(shuru.equals("")){
						Toast.makeText(GameActivitu.this, "请输入你的答案", Toast.LENGTH_SHORT).show();
					}else{
					if(shuru.equals(str)){
						int jifen = defen();
						fenshu = jifen + "";
						tvjifen.setText(fenshu);
						if(btweishu.getText().equals("个位数")){
							SuiJiShu1();
						}
						if(btweishu.getText().equals("十位数")){
							SuiJiShu2();
							}
						if(btweishu.getText().equals("百位数")){
							SuiJiShu3();
							}
						edresult.setText("");
						Toast.makeText(GameActivitu.this, "答案正确", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(GameActivitu.this, "答案错误", Toast.LENGTH_SHORT).show();
					}
					}
				
			}
		});
        //退出游戏界面
        bttuichu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strtime = edtime.getText().toString().trim();
				if(strtime.equals("请输入时间")){
					finish();
				}else{
				time.cancel();
				finish();
				}
			}
		});
        edtime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edtime.setText("");
			}
		});
        
        
	}
	//游戏结束提醒
	private void GameOver(){
		if(tvjishi.getText().equals("0")){
        	StopTime();
        	AlertDialog.Builder adb = new AlertDialog.Builder(this);
        	adb.setTitle("游戏提示");
        	adb.setMessage("时间到，游戏结束");
        	adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String date = tvdate.getText().toString().trim();
					String suanfa = tvfuhao.getText().toString().trim();
					String yongshi = edtime.getText().toString().trim();
					String fenshu = tvjifen.getText().toString().trim();
					
					
					String insert = "insert into fenshu values(?,?,?,?,?,?)";
					mydata.getReadableDatabase().execSQL(insert,new String[] {null,username,date,suanfa,yongshi,fenshu});
					Toast.makeText(GameActivitu.this, "分数保存", Toast.LENGTH_SHORT).show();
					
					Intent in = new Intent();
					in.setClass(GameActivitu.this, JiFenActivity.class);
					startActivity(in);
					finish();
					
				}
			});
			
			adb.show();
        }
	}
	//随即获得1个数字
	private void SuiJiShu1(){
		Random rd= new Random();
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>();
		while(set1.size()<1){
			int shuzi1 = rd.nextInt(10);
			set1.add(shuzi1);
			
		}
		while(set2.size()<1){
			
			int shuzi2 = rd.nextInt(10);
			set2.add(shuzi2);
		}
		StringBuffer sz1 = new StringBuffer();
		StringBuffer sz2 = new StringBuffer();
		for(Integer i:set1){
			sz1.append(""+i);
		}
		for(Integer i:set2){
			sz2.append(""+i);
		}
		tvshuzi1.setText(sz1.toString());
		tvshuzi2.setText(sz2.toString());
	}
	//随即获得两个数字
	private void SuiJiShu2(){
		Random rd= new Random();
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>();
		while(set1.size()<2){
			int shuzi1 = rd.nextInt(10);
			set1.add(shuzi1);
			
		}
		while(set2.size()<2){
			
			int shuzi2 = rd.nextInt(10);
			set2.add(shuzi2);
		}
		StringBuffer sz1 = new StringBuffer();
		StringBuffer sz2 = new StringBuffer();
		for(Integer i:set1){
			sz1.append(""+i);
		}
		for(Integer i:set2){
			sz2.append(""+i);
		}
		tvshuzi1.setText(sz1.toString());
		tvshuzi2.setText(sz2.toString());
	}
	//随即获得一个3个数字
	private void SuiJiShu3(){
		Random rd= new Random();
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>();
		while(set1.size()<3){
			int shuzi1 = rd.nextInt(10);
			set1.add(shuzi1);
			
		}
		while(set2.size()<3){
			
			int shuzi2 = rd.nextInt(10);
			set2.add(shuzi2);
		}
		StringBuffer sz1 = new StringBuffer();
		StringBuffer sz2 = new StringBuffer();
		for(Integer i:set1){
			sz1.append(""+i);
		}
		for(Integer i:set2){
			sz2.append(""+i);
		}
		tvshuzi1.setText(sz1.toString());
		tvshuzi2.setText(sz2.toString());
	}
	//时间开始倒计时
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			tvjishi.setText(msg.arg1+"");
			StartTime();
			GameOver();
			
		};
	};
	private void StartTime(){
		if(time==null){
			time = new Timer();
		}
		timek = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				t--;
				Message message = Message.obtain();
				message.arg1=t;
				mHandler.sendMessage(message);
			}
		};
		time.schedule(timek, 1000);
	}
	
	private void StopTime(){
		if(time!=null){
			time.cancel();
		}
	}
	//得到对比的运算结果
	protected int jisuan() {
		// TODO Auto-generated method stub
		
		String fuhao = tvfuhao.getText().toString();
		String n_str1 = tvshuzi1.getText().toString();
		String n_str2 = tvshuzi2.getText().toString();
		
		int n1 = (int) Double.parseDouble(n_str1);
		int n2 = (int) Double.parseDouble(n_str2);
		if(fuhao.equals("+"))
		{
			result=n1+n2;
			
			
		}
		if(fuhao.equals("-"))
		{
			result=n1-n2;
		}
		if(fuhao.equals("×"))
		{
			result=n1*n2;
		}
		if(fuhao.equals("÷"))
		{
			result=n1/n2;
		}	
		return result;
	}
	//获得积分
	protected int defen() {
		
		jifen=jifen+1;
		return jifen;
	
	}
	
}
