/**
 * 
 */
package com.tunein.tvschedule;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriod extends TimePeriod {
    
    private static Map<String, Integer> WEEK_DAYS_LONG = new HashMap<String, Integer>();
    private static Map<String, Integer> WEEK_DAYS_SHORT = new HashMap<String, Integer>();
    
    // Maps intialization for parsing Week days
    static {
        String[] weekdays = DateFormatSymbols.getInstance(Locale.US).getWeekdays();
        for (int i = 0; i < weekdays.length; i++) {
            WEEK_DAYS_LONG.put(weekdays[i], i);
        }

        String[] shortdays = DateFormatSymbols.getInstance(Locale.US).getShortWeekdays();
        for (int i = 0; i < shortdays.length; i++) {
            WEEK_DAYS_SHORT.put(shortdays[i], i);
        }
    }

    
    private String shortName;
    private String weekDay;
    private String startTime;
    private String duration;

    public TVTimePeriod(String shortName, String weekDay, String startTime, String duration) {
        super(getTime(weekDay, startTime), getDuration(duration));
        this.shortName = shortName;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.duration = duration;
        
    }


    private static long getTime(String weekDay, String startTime) {
        Integer day = getDayOfWeek(weekDay);
        Integer hour = getHourOfDay(startTime);
        Integer min = getMinuteOfDay(startTime);
        
        return day * 24 * 3600 * 1000 + // Millis for the day
               hour * 3600 * 1000 +     // Millis for the hour
               min * 60 * 1000;         // Millis for minute
    }

    private static long getDuration(String duration) {
        // TODO Auto-generated method stub
        return 0l;
    }

    private static Integer getMinuteOfDay(String startTime2) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Integer getHourOfDay(String startTime2) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Integer getDayOfWeek(String weekDay) {
        Integer day = WEEK_DAYS_LONG.get(weekDay);
        if (day == null) {
            day = WEEK_DAYS_SHORT.get(weekDay);
        }
        if (day == null) {
            throw new IllegalArgumentException("Invalid week day: " + weekDay);
        }
        return day;
    }
    
    public static void main(String[] args) {
        System.out.println(TVTimePeriod.getDayOfWeek("Sat"));
    }

}
