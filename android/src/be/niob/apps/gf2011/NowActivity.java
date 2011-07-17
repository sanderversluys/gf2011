package be.niob.apps.gf2011;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;

public class NowActivity extends BaseActivity implements OnItemClickListener {

	private ExpandableListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Cursor cursor = getContentResolver().query(Events.buildEventsNow(), EventContract.RICH_PROJECTION, null, null, Events.EVENT_BEGIN);
		
		listView = (ExpandableListView) findViewById(android.R.id.list);
		startManagingCursor(cursor);
		
		//SimpleCursorTreeAdapter
		
		ListAdapter adapter = new EventAdapter(this, cursor);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	protected class EventAdapter extends CursorAdapter {

		private LayoutInflater mInflater;
		private int titleIndex;
		private int descriptionIndex;
		private int beginIndex;
		private int endIndex;
		
		public EventAdapter(Context context, Cursor c) {
			super(context, c);
			titleIndex = c.getColumnIndex(Events.EVENT_TITLE);
			descriptionIndex = c.getColumnIndex(Events.EVENT_DESCRIPTION);
			beginIndex = c.getColumnIndex(Events.EVENT_BEGIN);
			endIndex = c.getColumnIndex(Events.EVENT_END);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			final TextView titleView = (TextView) view.findViewById(R.id.title);
            final TextView descriptionView = (TextView) view.findViewById(R.id.description);
            final TextView timeView = (TextView) view.findViewById(R.id.time);
            
            String title = cursor.getString(titleIndex);
            String description = cursor.getString(descriptionIndex);
            String time = cursor.getString(beginIndex) + " - " + cursor.getString(endIndex);
            
            titleView.setText(title);
            descriptionView.setText(description);
            timeView.setText(time);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(R.layout.list_item_event, null);
		}

	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_now;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
