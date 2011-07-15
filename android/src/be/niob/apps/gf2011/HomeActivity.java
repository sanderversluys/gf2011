package be.niob.apps.gf2011;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import be.niob.apps.gf2011.provider.EventContract.Events;

public class HomeActivity extends ListActivity implements OnItemClickListener {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        Cursor cursor = getContentResolver().query(Events.CONTENT_URI, new String[] {Events._ID, Events.EVENT_TITLE, Events.EVENT_DESCRIPTION}, null, null, null);
        startManagingCursor(cursor);

        String[] columns = new String[] { Events.EVENT_TITLE, Events.EVENT_DESCRIPTION };
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
        
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to);
        setListAdapter(mAdapter);
        
        getListView().setOnItemClickListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
}