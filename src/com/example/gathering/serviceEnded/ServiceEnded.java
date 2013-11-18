package com.example.gathering.serviceEnded;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.R;
import com.example.gathering.R.layout;
import com.example.gathering.object.Functions;

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

public class ServiceEnded  extends Activity{
	public static ServiceEnded ts_this;
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
	public void logOut(View v) {
		this.finish();
	}
}