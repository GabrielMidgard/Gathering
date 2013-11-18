package com.example.gathering.gallery;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

class AsyncGallery extends AsyncTask<String, Integer, Boolean>{
	Bitmap barray[];
	private ProgressDialog 	pd;
	String idn[];
	String ids[];
	public AsyncGallery(String ids[], String idn[] ){
		this.ids = ids;
		this.idn = idn;
		barray= new Bitmap[ids.length];
	}
	@Override
	protected void onPreExecute(){
		pd = ProgressDialog.show(Gallery.gthis, "", "Download Picture");
		pd.setCancelable(true);

	}
	@Override
	protected Boolean doInBackground(String... arg0) {

		for (int x = 0; x<ids.length;x++){
			 URL myFileUrl =null;          
		      try {
		           myFileUrl= new URL("http://photos.gthrng.com/"+ids[x]+"_thumb.jpg");
		           Log.i("URL","getted");
		      } catch (MalformedURLException e) {
		           // TODO Auto-generated catch block
		          // e.printStackTrace();
		      }
		      try {
		           Log.i("img","getting");
		           HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
		           conn.setDoInput(true);
		           conn.connect();
		           InputStream is = conn.getInputStream();

		           barray[x] = BitmapFactory.decodeStream(is);
		           
		      } catch (Exception e) {
		           // TODO Auto-generated catch block
		          // e.printStackTrace();
		      }
				Log.i("Download","picture "+x);

		}
		return true;
	}
	
	protected void onPostExecute(Boolean result){
		pd.dismiss();
//		Galery.gthis.startGallery(barray, idn);
	}
}