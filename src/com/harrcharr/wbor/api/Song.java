package com.harrcharr.wbor.api;

import org.json.JSONObject;

public class Song extends ApiObject {
	public static final String API_OBJECT_NAME = "song";
	
	private String mTrackName;
	private String mArtistName;

	public Song(JSONObject json){
		this.loadFromJSON(json);
	}
	public Song(String key) {
		this.mKey = key;
		this.loadedFromApi = false;
	}	
	public Song(String trackName, String artistName) {
		this.mTrackName = trackName;
		this.mArtistName = artistName;
	}
	
	public String getTrackName() {
		this.maybeLoadFromApi();
		return this.mTrackName;
	}
	public String getArtistName() {
		this.maybeLoadFromApi();
		return this.mArtistName;
	}
	
	@Override
	protected Song loadFromJSON(JSONObject json) {
		String key, trackName, artistName, albumKey;
		
		try {
			key = json.getString("key");
			trackName = json.getString("title");
			artistName = json.getString("artist");

			this.mKey = key;
			this.mTrackName = trackName;
			this.mArtistName = artistName;
		} catch (Exception e) {
			System.err.println("Error digesting JSON object for nowPlaying API call");
			e.printStackTrace();			
		}
		return this;
	}
	
	@Override
	protected String getApiName() {
		return Song.API_OBJECT_NAME;
	}
}
