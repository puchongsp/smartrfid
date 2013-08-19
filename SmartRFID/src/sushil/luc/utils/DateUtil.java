package sushil.luc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sushil on 6/26/13.
 */
public class DateUtil {
    public DateUtil(){

    }

    public static Date stringToDate(String str){
    	// 2013-05-10T21:13:06
    	//String s = str.replace("T", " ");
       // DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss");
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
    
    public static String formatDate (String Date)
    {
    	String s = Date.replaceAll("T", " ");
    	int point = s.indexOf(".");
    	if (point!=-1)
    		s = s.substring(0, point);
    	return s;
    }
}
