package com.harrcharr.wbor;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.harrcharr.wbor.api.Album;
import com.harrcharr.wbor.api.Cover;

public class CoverCollageAdapter extends BaseAdapter {
	private List<Album> mAlbums;
	private LayoutInflater mInflater;
	
	public CoverCollageAdapter(Context context, List<Album> albums) {
		mAlbums = albums;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mAlbums.size();
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
            convertView = mInflater.inflate(R.layout.cover_collage_item_view, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.albumCover = (CoverView) convertView.findViewById(R.id.album_cover);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        Cover cover = mAlbums.get(position).getSmallCover();
        holder.albumCover.setCover(cover);

        return convertView;
	}
	
	static class ViewHolder {
        CoverView albumCover;
    }
}
