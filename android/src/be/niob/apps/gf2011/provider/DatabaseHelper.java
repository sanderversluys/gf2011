package be.niob.apps.gf2011.provider;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/be.niob.apps.gf2011/databases/";

	private static String DB_NAME = "events.db";

	public static int DB_VERSION = 6;

	private SQLiteDatabase db;

	private final Context context;

	interface Tables {
		String EVENTS = "events";
	}

	public static final String CREATE_SQL = "CREATE TABLE \"events\" (\"_id\" INTEGER PRIMARY KEY  NOT NULL , \"title\" TEXT, \"description\" TEXT, \"date\" TEXT, \"begin\" TEXT, \"end\" TEXT, \"time_begin\" NUMERIC, \"time_end\" NUMERIC, \"location\" TEXT, \"starred\" INTEGER, \"indoor\" INTEGER, \"participants\" INTEGER)";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {

		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SQL);
		InputStream inputStream = null;
		try {
			inputStream = context.getAssets().open("events.csv.png");
			DataInputStream in = new DataInputStream(inputStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String insert_start = "INSERT INTO events VALUES(";
			String insert_end = ")";
			while ((strLine = br.readLine()) != null)   {
				db.execSQL(insert_start + strLine + insert_end);
			}
			in.close();
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Tables.EVENTS);
		onCreate(db);
	}

}
