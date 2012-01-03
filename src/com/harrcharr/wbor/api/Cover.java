package com.harrcharr.wbor.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Cover extends ApiObject {
	private static final String API_OBJECT_NAME = "cover";
	protected Bitmap mImage;
	
	public Cover() {
		super();
	}
	public Cover(String key) {
		super(key);
	}
	
	public Bitmap getImage() {
		return mImage;
	}
	
	@Override
	protected String getApiName() {
		return Cover.API_OBJECT_NAME;
	}
	
	@Override
	public void loadFromApi() {
		if (mKey != null) {
			String apiCall = getApiName() + "/" + mKey;
			URL apiUrl;
			try {
				apiUrl = new URL(Wbor.API_BASE_URL + apiCall);
				HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
				try {
					InputStream in = new BufferedInputStream(urlConnection.getInputStream());
					mImage = BitmapFactory.decodeStream(in);
				}
				finally {
					urlConnection.disconnect();
				}
				loadedFromApi = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
