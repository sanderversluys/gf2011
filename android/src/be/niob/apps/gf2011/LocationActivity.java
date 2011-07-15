package be.niob.apps.gf2011;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.provider.EventContract.Locations;

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
			
			Cursor cursor = getContentResolver().query(Locations.buildLocationsOnDayUri(day), EventContract.LOCATION_PROJECTION, null, null, null);
			startManagingCursor(cursor);
			
			String[] columns = new String[] { Events.EVENT_LOCATION };
			int[] to = new int[] { android.R.id.text1 };
			
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, columns, to);
			listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			
		} else
			finish();
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
