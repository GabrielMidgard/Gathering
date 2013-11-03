package com.example.gathering;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

public class AsyncInit extends AsyncTask<String, Integer, Boolean>{
	//private ProgressDialog diaglog:
	protected Context applicationContext;
	public splash splashScreend;
	//public PABS_SPLASH context=null;
	String user="";
	
	/*
	 * OnPreExcute
	 * this method is called before execute for do another validations
	 * 
	 * */
	protected void onPreExcute()
	{
		//YellowBrick.mthis.pd = ProgressDialog.show(applicationContext, "checking internet connection","trying to reach the page",true);
	}
	
	/* 
	 * doInBackground method
	 * this method do all the Async task we can receive r manage anothr url*/
	@Override
	protected Boolean doInBackground(String... arg0) {
		long actual =SystemClock.uptimeMillis();
		while(SystemClock.uptimeMillis()-actual<2000);
		
		//return YellowBrick.canReach(YellowBrick.base_url);
		return true;
	}
	
	
	/*
	 * onPostExecute*/
	protected void onPostExecute(Boolean result)
	{
		splashScreend.iniciar_mainActivity();
	}
}
