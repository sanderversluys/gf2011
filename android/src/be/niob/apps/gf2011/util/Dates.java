package be.niob.apps.gf2011.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {
	
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
	
}
