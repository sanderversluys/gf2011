package be.niob.apps.gf2011.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import be.niob.apps.gf2011.provider.EventContract;

public class EventUtil {

	public static String[] splitLocation(String location) {
		
		int firstComma = location.indexOf(",");
        
        String name = null;
        String address = null;
        if (firstComma > 0) {
        	name = location.substring(0, firstComma);
            address = location.substring(firstComma + 1).trim();
        } else {
        	name = location;
        }
        
        return new String[] { name, address };
		
	}
	
	public static List<String> loadFavs(Context context) {
		String key = EventContract.Locations.PREFERENCES_KEY;
		SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
		String locString = prefs.getString(key, "");
		List<String> favLocations = null;
		if (!locString.equals("")) {
			String[] locs = locString.split("\\|\\|\\|");
			favLocations = new ArrayList<String>(Arrays.asList(locs));
		} else
			favLocations = new ArrayList<String>();
		return favLocations;
	}
	
	public static void saveFavs(Context context, List<String> favLocations) {
		String key = EventContract.Locations.PREFERENCES_KEY;
		SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		StringBuffer buffer = new StringBuffer();
		int size = favLocations.size();
		for (int i=0; i<size; i++) {
			buffer.append(favLocations.get(i));
			if (i < size-1) buffer.append("|||");
		}
		editor.putString(key, buffer.toString());
		editor.commit();
	}
	
}
