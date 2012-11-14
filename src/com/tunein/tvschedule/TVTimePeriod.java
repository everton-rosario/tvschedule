/**
 * 
 */
package com.tunein.tvschedule;

import com.tunein.tvschedule.parser.TVPeriodParser;


/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriod implements Comparable<TVTimePeriod> {

    // Calculated fields
    private long start;
    private long duration;

    // Informed fields
    private String shortName;
    private String weekDay;
    private String startTime;
    private String durationTime;

    public TVTimePeriod(String shortName, String weekDay, String startTime, String duration) {
        this.shortName = shortName;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.durationTime = duration;
        this.start = TVPeriodParser.getTime(weekDay, startTime);
        this.duration = TVPeriodParser.getDuration(duration);
    }

    
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
    
    public boolean isContiguous(TVTimePeriod other) {
        if (other == null) {
            return false;
        }
        
        return this.start + this.duration == other.start || this.start == other.start + other.duration;
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
    
    

}
