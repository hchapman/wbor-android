package com.harrcharr.wbor;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.harrcharr.wbor.api.Album;
import com.harrcharr.wbor.api.Wbor;

public class NewShelfFragment extends Fragment {
	Handler updateHandler;
	GridView mCoverGrid;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
        updateHandler = new Handler() {
        	@SuppressWarnings("unchecked")
			@Override
        	public void handleMessage(Message msg) {   
        		mCoverGrid = (GridView)getView().findViewById(R.id.grid);
        		mCoverGrid.setAdapter(new CoverCollageAdapter(
        				getView().getContext(), (List<Album>)msg.obj));
        	}
        };
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.new_shelf, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new Thread(new Runnable() {
			public void run() {
        		Message msg = new Message();
        		try {
        			List<Album> albums = Wbor.getNewShelf();
        			msg.obj = albums;
        			
        			updateHandler.sendMessage(msg);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
			}
		}).start();
	}
}
