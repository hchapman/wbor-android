package com.harrcharr.wbor;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBar;
import android.support.v4.view.ViewPager;

public class WborActivity extends ActionBarTabsPager {
	/**
	 * @author Harrison Chapman
	 *
	 */
	Handler mHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab tab1 = getSupportActionBar().newTab().setText("Now Playing");
        ActionBar.Tab tab2 = getSupportActionBar().newTab().setText("Recently");

        mViewPager = (ViewPager)findViewById(R.id.pager);
        System.err.println(mViewPager);
        mTabsAdapter = new TabsAdapter(this, getSupportActionBar(), mViewPager);
        mTabsAdapter.addTab(tab1, NowPlayingFragment.class);
        mTabsAdapter.addTab(tab2, LastPlayedFragment.class);

        if (savedInstanceState != null) {
        	getSupportActionBar().setSelectedNavigationItem(savedInstanceState.getInt("index"));
        }
    }
}