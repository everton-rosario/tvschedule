/**
 * 
 */
package com.tunein.tvschedule;


/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriod extends TimePeriod {
    
    private String shortName;
    private String weekDay;
    private String startTime;
    private String duration;

    public TVTimePeriod(String shortName, String weekDay, String startTime, String duration) {
        super(TVPeriodParser.getTime(weekDay, startTime), TVPeriodParser.getDuration(duration));
        this.shortName = shortName;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.duration = duration;
    }


    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TVTimePeriod [");
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
        if (duration != null) {
            builder.append("duration=");
            builder.append(duration);
        }
        builder.append("]");
        return builder.toString();
    }
}
