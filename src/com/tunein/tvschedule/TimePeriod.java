package com.tunein.tvschedule;


/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TimePeriod implements Comparable {
    private long start;
    private long duration;
    
    public TimePeriod(long start, long duration) {
        this.start = start;
        this.duration = duration;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
