/**
 * 
 */
package com.tunein.tvschedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.tunein.tvschedule.parser.TVPeriodParser;


/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriod implements Comparable<TVTimePeriod>, Cloneable {

    // Calculated fields
    private long start;
    private long startDaytime;
    private long duration;
    private long end;

    // Informed fields
    private String shortName;
    private String weekDay;
    private String startTime;
    private String durationTime;
    
    // Grouped Periods
    private TreeSet<String> daysOfWeek = new TreeSet<String>(new DaysOfWeekComparator());
    private TreeSet<TVTimePeriod> group = new TreeSet<TVTimePeriod>();

    /**
     * Only constructor, strict encapsulation
     * @param shortName
     * @param weekDay
     * @param startTime
     * @param duration
     */
    public TVTimePeriod(String shortName, String weekDay, String startTime, String duration) {
        this.shortName = shortName;
        this.weekDay = weekDay;
        this.daysOfWeek.add(weekDay);
        this.startTime = startTime;
        this.durationTime = duration;
        this.start = TVPeriodParser.getTime(weekDay, startTime);
        this.startDaytime = TVPeriodParser.getDaytime(startTime); // normalizes 6pm to miliseconds in a day to achieve this amount 
        this.duration = TVPeriodParser.getDuration(duration);
        this.end = this.start + this.duration;
    }
    
    
    
    /**
     * @param other
     * @return
     */
    public TVTimePeriod group(TVTimePeriod other) {
        
        TVTimePeriod grouped = this;
        
        if (isGroupable(other)) {
            grouped = (TVTimePeriod) this.clone();
            
            if (isContiguous(other)) {
                grouped.duration += other.duration;
                grouped.durationTime = grouped.duration / (3600 * 1000) + "hr";

            } else if (isGroupableWeekday(other)) {
                grouped.daysOfWeek.addAll(other.daysOfWeek);

            }

            if (isGroup()) {
                grouped.group.addAll(group);
            } else {
                grouped.group.add(this);
            }
            grouped.group.add(other);
        }

        return grouped;
    }


    public String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(shortName);
        builder.append(", ");
        builder.append(StringUtils.join(daysOfWeek, "/"));
        builder.append(", ");
        builder.append(startTime);
        builder.append(", ");
        builder.append(durationTime);
        return builder.toString();
    }
    
    
    
    public String getOriginalRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(shortName);
        builder.append("\n");
        builder.append(weekDay);
        builder.append("\n");
        builder.append(startTime);
        builder.append("\n");
        builder.append(durationTime);
        return builder.toString();

        
    }
    
    
    public boolean isGroup() {
        return group.size() > 0;
    }



    public boolean isContiguous(TVTimePeriod other) {
        
        // Excluded conditions: Same day of week and same program name 
        if (other == null || 
            !shortName.equals(other.shortName) ||
            !this.weekDay.equals(other.weekDay)) {
            
            return false;
        }
        
        return this.start + this.duration == other.start || this.start == other.start + other.duration;
    }

    public boolean isGroupable(TVTimePeriod other) {
        return isContiguous(other) || isGroupableWeekday(other);
    }

    public boolean isGroupableWeekday(TVTimePeriod other) {
        return this.shortName.equals(other.shortName) &&
                this.duration == other.duration &&
                this.startDaytime == other.startDaytime;
    }
    
    
    /**
     * Has conflict periods:
     * 
     * ========
     *   ----
     *     
     * ====
     *   -----
     *     
     * Has NO conflict periods:
     * ====
     *     ----
     * 
     * ====  
     *       -----
     *       
     * @param other
     * @return
     */
    public boolean hasConflict(TVTimePeriod other) {
        
        boolean hasConflict = false;
        
        if (other == null) {
            return false;
        }

        
        // Recursion for checking grouped periods
        if (isGroup()) {
            for (TVTimePeriod period : group) {
                hasConflict = period.hasConflict(other);
                if (hasConflict) return true;
            }
        } else {
            if (other.isGroup()) {
                for (TVTimePeriod period : other.group) {
                    hasConflict = period.hasConflict(this);
                    if (hasConflict) return true;
                }
            }
        }
        
        TVTimePeriod first = start > other.start ? other : this;
        TVTimePeriod second = start > other.start ? this : other;
        
        return first.start <= second.start &&
               first.start < second.end &&
               first.end >= second.start  &&
               first.end >= second.end;
    }

    /**
     * Ordered by starting time, works better for greedy algorithm
     */
    @Override
    public int compareTo(TVTimePeriod obj) {
        if (start < obj.start)
            return -1;
        if (start > obj.start)
            return 1;
        if (!shortName.equals(obj.shortName)) {
            return 1;
        }
        return 0;
    }

    
    public String getGroupingKey() {
        StringBuffer sbuff = new StringBuffer();
        sbuff.append(this.shortName).append("-").append(this.startTime).append("-").append(this.durationTime);
        return sbuff.toString();
    }


    public String getGroupingKeyDaily() {
        StringBuffer sbuff = new StringBuffer();
        sbuff.append(this.shortName).append("-").append(this.weekDay).append("-").append(this.durationTime);
        return sbuff.toString();
    }
    

    
    
    /* -----------
     * Getters
     * ----
     */
    
    public long getStart() {
        return start;
    }

    
    public String getShortName() {
        return shortName;
    }


    public String getWeekDay() {
        return weekDay;
    }


    public String getStartTime() {
        return startTime;
    }


    public long getDuration() {
        return duration;
    }
    
    public String getDurationTime() {
        return durationTime;
    }

    
    
    
    /* ----------------------
     * 
     * Overriding methods for utilities (hashCode, equals, toString, clone)
     * 
     * ---- 
     */
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (duration ^ (duration >>> 32));
        result = prime * result
                + ((durationTime == null) ? 0 : durationTime.hashCode());
        result = prime * result
                + ((shortName == null) ? 0 : shortName.hashCode());
        result = prime * result + (int) (start ^ (start >>> 32));
        result = prime * result
                + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((weekDay == null) ? 0 : weekDay.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TVTimePeriod other = (TVTimePeriod) obj;
        if (duration != other.duration)
            return false;
        if (durationTime == null) {
            if (other.durationTime != null)
                return false;
        } else if (!durationTime.equals(other.durationTime))
            return false;
        if (shortName == null) {
            if (other.shortName != null)
                return false;
        } else if (!shortName.equals(other.shortName))
            return false;
        if (start != other.start)
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (weekDay == null) {
            if (other.weekDay != null)
                return false;
        } else if (!weekDay.equals(other.weekDay))
            return false;
        return true;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TVTimePeriod [start=");
        builder.append(start);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", ");
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
        }
        builder.append("]");
        return builder.toString();
    }
    
    @Override
    protected Object clone() {
        
        TVTimePeriod cloned = new TVTimePeriod(shortName, weekDay, startTime, durationTime);

        if (!group.isEmpty()) {
            
            TreeSet<String> clonedDaysOfWeek = new TreeSet<String>();
            cloned.daysOfWeek.clear();

            for (TVTimePeriod period : group) {
                cloned.group.add((TVTimePeriod) period.clone());
                clonedDaysOfWeek.addAll(period.daysOfWeek);
            }
            
            cloned.daysOfWeek.addAll(clonedDaysOfWeek);
        }
        return cloned;
    }



    public int size() {
        return group.size();
    }



    public static List<String> format(List<TVTimePeriod> groups) {
        List<String> result = new ArrayList<String>();
        for (TVTimePeriod period : groups) {
            result.add(period.getStringRepresentation());
        }
        
        return result;
    }



    

}

class DaysOfWeekComparator implements Comparator<String> {

    @Override
    public int compare(String day1, String day2) {
        return TVPeriodParser.getDayOfWeek(day1).compareTo(TVPeriodParser.getDayOfWeek(day2));
    }

}
