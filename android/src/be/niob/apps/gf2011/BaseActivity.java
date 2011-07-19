package be.niob.apps.gf2011;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.provider.EventContract.Locations;
import be.niob.apps.gf2011.util.EventUtil;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public abstract class BaseActivity extends Activity {
	
	protected static final int REQUEST_LOCATIONS = 1;
	
	protected ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		initActionBar();
		findAndSetupViews();
	}
	
	protected abstract int getLayoutId();
	
	protected void findAndSetupViews() {
		
	}
	
	public void initActionBar() { 
    	actionBar = (ActionBar) findViewById(R.id.actionbar);
    	if (actionBar != null) {
	    	actionBar.setTitle(R.string.app_name);
	    	actionBar.setHomeAction(new IntentAction(this, new Intent(this, HomeActivity.class), R.drawable.ic_title_home));
	    	
	    	//actionBar.addAction(new FilterLocation());
	    	
	    	actionBar.addAction(new FilterLocation());
	    	//actionBar.addAction(new IntentAction(this, new Intent(this, DaysActivity.class), R.drawable.ic_title_search));
    	}
    }
	
	private class FilterLocation implements Action {

	    @Override
	    public int getDrawable() {
	        return R.drawable.ic_title_map;
	    }

	    @Override
	    public void performAction(View view) {
	    	Intent intent = new Intent(BaseActivity.this, ChooseLocationsActivity.class);
	    	startActivityForResult(intent, REQUEST_LOCATIONS);
	    }

	}
	
	public class LocationAdapter extends CursorAdapter {

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
	        
	        //view.setBackgroundColor(favLocations.contains(location) ? Color.WHITE : green);
	        
	        if (text2 != null) text2.setText(parts[1]);
	        
	        view.setTag(location);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(layoutId, null);
		}

	}
		
}
