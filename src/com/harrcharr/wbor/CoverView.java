package com.harrcharr.wbor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.harrcharr.wbor.api.Cover;

public class CoverView extends ImageView {
	protected Cover mCover;
	protected Handler mCoverUpdateHandler;
	protected Resources mResources;
	
	public CoverView(Context context) {
		super(context);
        mCoverUpdateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		Bitmap coverImage = (Bitmap)msg.obj;
        		try {
        			setImageBitmap(coverImage);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        };
	}
	
	public CoverView(Context context, AttributeSet attrib) {
		super(context, attrib);
        mCoverUpdateHandler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        		Bitmap coverImage = (Bitmap)msg.obj;
        		try {
        			setImageBitmap(coverImage);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        };
	}
	
	public CoverView(Context context, Cover cover) {
		this(context);
		setCover(cover);
	}

	public void setCover(Cover cover) {
		mCover = cover;
		new Thread(new Runnable() {
			public void run() { 
				if (mCover != null) {
					mCover.maybeLoadFromApi();

					Message msg = new Message();
					try {
						msg.obj = mCover.getImage();
						mCoverUpdateHandler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
