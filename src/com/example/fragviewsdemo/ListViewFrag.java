package com.example.fragviewsdemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewFrag extends Fragment implements OnClickListener{
	public static final String FRAGMENT_NAME = "List View";
    private static final String logTag = FRAGMENT_NAME;
    private static final String TAG = logTag;
    private static ListView listView = null;
    private static ButtonAdapter buttonAdapter = null;
	private class ButtonInfo {
		boolean enabled = true;
		int position;
		Object viewHolder;
		ButtonInfo(int position) {
			this.position = position;
		}
		public String toString() {
			return String.format("button %d:%b", position, enabled);
		}
	}
	private static final ButtonInfo[] buttonInfo = new ButtonInfo[256];
    /*
     * Required fragment empty constructor.
     */
    public ListViewFrag() {
    	Log.i(logTag, "frag creation...");
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buttonAdapter = new ButtonAdapter(getActivity());
		if (null == buttonInfo[0])
			for (int i = 0; i < buttonInfo.length; i++)
				buttonInfo[i] = new ButtonInfo(i);
		Log.i(logTag, "onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.listview_frag, container, false);
		ListView listView = (ListView) v.findViewById(R.id.listview_frag);
		listView.setAdapter(buttonAdapter);
		return v;
	}
	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		ButtonInfo bi = (ButtonInfo) v.getTag();
		bi.enabled = bi.enabled == false;
		buttonAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity()
            	, logTag + " item " + b.getText(), Toast.LENGTH_SHORT).show();
	}
	private class ButtonAdapter extends BaseAdapter {
		class ViewHolder {
			int index;
			Button button;
		}
		private Context context = null;
		private ButtonAdapter (Context c) {context = c; }
		//  How many items are in the data set represented by this Adapter. 
		@Override
		public int getCount() {
            Log.i(logTag, "ButtonAdapter:getCount");
			return buttonInfo.length;
		}
		// Get the data item associated with the specified position in the data set. 
		@Override
		public Object getItem(int position) {
            Log.i(logTag, "ButtonAdapter:getItem");
			return null;
		}
		// Get the row id associated with the specified position in the list.
		@Override
		public long getItemId(int position) {
            Log.i(logTag, "ButtonAdapter:getItemId");
			return 0;
		}
		// Get a View that displays the data at the specified position in the data set.
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(logTag, "ButtonAdapter:getView:positon = " + position);
            ViewHolder vh = null;
            Button b = null;
            ButtonInfo bi = null;
            if (null != convertView)
            	bi = (ButtonInfo) convertView.getTag();
            if (null == bi || position != bi.position) {
            	bi = buttonInfo[position];
            	bi.viewHolder = vh = new ViewHolder();
            	vh.index = position;
            	ListView.LayoutParams params = new ListView.LayoutParams(
            		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            	vh.button = b = new Button(context);
            	b.setTag(bi);
            	b.setBackgroundColor(0xff000000);
            	b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            	b.setLayoutParams(params);
            	b.setText(String.format("Button %d", position));
            	b.setOnClickListener(ListViewFrag.this);
            	vh.button = b;
            	buttonInfo[position].viewHolder = vh;
            }
            vh = (ViewHolder) bi.viewHolder;
            b = vh.button;
        	if (bi.enabled)
        		b.setTextColor(0xff00ff00);
        	else
        		b.setTextColor(0xffff0000);
			return b;
		}
	}
}
