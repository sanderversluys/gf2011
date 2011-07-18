package be.niob.apps.gf2011;

import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.provider.EventContract.Locations;
import be.niob.apps.gf2011.util.EventUtil;

public class ChooseLocationsActivity extends Activity implements OnItemClickListener {

	private List<String> favLocations;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_locations);
		
		favLocations = EventUtil.loadFavs(this);
		
		Cursor	cursor = getContentResolver().query(Locations.CONTENT_URI, EventContract.LOCATION_PROJECTION, null, null, null);
		startManagingCursor(cursor);
		
		ListAdapter adapter = new LocationAdapter(this, cursor, android.R.layout.simple_list_item_2);
		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
            view.setBackgroundColor(favLocations.contains(location) ? Color.WHITE : green);
            
            if (text2 != null) text2.setText(parts[1]);
            
            view.setTag(location);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(layoutId, null);
		}

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Cursor o = (Cursor) listView.getAdapter().getItem(arg2);
	    String location = o.getString(0);
    
    	if (favLocations.contains(location))
    		favLocations.remove(location);
    	else
    		favLocations.add(location);
    	EventUtil.saveFavs(this, favLocations);
   
		Log.d("boe", "check count: " + listView.getCheckedItemPositions().size());	
	}
	
}
