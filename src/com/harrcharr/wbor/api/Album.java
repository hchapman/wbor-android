package com.harrcharr.wbor.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Album extends JsonApiObject {
	public static final String API_OBJECT_NAME = "album";
	
	protected String mTitle;
	protected String mArtistName;

	protected Date mAddDate;
	protected List<Song> mSongList;
	
	protected Cover mSmallCover;
	protected Cover mLargeCover;
	
	public Album(String key) {
		super(key);
	}
	public Album(JSONObject json) {
		this.loadFromJSON(json);
	}
	
	public String getTitle() {
		maybeLoadFromApi();
		return mTitle;
	}
	public Cover getSmallCover() {
		maybeLoadFromApi();
		return mSmallCover;
	}
	
	public Album maybeLoadFromApi(int flags) {
		maybeLoadFromApi();
		return this;
	}

	@Override
	protected Album loadFromJSON(JSONObject json) {
		if (json == null) {
			return this;
		}
		
		try {
			String key, title, artistName, smallKey, largeKey;
			JSONArray songList;
			long addDateMs;
			
			key = json.getString("key");
			title = json.getString("title");
			artistName = json.getString("artist");
			smallKey = json.optString("cover_small_key");
			largeKey = json.optString("cover_large_key");
			addDateMs = json.optLong("add_date");
			songList = json.optJSONArray("song_list");

			this.mKey = key;
			this.mTitle = title;
			this.mArtistName = artistName;
			this.mSmallCover = new Cover(smallKey);
			this.mLargeCover = new Cover(largeKey);
			this.mAddDate = new Date(addDateMs);
			this.loadedFromApi = true;
		} catch (Exception e) {
			System.err.println("Error digesting JSON object for GET album API call");
			e.printStackTrace();			
		}
		return this;
	}

	@Override
	protected String getApiName() {
		return Album.API_OBJECT_NAME;
	}
}
