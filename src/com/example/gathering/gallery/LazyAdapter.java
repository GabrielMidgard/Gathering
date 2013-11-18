package com.example.gathering.gallery;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.gathering.R;
import com.example.gathering.R.id;
import com.example.gathering.R.layout;
import com.example.gathering.cache.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private String[] data;
	private String[] created;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	String idn[];

	public LazyAdapter(Activity a, String[] d, String idn[], String created[]) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		this.idn = idn;
		this.created = created;
	}

	public int getCount() {
		return (data.length) / 2;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {


		View a = inflater.inflate(R.layout.element_gallery, null);

		TextView text = (TextView) a.findViewById(R.id.textViewGallery);
		TextView text2 = (TextView)a.findViewById(R.id.textViewGallery2);
		ImageView image = (ImageView) a.findViewById(R.id.imageView1);
		image.setScaleType(ScaleType.FIT_XY);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Log.i("fecha",created[position]);
			text.setText(idn[position]);
			text2.setText(""+dateFormat.format(new Date(1000L * Long.parseLong(created[position]))));
			imageLoader.DisplayImage(data[position], image);
		} catch (NullPointerException e) {
			Log.e("Texto de Imagen", "Null");
		}
		return a;
		
	}
}