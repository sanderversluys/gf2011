package be.niob.apps.gf2011;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DaysActivity extends BaseActivity implements OnItemClickListener {

	private ListView listView;
	
	private String[] dates = new String[] {
			"16/07/2011",
			"17/07/2011",
			"18/07/2011",
			"19/07/2011",
			"20/07/2011",
			"21/07/2011",
			"22/07/2011",
			"23/07/2011",
			"24/07/2011",
			"25/07/2011"
	};
	                                    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<String> days = new ArrayList<String>();
		
		SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMMM"); 

		Date date = null;
		for (String ds : dates) {
			try {
				date = parser.parse(ds);
				days.add(formatter.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
		String date = dates[arg2];
		Intent intent = new Intent(this, LocationActivity.class);
		intent.putExtra(LocationActivity.DAY, date);
		startActivity(intent);
	}
	
}
