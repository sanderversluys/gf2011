package be.niob.apps.gf2011;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
		
}
