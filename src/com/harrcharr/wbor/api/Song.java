package com.harrcharr.wbor.api;

import org.json.JSONObject;

public class Song extends JsonApiObject {
	public static final String API_OBJECT_NAME = "song";
	
	private String mTrackName;
	private String mArtistName;
	private Album mAlbum;

	public Song(JSONObject json){
		this.loadFromJSON(json);
	}
	public Song(String key) {
		super(key);
	}	
	public Song(String trackName, String artistName) {
		mTrackName = trackName;
		mArtistName = artistName;
	}
	public Song(String trackName, String artistName, String albumKey) {
		mTrackName = trackName;
		mArtistName = artistName;
		mAlbum = new Album(albumKey);
	}
	
	public Song maybeLoadAlbumFromApi() {
		getAlbum().maybeLoadFromApi();
		return this;
	}
	
	public String getTrackName() {
		maybeLoadFromApi();
		return mTrackName;
	}
	public String getArtistName() {
		maybeLoadFromApi();
		return mArtistName;
	}
	public Album getAlbum() {
		maybeLoadFromApi();
		return mAlbum;
	}
	
	@Override
	protected Song loadFromJSON(JSONObject json) {		
		try {
			String key, trackName, artistName, albumKey;
			
			key = json.getString("key");
			trackName = json.getString("title");
			artistName = json.getString("artist");
			albumKey = json.getString("album_key");

			this.mKey = key;
			this.mTrackName = trackName;
			this.mArtistName = artistName;
			this.mAlbum = new Album(albumKey);
		} catch (Exception e) {
			System.err.println("Error digesting JSON object for GET song API call");
			e.printStackTrace();			
		}
		return this;
	}
	
	@Override
	protected String getApiName() {
		return Song.API_OBJECT_NAME;
	}
}
