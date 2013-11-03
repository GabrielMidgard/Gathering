package com.example.gathering;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Event extends Activity{
	public static Event ethis;
	static Users_object user = new Users_object();
	static Event_object event = new Event_object();
	
	
	private static final String TAG_json_NAME = "name";
    private static final String TAG_json_EVENTWHEN = "eventWhen";
	

    static JSONArray array_events = null;
    
 // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		ethis = this;
	
		Intent intent = getIntent();
		
		user.setEmail(intent.getStringExtra("EXTRA_MESSANGE_EMAIL"));
		user.setName(intent.getStringExtra("EXTRA_MESSANGE_NAME"));
		
	    TextView t=new TextView(this); 
		t=(TextView)findViewById(R.id.textView2); 

		String sText = "All events for "+user.getName();
		t.setText(sText);
		   
		loadEvents(user);
		final ImageView image = (ImageView) findViewById(R.id.btnAdd);
		image.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()){
					image.setImageDrawable(getResources().getDrawable(R.drawable.button_add_pressed));
					return true;
				}
				if (MotionEvent.ACTION_UP ==  event.getAction()){
					image.setImageDrawable(getResources().getDrawable(R.drawable.button_add));
					show_registerEvent(v);
					return true;
				}
				return false;
			}
			
		});
	}

	public void log_Out(View v)
	{
		/*Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);*/
		this.finish();
	}
	
	
	public void show_galery(View v)
	{
		Intent intent = new Intent(this, Galery.class);
		startActivity(intent);
		//this.finish();
	}
	
	public void show_registerEvent(View v)
	{
		Intent intent = new Intent(this, RegisterEvent.class);
		intent.putExtra("email", user.getEmail());
		startActivity(intent);

		
		//this.finish();
	}
	
	/*loadEvents
	 * the class  Postask parameters
	 * where the process will be split to send the json with the parameters listed
	 * and validate it to run properly
	 */
	public void loadEvents(Users_object user)
	{
		Posttask post_task= new Posttask(3, user);
		post_task.ethis=this;
		post_task.execute("");
	}
	

	/*receive_json
	 * This is call is sent from the class Posttask
	 * Read the answer which gives the server after it has received the json*/
	public static void receive_json(JSONArray json)
	{
		try {
			Log.i("s",json.toString());
			Adapter adapter = new Adapter(json,ethis);
			ListView list = (ListView)ethis.findViewById(R.id.listView1);
			list.setAdapter(adapter);
			
/*			array_events = json.getJSONArray("listEvents");
			for(int i = 0; i < array_events.length(); i++)
			{
				JSONObject e = array_events.getJSONObject(i);
				
				event.setName(json.getJSONObject("result").getString(TAG_json_NAME));
				event.setEventWhen(json.getJSONObject("result").getString(TAG_json_EVENTWHEN));
			
				// creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map.put(TAG_json_NAME, event.getName());
                map.put(TAG_json_EVENTWHEN, event.getEventWhen());
                
                // adding HashList to ArrayList
                event_List.add(map);                  
			}
*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("json",json.toString());
	}
	
	/* 
	 * enviarLogin
	 * 
	 * receives MainActivity
	 * save text fields */
	public static RESTClient sendEmail(Users_object user)
	{
		RESTClient post = new RESTClient("http://api.gthrng.com/gathering/listEventsUsersIsInvited?email="+user.getEmail());
		try {
			post.Execute(RESTClient.RequestMethod.GET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
		
	}
		
	
}