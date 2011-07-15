package be.niob.apps.gf2011;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import be.niob.apps.gf2011.provider.EventContract.Locations;
import be.niob.apps.gf2011.util.Dates;
import be.niob.apps.gf2011.util.EventUtil;

public class LocationActivity extends BaseActivity implements OnItemClickListener {

	public static final String DAY = "day";
	
	private String day;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(DAY)) {
			
			day = extras.getString(DAY);
			
			actionBar.setTitle(Dates.parseFormat(day));
			
			Cursor cursor = getContentResolver().query(Locations.buildLocationsOnDayUri(day), EventContract.LOCATION_PROJECTION, null, null, null);
			startManagingCursor(cursor);
			
			ListAdapter adapter = new LocationAdapter(this, cursor);
			listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			
		} else
			finish();
	}
	
	protected class LocationAdapter extends CursorAdapter {

		private LayoutInflater mInflater;
		private int locationIndex;
		
		public LocationAdapter(Context context, Cursor c) {
			super(context, c);
			locationIndex = c.getColumnIndex(Events.EVENT_LOCATION);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			final TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            final TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            
            String location = cursor.getString(locationIndex);
            String[] parts = EventUtil.splitLocation(location);
            
            text1.setText(parts[0]);
            text2.setText(parts[1]);
            
            view.setTag(location);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(android.R.layout.simple_list_item_2, null);
		}

	}
	
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Cursor o = (Cursor) listView.getAdapter().getItem(arg2);
	    String location = o.getString(0);
		Intent intent = new Intent(this, EventActivity.class);
		intent.putExtra(EventActivity.DAY, day);
		intent.putExtra(EventActivity.LOCATION, location);
		startActivity(intent);
	}

}
