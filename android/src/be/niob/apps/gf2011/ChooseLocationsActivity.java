package be.niob.apps.gf2011;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import be.niob.apps.gf2011.provider.EventContract;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.provider.EventContract.Locations;
import be.niob.apps.gf2011.util.EventUtil;

public class ChooseLocationsActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	private List<String> favLocations;
	private ListView listView;
	private Cursor cursor;

	private Button reset;
	private Button party;

	LocationAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_choose_locations);

		findAndSetupViews();

		favLocations = EventUtil.loadFavs(this);

		listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setTextFilterEnabled(true);

		initCursor();
	}

	protected void findAndSetupViews() {
		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(this);
		party = (Button) findViewById(R.id.populair);
		party.setOnClickListener(this);
	}

	protected void initCursor() {
		if (cursor != null)
			stopManagingCursor(cursor);
		cursor = getContentResolver().query(Locations.CONTENT_URI,
				EventContract.LOCATION_PROJECTION, null, null, null);
		startManagingCursor(cursor);
		adapter = new LocationAdapter(this, cursor,
				android.R.layout.simple_list_item_2);
		listView.setAdapter(adapter);
	}

	protected class LocationAdapter extends CursorAdapter implements Filterable {

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

			final TextView text1 = (TextView) view
					.findViewById(android.R.id.text1);
			final TextView text2 = (TextView) view
					.findViewById(android.R.id.text2);

			String location = cursor.getString(locationIndex);
			String[] parts = EventUtil.splitLocation(location);

			text1.setText(parts[0]);
			view.setBackgroundColor(favLocations.contains(location) ? Color.WHITE
					: green);

			if (text2 != null)
				text2.setText(parts[1]);

			view.setTag(location);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(layoutId, null);
		}

		@Override
		public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
			if (getFilterQueryProvider() != null) {
				return getFilterQueryProvider().runQuery(constraint);
			}
			String filter = constraint.toString();
			Cursor cursor = null;
			if (filter.equals("")) {
				cursor = getContentResolver().query(Locations.CONTENT_URI,
						EventContract.LOCATION_PROJECTION, null, null, null);
			} else {
				cursor = getContentResolver().query(
						Locations.buildLocationsFilter(constraint.toString()),
						EventContract.LOCATION_PROJECTION, null,
						new String[] { "%" + constraint.toString() + "%" },
						null);
			}
			return cursor;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Cursor o = (Cursor) listView.getAdapter().getItem(arg2);
		String location = o.getString(0);

		if (favLocations.contains(location))
			favLocations.remove(location);
		else
			favLocations.add(location);
		EventUtil.saveFavs(this, favLocations);

		Log.d("boe", "check count: "
				+ listView.getCheckedItemPositions().size());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset:
			favLocations = new ArrayList<String>();
			EventUtil.saveFavs(this, favLocations);
			adapter.notifyDataSetChanged();
			Toast.makeText(this,
					"Er zijn geen meer favoriete locaties geselecteerd",
					Toast.LENGTH_LONG);
			break;
		case R.id.populair:
			favLocations = Arrays.asList(popLocations);
			EventUtil.saveFavs(this, favLocations);
			adapter.notifyDataSetChanged();
			Toast.makeText(this, "Populaire party locaties zijn geselected",
					Toast.LENGTH_LONG);
			break;
		}
	}

	private String[] popLocations = new String[] {
			"Sint-Jacobs, Bij Sint-Jacobs, Gent",
			"Sint-Jacobs, Bij St-Jacobs, Gent", 
			"Baudelopark ",
			"Baudelopark Spiegeltent Baudelopark", 
			"Vlasmarkt ",
			"St - Baafsplein ",
			"10 Days Off - Vooruit, Sint-Pietersnieuwstraat , Gent",
			"Kinky Star, Vlasmarkt 9, Gent", 
			"Groentenmarkt, Gent",
			"Korenmarkt, Gent", 
			"Beverhoutplein, Gent",
			"Beverhoutplein, Gent ", 
			"Sint-Veerleplein, Gent",
			"St - Veerleplein ", 
			"François Laurentplein ",
			"Monasterium Poortackere, Oude Houtlei 56, Gent",
			"Minnemeers (NT2Gent), Minnemeers 9, Gent",
			"Hotsy Totsy, Hoogstraat 1, Gent",
			"café de loge, Annonciadenstraat 5, Gent",
			"Tijuana, Schuurkenstraat 2, Gent",
			"Los Perros Calientes, Goudstraat 2, Gent",
			"Zodiac, Heilige-Geeststraat 3, Gent", 
			"Emile Braunplein ",
			"Korenlei - Graslei ", 
			"Vrijdagmarkt ", 
			"Woodrow Wilsonplein, Gent" 
		};

}
