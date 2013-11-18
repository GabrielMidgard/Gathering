package com.example.gathering.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

//import android.content.Context;
import android.util.Log;


public class RESTClient {

	public static enum RequestMethod {
		GET, POST
	}

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	public String post;
	private String url;

	private int responseCode;
	private String message;
	private String response;

/*
 * default methods to manage the restclient
 * */
	public String getResponse() {
		return response;
	}
	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RESTClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}
/*
 * exceute the post defined to the url defined
 * 
 * */
	public void Execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}
			HttpGet request = new HttpGet(url);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			executeRequest(request, url);
			break;
		}
		case POST: {			
			HttpPost request = new HttpPost(url);
			request.setHeader("Content-type","application/json");
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
				//Log.i("headeradding",""+h.getName()+","+h.getValue());
			}		
			//if (!params.isEmpty()) {
				Log.i("addingjson","ading to post the json");
				request.setEntity(new ByteArrayEntity(post.toString().getBytes("UTF8")));
				Log.i("posting",""+post.toString()+" in "+url );
			//}		
			
			executeRequest(request, url);
			break;
		}
		}
	}
/*
 * executing recuest from post in execute restclient
 * 
 * */
	private void executeRequest(HttpUriRequest request, String url) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse;
		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				instream.close();
			}
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}
/*
 * 
 * convert the Stream from the request in a String readeable to the rest client
 * 
 * */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
