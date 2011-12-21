package com.harrcharr.wbor.api;

import java.util.Date;

import org.json.JSONObject;

public class Play extends ApiObject {
	public static final String API_OBJECT_NAME = "play";
	
	private Song mSong;
	private Program mProgram;
	private Date mDate;
	
	public Play(JSONObject json){
		this.loadFromJSON(json);
	}
	public Play(String key) {
		this.mKey = key;
		this.loadedFromApi = false;
	}
	public Play(Song song) {
		this.mSong = song;
	}
	public Play(Song song, Program program) {
		this.mSong = song;
		this.mProgram = program;
	}
	
	public Song getSong() {
		this.maybeLoadFromApi();
		return this.mSong;

	}
	public Program getProgram() {
		this.maybeLoadFromApi();
		return this.mProgram;
	}

	@Override
	protected Play loadFromJSON(JSONObject json) {
		String key, songKey, programKey;
		
		try {
			key = json.getString("key");
			songKey = json.getString("song_key");
			programKey = json.getString("program_key");

			this.mKey = key;
			this.mSong = new Song(songKey);
			this.mProgram = new Program(programKey);
		} catch (Exception e) {
			System.err.println("Error digesting JSON object for nowPlaying API call");
			e.printStackTrace();			
		}
		return this;
	}
	
	@Override
	public String toString() {
		return this.getSong().getTrackName();
	}
	
	@Override
	protected String getApiName() {
		return Play.API_OBJECT_NAME;
	}
}
