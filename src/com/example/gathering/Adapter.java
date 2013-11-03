package com.example.gathering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class Adapter extends BaseAdapter{
	public JSONArray jsonarray;
	public final static String EXTRA_MESSANGE = "com.example.gathering.MESSAGE";

	public Context context;
	public Adapter(JSONArray jsonarray, Context context){
		this.jsonarray = jsonarray;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jsonarray.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = new View (context);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.element_list, null);
			try {
				final JSONObject json;

				json = jsonarray.getJSONObject(arg0);
				String name = json.getString("name");
				TextView txname = (TextView)view.findViewById(R.id.textViewEvent);
				txname.setText(name);
				view.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context, com.example.gathering.Galery.class);
						try {
							intent.putExtra(EXTRA_MESSANGE,json.getString("id"));
							intent.putExtra("EXTRA_MESSANGE_NAME",json.getString("name"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						context.startActivity(intent);
						

					}
					
				});

			} catch (JSONException e) {
				TextView txname = (TextView)view.findViewById(R.id.textViewEvent);
				txname.setText("No value");
				e.printStackTrace();
			}
			try{
				final JSONObject json;
				json = jsonarray.getJSONObject(arg0);
				String date = json.getString("when");
				TextView txdate = (TextView)view.findViewById(R.id.textViewTime);
				txdate.setText(date);
			}catch(JSONException e){
				TextView txdate = (TextView)view.findViewById(R.id.textViewTime);
				txdate.setText("No value");
				e.printStackTrace();
			}
					return view;
	}
	
}