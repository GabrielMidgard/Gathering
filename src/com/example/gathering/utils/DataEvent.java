package com.example.gathering.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gathering.object.UsersObject;

public class DataEvent {
	private static DataEvent event= new DataEvent();
	private String currentEventId;
	private String currentEventEmail;
	private String currentEventKey;
	private UsersObject user;
	
	private JSONArray eventArray = null;
	
	private DataEvent(){}
	
	public static DataEvent getInstance(){
		
		return event;
	}

	public String getCurrentEventKey() {
		return currentEventKey;
	}

	public void setCurrentEventKey(String currentEventKey) {
		this.currentEventKey = currentEventKey;
	}

	public String getCurrentEventEmail() {
		return currentEventEmail;
	}

	public void setCurrentEventEmail(String currentEventEmail) {
		this.currentEventEmail = currentEventEmail;
	}

	public String getCurrentEventId() {
		return currentEventId;
	}

	public void setCurrentEventId(String currentEventId) throws JSONException {
		this.currentEventId = currentEventId;
		JSONObject jsonEvent = null;
		
		for(int i=0; i<eventArray.length(); i++){
			jsonEvent = eventArray.getJSONObject(i);
			if(currentEventId == jsonEvent.getString("id")){
				break;
			}
		}
		getInstance().setCurrentEventKey("969490e925ae635134d0977aa6e74f9e");
	}

	public void setEventArray(JSONArray eventArray) {
		this.eventArray = eventArray;
	}

	public UsersObject getUser() {
		return user;
	}

	public void setUser(UsersObject user) {
		this.user = user;
	}
	
	
}

