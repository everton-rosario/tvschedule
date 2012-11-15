/**
 * 
 */
package com.tunein.tvschedule;

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
        this.startTime = startTime;
        this.durationTime = duration;
        this.start = TVPeriodParser.getTime(weekDay, startTime);
        this.startDaytime = TVPeriodParser.getDaytime(startTime); // normalizes 6pm to miliseconds in a day to achieve this amount 
        this.duration = TVPeriodParser.getDuration(duration);
        this.end = this.start + this.duration;
    }

    
    public boolean isContiguous(TVTimePeriod other) {
        if (other == null || !shortName.equals(other.shortName)) {
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
        
        TVTimePeriod first = start > other.start ? other : this;
        TVTimePeriod second = start > other.start ? this : other;
        
        return first.start <= second.start &&
               first.start < second.end &&
               first.end >= second.start  &&
               first.end >= second.end; /* ||
               
               first.start <= second.start &&
               first.start < second.end &&
               first.end > second.start &&
               first.end <= second.end; */
    }

    /**
     * Ordered by finishing time, works better for greedy algorithm
     */
    @Override
    public int compareTo(TVTimePeriod obj) {
        if (start + duration < obj.start + obj.duration)
            return -1;
        if (start + duration > obj.start + obj.duration)
            return 1;
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
    protected Object clone() throws CloneNotSupportedException {
        TVTimePeriod cloned = new TVTimePeriod(shortName, weekDay, startTime, durationTime);
        return cloned;
    }

}
