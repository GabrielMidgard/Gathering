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
import com.example.gathering.serviceEnded.ServiceEnded;

import android.R.style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        AlertDialog.Builder alert;
        EditText txtStart;
        EditText txtEnd;
        
        public static ServiceEnded ts_this;
        Functions function = new Functions();
        static List<String> correos;
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
                                                txtStart.setText( dayStar.substring( dayStar.length() - 2)+"-"+  monthStar.substring( monthStar.length() - 2) + "-"+yearStar);
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
                                final View view = inflater.inflate(
                                                R.layout.dialog_date_picker, null);
                                alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
                                        
                                        DatePicker datePicker = (DatePicker) view.findViewById( R.id.datePicker );
                                        
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                dayEnd += datePicker.getDayOfMonth();
                                                monthEnd += ( datePicker.getMonth() + 1 );
                                                yearEnd += datePicker.getYear();
                                        
                                                txtEnd.setText( dayEnd.substring( dayEnd.length() - 2)+"-"+  monthEnd.substring( monthEnd.length() - 2) + "-"+yearEnd);
                                                dialog.cancel();
                                        }
                                });
                                alert.setView(view);
                                alert.show();
                                
                        }
                });
                Intent intent = getIntent();
            final LinearLayout linear = (LinearLayout)findViewById(R.id.linearGuest);
            TextView emailusertx = new TextView(this);
                String emailuser = intent.getStringExtra("email");
            emailusertx.setText(emailuser);
            linear.addView(emailusertx);
                dialog = new Dialog(this);
                correos = new ArrayList<String>();
                mthis = this;
                correos.add(emailuser);
                dialog.setContentView(R.layout.popup_invite);
                
            final Button button = (Button)dialog.findViewById(R.id.button1);
            button.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View v) {
                                dialog.dismiss();
                        }
                    
            });
            final Button button2 = (Button)dialog.findViewById(R.id.button2);
            final EditText email = (EditText)dialog.findViewById(R.id.editText1);
            button2.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View v) {
                                String user = email.getText().toString();
                                if (user.length()!=0){
                                        TextView tx = new TextView(getApplicationContext());
                                        tx.setText(user);
                                        tx.setTextSize(20);
                                        correos.add(user);
                                        linear.addView(tx);
                                }
                                dialog.dismiss();
                        }
                    
            });

        }

        /*
         * log_Out return to Register
         */
        public void log_Out(View v) {
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
                /*Posttask post = new Posttask(5, null);
                post.execute();*/
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
        
       public static void manageEvent(JSONObject res) throws JSONException{
                res = res.getJSONObject("result");
                eventId = res.getString("id");
                for(int x = 0; x<correos.size();x++){
                        PostTask post = new PostTask(6,correos.get(x) ,0);
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