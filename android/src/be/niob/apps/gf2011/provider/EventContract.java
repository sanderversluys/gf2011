package be.niob.apps.gf2011.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class EventContract {
	
	private EventContract() {}

	interface EventsColumns {

		// String EVENT_ID = "_id";
		String EVENT_TITLE = "title";
		String EVENT_DESCRIPTION = "description";
		String EVENT_DATE = "date";
		String EVENT_BEGIN = "begin";
		String EVENT_END = "end";
		String EVENT_TIME_BEGIN = "time_begin";
		String EVENT_TIME_END = "time_end";
		String EVENT_LOCATION = "location";
		String EVENT_STARRED = "starred";
		String EVENT_INDOOR = "indoor";
		String EVENT_PARTICIPANTS = "participants";
	}

	public static final String CONTENT_AUTHORITY = "be.niob.apps.gf2011";

	private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	private static final String PATH_EVENTS = "events";
	//private static final String PATH_DAYS = "days";
	private static final String PATH_LOCATIONS = "locations";

	public static class Events implements EventsColumns, BaseColumns {

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_EVENTS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.event";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.event";

		public static Uri buildEventUri(String eventId) {
			return CONTENT_URI.buildUpon().appendPath(eventId).build();
		}
		
		public static Uri buildEventsOnDayUri(String day) {
			return CONTENT_URI.buildUpon().appendPath("on").appendPath(day).build();
		}
		
		public static Uri buildEventsOn(String day, String location) {
			return CONTENT_URI.buildUpon().appendPath("on").appendPath(day)
										  .appendPath("in").appendPath(location).build();
		}
		
		public static Uri buildEventsNow() {
			return CONTENT_URI.buildUpon().appendPath("now").build();
		}
		
		public static String getEventId(Uri uri) {
            return uri.getPathSegments().get(0);
        }
		
	}
	
	public static class Locations {
		
		public static final String PREFERENCES_KEY = "LocationsPrefs";
		
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
												.appendPath(PATH_LOCATIONS).build();
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.location";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.location";
		
		public static Uri buildLocationsOnDayUri(String day) {
			return CONTENT_URI.buildUpon().appendPath("day").appendPath(day).build();
		}
		
		public static Uri buildLocationsFilter(String filter) {
			return CONTENT_URI.buildUpon().appendPath("filter").appendPath(filter).build();
		}
		
	}
	
	public static final String[] LOCATION_PROJECTION = new String[] {
		Events.EVENT_LOCATION
	};
	
	public static final String[] SMALL_PROJECTION = new String[] {
		Events._ID,
		Events.EVENT_TITLE,
		Events.EVENT_DESCRIPTION
	};
	
	public static final String[] RICH_PROJECTION = new String[] {
		Events._ID,
		Events.EVENT_TITLE,
		Events.EVENT_DESCRIPTION,
		Events.EVENT_DATE,
		Events.EVENT_LOCATION,
		Events.EVENT_BEGIN,
		Events.EVENT_END,
		Events.EVENT_INDOOR,
		Events.EVENT_PARTICIPANTS
	};
	
	/*
	public static class Days {
		
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_DAYS).build();
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.day";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.day";
	}
*/
}
