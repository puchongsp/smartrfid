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
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date timestampToDate(Long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }
}
