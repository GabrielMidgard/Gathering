package com.example.gathering;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class GalleryAdapter extends BaseAdapter{

	Bitmap imgs[];
	Context context;
	String idn[];
	
	public GalleryAdapter(Bitmap[] barray, Context context, String idn[]){
		this.imgs=barray;
		this.context = context;
		this.idn = idn;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.length;
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
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.element_gallery, null);
		Log.i("Download","picture "+arg0);
		ImageView img = (ImageView)view.findViewById(R.id.imageView1);
		img.setImageBitmap(imgs[arg0]);
		TextView text = (TextView)view.findViewById(R.id.textViewGallery);
		try{
		text.setText(idn[arg0]);
		}catch(Exception e){
			e.printStackTrace();
		}
		//img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		return view;
	}
	
	Bitmap bmImg;
	

	
}