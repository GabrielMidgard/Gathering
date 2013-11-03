package com.example.gathering;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TermsService  extends Activity{
	public static TermsService ts_this;
	Functions function = new Functions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms_service);
		ts_this=this;
	}
	
	/*
	 * log_Out return to Register
	 */
	public void log_Out(View v) {
		this.finish();
	}
}