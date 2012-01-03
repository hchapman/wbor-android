package com.harrcharr.wbor.api;

public abstract class ApiObject {
	protected String mKey;
	protected boolean loadedFromApi = false;
	
	public static final String TB_COL_KEY = "key";
	
	public static final int PROPERTY_SONG = 1;
	public static final int PROPERTY_ALBUM = 2;
	public static final int PROPERTY_PROGRAM = 4;

	public ApiObject() {
		loadedFromApi = false;
	}
	public ApiObject(String key) {
		mKey = key;
		loadedFromApi = false;
	}
	
	public abstract void loadFromApi();
	public void maybeLoadFromApi() {
		if (!(this.loadedFromApi || this.mKey == null)) {
			this.loadFromApi();
		}
	}
	public boolean isLoadedFromApi() {
		return loadedFromApi;
	}
	
	public void setKey(String key) {
		this.mKey = key;
		this.loadedFromApi = false;
	}
	
	public String getKey(String key) {
		return this.mKey;
	}
	
	protected abstract String getApiName();
}
