package com.harrcharr.wbor.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Wbor {
	public static final String API_BASE_URL = "http://1-6-api1.wbor-hr.appspot.com/api/";
//	public static final String API_BASE_URL = "http://192.168.1.103:8080/api/";

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
		try {
			return new JSONObject(jsonString);
		} catch (Exception e) {
			System.err.println("Error creating JSON object from response for call, " +apiCall);
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getBlobResponseFromApi(String apiCall) {
		try {
			URL apiUrl = new URL(Wbor.API_BASE_URL + apiCall);
			HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				return in;
			}
			finally {
				urlConnection.disconnect();
			}
		} catch (Exception e) {
			System.err.println("Error during HTTP connection for API call, " + apiCall);
			e.printStackTrace();
		}
		return null;
	}
	
	public static Play getNowPlaying() {
		JSONObject playJson = getJSONResponseFromApi("nowPlaying");
		return new Play(playJson);
	}
	
	public static List<Play> getLastPlays() {
		JSONObject lastPlaysJson = getJSONResponseFromApi("lastPlays");
		ArrayList<Play> lastPlaysArray = new ArrayList<Play>();
		try {
			JSONArray lastPlays = lastPlaysJson.getJSONArray("play_list");
			for (int i = 0; i < lastPlays.length(); i++) {
				String key = lastPlays.getString(i);
				if (key != JSONObject.NULL) {
					Play play = new Play(lastPlays.getJSONObject(i))
					.maybeLoadSongFromApi();
					play.getSong().maybeLoadAlbumFromApi();
					lastPlaysArray.add(play);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return lastPlaysArray;
	}
	
	public static List<Album> getNewShelf() {
		JSONObject newShelfJson = getJSONResponseFromApi("newShelf");
		ArrayList<Album> newShelfArray = new ArrayList<Album>();
		try {
			JSONArray newShelf = newShelfJson.getJSONArray("album_list");
			for (int i = 0; i < newShelf.length(); i++) {
				String key = newShelf.getString(i);
				if (key != JSONObject.NULL) {
					Album album = new Album(newShelf.getJSONObject(i));
					newShelfArray.add(album);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return newShelfArray;
	}
}
