package com.example.gathering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class Posttask extends AsyncTask<String, Integer, RESTClient>{
	MainActivity mthis;
	Register rthis;
	Event ethis;
	String data;
	private ProgressDialog 	pd;

	
	int option=-1;
	Users_object user;
	
	public Posttask(int opc, Users_object usr) {
		option=opc;
		user=usr;
	}
	public Posttask (int opc, String data, int op){
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
			return Galery.getPics();
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
					MainActivity.receive_json(rs);
				}
				
				else if(option==2)
				{
					JSONObject rs;
					rs = new JSONObject(Result.getResponse());
					Register.receive_json(rs);
				}
				
				else if(option==3)
				{
					JSONArray rs = new JSONArray(Result.getResponse());
					Event.receive_json(rs);
				}
				
				else if (option ==4){
					JSONArray rs = new JSONArray(Result.getResponse());
					Galery.Manage_idpics(rs);
				} else if(option ==5){
					JSONObject rs = new JSONObject(Result.getResponse());
					RegisterEvent.mthis.manageEvent(rs);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(option==1){
					mthis.menssageError_NoConnexion();
				}
			}	
		}catch(Exception e){
			if(option==1){
				mthis.efect_hideLogin();
				mthis.menssageError_NoConnexion();
			}
			
			if(option==2){
				//rthis.ocultarLogin();
				rthis.menssageError_NoConnexion();
			}
			e.printStackTrace();
		}
		
	}
}
