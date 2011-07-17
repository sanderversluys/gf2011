package be.niob.apps.gf2011;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.util.EventUtil;

import com.markupartist.android.widget.ActionBar.IntentAction;

public class EventActivity extends BaseActivity implements OnItemClickListener {

	private ListView listView;
	
	public static String DAY ="day";
	public static String LOCATION = "location";
	
	private String day;
	private String location;
	
	/*
	 Cursor cursor = getContentResolver().query(Events.buildEventsOnDayUri("17/07/2011"), EventContract.SMALL_PROJECTION, null, null, null);
		startManagingCursor(cursor);
		
		String[] columns = new String[] { Events.EVENT_TITLE, Events.EVENT_DESCRIPTION };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to);
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getExtras()) {
			
			String[] locParts = EventUtil.splitLocation(location);
			actionBar.setTitle(locParts[0]);
			
			Uri geoUri = Uri.parse("geo:0,0?q="+location);
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri); 
			
			actionBar.addAction(new IntentAction(this, mapIntent, R.drawable.ic_title_map));
			
			listView = (ListView) findViewById(android.R.id.list);
			Cursor cursor = getContentResolver().query(Events.buildEventsOn(day, location), EventContract.RICH_PROJECTION, null, null, Events.EVENT_BEGIN);
			startManagingCursor(cursor);
			ListAdapter adapter = new EventAdapter(this, cursor);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
		
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
	
	private boolean getExtras() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.containsKey(DAY))
				day = extras.getString(DAY);
			if (extras.containsKey(LOCATION))
				location = extras.getString(LOCATION);
			return true;
		}
		return false;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
