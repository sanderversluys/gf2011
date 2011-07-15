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

public class EventActivity extends BaseActivity implements OnItemClickListener {

	private ListView listView;
	
	public static String ACTION_SHOW_DAY ="showDay";
	
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
		
		listView = (ListView) findViewById(android.R.id.list);
		
		Cursor cursor = getContentResolver().query(Events.CONTENT_URI, new String[] {Events._ID, Events.EVENT_TITLE, Events.EVENT_DESCRIPTION}, null, null, null);
		startManagingCursor(cursor);
		
		String[] columns = new String[] { Events.EVENT_TITLE, Events.EVENT_DESCRIPTION };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
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
