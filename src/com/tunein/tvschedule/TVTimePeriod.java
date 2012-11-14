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
    
}
