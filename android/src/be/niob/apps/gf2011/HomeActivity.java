package be.niob.apps.gf2011;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import be.niob.apps.gf2011.provider.EventContract.Events;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class HomeActivity extends ListActivity implements OnItemClickListener {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initActionBar();
        
        Cursor cursor = getContentResolver().query(Events.CONTENT_URI, new String[] {Events._ID, Events.EVENT_TITLE, Events.EVENT_DESCRIPTION}, null, null, null);
        startManagingCursor(cursor);

        String[] columns = new String[] { Events.EVENT_TITLE, Events.EVENT_DESCRIPTION };
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
        
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to);
        setListAdapter(mAdapter);
        
        getListView().setOnItemClickListener(this);
    }
    
    public void initActionBar() {
    	ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
    	// You can also assign the title programmatically by passing a
    	// CharSequence or resource id.
    	actionBar.setTitle(R.string.app_name);
    	actionBar.setHomeAction(new IntentAction(this, new Intent(this, HomeActivity.class), R.drawable.ic_title_home));
    	actionBar.addAction(new IntentAction(this, null, R.drawable.ic_title_search));
    	//actionBar.addAction(new ToastAction());
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
}