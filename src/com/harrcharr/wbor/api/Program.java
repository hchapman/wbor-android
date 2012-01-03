package com.harrcharr.wbor.api;

import org.json.JSONObject;

public class Program extends JsonApiObject {
	public static final String API_OBJECT_NAME = "program";
	
	private String mName;
	private String mSlug;
	private String mDescription;
	private String mPageHTML;
	
	public Program(JSONObject json){
		this.loadFromJSON(json);
	}
	public Program(String key) {
		this.mKey = key;
		this.loadedFromApi = false;
	}
	public Program(String name, String slug) {
		
	}
	
	public Program maybeLoadFromApi(int flags) {
		maybeLoadFromApi();
		return this;
	}
	
	@Override
	protected Program loadFromJSON(JSONObject json) {
		String key;
		
		try {
			key = json.getString("key");

			this.mKey = key;
		} catch (Exception e) {
			System.err.println("Error digesting JSON object for nowPlaying API call");
			e.printStackTrace();			
		}
		return this;
	}
	
	@Override
	protected String getApiName() {
		return Program.API_OBJECT_NAME;
	}

}
