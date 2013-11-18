package com.example.gathering.utils;

import org.json.JSONArray;

public class DataEvent {
	private static DataEvent event= new DataEvent();
	private String currentEventId;
	private String currentEventEmail;
	private String currentEventKey;
	
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

	public void setCurrentEventId(String currentEventId) {
		
		/* declara un objeto
		 * para cada elemento de eventArray resisar si el eventArrey[i]
		 * es igual a currenteEventId
		 * 
		 * para cada elemento de eventArray revisar si el id del eventArray
		 * es igual a currentEventId
		 * si es asi el objeto que declare igualalo a eventArray[i]
		 * 
		 * una vez obtenido event.setNameObjeto.name
		 * 
		 *  lo mismo para email*/
		this.currentEventId = currentEventId;
		
		for(int i=0; i<eventArray.length(); i++){
			/*if(event.currentEventId==  eventArray[i]){
				
			}*/
		}
		
	}

	public void setArrayEvents(JSONArray eventArray) {
		this.eventArray = eventArray;
	}
	
	
}

