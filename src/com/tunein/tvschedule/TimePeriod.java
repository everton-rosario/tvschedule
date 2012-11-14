package com.tunein.tvschedule;


/**
 * @author Everton Rosario (erosario@gmail.com)
 * @param <T>
 */
public class TimePeriod implements Comparable<TimePeriod> {
    private long start;
    private long duration;
    
    public TimePeriod(long start, long duration) {
        this.start = start;
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (duration ^ (duration >>> 32));
        result = prime * result + (int) (start ^ (start >>> 32));
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

        TimePeriod other = (TimePeriod) obj;
        if (duration != other.duration)
            return false;
        if (start != other.start)
            return false;
        return true;
    }

    @Override
    public int compareTo(TimePeriod obj) {
        if (start < obj.start)
            return -1;
        if (start > obj.start)
            return 1;
        return 0;
    }

}
