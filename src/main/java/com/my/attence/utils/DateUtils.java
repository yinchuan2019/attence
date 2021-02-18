package com.my.attence.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/3/10 010.
 */
public class DateUtils {

    public static final int day = 26;

    public static LocalDate getTodayBegin() {
        int dd = DateUtils.day;
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


    public static List<LocalDate> querySaturday(int year, Month month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstSaturday = LocalDate.now().with(yearMonth).with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        return Stream.iterate(firstSaturday, localDate -> localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)))
                .limit(4)
                .filter(localDate -> localDate.getMonth() == month)
                .collect(Collectors.toList());
    }

    public static List<LocalDate> querySunday(int year, Month month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstSaturday = LocalDate.now().with(yearMonth).with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
        return Stream.iterate(firstSaturday, localDate -> localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)))
                .limit(4)
                .filter(localDate -> localDate.getMonth() == month)
                .collect(Collectors.toList());
    }

}
