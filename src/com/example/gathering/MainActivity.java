package com.example.gathering;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static MainActivity mthis;
	static Users_object user = new Users_object();
	Functions function = new Functions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mthis = this;

		EditText Key_Text_Email = (EditText) findViewById(R.id.txtName);
		EditText Key_editText_Password = (EditText) findViewById(R.id.txtPassword);

		Key_Text_Email.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						validateFields();
					}
				}
				return false;
			}
		});

		Key_editText_Password.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						validateFields();
					}
				}
				return false;
			}
		});

		efect_hideLogin();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("ShowToast")
	public void login(View v) {
		// Toast.makeText(this, "Hello word", 1000).show();
		validateFields();

	}

	/*
	 * sendRegister the class Postask parameters where the process will be split
	 * to send the json with the parameters listed and validate it to run
	 * properly
	 */
	public void sendServer() {
		efect_showLogin();

		Posttask post_task = new Posttask(1, null);
		post_task.mthis = this;
		post_task.execute("");
	}

	/*
	 * receive_json This is call is sent from the class Posttask Read the answer
	 * which gives the server after it has received the json
	 */
	public static void receive_json(JSONObject json) {
		try {

			if (json.getBoolean("success") == false) {
				efect_hideLogin();
				Toast.makeText(mthis, "The parameters are incorrect", 2000)
						.show();
			} else {
				efect_hideLogin();
				user.setName(json.getJSONObject("result").getString("name"));
				user.setEmail(json.getJSONObject("result").getString("email"));

				showEvent(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.e("json", json.toString());
	}

	/*
	 * sendLogin
	 * 
	 * receives MainActivity save text fields Sending the parameters are
	 * required for validate user
	 */
	public static RESTClient sendLogin(MainActivity mthis) {
		String email;
		String password;

		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		email = editText_Email.getText().toString();

		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		password = editText_Password.getText().toString();

		RESTClient post = new RESTClient(
				"http://api.gthrng.com/gathering/login?email=" + email
						+ "&password=" + password);
		try {
			post.Execute(RESTClient.RequestMethod.GET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}

	/*
	 * validateFields validates that the email is well structured and that no
	 * empty fields daufter
	 */
	public void validateFields() {
		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		boolean valid_Email = function.isValidEmail(editText_Email.getText()
				.toString());

		if (valid_Email == false) {
			editText_Email.setError("Check your email account");
			Toast.makeText(this, "Check your email account.", 2000).show();
		}

		else if (editText_Password.getText().toString().length() == 0) {
			editText_Password.setError("Enter your password account.");
		}

		else {
			sendServer();
		}
	}

	/* Error Menssend for lack of internet connection */
	public void menssageError_NoConnexion() {
		Toast.makeText(this, "The server cannot be reached.", 1000).show();
	}

	/** Open Layout **/

	/*
	 * showRegistration
	 * 
	 * opens the intent of Register
	 */
	public void showRegistration(View v) {
		Intent intent = new Intent(mthis, Register.class);
		startActivity(intent);
		// this.finish();
	}

	/*
	 * showRegistration
	 * 
	 * opens the intent of Event
	 */
	public static void showEvent(Users_object user) {
		Intent intent = new Intent(mthis, Event.class);
		/*
		 * messange[0]=user.getName(); messange[1]=user.getEmail();
		 * intent.putExtra(EXTRA_MESSANGE,messange);
		 */
		// intent.putExtra("EXTRA_MESSANGE_EMAIL",user.getEmail());
		intent.putExtra("EXTRA_MESSANGE_EMAIL", user.getEmail());
		intent.putExtra("EXTRA_MESSANGE_NAME", user.getName());
		mthis.startActivity(intent);
	}

	/*
	 * efect_showLogin shows the process bar on the screen
	 */
	public static void efect_showLogin() {
		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		editText_Email.setEnabled(false);
		editText_Password.setEnabled(false);
	}

	/*
	 * efect_hideLogin process hides the on-screen bar
	 */
	public static void efect_hideLogin() {
		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		editText_Email.setEnabled(true);
		editText_Password.setEnabled(true);
	}
}
