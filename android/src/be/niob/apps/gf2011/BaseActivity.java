package be.niob.apps.gf2011;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	
	protected int layoutId = R.layout.activity_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutId);
		initActionBar();
	}
	
	protected abstract int getLayoutId();
	
	protected void findAndSetupViews() {
		
	}
	
	public void initActionBar() {
    	ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
    	if (actionBar != null) {
	    	actionBar.setTitle(R.string.app_name);
	    	actionBar.setHomeAction(new IntentAction(this, new Intent(this, HomeActivity.class), R.drawable.ic_title_home));
	    	actionBar.addAction(new IntentAction(this, null, R.drawable.ic_title_search));
	    	//actionBar.addAction(new ToastAction());
    	}
    }
	
}
