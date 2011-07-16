package be.niob.apps.gf2011;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeActivity extends BaseActivity implements OnClickListener {
	
	private Button btDay;
	private Button btLocation;
	private Button btStarred;
	private Button btNow;
  
    @Override
	protected int getLayoutId() {
		return R.layout.activity_home;
	}
    
    @Override
    protected void findAndSetupViews() {
    	super.findAndSetupViews();
    	btDay = (Button) findViewById(R.id.home_btn_day);
    	btDay.setOnClickListener(this);
    	btLocation = (Button) findViewById(R.id.home_btn_location);
    	btLocation.setOnClickListener(this);
    	btStarred = (Button) findViewById(R.id.home_btn_starred);
    	btStarred.setOnClickListener(this);
    	btNow = (Button) findViewById(R.id.home_btn_now);
    	btNow.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.home_btn_day:
				startActivity(new Intent(this, DaysActivity.class));
				break;
			case R.id.home_btn_location:
				startActivity(new Intent(this, LocationActivity.class));
				break;
			case R.id.home_btn_starred:
				// TODO go to starred activity
				break;
			case R.id.home_btn_now:
				// TODO go to now activity
				break;
		}
	}
	
}