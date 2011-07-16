package be.niob.apps.gf2011;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeActivity extends BaseActivity implements OnClickListener {
	
	private Button btDays;
	private Button btLocations;
  
    @Override
	protected int getLayoutId() {
		return R.layout.activity_home;
	}
    /*
    @Override
    protected void findAndSetupViews() {
    	super.findAndSetupViews();
    	btDays = (Button) findViewById(R.id.days);
    	btDays.setOnClickListener(this);
    	btLocations = (Button) findViewById(R.id.locations);
    	btLocations.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.days: {
				startActivity(new Intent(this, EventActivity.class));
			}
			case R.id.locations: {
				
			}
		}
	}*/

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}