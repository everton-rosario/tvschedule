/**
 * 
 */
package com.tunein.tvschedule;



/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriodConflict extends TVTimePeriod {

    private TVTimePeriod conflict1;
    private TVTimePeriod conflict2;
    
    
    /**
     * Only constructor, strict encapsulation
     * @param shortName
     * @param weekDay
     * @param startTime
     * @param duration
     */
    public TVTimePeriodConflict(TVTimePeriod conflict1, TVTimePeriod conflict2) {
        super(conflict1.getShortName(), conflict1.getWeekDay(), conflict1.getStartTime(), conflict1.getDurationTime());
        this.conflict1 = conflict1;
        this.conflict2 = conflict2;
    }
    
    public String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append("Conflict in Period[");
        builder.append(conflict1.getStringRepresentation());
        builder.append("] with Period[");
        builder.append(conflict2.getStringRepresentation());
        builder.append("]");
        return builder.toString();
    }
    
    @Override
    public int compareTo(TVTimePeriod obj) {
        return 1;
    }
}