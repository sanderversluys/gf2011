package be.niob.apps.gf2011;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeActivity extends BaseActivity implements OnClickListener {
	
	private Button btDay;
	private Button btLocation;
	private Button btStarred;
	private Button btNow;
	
	private ProgressDialog progressDialog;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
		
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
		Intent intent = null;
		switch(v.getId()) {
			case R.id.home_btn_day:
				startActivity(new Intent(this, DaysActivity.class));
				break;
			case R.id.home_btn_location:
				intent = new Intent(this, LocationActivity.class);
				intent.putExtra(LocationActivity.ACTION_CHOOSE_FAVS, true);
				startActivity(intent);
				break;
			case R.id.home_btn_starred:
				// TODO go to starred activity
				break;
			case R.id.home_btn_now:
				intent = new Intent(this, EventActivity.class);
				intent.putExtra(EventActivity.NOW, true);
				startActivity(intent);
				//new Intent(this, NowActivity.class));
				break;
		}
	}
	
}