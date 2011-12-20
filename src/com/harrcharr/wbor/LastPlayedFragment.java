package com.harrcharr.wbor;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 */

/**
 * @author harrcharr
 *
 */
public class LastPlayedFragment extends Fragment {
	Handler updateHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
        updateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		JSONObject json = (JSONObject)msg.obj;
        		JSONArray lastPlays;
        		try {
        			lastPlays = json.getJSONArray("last_plays");

        			for (int i = 0; i < lastPlays.length(); i++) {
        				try {
        					addPlayListNode(lastPlays.getJSONObject(i).getString("song_title"),
        							lastPlays.getJSONObject(i).getString("song_artist"));
        				} catch (Exception e) {
        					e.printStackTrace();
        				}	
        			} 
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        };
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.lastplayed, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new Thread(new Runnable() {
        	public void run() {
        		String np_json = "";
        		try {
        			URL updateinfo = new URL("http://1-6-api1.wbor-hr.appspot.com/api/lastPlays");
        			HttpURLConnection urlConnection = (HttpURLConnection) updateinfo.openConnection();
        			try {
        				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        				np_json = new String(IOUtils.toByteArray(in));
        			}
        			finally {
        				urlConnection.disconnect();
        			}
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
        		Message msg = new Message();
        		try {
        			msg.obj = new JSONObject(np_json);
        			updateHandler.sendMessage(msg);
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
        	}
        }).start();
		
	}
	public void addPlayListNode(String trackName, String artistName) {
		PlayListNode playNode = new PlayListNode(getView().getContext(),
				trackName, artistName);
		((LinearLayout)getView()).addView(playNode);
	}
	
	protected static class PlayListNode extends LinearLayout {
		private TextView trackNameView;
		private TextView artistNameView;
		
		public PlayListNode(Context context){
			super(context);
			inflate(context, R.layout.playlistnode, this);
			trackNameView = (TextView)findViewById(R.id.trackname);
			artistNameView = (TextView)findViewById(R.id.artistname);
		}
		
		public PlayListNode(Context context, String trackName, String artistName) {
			this(context);			
			setTrackInfo(trackName, artistName);
		}
		
		public void setTrackInfo(String trackName, String artistName) {
			trackNameView.setText(trackName);
			artistNameView.setText(artistName);
		}
	}
}
