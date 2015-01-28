package com.example.fragviewsdemo;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;


public class TabbedView extends Activity {
    private static DisplayMetrics displayMetrics;

    // convert pix/font point for display independence
    public static int _dp(float pixels) {
        float dp = pixels * displayMetrics.density;
        // dp = 1 pixel if its zero to prevent divide by 0 error.
        if (dp < 1) dp = 1;
        return (int)dp;
    }
    private class tabListener implements TabListener {
        private final Activity currentActivity;
        private final String currentFragmentName;
        private Fragment fragToLaunch;
    	public tabListener(Activity activity, String fragmentName) {
    		currentActivity = activity; currentFragmentName = fragmentName;
    	}
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
    		fragToLaunch = Fragment.instantiate(currentActivity, currentFragmentName);
    		ft.replace(R.id.frag_container, fragToLaunch);
		}
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    		ft.remove(fragToLaunch);
    		fragToLaunch = null;
		}
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {}
    }
	// Save the current tab index.
    @Override
	protected void onSaveInstanceState(Bundle outState) {
    	int currentTabIndex = getActionBar().getSelectedNavigationIndex();
    	outState.putInt("currentTabIndex", currentTabIndex);
		super.onSaveInstanceState(outState);
	}
    private TextView customTextView(String tabText) {
    	TextView tv = new TextView(this);
    	tv.setText(tabText);
    	tv.setTextColor(0xffffffff);
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    	tv.setPadding(0, _dp(10), 0, 0); // pad top of tab title in dp
    	return tv;
    }
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
        displayMetrics = (this.getResources()).getDisplayMetrics();
        
        if (null != findViewById(R.id.frag_container)) {
			ActionBar tabbedActionBar = getActionBar();
			tabbedActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			Tab tabArray = tabbedActionBar.newTab();
			tabArray
				.setCustomView(customTextView(GridViewFrag.FRAGMENT_NAME))
				.setTabListener(new tabListener(
					this, GridViewFrag.class.getName()));
			tabbedActionBar.addTab(tabArray);
			tabArray = tabbedActionBar.newTab();
			tabArray
				.setCustomView(customTextView(ListViewFrag.FRAGMENT_NAME))
				.setTabListener(new tabListener(
					this, ListViewFrag.class.getName()));
			tabbedActionBar.addTab(tabArray);
			// restore the previous actionbar tab if it was saved.
			if (null != savedInstanceState) {
				tabbedActionBar.setSelectedNavigationItem(
					savedInstanceState.getInt("currentTabIndex")
				);
			}
        }
	}
}
