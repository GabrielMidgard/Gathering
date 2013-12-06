package com.example.gathering.newEvent;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.R;
import com.example.gathering.json.PostTask;
import com.example.gathering.json.RESTClient;
import com.example.gathering.object.Functions;
import com.example.gathering.object.UsersObject;
import com.example.gathering.serviceEnded.ServiceEnded;

import android.R.color;
import android.R.style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterEvent extends Activity {
	static UsersObject user = new UsersObject();
	
    AlertDialog.Builder alert;
    EditText txtStart;
    EditText txtEnd;
    
    public static ServiceEnded ts_this;
    Functions function = new Functions();
    static List<String> Emails;
    static String eventId;
    Dialog dialog;
    
    String dayStar = "00";
    String monthStar = "00";
    String yearStar = "";
    String dayEnd = "00";
    String monthEnd = "00";
    String yearEnd = "";
    
    public static RegisterEvent mthis;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_event);
         
        Intent intent = getIntent();
		
		user.setEmail(intent.getStringExtra("email"));
		user.setName(intent.getStringExtra("userName"));
               
		/** Gets activiy label ('All events for')
		 * and we add the name of the user who entered**/
	    TextView t=new TextView(this); 
		t=(TextView)findViewById(R.id.textView2); 

		String sText = "All events for "+user.getName();
		t.setText(sText);
		
		
        alert = new AlertDialog.Builder(this);
        txtStart = (EditText) findViewById(R.id.txtStart);
        txtEnd = (EditText) findViewById(R.id.txtEnd);
        
        txtStart.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            txtStart.setText("");
	            dayStar = "00";
	            monthStar = "00";
	            yearStar = "";
	                                
	            alert.setTitle("Select the start date of the event.");
	            LayoutInflater inflater = getLayoutInflater();
	            final View view = inflater.inflate(
	                            R.layout.dialog_date_picker, null);
	            alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
	                    
	                DatePicker datePicker = (DatePicker) view.findViewById( R.id.datePicker );
	                
	                @Override
	                public void onClick(DialogInterface dialog, int which) {  
	                    dayStar += datePicker.getDayOfMonth();
	                    monthStar += ( datePicker.getMonth() + 1 );
	                    yearStar += datePicker.getYear();
	                    txtStart.setText(monthStar.substring( monthStar.length() - 2)+"/"+  dayStar.substring( dayStar.length() - 2)  + "/"+yearStar);
	                    dialog.cancel();
	                }
	            });
	            alert.setView(view);
	            alert.show();
	        }
        });
                
        txtEnd.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnd.setText("");
                dayEnd = "00";
                monthEnd = "00";
                yearEnd = "";
                
                alert.setTitle("Select the end date of the event.");
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_date_picker, null);
                
                alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
                	DatePicker datePicker = (DatePicker) view.findViewById( R.id.datePicker );
                            
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dayEnd += datePicker.getDayOfMonth();
                        monthEnd += ( datePicker.getMonth() + 1 );
                        yearEnd += datePicker.getYear();
                
                        txtEnd.setText(  monthEnd.substring( monthEnd.length() - 2)+"/"+dayEnd.substring( dayEnd.length() - 2)  + "/"+yearEnd);
                        dialog.cancel();
                    }
                });
                alert.setView(view);
                alert.show();
                    
            }
        });
               
        final LinearLayout linear = (LinearLayout)findViewById(R.id.linearGuest);
        /** Add text the name of the event organizer **/
        TextView txtUserNameOrganizer = new TextView(this);
        txtUserNameOrganizer.setText(user.getName());
        
        TextView NameOrganizer = new TextView (getApplicationContext());
        
        NameOrganizer.setText(user.getName());
        NameOrganizer.setPadding(20, 0, 0, 0);
        NameOrganizer.setTextSize(25);
        NameOrganizer.setTextColor(Color.rgb(88, 00, 00));
        
        linear.addView(NameOrganizer);
        
        
        TextView txtEmailUserOrganizer = new TextView(this);        
        txtEmailUserOrganizer.setText(user.getEmail());
        
        
        /**Añadir texto el email del organizador del evento **/
        TextView txtEmailOrganizer = new TextView(this);        
        txtEmailOrganizer.setText(user.getEmail());
        
        TextView EmailOrganizer = new TextView (getApplicationContext());
        EmailOrganizer.setText(user.getEmail());
        EmailOrganizer.setPadding(20, 0, 0, 20);
        EmailOrganizer.setTextSize(15);
        EmailOrganizer.setTextColor(Color.rgb(178, 175, 189));
        
        linear.addView(EmailOrganizer);
        
        
        
        
        dialog = new Dialog(this);
        Emails = new ArrayList<String>();
        mthis = this;
        Emails.add(user.getEmail());
        dialog.setContentView(R.layout.popup_invite);
                
        final Button popBtnCancel = (Button)dialog.findViewById(R.id.popBtnCancel);
        popBtnCancel.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
                    
        });
            
        
        
        final Button popBtnOk = (Button)dialog.findViewById(R.id.popBtnOk);
        final EditText popTxtEmail = (EditText)dialog.findViewById(R.id.popTxtEmail);
        final EditText popTxtName = (EditText)dialog.findViewById(R.id.popTxtName);
        
        /** Event on OK which evaluate the name and email user
          * Ahigan been entered correctly
          * Would you rate the email
          * Captured text fields Name and Email
          * Fields are correct if the show in this activity
          * And would add the email to a list
          *
          * - NOTE: NO LIST NAMES BECAUSE YOU ARE INVITED IN THAT FIELD IN THE JSON **/
        popBtnOk.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String userEmail = popTxtEmail.getText().toString();
                String userName = popTxtName.getText().toString();
                if (userEmail.length()!=0){
                	/** Format for Name of guest **/
                    TextView nameInvited = new TextView (getApplicationContext());
                   
                    nameInvited.setText(userName);
                    nameInvited.setPadding(20, 0, 0, 0);
                    nameInvited.setTextSize(25);
                    nameInvited.setTextColor(Color.rgb(59, 4, 21));
                    
                    linear.addView(nameInvited);
                    
                    
                	/** Format for Email of guest **/
                    TextView emailInvited = new TextView(getApplicationContext());
                    emailInvited.setText(userEmail);
                    emailInvited.setTextSize(15);
                    emailInvited.setPadding(20, 10, 0, 20);
                    emailInvited.setTextColor(Color.rgb(178, 175, 189));
                    Emails.add(userEmail);
                    
                    popTxtEmail.setText("");
                    popTxtName.setText("");
                    linear.addView(emailInvited);
                }
                
                
                dialog.dismiss();
            }
        });
    }//End onCreate

        /*
     * logOut return to Register
     */
    public void logOut(View v) {
        this.finish();
    }

    public void validateRegistration(View v) {
        dialog.show();
    }
    
    public void accept(View v){
    	validateFields();
    }   
    
    public void validateFields() {
    	try{
            EditText editText_EventName = (EditText) mthis.findViewById(R.id.txtEventName);
            EditText editText_Start = (EditText) mthis.findViewById(R.id.txtStart);
            EditText editText_End = (EditText) mthis.findViewById(R.id.txtEnd);
            
            if (editText_EventName.getText().toString()=="") {
                    editText_EventName.setError("Check your event name");
            }
            
            else if(editText_Start.getText().toString()==""){
                    editText_Start.setError("Enter a start date for the event");
            }
            else if(editText_End.getText().toString()==""){
                    editText_End.setError("Enter a end date for the event");
            }
            
            
            //http://solucionesdeprogramacion.blogspot.mx/2011/10/comparacion-de-fechas-en-java.html
            else {
                int nDayStart= Integer.parseInt(dayStar.substring( dayStar.length() - 2));
                int nMonthStart= Integer.parseInt(monthStar.substring( monthStar.length() - 2));
                int nYearStart= Integer.parseInt(yearStar);
                
                int nDayEnd= Integer.parseInt(dayEnd.substring( dayEnd.length() - 2));
                int nMonthEnd= Integer.parseInt(monthEnd.substring( monthEnd.length() - 2));
                int nYearEnd= Integer.parseInt(yearEnd);
                
                if(nYearStart<=nYearEnd){
                    if(nMonthStart==nMonthEnd)
                    {
                        if(nDayStart<=nDayEnd)
                        {
                            saveEvent();
                        }
                        else{
                            Toast.makeText(this, "Check the day of the end of the event", 3000).show();
                        }
                    }
                    else if(nMonthStart<nMonthEnd){
                            saveEvent();
                    }
                    else{
                        Toast.makeText(this, "Check the month of the end of the event", 3000).show();
                    }
                }
                else{
                    Toast.makeText(this, "Check the year of the end of the event", 3000).show();
                }
            }
    	}catch(Exception e){
    		Toast.makeText(this, "Need to add a data", 3000).show();
    	}
            
    }
    
    public void saveEvent(){
            Toast.makeText(this, "The event has been added", 3000).show();
            PostTask post = new PostTask(5, null);
            post.execute();
    }
    
    public static RESTClient postEvent(){
            EditText txname = (EditText)mthis.findViewById(R.id.txtEventName);
            EditText txdatestart = (EditText)mthis.findViewById(R.id.txtStart);
    Log.i("URL","http://api.gthrng.com/gathering/addEvent?name="+txname.getText().toString()+"+&when="+txdatestart.getText().toString());

            RESTClient post = new RESTClient("http://api.gthrng.com/gathering/addEvent?name="+txname.getText().toString()+"+&when="+txdatestart.getText().toString());
            try {
                    post.Execute(RESTClient.RequestMethod.GET);
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return post;
            
    }
    
    /** Validates that can not be more than 6 mails and saves the added inviting **/
    
    public static void manageEvent(JSONObject res) throws JSONException{
        res = res.getJSONObject("result");
        eventId = res.getString("id");
        for(int x = 0; x<Emails.size();x++){
            PostTask post = new PostTask(6,Emails.get(x) ,0);
            post.execute();
        }
        Toast.makeText(mthis, "Succesfull", Toast.LENGTH_SHORT).show();
        mthis.finish();
    }
    
    public static RESTClient postUser(String email){
         Log.i("URL","http://api.gthrng.com/gathering/inviteUserToEvent?event_id="+mthis.eventId+"&email="+email);
            RESTClient post = new RESTClient("http://api.gthrng.com/gathering/inviteUserToEvent?event_id="+mthis.eventId+"&email="+email);
            try {
                    post.Execute(RESTClient.RequestMethod.GET);
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return post;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}