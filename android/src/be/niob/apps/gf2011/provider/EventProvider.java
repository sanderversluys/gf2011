package be.niob.apps.gf2011.provider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import be.niob.apps.gf2011.provider.DatabaseHelper.Tables;
import be.niob.apps.gf2011.provider.EventContract.Events;
import be.niob.apps.gf2011.util.SelectionBuilder;

public class EventProvider extends ContentProvider {

	private static final String TAG = "EventProvider";
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

	private DatabaseHelper dbHelper;
	
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	private static final int EVENTS = 100;
	private static final int EVENTS_ID = 101;
    private static final int EVENTS_BETWEEN = 102;
    private static final int EVENTS_ON = 103;
    
    private static final int LOCATIONS = 300;
    
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = EventContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "events", EVENTS);
        matcher.addURI(authority, "events/on/*", EVENTS_ON);
        matcher.addURI(authority, "events/between/*/*", EVENTS_BETWEEN);
        matcher.addURI(authority, "events/*", EVENTS_ID);
        
        return matcher;
    }
    
    @Override
	public boolean onCreate() {
    	final Context context = getContext();
        dbHelper = new DatabaseHelper(context);
        try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return true;
	}
    
	

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return Events.CONTENT_TYPE;
            case EVENTS_ID:
                return Events.CONTENT_ITEM_TYPE;
            case EVENTS_BETWEEN:
                return Events.CONTENT_TYPE;
            case EVENTS_ON:
            	return Events.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (LOGV) Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
		final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
	        default: {
	            // Most cases are handled with simple SelectionBuilder
	            final SelectionBuilder builder = buildSelection(uri, match);
	            return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
	        }
	        /*case SEARCH_SUGGEST: {
	            final SelectionBuilder builder = new SelectionBuilder();
	
	            // Adjust incoming query to become SQL text match
	            selectionArgs[0] = selectionArgs[0] + "%";
	            builder.table(Tables.SEARCH_SUGGEST);
	            builder.where(selection, selectionArgs);
	            builder.map(SearchManager.SUGGEST_COLUMN_QUERY,
	                    SearchManager.SUGGEST_COLUMN_TEXT_1);
	
	            projection = new String[] { BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1,
	                    SearchManager.SUGGEST_COLUMN_QUERY };
	
	            final String limit = uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT);
	            return builder.query(db, projection, null, null, SearchSuggest.DEFAULT_SORT, limit);
	        }*/
	    }

	}
	
	private SelectionBuilder buildSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case EVENTS: {
                return builder.table(Tables.EVENTS);
            }
            case EVENTS_ID: {
                return builder.table(Tables.EVENTS)
                        .where(Events._ID + "=?", uri.getPathSegments().get(1));
            }
            case EVENTS_BETWEEN: {
                final List<String> segments = uri.getPathSegments();
                final String startTime = segments.get(2);
                final String endTime = segments.get(3);
                return builder.table(Tables.EVENTS)
                        .where(Events.EVENT_BEGIN + ">=?", startTime)
                        .where(Events.EVENT_END + "<=?", endTime);
            }
            case EVENTS_ON: {
            	return builder.table(Tables.EVENTS)
                .where(Events.EVENT_DATE + "=?", uri.getPathSegments().get(2));
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
