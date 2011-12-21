package com.harrcharr.wbor.api;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.BitmapDrawable;

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

	@Override
	protected Album loadFromJSON(JSONObject json) {
		try {
			String key, title, artistName, smallKey, largeKey;
			JSONArray songList;
			long addDateMs;
			
			key = json.getString("key");
			title = json.getString("title");
			artistName = json.getString("artist");
			smallKey = json.getString("small_cover_key");
			largeKey = json.getString("large_cover_key");
			addDateMs = json.getLong("add_date");
			songList = json.getJSONArray("song_list");

			this.mKey = key;
			this.mTitle = title;
			this.mArtistName = artistName;
			this.mSmallCover = new Cover(smallKey);
			this.mLargeCover = new Cover(largeKey);
			this.mAddDate = new Date(addDateMs);
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

	private class Cover extends ApiObject {
		private static final String API_OBJECT_NAME = "cover";
		protected BitmapDrawable mImage;
		
		public Cover() {
			super();
		}
		public Cover(String key) {
			super(key);
		}

		@Override
		protected String getApiName() {
			return Cover.API_OBJECT_NAME;
		}
		
		@Override
		public ApiObject loadFromApi() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
