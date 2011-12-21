package com.harrcharr.wbor;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.harrcharr.wbor.api.Play;

public class PlaylistAdapter extends BaseAdapter {
	private List<Play> mPlays;
	private LayoutInflater mInflater;
	
	public PlaylistAdapter(Context context, List<Play> play) {
		mPlays = play;
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
            convertView = mInflater.inflate(R.layout.play_list_item_view, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.trackName = (TextView) convertView.findViewById(R.id.track_name);
            holder.artistName = (TextView) convertView.findViewById(R.id.artist_name);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        System.err.println(mPlays.get(position).toString());
        
        // Bind the data efficiently with the holder.
        holder.trackName.setText(mPlays.get(position).getSong().getTrackName());
        holder.artistName.setText(mPlays.get(position).getSong().getArtistName());

        return convertView;
	}
	
	static class ViewHolder {
        TextView trackName;
        TextView artistName;
    }
}
