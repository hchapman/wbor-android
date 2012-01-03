package com.harrcharr.wbor.api;

import org.json.JSONObject;

public abstract class JsonApiObject extends ApiObject {
	public JsonApiObject() {
		super();
	}
	public JsonApiObject(String key) {
		super(key);
	}
	
	protected abstract JsonApiObject loadFromJSON(JSONObject json);
	
	public void loadFromApi() {
		if (this.mKey != null) {
			String apiCall = this.getApiName() + "/" + this.mKey;
			try {
				this.loadFromJSON(Wbor.getJSONResponseFromApi(apiCall));
				this.loadedFromApi = true;
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}

	@Override
	protected String getApiName() {
		return null;
	}

}
