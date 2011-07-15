package be.niob.apps.gf2011.util;

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
	
}
