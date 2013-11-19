package com.example.gathering.gallery;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.example.gathering.R;
import com.example.gathering.R.drawable;
import com.example.gathering.R.id;
import com.example.gathering.R.layout;
import com.example.gathering.json.PostTask;
import com.example.gathering.json.RESTClient;
import com.example.gathering.object.Adapter;
import com.example.gathering.utils.DataEvent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.MediaStore;
import android.widget.Button;


public class Gallery extends Activity{
	public static Gallery gthis;
	static String id = "";
	static String name_Event ="";
	static String idn[];
	static String ids[];
	
	public static String namePicture="";
	public static String urlPicture="";

	public static final int album_name=0x7f040006;
	public static final int cannot=0x7f040005;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private ImageView mImageView;
	private Bitmap mImageBitmap;

	
		
	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	Uri fileUri = null;
	ImageView photoImage = null;
	
	private static AsyncHttpClient client = new AsyncHttpClient();
	private DataEvent dEvent = null;
	
	public Gallery()
	{
		dEvent = DataEvent.getInstance();
	}

	private void sendPhoto(File image) {

	    RequestParams params = new RequestParams();

	    try 
	    {	    	
	    	//3d9111551d634871a64cb2d7307b3f7e
	    	@SuppressWarnings("unused")
			String a = dEvent.getCurrentEventId();
	    	String b = dEvent.getUser().getEmail();
	    	String c = dEvent.getCurrentEventKey();
	    	params.put("event_id", dEvent.getCurrentEventId());
			params.put("email", dEvent.getCurrentEventEmail());
			params.put("key", dEvent.getCurrentEventKey());
	    	params.put("file", image); 
	    } 
	    catch (FileNotFoundException e) {
	    	Toast.makeText(gthis, e.getMessage(), 1000).show();
	    }

	    client.post("http://api.gthrng.com/gathering/uploadMediaFile", params,

	        new AsyncHttpResponseHandler() {

	            public void onSuccess(String result) {
	            	Toast.makeText(gthis, "Image Sent", 1000).show();
	            };

	            public void onFailure(Throwable arg0, String errorMsg) {
	            	Toast.makeText(gthis, errorMsg, 1000).show();
	            };

	        }

	    );

	}
	
	/* CAMERA **/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
		    if (resultCode == RESULT_OK) {
		      Uri photoUri = null;
		      if (data == null) {
		        // A known bug here! The image should have saved in fileUri
		        Toast.makeText(this, "Image saved successfully", 
		                       Toast.LENGTH_LONG).show();
		        photoUri = fileUri;
		      } else {
		        photoUri = data.getData();
		        Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
		                       Toast.LENGTH_LONG).show();
		      }
		      try {
				this.showPhoto(photoUri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    } else if (resultCode == RESULT_CANCELED) {
		      Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
		    } else {
		      Toast.makeText(this, "Callout for image capture failed!", 
		                     Toast.LENGTH_LONG).show();
		    }
		  }
	}
	
	private void showPhoto(Uri photoUri) throws IOException {
	  File imageFile = new File(photoUri.getEncodedPath());
	  if (imageFile.exists()){
	     this.sendPhoto(imageFile);
	  }       
	}

	private File getOutputPhotoFile() {
	  File directory = new File(Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES), getPackageName());
	  if (!directory.exists()) {
	    if (!directory.mkdirs()) {
	      Log.e(TAG, "Failed to create storage directory.");
	      return null;
	    }
	  }
	  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date(0));
	  return new File(directory.getPath() + File.separator + "IMG_"  
	                    + timeStamp + ".jpg");
	}
	
	Button.OnTouchListener mTakePicSOnClickListener = 
		new Button.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncam_pressed));
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_UP){
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncam_unpressed));
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        File file = getOutputPhotoFile();
		        fileUri = Uri.fromFile(getOutputPhotoFile());
		        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		        startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
				return true;
			}
			return false;
		}
	};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		id = intent.getExtras().getString(Adapter.EXTRA_MESSANGE);
		name_Event=intent.getExtras().getString("EXTRA_MESSANGE_NAME");
		
		setContentView(R.layout.activity_gallery);
		
		
		TextView t=new TextView(this); 
		t=(TextView)findViewById(R.id.textView2); 
		String sText = name_Event;
		t.setText(sText);
		
		gthis = this;
		PostTask task = new PostTask(4, null);
		task.execute();
		
		/* Camara **/
		mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageBitmap = null;

		Button picSBtn = (Button) findViewById(R.id.btnIntendS);
		setBtnListenerOrDisable( 
				picSBtn, 
				mTakePicSOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE
		);
	}
		
	public void logOut(View v)
	{
		/*Intent intent = new Intent(this, Event.class);
		startActivity(intent);*/
		this.finish();
	}
	public static RESTClient getPics()
	{
        Log.i("URL","http://api.gthrng.com/gathering/listMedia?event_id="+id);

		RESTClient post = new RESTClient("http://api.gthrng.com/gathering/listMedia?event_id="+id);
		try {
			post.Execute(RESTClient.RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
		
	}
	public static void ManageIdPics(JSONArray jsonarray){
		ids = new String [jsonarray.length()];
		idn = new String [jsonarray.length()];
		String[] create = new String [jsonarray.length()];
        Log.i("arra",jsonarray.toString());

		try{
			for (int x = 0;x<jsonarray.length();x++){
				JSONObject json = jsonarray.getJSONObject(x);
				try{
					ids[x] = json.getJSONObject("_id").getString("$id");
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					idn[x] = json.getJSONObject("user").getString("name");
					create[x] = json.getJSONObject("user").getString("created");
					Log.i("IDN",idn[0]);

				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		Log.i("IDS",ids[0]);
		String urls[] =  new String[ids.length];
		for (int x = 0; x<ids.length;x++){
		           urls[x] = "http://photos.gthrng.com/"+ids[x]+".jpg";
		           Log.i("URL","getted");    
		}
		LazyAdapter adapter = new LazyAdapter(gthis, urls,idn,create);
		ListView galery = (ListView)gthis.findViewById(R.id.gallerylayout);
		galery.setAdapter(adapter);
	}



	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mImageView.setImageBitmap(mImageBitmap);
		mImageView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable( 
			Button btn, 
			Button.OnTouchListener onClickListener,
			String intentName
	) {
		if (isIntentAvailable(this, intentName)) {
			btn.setOnTouchListener(onClickListener);        	
		} else {
			btn.setText( 
				getText(cannot).toString() + " " + btn.getText());
			btn.setClickable(false);
		}
	}
}


