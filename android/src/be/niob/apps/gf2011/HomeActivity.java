package be.niob.apps.gf2011;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import be.niob.apps.gf2011.provider.DatabaseHelper;


public class HomeActivity extends BaseActivity implements OnClickListener {
	
	private Button btDay;
	private Button btLocation;
	private Button btStarred;
	private Button btNow;
	
	private ProgressDialog progressDialog;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkDatabase();
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
	
	private void checkDatabase() {
		final int currentVersion = DatabaseHelper.DB_VERSION;
		
		final SharedPreferences prefs = getSharedPreferences(DatabaseHelper.class.getName(), Context.MODE_PRIVATE);
		final int lastVersion = prefs.getInt("DB_VERSION", 1);
		
		if (currentVersion > lastVersion) {
			
			this.deleteDatabase(DatabaseHelper.DB_NAME);
			
			progressDialog = ProgressDialog.show(this, "Bezig met laden", "bezig met database te initialiseren", true);
			
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					DatabaseHelper db = new DatabaseHelper(HomeActivity.this);
					db.getReadableDatabase();
					return null;
				}
				
				protected void onPostExecute(Void result) {
					progressDialog.dismiss();
					
					Editor editor = prefs.edit();
					editor.putInt("DB_VERSION", currentVersion);
					editor.commit();
				}
				
			}.execute();
			
		}
	}
	
}