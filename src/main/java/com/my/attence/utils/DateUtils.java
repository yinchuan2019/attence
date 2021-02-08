package com.my.attence.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/10 010.
 */
public class DateUtils {

    public static LocalDate getTodayBegin(int dd) {
        LocalDate now ;
        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        if(dayOfMonth < dd){
            //取上月、
            today = today.minusMonths(1);
            now = LocalDate.of(today.getYear(), today.getMonth(), dd);
        }else {
            //取本月
            now = LocalDate.of(today.getYear(), today.getMonth(), dd);
        }
        return now;
    }

}
