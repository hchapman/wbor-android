package com.harrcharr.wbor.api;

import android.os.Handler;

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
	public void maybeLoadFromApi(int flags){
		maybeLoadFromApi();
	}
	public void maybeLoadAsyncFromApi(final int flags, final Handler handler) {
		final ApiObject msgObj = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				maybeLoadFromApi(flags);
				if (handler != null) {
					handler.sendMessage(handler.obtainMessage(0, msgObj));
				}
			}}).start();
	}
	public void maybeLoadAsyncFromApi(final Handler handler) {
		maybeLoadAsyncFromApi(0, handler);
	}
	public void maybeLoadAsyncFromApi(int flags) {
		maybeLoadAsyncFromApi(flags, null);
	}
	public void maybeLoadAsyncFromApi() {
		maybeLoadAsyncFromApi(0, null);
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
	
	public static class NotLoadedException extends Exception {
		private static final long serialVersionUID = -1624769375580840095L;
		public NotLoadedException() {
			super();
		}
		public NotLoadedException(String msg) {
			super(msg);
		}
	}
}
