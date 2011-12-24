package com.harrcharr.wbor.api;

import org.json.JSONObject;

public abstract class ApiObject {
	protected String mKey;
	protected boolean loadedFromApi = false;

	public ApiObject() {
		loadedFromApi = false;
	}
	public ApiObject(String key) {
		mKey = key;
		loadedFromApi = false;
	}
	
	public abstract ApiObject loadFromApi();
	public ApiObject maybeLoadFromApi() {
		if (!(this.loadedFromApi || this.mKey == null)) {
			return this.loadFromApi();
		}
		return this;
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
