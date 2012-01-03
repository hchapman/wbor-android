package com.harrcharr.wbor;

import java.util.List;

import android.os.AsyncTask;
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
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.new_shelf, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mCoverGrid = (GridView)getView().findViewById(R.id.grid);
		new GetNewShelfTask().execute();
	}
	
	private class GetNewShelfTask extends AsyncTask<Void, Void, List<Album>> {
		@Override
		protected List<Album> doInBackground(Void... arg0) {
			return Wbor.getNewShelf();
		}
		
		@Override
		protected void onPostExecute(List<Album> newShelfList) {
       		mCoverGrid = (GridView)getView().findViewById(R.id.grid);
    		mCoverGrid.setAdapter(new CoverCollageAdapter(
    				getView().getContext(), newShelfList));			
		}
	}
	
}
