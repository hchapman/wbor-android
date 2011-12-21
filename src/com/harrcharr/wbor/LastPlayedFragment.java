package com.harrcharr.wbor;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.harrcharr.wbor.api.Play;
import com.harrcharr.wbor.api.Wbor;

/**
 * 
 */

/**
 * @author harrcharr
 *
 */
public class LastPlayedFragment extends ListFragment {
	Handler updateHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
        updateHandler = new Handler() {
        	@SuppressWarnings("unchecked")
			@Override
        	public void handleMessage(Message msg) {        		
        		setListAdapter(new ArrayAdapter<Play>(getView().getContext(), 
        				R.layout.list_item, (ArrayList<Play>)msg.obj));

        		ListView lv = getListView();
        		lv.setTextFilterEnabled(true);
        	}
        };
	}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			ViewGroup container, Bundle savedInstanceState) {
//		//return inflater.inflate(R.layout.lastplayed, container, false);
//	}
	
	@Override
	public void onStart() {
		super.onStart();
		new Thread(new Runnable() {
			public void run() {
        		Message msg = new Message();
        		try {
        			ArrayList<Play> plays = Wbor.getLastPlays();
        			msg.obj = plays;
        			
        			updateHandler.sendMessage(msg);
        		} catch (Exception e) {
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
