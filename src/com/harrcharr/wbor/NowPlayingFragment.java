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

import com.harrcharr.wbor.api.Play;
import com.harrcharr.wbor.api.Wbor;

public class NowPlayingFragment extends Fragment {
	Handler updateHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
        updateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		Play play = (Play)msg.obj;
        		TextView songTitleView = (TextView)getView()
        				.findViewById(R.id.trackname);
        		TextView songArtistView = (TextView)getView()
        				.findViewById(R.id.artistname);
        		try {
        			songTitleView.setText(play.getSong().getTrackName());
        			songArtistView.setText(play.getSong().getArtistName());
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
        		Message msg = new Message();
        		try {
        			Play play = Wbor.getNowPlaying();
        			play.getSong().loadFromApi();
        			msg.obj = play;
        			
        			updateHandler.sendMessage(msg);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        }).start();
	}
}
