package com.example.gathering;
import com.example.gathering.AsyncInit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity{
	AsyncInit asy = new AsyncInit();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		asy.splashScreend = this;
		asy.execute("");
	}
	
	
	
	public void iniciar_mainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}
