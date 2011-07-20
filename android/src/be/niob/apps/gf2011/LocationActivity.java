package be.niob.apps.gf2011;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.provider.EventContract.Locations;
import be.niob.apps.gf2011.util.Dates;
import be.niob.apps.gf2011.util.EventUtil;

public class LocationActivity extends BaseActivity implements OnItemClickListener {

	public static final String DAY = "day";
	
	public static final String ACTION_CHOOSE_FAVS = "chooseFavs";
	private boolean isChoosingFavs = false;
	private List<String> favLocations;
	
	private Cursor cursor;
	
	private String day;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchData();
	}
	
	protected void fetchData() {
		Bundle extras = getIntent().getExtras();
		if (extras != null && (extras.containsKey(DAY) || extras.containsKey(ACTION_CHOOSE_FAVS))) {
			
			isChoosingFavs = extras.containsKey(ACTION_CHOOSE_FAVS);
			
			if (cursor != null)
				stopManagingCursor(cursor);
			
			if (isChoosingFavs) {
				favLocations = EventUtil.loadFavs(this);
				actionBar.setTitle(R.string.choose_locations);
				cursor = getContentResolver().query(Locations.CONTENT_URI, EventContract.LOCATION_PROJECTION, null, null, null);
			} else {
				day = extras.getString(DAY);
				actionBar.setTitle(Dates.parseFormat(day));
				cursor = getContentResolver().query(Locations.buildLocationsOnDayUri(day), EventContract.LOCATION_PROJECTION, null, null, null);
			}
			
			startManagingCursor(cursor);
			
			int layoutId = isChoosingFavs ? android.R.layout.simple_list_item_2 : android.R.layout.simple_list_item_2;
			
			ListAdapter adapter = new LocationAdapter(this, cursor, layoutId);
			listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			
		} else
			finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		fetchData();
	}
	
	protected class LocationAdapter extends CursorAdapter {

		private LayoutInflater mInflater;
		private int locationIndex;
		private int layoutId;
		
		private int green;
		
		public LocationAdapter(Context context, Cursor c, int layoutId) {
			super(context, c);
			locationIndex = c.getColumnIndex(Events.EVENT_LOCATION);
			mInflater = LayoutInflater.from(context);
			this.layoutId = layoutId;
			green = context.getResources().getColor(R.color.green);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			final TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            final TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            
            String location = cursor.getString(locationIndex);
            String[] parts = EventUtil.splitLocation(location);
            
            text1.setText(parts[0]);
            
            if (isChoosingFavs) {
            	view.setBackgroundColor(favLocations.contains(location) ? Color.WHITE : green);
            }
            
            if (text2 != null) text2.setText(parts[1]);
            
            view.setTag(location);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(layoutId, null);
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
	    
	    if (isChoosingFavs) {
	    	if (favLocations.contains(location))
	    		favLocations.remove(location);
	    	else
	    		favLocations.add(location);
	    	EventUtil.saveFavs(this, favLocations);
	    } else {
	    	Intent intent = new Intent(this, EventActivity.class);
			intent.putExtra(EventActivity.DAY, day);
			intent.putExtra(EventActivity.LOCATION, location);
			startActivity(intent);
	    }
		
		Log.d("boe", "check count: " + listView.getCheckedItemPositions().size());
		
	}

	
	
}
