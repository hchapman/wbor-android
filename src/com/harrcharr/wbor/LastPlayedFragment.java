package com.harrcharr.wbor;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.widget.ListView;

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
        		setListAdapter(new PlaylistAdapter(getView().getContext(), 
        				(List<Play>)msg.obj));

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
        			List<Play> plays = Wbor.getLastPlays();
        			for (Play play : plays) {
        				play.maybeLoadAsyncFromApi(
        						Play.PROPERTY_ALBUM | 
        						Play.PROPERTY_SONG);
        			}
        			msg.obj = plays;
        			
        			updateHandler.sendMessage(msg);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
			}
		}).start();
	}
}
