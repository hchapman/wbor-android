package com.harrcharr.wbor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NowPlayingFragment extends Fragment {
	Handler updateHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
        updateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		JSONObject json = (JSONObject)msg.obj;

        		TextView songTitleView = (TextView)getView()
        				.findViewById(R.id.trackname);
        		TextView songArtistView = (TextView)getView()
        				.findViewById(R.id.artistname);
        		try {
        			songTitleView.setText(json.getString("song_title"));
        			songArtistView.setText(json.getString("song_artist"));
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        };
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.nowplaying, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new Thread(new Runnable() {
        	public void run() {
        		String np_json = "";
        		try {
        			URL updateinfo = new URL("http://1-6-api1.wbor-hr.appspot.com/api/nowPlaying");
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
}
