package com.example.fragviewsdemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridViewFrag extends Fragment implements OnItemClickListener {
	public static final String FRAGMENT_NAME = "Grid View";
    private static final String logTag = "GridViewFrag";
    private static final String TAG = logTag;
    private ImageAdapter imageAdapter = null;
    /*
     * Required fragment empty constructor.
     */
    public GridViewFrag() {
    	Log.i(logTag, "frag creation...");
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
        Toast.makeText(getActivity()
        	, logTag + " item " + position, Toast.LENGTH_SHORT).show();
	}
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageAdapter = new ImageAdapter(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	Log.i(logTag, "onCreateView");
		View v = inflater.inflate(R.layout.gridview_frag, container, false);
		GridView gv = (GridView) v.findViewById(R.id.gridview_frag);
		gv.setAdapter(imageAdapter);
		gv.setOnItemClickListener(this);
		return v;
	}

	private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public ImageAdapter(Context c) {
            Log.i(logTag, "ImageAdapter:creator");
            mContext = c;
        }
        public int getCount() {
            Log.i(logTag, "ImageAdapter:getCount");
            return mThumbIds.length;
        }
        public Object getItem(int position) {
            Log.i(logTag, "ImageAdapter:getItem");
            return null;
        }
        public long getItemId(int position) {
            Log.i(logTag, "ImageAdapter:getItemId");
            return 0;
        }
        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(logTag, "ImageAdapter:getView");
            ImageView imageView;
            // if it's not recycled, initialize some attributes
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        TabbedView._dp(300), TabbedView._dp(300)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
            }
            else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
    }
}
