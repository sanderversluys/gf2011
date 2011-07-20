package be.niob.apps.gf2011.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {
	
	public static String[] days = new String[] {
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
	
	private static SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd MMMM"); 

	public static Date parse(String date) {
		try {
			return parser.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date) {
		return formatter.format(date);
	}
	
	public static String parseFormat(String date) {
		return format(parse(date));
	}
	
	public static String getToday() {
		return parser.format(new Date());
	}
	
}
