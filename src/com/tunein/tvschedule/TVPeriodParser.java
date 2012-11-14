/**
 * 
 */
package com.tunein.tvschedule;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVPeriodParser {

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

    
    private static Pattern DURATION_PATTERN_DEFAULT = Pattern.compile("\\d+hr");
    private static Pattern DURATION_PATTERN_EXTRA_MIN = Pattern.compile("\\d+:\\d*hr");
    private static Pattern HOUR24_PATTERN = Pattern.compile("\\d+:\\d+");
    private static Pattern HOUR24_PATTERN_NOMINUTE = Pattern.compile("\\d+:\\d+");
    private static Pattern HOUR_AMPM_PATTERN = Pattern.compile("\\d+:\\d+(am|pm)");
    private static Pattern HOUR_AMPM_PATTERN_NOMINUTE = Pattern.compile("\\d+(am|pm)");


    
    /**
     * @param lines The 4 lines for parsing the TVTimePeriod
     * @return The parsed object
     */
    public static TVTimePeriod parsePeriod(String... lines) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("At least 4 lines must be informed for TVPeriodParser.parsePeriod(String... lines).");
        }

        String shortName = lines[0];
        String weekDay = lines[1];
        String startTime = lines[2];
        String duration = lines[3];

        return new TVTimePeriod(shortName, weekDay, startTime, duration);
    }

    
    
    /**
     * @param weekDay Day of the week in string format
     * @param startTime Hour of day in string format
     * @return long notation of the day
     */
    public static long getTime(String weekDay, String startTime) {
        long day = getDayOfWeek(weekDay);
        long daytime = getDaytime(startTime);
        
        return day * 24L * 3600L * 1000L + // Millis for the day
               daytime;                    // Millis for the daytime
    }


    
    /**
     * @param duration Expected format: 1hr | 1:30hr | 10hr
     * @return millis format of hour + minute
     */
    public static long getDuration(String duration) {
        long hour = 0L;
        long min = 0L;
        

        // Example: 20hr
        if (DURATION_PATTERN_DEFAULT.matcher(duration).matches()) {
            hour = Long.parseLong(duration.substring(0, duration.indexOf("hr")));
        }


        // Example: 10:30hr
        else if (DURATION_PATTERN_EXTRA_MIN.matcher(duration).matches()) {
            hour = Long.parseLong(duration.substring(0, duration.indexOf(":")));
            min  = Long.parseLong(duration.substring(duration.indexOf(":") + 1, duration.indexOf("hr")));
        }
        

        // Example: 10:30
        else if (HOUR24_PATTERN.matcher(duration).matches()) {
            hour = Long.parseLong(duration.substring(0, duration.indexOf(":")));
            min  = Long.parseLong(duration.substring(duration.indexOf(":") + 1));
        }
        
        
        else {
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }

        return hour * 3600L * 1000L + 
                min * 60L * 1000L;
    }

    
    /**
     * @param startTime String format
     * @return Milliseconds formated hour and minute
     */
    private static long getDaytime(String startTime) {
        long hour = 0L;
        long min = 0L;


        // Example: 1pm
        if (HOUR_AMPM_PATTERN_NOMINUTE.matcher(startTime).matches()) {
            if (startTime.contains("am")) { 
                hour = Long.parseLong(startTime.substring(0, startTime.indexOf("am")));
            } else {
                hour = 12 + Long.parseLong(startTime.substring(0, startTime.indexOf("pm")));
            }
        }


        // Example: 1:30pm
        else if (HOUR_AMPM_PATTERN.matcher(startTime).matches()) {
            if (startTime.contains("am")) { 
                hour = Long.parseLong(startTime.substring(0, startTime.indexOf(":")));
                min = Long.parseLong(startTime.substring(startTime.indexOf(":") + 1, startTime.indexOf("am")));
            } else {
                hour = 12 + Long.parseLong(startTime.substring(0, startTime.indexOf(":")));
                min = Long.parseLong(startTime.substring(startTime.indexOf(":") + 1, startTime.indexOf("pm")));
            }
        }

        
        // Example: 10:30
        else if (HOUR24_PATTERN.matcher(startTime).matches()) {
            hour = Long.parseLong(startTime.substring(0, startTime.indexOf(":")));
            min  = Long.parseLong(startTime.substring(startTime.indexOf(":") + 1));
        } 
        

        // Example: 13
        else if (HOUR24_PATTERN_NOMINUTE.matcher(startTime).matches()) {
            hour = Long.parseLong(startTime);
        }
        
        return hour * 3600L * 1000L + //millis in the hour
                min * 60L * 1000L;    // millis in the minute
    }


    
    /**
     * @param weekDay String format of Dayof Week
     * @return Calendar.MONDAY and other values (the int value 1 for Sunday, 2 for Monday and so on)
     */
    public static Integer getDayOfWeek(String weekDay) {
        Integer day = WEEK_DAYS_LONG.get(weekDay);

        if (day == null) {
            day = WEEK_DAYS_SHORT.get(weekDay);
        }

        if (day == null) {
            throw new IllegalArgumentException("Invalid week day: " + weekDay);
        }
        return day;
    }


    
    /*
     * Simple testing
     * @param args
     */
    public static void main(String[] args) {
        TVPeriodParser.parsePeriod(new String[] {"Car Racing", "Tuesday", "6pm", "1h"});
        System.out.println(TVPeriodParser.getDayOfWeek("Sat"));
        System.out.println(TVPeriodParser.getDuration("10hr"));
        System.out.println(TVPeriodParser.getDuration("11:30hr"));
        System.out.println(TVPeriodParser.getDuration("20:30"));
        
        System.out.println(TVPeriodParser.getTime("Sunday", "10:30pm"));
        System.out.println(TVPeriodParser.getTime("Tuesday", "9:30am"));
        System.out.println(TVPeriodParser.getTime("Sunday", "22:30"));
        System.out.println(TVPeriodParser.getTime("Tuesday", "9:30"));
        System.out.println(TVPeriodParser.getTime("Sunday", "10pm"));
        System.out.println(TVPeriodParser.getTime("Tuesday", "9am"));
    }
    
}
