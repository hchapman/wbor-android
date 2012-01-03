package com.harrcharr.wbor;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.harrcharr.wbor.api.Cover;
import com.harrcharr.wbor.api.Play;

public class PlaylistAdapter extends BaseAdapter {
	private List<Play> mPlays;
	private LayoutInflater mInflater;
	private Context mContext;
	
	public PlaylistAdapter(Context context, List<Play> play) {
		mPlays = play;
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mPlays.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
        	//convertView = new PlayView(mContext);
            convertView = mInflater.inflate(R.layout.play_list_item_view, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.trackName = (TextView) convertView.findViewById(R.id.track_name);
            holder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            holder.albumName = (TextView) convertView.findViewById(R.id.album_name);
            holder.albumCover = (CoverView) convertView.findViewById(R.id.album_cover);
            holder.play = mPlays.get(position);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        new LoadPlayTask().execute(holder);
        
        // Bind the data efficiently with the holder.
//        holder.trackName.setText(play.getSong().getTrackName());
//        holder.artistName.setText(play.getSong().getArtistName());
//        
//        String albumName = play.getSong().getAlbum().getTitle();
//        holder.albumName.setText(albumName);
//        Cover cover = play.getSong().getAlbum().getSmallCover();
//        holder.albumCover.setCover(cover);

        return convertView;
	}
	
	private static class ViewHolder {
		Play play;
        TextView trackName;
        TextView artistName;
        TextView albumName;
        CoverView albumCover;
    }
	
	private class LoadPlayTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
		@Override
		protected ViewHolder doInBackground(ViewHolder... v) {
			v[0].play.maybeLoadFromApi(Play.PROPERTY_SONG | Play.PROPERTY_ALBUM);
			return v[0];
		}
		
		@Override
		protected void onPostExecute(ViewHolder v) {
			v.trackName.setText(v.play.getSong().getTrackName());
			v.artistName.setText(v.play.getSong().getArtistName());
			if (v.play.getSong().getAlbum() != null) {
				v.albumName.setText(v.play.getSong().getAlbum().getTitle());
				v.albumCover.setCover(v.play.getSong().getAlbum().getSmallCover());	
			}
		}
	}
}
