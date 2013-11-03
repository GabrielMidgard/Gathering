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

public class Register extends Activity {
	public static Register rthis;
	Functions function = new Functions();

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
		EditText editText_Email = (EditText) this.findViewById(R.id.txtEmail);
		EditText editText_Name = (EditText) this.findViewById(R.id.txtName);
		EditText editText_Password1 = (EditText) this
				.findViewById(R.id.txtPassword1);
		EditText editText_Password2 = (EditText) this
				.findViewById(R.id.txtPassword2);
		CheckBox press_checkBox = (CheckBox) this
				.findViewById(R.id.checkBoxAccept);

		String pass1 = editText_Password1.getText().toString();
		String pass2 = editText_Password2.getText().toString();

		boolean valid_Email = function.isValidEmail(editText_Email.getText()
				.toString());

		if (valid_Email == false) {
			editText_Email.setError("Check your email account");
			Toast.makeText(this, "Check your email account.", 2000).show();
		}

		else if (editText_Name.getText().toString().length() == 0) {
			editText_Name.setError("Enter your full name.");
			Toast.makeText(this, "Enter your full name.", 2000).show();
		}

		else if (editText_Password1.getText().toString().length() == 0) {
			editText_Password1.setError("Enter your password account.");
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
			Users_object user = new Users_object();
			Functions function = new Functions();

			user.setName(function.validName(editText_Name.getText().toString()));
			Toast.makeText(this, user.getName(), 2000).show();
			// user.setName(editText_Name.getText().toString());
			user.setEmail(editText_Email.getText().toString());
			user.setPassword(editText_Password1.getText().toString());

			sendRegister(user);

		}
	}

	/*
	 * sendRegister receives an object of type Users_object and sends this to
	 * the class parameters where the process will be split to send the json
	 * with the parameters listed and validate it to run properly
	 */
	public void sendRegister(Users_object user) {
		try {
			Posttask post_task = new Posttask(2, user);
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
	public static RESTClient sendServer(Users_object user) {
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
	public static void receive_json(JSONObject json) {
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
	public void log_Out(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	// Error
	public void menssageError_NoConnexion() {
		Toast.makeText(this, "The server cannot be reached.", 2000).show();
	}

	/*
	 * /*showTermsService
	 * 
	 * opens the intent the Terms of Service
	 */
	public void showTermsService(View v) {
		// Intent intent = new Intent(this, TermsService.class);
		startActivity(new Intent(this, TermsService.class));
		// this.finish();
		// Toast.makeText(this, "Prueba.", 2000).show();
	}
}
