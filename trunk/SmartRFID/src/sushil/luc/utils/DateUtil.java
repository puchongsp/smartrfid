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
    	
        DateFormat dateFormat = new SimpleDateFormat("dd-MM");
        String str = input.toString();
        
        str = dateFormat.format(input);
        return str;    }
    

    public static Date timestampToDate(Long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }
}