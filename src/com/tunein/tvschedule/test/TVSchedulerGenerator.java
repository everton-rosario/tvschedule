/**
 * 
 */
package com.tunein.tvschedule.test;

import java.util.ArrayList;
import java.util.List;

import com.tunein.tvschedule.TVSchedule;
import com.tunein.tvschedule.TVTimePeriod;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVSchedulerGenerator {
    private static final String[] TV_SHOW_NAMES = new String[] {"F1 Racing", "XGames", "NFL Game", "NBA Game"};
    private static final int[] TV_SHOW_DURATION = new int[] {1, 2, 3, 1};
    private static final String[] WEEKDAYS = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String[] DAY_HOURS = new String[] {"12am", "1am", "2am", "3am", "4am", "5am", "6am", "7am", "8am", "9am", "10am", "11am",
                                                            "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm"};
    public static TVTimePeriod generate(String weekDay, int start, String startTime) {
        return new TVTimePeriod(sortShowName(), weekDay, startTime, sortDuration(start));
    }

    private static String sortDuration(int startTime) {
        int index = (int)(Math.random() * ((TV_SHOW_DURATION.length -1) + 1));

        int duration = TV_SHOW_DURATION[index];
        if (startTime + duration > 24) {
            duration = 24 - startTime;
        }
        return duration + "hr";
    }

    private static String sortShowName() {
        int index = (int)(Math.random() * ((TV_SHOW_NAMES.length-1) + 1));
        return TV_SHOW_NAMES[index];
    }

    
    public static List<TVTimePeriod> generateFullWeek() {
        List<TVTimePeriod> generateds = new ArrayList<TVTimePeriod>();
        for (String weekDay : WEEKDAYS) {
            int start = 0;
            while (start < 24) {
                TVTimePeriod period = generate(weekDay, start, DAY_HOURS[start]);
                generateds.add(period);
                start += period.getDuration() / (3600 * 1000);
            }
            
        }
        
        return generateds;
    }
    
    public static void main(String[] args) {
        long totalSpent = 0L;
        for (int i = 0; i < 1000; i++) {
            List<TVTimePeriod> week = generateFullWeek();
//            TreeSet<TVTimePeriod> periods = new TreeSet<TVTimePeriod>(week);
//            for (TVTimePeriod tvTimePeriod : periods) {
//                System.out.println(tvTimePeriod.getStringRepresentation());
//            }
            long start = System.currentTimeMillis();
            List<TVTimePeriod> optmize = TVSchedule.optmize(week);
            long spent = System.currentTimeMillis() - start;
            totalSpent += spent;
//            List<String> list = TVTimePeriod.format(optmize);
//            System.out.println("#######################");
//            for (String line : list) {
//                System.out.println(line);
//            }
//            System.out.println("optmization: " + spent + "ms");
        }
        System.out.println("Benchmark execution for 1000 weeks");
        System.out.println("TOTAL:" + totalSpent + "ms");
        System.out.println("Medium:" + totalSpent/1000 + "ms");
    }
    
}
