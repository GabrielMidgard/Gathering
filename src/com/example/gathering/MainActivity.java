package com.example.gathering;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.eventList.Event;
import com.example.gathering.json.PostTask;
import com.example.gathering.json.RESTClient;
import com.example.gathering.newUser.Register;
import com.example.gathering.object.Functions;
import com.example.gathering.object.UsersObject;

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
	static UsersObject user = new UsersObject();
	Functions function = new Functions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mthis = this;

		EditText KeyTextEmail = (EditText) findViewById(R.id.txtName);
		EditText KeyEditTextPassword = (EditText) findViewById(R.id.txtPassword);

		KeyTextEmail.setOnKeyListener(new OnKeyListener() {
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

		KeyEditTextPassword.setOnKeyListener(new OnKeyListener() {
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

		efectHideLogin();
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
		efectShowLogin();

		PostTask postTask = new PostTask(1, null);
		postTask.mthis = this;
		postTask.execute("");
	}

	/*
	 * receiveJson This is call is sent from the class Posttask Read the answer
	 * which gives the server after it has received the json
	 */
	public static void receiveJson(JSONObject json) {
		try {

			if (json.getBoolean("success") == false) {
				efectHideLogin();
				Toast.makeText(mthis, "The parameters are incorrect", 2000)
						.show();
			} else {
				efectHideLogin();
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

		EditText editTextEmail = (EditText) mthis.findViewById(R.id.txtName);
		email = editTextEmail.getText().toString();

		EditText editTextPassword = (EditText) mthis
				.findViewById(R.id.txtPassword);
		password = editTextPassword.getText().toString();

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
		EditText editTextEmail = (EditText) mthis.findViewById(R.id.txtName);
		EditText editTextPassword = (EditText) mthis
				.findViewById(R.id.txtPassword);
		boolean validEmail = function.isValidEmail(editTextEmail.getText()
				.toString());

		if (validEmail == false) {
			editTextEmail.setError("Check your email account");
			Toast.makeText(this, "Check your email account.", 2000).show();
		}

		else if (editTextPassword.getText().toString().length() == 0) {
			editTextPassword.setError("Enter your password account.");
		}

		else {
			sendServer();
		}
	}

	/* Error Menssend for lack of internet connection */
	public void menssageErrorNoConnexion() {
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
	public static void showEvent(UsersObject user) {
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
	public static void efectShowLogin() {
		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		editText_Email.setEnabled(false);
		editText_Password.setEnabled(false);
	}

	/*
	 * efect_hideLogin process hides the on-screen bar
	 */
	public static void efectHideLogin() {
		EditText editText_Email = (EditText) mthis.findViewById(R.id.txtName);
		EditText editText_Password = (EditText) mthis
				.findViewById(R.id.txtPassword);
		editText_Email.setEnabled(true);
		editText_Password.setEnabled(true);
	}
}
