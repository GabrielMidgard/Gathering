package com.example.gathering.eventList;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.R;
import com.example.gathering.R.drawable;
import com.example.gathering.R.id;
import com.example.gathering.R.layout;
import com.example.gathering.gallery.Gallery;
import com.example.gathering.json.PostTask;
import com.example.gathering.json.RESTClient;
import com.example.gathering.newEvent.RegisterEvent;
import com.example.gathering.object.Adapter;
import com.example.gathering.object.EventObject;
import com.example.gathering.object.UsersObject;
import com.example.gathering.utils.DataEvent;

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
	static UsersObject user = new UsersObject();
	static EventObject event = new EventObject();
	
	
	private static final String TAG_json_NAME = "name";
    private static final String TAG_json_EVENTWHEN = "eventWhen";
	

    static JSONArray array_events = null;
    
    private static DataEvent dEvent = null;
    
 // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
    
    public Event()
    {
    	dEvent = DataEvent.getInstance();
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		ethis = this;
	
		Intent intent = getIntent();
		
		user.setEmail(intent.getStringExtra("EXTRA_MESSANGE_EMAIL"));
		user.setName(intent.getStringExtra("EXTRA_MESSANGE_NAME"));
		dEvent.setUser(user);
		
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
					showRegisterEvent(v);
					return true;
				}
				return false;
			}
			
		});
	}

	public void logOut(View v)
	{
		/*Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);*/
		this.finish();
	}
	
	
	public void showGalery(View v)
	{
		Intent intent = new Intent(this, Gallery.class);
		startActivity(intent);
		//this.finish();
	}
	
	public void showRegisterEvent(View v)
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
	public void loadEvents(UsersObject user)
	{
		PostTask postTask= new PostTask(3, user);
		postTask.ethis=this;
		postTask.execute("");
	}
	

	/*receive_json
	 * This is call is sent from the class Posttask
	 * Read the answer which gives the server after it has received the json*/
	public static void receiveJson(JSONArray json)
	{
		try {
			Log.i("s",json.toString());
			com.example.gathering.object.Adapter adapter = new Adapter(json,ethis);
			ListView list = (ListView)ethis.findViewById(R.id.listView1);
			list.setAdapter(adapter);
			//Save the Events list to future process
			dEvent.setEventArray(json);
			
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
	public static RESTClient sendEmail(UsersObject user)
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