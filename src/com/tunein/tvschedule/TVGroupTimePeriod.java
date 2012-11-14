/**
 * 
 */
package com.tunein.tvschedule;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Everton Rosario (erosario@gmail.com)
 *
 */
public class TVGroupTimePeriod {
    private String shortName;
    private String weekDay;
    private String startTime;
    private String durationTime;

    private TreeSet<TVTimePeriod> periods = new TreeSet<TVTimePeriod>();
    
    
    
    public TVGroupTimePeriod(String shortName, String weekDay, String startTime, String durationTime) {
        super();
        this.shortName = shortName;
        this.weekDay = "" + weekDay.charAt(0);
        this.startTime = startTime;
        this.durationTime = durationTime;
        
        this.periods.add(new TVTimePeriod(shortName, weekDay, startTime, durationTime));
    }

    public TVGroupTimePeriod(TVTimePeriod period) {
        this(period.getShortName(), period.getWeekDay(), period.getStartTime(), period.getDurationTime());
    }
    
    public void addPeriod(TVTimePeriod periodToAdd) {
        periods.add(periodToAdd);
        this.weekDay += "/" + periodToAdd.getWeekDay().charAt(0);
    }

    public String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(shortName);
        builder.append(", ");
        builder.append(weekDay);
        builder.append(", ");
        builder.append(startTime);
        builder.append(", ");
        builder.append(durationTime);
        return builder.toString();
    }

    @Override
    public String toString() {
        final int maxLen = 500;
        StringBuilder builder = new StringBuilder();
        builder.append("TVGroupTimePeriod [");
        if (shortName != null) {
            builder.append("shortName=");
            builder.append(shortName);
            builder.append(", ");
        }
        if (weekDay != null) {
            builder.append("weekDay=");
            builder.append(weekDay);
            builder.append(", ");
        }
        if (startTime != null) {
            builder.append("startTime=");
            builder.append(startTime);
            builder.append(", ");
        }
        if (durationTime != null) {
            builder.append("durationTime=");
            builder.append(durationTime);
            builder.append(", ");
        }
        if (periods != null) {
            builder.append("periods=");
            builder.append(toString(periods, maxLen));
        }
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
