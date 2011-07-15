package be.niob.apps.gf2011;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.util.EventUtil;

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
			
			actionBar.setTitle(EventUtil.splitLocation(location)[0]);
			
			listView = (ListView) findViewById(android.R.id.list);
			Cursor cursor = getContentResolver().query(Events.buildEventsOn(day, location), new String[] {Events._ID, Events.EVENT_TITLE, Events.EVENT_DESCRIPTION}, null, null, null);
			startManagingCursor(cursor);
			
			String[] columns = new String[] { Events.EVENT_TITLE, Events.EVENT_DESCRIPTION };
			int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
			
			SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to);
			listView.setAdapter(mAdapter);
			listView.setOnItemClickListener(this);
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
