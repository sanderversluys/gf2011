package be.niob.apps.gf2011;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class ToiletMapActivity extends MapActivity {

	public static final String TAG = "ToiletMapActivity";
	
	private MapView mapView;
	private ArrayList<OverlayItem> mOverlays;
	
	private Drawable unisexMarker;
	private Drawable boyMarker;
	
	private class ToiletItemizedOverlay extends ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		
		public ToiletItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		public void addOverlay(OverlayItem overlay) {
		    mOverlays.add(overlay);
		    populate();
		}
		
		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}
		
	}
	
	public boolean isOnline() {
	 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	 return cm.getActiveNetworkInfo().isConnectedOrConnecting();

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_toilet_map);
	    
	    if (!isOnline()) {
	    	Toast.makeText(this, "Er is internet verbinding nodig om de kaart met toiletten te laden", Toast.LENGTH_LONG).show();
	    	finish();
	    } else {
		    ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	    	if (actionBar != null) {
		    	actionBar.setTitle("Openbare toiletten");
		    	actionBar.setHomeAction(new IntentAction(this, new Intent(this, HomeActivity.class), R.drawable.ic_title_home));
	    	}
		    
		    mapView = (MapView) findViewById(R.id.mapview);
		    mapView.setBuiltInZoomControls(true);
		    
		    unisexMarker = this.getResources().getDrawable(R.drawable.ic_map_boy_girl);
		    boyMarker = this.getResources().getDrawable(R.drawable.ic_map_boy);
		    
		    new ParseToiletsFromKML().execute();
	    }
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public static GeoPoint getGeoPoint(double latitude , double longitude) {
	   Log.d(TAG, "GeoPointUtils.getGeoPoint(double)");
	   Log.d(TAG, "\tIncoming lat -> " + latitude);
	   Log.d(TAG, "\tConverted lat -> " + (int) (latitude * 1E6));
	   Log.d(TAG, "\tIncoming long -> " + longitude);
	   Log.d(TAG, "\tConverted long -> " + (int) (longitude * 1E6));

	   return new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
	}
	
	public static GeoPoint getGeoPoint(String latitude , String longitude) {
	   Log.d(TAG, "GeoPointUtils.getGeoPoint(String)");
	   Log.d(TAG, "\tIncoming lat -> " + latitude);
	   Log.d(TAG, "\tConverted lat -> " + Double.parseDouble(latitude));
	   Log.d(TAG, "\tIncoming long -> " + longitude);
	   Log.d(TAG, "\tConverted long -> " + Double.parseDouble(longitude));

	   return getGeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
	}
	
	private class ParseToiletsFromKML extends AsyncTask<Void, Void, ArrayList<OverlayItem>> {

		@Override
		protected ArrayList<OverlayItem> doInBackground(Void... params) {
			ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
			
			try {
				XmlPullParser xpp=getResources().getXml(R.xml.toilets);

				String[] pointSplit = null;
				GeoPoint point = null;
				
				while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {

					if (xpp.getEventType()==XmlPullParser.START_TAG) {
					
						boolean unisex = true;
						if (xpp.getName().equals("SimpleData") && xpp.getAttributeValue(0).equals("Type")) {
							/*if(xpp.next() == XmlPullParser.TEXT)
								unisex = xpp.getText() != null && !xpp.getText().equals("Plaszuilen");*/
						}
						
						if (xpp.getName().equals("coordinates")) {
							
							if(xpp.next() == XmlPullParser.TEXT) {
								pointSplit = xpp.getText().split(",");
								point = getGeoPoint(pointSplit[1], pointSplit[0]);
							    OverlayItem overlayitem = new OverlayItem(point, "Toilet", "Fiesteuh!");
							    //overlayitem.setMarker(unisex ? unisexMarker : boyMarker);
								overlayItems.add(overlayitem);
							}
						}
					}
					
					xpp.next();
				}
				
				return overlayItems;
			}
			catch (Throwable t) {
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(ArrayList<OverlayItem> result) {
			if (result != null) {
				showToilets(result);
			} else {
				Toast.makeText(ToiletMapActivity.this, "kon toilet coordinaten niet verwerken", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	protected void showToilets(ArrayList<OverlayItem> overlayItems) {
		List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.ic_map_boy_girl);
	    ToiletItemizedOverlay itemizedoverlay = new ToiletItemizedOverlay(drawable);
	    for (OverlayItem item : overlayItems)
	    	itemizedoverlay.addOverlay(item);
		mapOverlays.add(itemizedoverlay);
		mapView.getController().setCenter(new GeoPoint(51055706, 3727820));
		mapView.getController().setZoom(16);
		
		
	}
	
}
