package com.harrcharr.wbor.api;

import org.json.JSONObject;

public abstract class ApiObject {
	protected String mKey;
	protected boolean loadedFromApi = false;

	protected abstract ApiObject loadFromJSON(JSONObject json);
	public ApiObject loadFromApi() {
		String apiCall = this.getApiName() + "/" + this.mKey;
		try {
			this.loadFromJSON(Wbor.getJSONResponseFromApi(apiCall));
			this.loadedFromApi = true;
		} catch (Exception e) {
			
		}
		return this;
	}
	public ApiObject maybeLoadFromApi() {
		if (!(this.loadedFromApi || this.mKey == null)) {
			return this.loadFromApi();
		}
		return this;
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
