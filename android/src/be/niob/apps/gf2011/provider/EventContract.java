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
		String EVENT_LOCATION = "location";
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
		
		public static String getEventId(Uri uri) {
            return uri.getPathSegments().get(0);
        }
		
	}
	
	public static class Locations {
		
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
		.appendPath(PATH_LOCATIONS).build();
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.location";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.location";
		
		public static Uri buildLocationsOnDayUri(String day) {
			return CONTENT_URI.buildUpon().appendPath("day").appendPath(day).build();
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
	
	/*
	public static class Days {
		
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_DAYS).build();
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.day";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.day";
	}
*/
}
