package com.harrcharr.wbor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harrcharr.wbor.api.ApiObject;
import com.harrcharr.wbor.api.Play;
import com.harrcharr.wbor.api.Wbor;

public class NowPlayingFragment extends Fragment {
	Handler mUpdateHandler;
	
	protected Play mPlay;
	protected TextView mTrackView;
	protected TextView mArtistView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		
        mUpdateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		if (msg.obj != null) {
        			mPlay = (Play)msg.obj;
        		}

        		if (mPlay != null) {
        			try {
        				mTrackView.setText(mPlay.getSong().getTrackName());
        				mArtistView.setText(mPlay.getSong().getArtistName());
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}
        	}
        };
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.nowplaying, container, false);
		mTrackView = (TextView)v
				.findViewById(R.id.trackname);
		mArtistView = (TextView)v
				.findViewById(R.id.artistname);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new Thread(new Runnable() {
        	public void run() {
        		Message msg = new Message();
        		try {
        			mPlay = Wbor.getNowPlaying();
        			mPlay.maybeLoadAsyncFromApi(
        					Play.PROPERTY_ALBUM | Play.PROPERTY_PROGRAM,
        					mUpdateHandler);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        }).start();
	}
}
