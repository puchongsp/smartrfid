package sushil.luc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to convert date to string and vice-versa
 */
public class DateUtil {
    public DateUtil(){

    }

    public static Date stringToDate(String str){
    	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
    
    public static String DatetoString(Date input){
    	
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String str = input.toString();
        
        str = dateFormat.format(input);
        return str;    }
    

    public static Date timestampToDate(Long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }

    /**
     * Replace T character with spaces,
     * if the format returned from API contains it
     * @param Date
     * @return
     */
    public static String formatDate (String Date)
    {
    	String s = Date.replaceAll("T", " ");
    	int point = s.indexOf(".");
    	if (point!=-1)
    		s = s.substring(0, point);
    	return s;
    }
}
