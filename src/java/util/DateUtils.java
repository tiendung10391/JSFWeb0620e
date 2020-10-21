/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class DateUtils implements Serializable {

    public static Date addYear(Date date, int year) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    public static String convertDate(Date dtDate, String formatDate) throws Exception {
        if (dtDate == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
            return formatter.format(dtDate);
        }
    }
    
    public static java.sql.Date getSQLDate(Date dtDate) throws Exception{
        if(dtDate != null ){
            return new java.sql.Date(dtDate.getTime());
        }else{
            return null;
        }
    }

}
