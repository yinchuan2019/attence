package com.my.attence.utils;

import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
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

    public static LocalDateTime getCompleteTime(LocalDateTime time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localTime = df.format(time);
        final String[] s = localTime.split(" ");

        String hour = "00";//小时
        String minutes = "00";//分钟
        String outTime = "00:00";
        StringTokenizer st = new StringTokenizer(s[1], ":");
        List<String> inTime = new ArrayList<>();
        while (st.hasMoreElements()) {
            inTime.add(st.nextToken());
        }
        hour = inTime.get(0);
        minutes = inTime.get(1);
        if (Integer.parseInt(minutes) > 45) {
            hour = (Integer.parseInt(hour) + 1) + "";
            outTime = hour + ":00";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                outTime = sdf.format(sdf.parse(outTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (Integer.parseInt(minutes) > 30) {
            outTime = hour + ":45";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                outTime = sdf.format(sdf.parse(outTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (Integer.parseInt(minutes) > 15) {
            outTime = hour + ":30";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                outTime = sdf.format(sdf.parse(outTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (Integer.parseInt(minutes) > 0) {
            outTime = hour + ":15";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                outTime = sdf.format(sdf.parse(outTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outTime = hour + ":" + minutes;
        }
        final String result = s[0] + " " + outTime + ":00";
        final LocalDateTime parse = LocalDateTime.parse(result,df);
        return parse;
    }

    public static void main(String[] args) {
        final LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());
        final LocalDateTime completeTime = getCompleteTime(now);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localTime = df.format(completeTime);
        System.out.println(completeTime.toString());
    }

}
