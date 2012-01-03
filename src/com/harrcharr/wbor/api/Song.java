package com.harrcharr.wbor.api;

import java.util.HashMap;

import org.json.JSONObject;

public class Song extends JsonApiObject {
	public static final String API_OBJECT_NAME = "song";
	
	private String mTrackName;
	private String mArtistName;
	private Album mAlbum;
	
	//                  String TB_COL_KEY
	public static final String TB_COL_TRACK_NAME = "track_name";
	public static final String TB_COL_ARTIST_NAME = "artist_name";
	public static final String TB_COL_ALBUM = "album";
	
	public static final String DB_CREATE_TABLE = 
			"CREATE TABLE songs_table (" +
	                TB_COL_KEY + " TEXT, " +
	                TB_COL_TRACK_NAME + " TEXT, " +
	                TB_COL_ARTIST_NAME + " TEXT, " +
	                TB_COL_ALBUM + " TEXT);";

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
	
	public void maybeLoadFromApi(int flags) {
		super.maybeLoadFromApi(flags);
		if (mAlbum != null) {
			if ((flags & PROPERTY_ALBUM) != 0) {
				mAlbum.maybeLoadFromApi(flags);
			}
		}
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
			String key, trackName, artistName, albumKey = null;
			
			key = json.getString("key");
			trackName = json.getString("title");
			artistName = json.getString("artist");
			
			if (!json.isNull("album_key")) {
				albumKey = json.optString("album_key");
			}

			this.mKey = key;
			this.mTrackName = trackName;
			this.mArtistName = artistName;
			
			if (albumKey != null || albumKey != JSONObject.NULL || albumKey.equals("null")) {
				this.mAlbum = new Album(albumKey);
			}
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
