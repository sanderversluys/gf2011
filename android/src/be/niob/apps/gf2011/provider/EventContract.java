package be.niob.apps.gf2011.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class EventContract {
	
	private EventContract() {}

	interface EventsColumns {

		// String EVENT_ID = "_id";
		String EVENT_TITLE = "title";
		String EVENT_DESCRIPTION = "description";
		String EVENT_DATE = "event_date";
		String EVENT_BEGIN = "event_begin";
		String EVENT_END = "event_end";
		String EVENT_LOCATION = "event_location";
		String EVENT_INDOOR = "event_indoor";
		String EVENT_PARTICIPANTS = "event_participants";
	}

	public static final String CONTENT_AUTHORITY = "be.niob.apps.gf2011";

	private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	private static final String PATH_EVENTS = "events";

	public static class Events implements EventsColumns, BaseColumns {

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_EVENTS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gf2011.event";

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gf2011.event";

		public static Uri buildEventUri(String eventId) {
			return CONTENT_URI.buildUpon().appendPath(eventId).build();
		}
		
		public static String getEventId(Uri uri) {
            return uri.getPathSegments().get(0);
        }
	}

}
