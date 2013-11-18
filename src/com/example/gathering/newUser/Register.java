package com.example.gathering.newUser;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.MainActivity;
import com.example.gathering.R;
import com.example.gathering.R.drawable;
import com.example.gathering.R.id;
import com.example.gathering.R.layout;
import com.example.gathering.json.PostTask;
import com.example.gathering.json.RESTClient;
import com.example.gathering.json.RESTClient.RequestMethod;
import com.example.gathering.object.Functions;
import com.example.gathering.object.UsersObject;
import com.example.gathering.serviceEnded.ServiceEnded;

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

public class Register extends Activity {
	public static Register rthis;
	com.example.gathering.object.Functions function = new Functions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setListener();
		rthis = this;
	}

	/*
	 * setListener() This feature alone makes the change of image for the button
	 * to give the effect that is being precionado
	 */
	void setListener() {
		final ImageButton imgb = (ImageButton) this
				.findViewById(R.id.imageButton1);
		imgb.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					imgb.setImageDrawable(getApplicationContext()
							.getResources().getDrawable(R.drawable.btn_pressed));
				} else {
					imgb.setImageDrawable(getApplicationContext()
							.getResources().getDrawable(
									R.drawable.btn_unpressed));
				}
				return false;
			}

		});
	}

	public void validateRegistration(View v) {
		EditText editTextEmail = (EditText) this.findViewById(R.id.txtEmail);
		EditText editTextName = (EditText) this.findViewById(R.id.txtName);
		EditText editTextPassword1 = (EditText) this
				.findViewById(R.id.txtPassword1);
		EditText editText_Password2 = (EditText) this
				.findViewById(R.id.txtPassword2);
		CheckBox press_checkBox = (CheckBox) this
				.findViewById(R.id.checkBoxAccept);

		String pass1 = editTextPassword1.getText().toString();
		String pass2 = editText_Password2.getText().toString();

		boolean valid_Email = function.isValidEmail(editTextEmail.getText()
				.toString());

		if (valid_Email == false) {
			editTextEmail.setError("Check your email account");
			Toast.makeText(this, "Check your email account.", 2000).show();
		}

		else if (editTextName.getText().toString().length() == 0) {
			editTextName.setError("Enter your full name.");
			Toast.makeText(this, "Enter your full name.", 2000).show();
		}

		else if (editTextPassword1.getText().toString().length() == 0) {
			editTextPassword1.setError("Enter your password account.");
			Toast.makeText(this, "Enter your password account.", 2000).show();
		}

		else if (editText_Password2.getText().toString().length() == 0) {
			editText_Password2
					.setError("Enter your password again to confirm.");
			Toast.makeText(this, "Enter your password again to confirm.", 2000)
					.show();
		}

		else if (!(pass1.equals(pass2))) {
			editText_Password2.setError("The password does not match.");
		}

		else if (press_checkBox.isChecked() == false) {
			Toast.makeText(this, "accept the terms of use", 2000).show();
		} else {
			UsersObject user = new UsersObject();
			Functions function = new Functions();

			user.setName(function.validName(editTextName.getText().toString()));
			
			
			Toast.makeText(this, user.getName(), 2000).show();
			// user.setName(editText_Name.getText().toString());
			
			user.setEmail(editTextEmail.getText().toString());
			user.setPassword(editTextPassword1.getText().toString());

			sendRegister(user);
		}
	}

	/*
	 * sendRegister receives an object of type Users_object and sends this to
	 * the class parameters where the process will be split to send the json
	 * with the parameters listed and validate it to run properly
	 */
	public void sendRegister(UsersObject user) {
		try {
			PostTask post_task = new PostTask(2, user);
			post_task.rthis = this;
			post_task.execute("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * sendServer This is call is sent from the class Posttask receives an
	 * object of type Users_object Sending the parameters are required for
	 * registration of a new user the server*
	 */
	public static RESTClient sendServer(UsersObject user) {
		RESTClient post = new RESTClient(
				"http://api.gthrng.com/gathering/signup?email="
						+ user.getEmail() + "&name=" + user.getName()
						+ "&password=" + user.getPassword());
		try {
			post.Execute(RESTClient.RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	/*
	 * receive_json This is call is sent from the class Posttask Read the answer
	 * which gives the server after it has received the json
	 */
	public static void receiveJson(JSONObject json) {
		try {
			if (json.getBoolean("success") == false) {
				Toast.makeText(rthis, "The parameters are incorrect", 2000)
						.show();
			} else {
				Toast.makeText(rthis, "user created successfully", 2000).show();
				rthis.finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.e("json", json.toString());

	}

	/*
	 * log_Out return to Login
	 */
	public void logOut(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	// Error
	public void menssageErrorNoConnexion() {
		Toast.makeText(this, "The server cannot be reached.", 2000).show();
	}

	/*
	 * /*showTermsService
	 * 
	 * opens the intent the Terms of Service
	 */
	public void showTermsService(View v) {
		// Intent intent = new Intent(this, TermsService.class);
		startActivity(new Intent(this, ServiceEnded.class));
		// this.finish();
		// Toast.makeText(this, "Prueba.", 2000).show();
	}
}
