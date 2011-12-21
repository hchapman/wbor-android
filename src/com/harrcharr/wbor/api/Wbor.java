package com.harrcharr.wbor.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Message;

public class Wbor {
	public static final String API_BASE_URL = "http://1-6-api1.wbor-hr.appspot.com/api/";
	
	public static JSONObject getJSONResponseFromApi(String apiCall) {
		String jsonString = "";
		try {
			URL apiUrl = new URL(Wbor.API_BASE_URL + apiCall);
			HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				jsonString = new String(IOUtils.toByteArray(in));
			}
			finally {
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			System.err.println("Error during HTTP connection for API call, " + apiCall);
			e.printStackTrace();
		}
		
		Message msg = new Message();
		try {
			return new JSONObject(jsonString);
		} catch (Exception e) {
			System.err.println("Error creating JSON object from response for call, " +apiCall);
			e.printStackTrace();
		}
		return null;
	}
	
	public static Play getNowPlaying() {
		JSONObject playJson = getJSONResponseFromApi("nowPlaying");
		return new Play(playJson);
	}
	
	public static ArrayList<Play> getLastPlays() {
		JSONObject lastPlaysJson = getJSONResponseFromApi("lastPlays");
		ArrayList<Play> lastPlaysArray = new ArrayList<Play>();
		try {
			JSONArray lastPlays = lastPlaysJson.getJSONArray("play_list");
			for (int i = 0; i < lastPlays.length(); i++) {
				String key = lastPlays.getString(i);
				if (key != JSONObject.NULL) {
					Play play = new Play(lastPlays.getJSONObject(i));
					play.getSong().loadFromApi();
					lastPlaysArray.add(play);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return lastPlaysArray;
	}
}
