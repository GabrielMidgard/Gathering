package com.example.gathering.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.MainActivity;
import com.example.gathering.eventList.Event;
import com.example.gathering.gallery.Gallery;
import com.example.gathering.newEvent.RegisterEvent;
import com.example.gathering.newUser.Register;
import com.example.gathering.object.UsersObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class PostTask extends AsyncTask<String, Integer, RESTClient>{
	public static MainActivity mthis;
	public static Register rthis;
	public static Event ethis;
	String data;
	private ProgressDialog 	pd;

	
	int option=-1;
	UsersObject user;
	
	public PostTask(int opc, UsersObject usr) {
		option=opc;
		user=usr;
	}
	public PostTask (int opc, String data, int op){
		option = opc;
		this.data = data;
	}
	
	/**
	 *Explication
	 *
	 * @param 1
	 *
	 * @return 
	 * sendLogin - Sending the parameters are required for validate user
	 * recibir_json - Read the answer which gives the server after it has received the json
	 * 
	 * @param 2
	 *
	 * @return .
	 * sendServer - Sending the parameters are required for registration of a new user
	 * the server
	 * recibir_json - Read the answer which gives the server after it has received the json
	 * 
	 * @param 3
	 *
	 * @return .
	 * 
	 * recibir_json - Read the answer which gives the server after it has received the json
	 *  
	 * @param 4
	 *
	 * @return 
	 * 
	 * recibir_json - Read the answer which gives the server after it has received the json .
	 */
	
	@Override
	protected RESTClient doInBackground(String... arg0) {
		
		// TODO Auto-generated method stub
		if(option==1){
			return MainActivity.sendLogin(mthis);
		}
		else if(option==2){
			return Register.sendServer(user);
		}
		else if(option==3)
		{
			return Event.sendEmail(user);
		}else if(option==4){
			return Gallery.getPics();
		}else if (option ==5){
			return RegisterEvent.postEvent();
		} else if(option ==6){
			return RegisterEvent.postUser(data);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(RESTClient Result){
		try{
			super.onPostExecute(Result);
			
			try {
				if(option==1){
					JSONObject rs;
					rs = new JSONObject(Result.getResponse());
					MainActivity.receiveJson(rs);
				}
				
				else if(option==2)
				{
					JSONObject rs;
					rs = new JSONObject(Result.getResponse());
					Register.receiveJson(rs);
				}
				
				else if(option==3)
				{
					JSONArray rs = new JSONArray(Result.getResponse());
					Event.receiveJson(rs);
				}
				
				else if (option ==4){
					JSONArray rs = new JSONArray(Result.getResponse());
					Gallery.ManageIdPics(rs);
				} else if(option ==5){
					JSONObject rs = new JSONObject(Result.getResponse());
					RegisterEvent.mthis.manageEvent(rs);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(option==1){
					mthis.menssageErrorNoConnexion();
				}
			}	
		}catch(Exception e){
			if(option==1){
				mthis.efectHideLogin();
				mthis.menssageErrorNoConnexion();
			}
			
			if(option==2){
				//rthis.ocultarLogin();
				rthis.menssageErrorNoConnexion();
			}
			e.printStackTrace();
		}
		
	}
}
