package be.niob.apps.gf2011;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import be.niob.apps.gf2011.util.Dates;

public class DaysActivity extends BaseActivity implements OnItemClickListener {

	private ListView listView;
	                                    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<String> days = new ArrayList<String>();
		for (String day : Dates.days) {
			days.add(Dates.parseFormat(day));
		}
		
		listView = (ListView) findViewById(android.R.id.list);
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String date = Dates.days[arg2];
		Intent intent = new Intent(this, LocationActivity.class);
		intent.putExtra(LocationActivity.DAY, date);
		startActivity(intent);
	}
	
}
