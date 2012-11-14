/**
 * 
 */
package com.tunein.tvschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVSchedule {
    
    /* Matrix of schedule
       Columns: days of week
       Lines: hours of day
       
       Sun(0)   Mon(1)  Tue(2)  Wed(3)  Thu(4)  Fri(5)  Sat(6)
  1am   1
  2am   1
  3am   1        1        1       1       1       1       1
  4am   1
  5am   1
  6am   1
  7am   1
  8am   1
  9am   1
 10am   1
 11am   1
 12am   0
  1pm   0
  2pm   0
  3pm   0
  4pm   0
  5pm   0
  6pm   0
  7pm   0
  8pm   0
  9pm   0
 10pm   0
 11pm   0
 12pm   0
     */ 
    
    

    public static List<TVGroupTimePeriod> optmize(List<TVTimePeriod> periods) {
        
        TreeSet<TVTimePeriod> orderedPeriods = new TreeSet<TVTimePeriod>(periods);
        

        
        // Optmize by shortName, start and duration fields
        Map<String, TVGroupTimePeriod> groupByNameStartDuration = new HashMap<String, TVGroupTimePeriod>();  
        
        // Optmize by shortName and weekday for contiguous
        Map<String, TVGroupTimePeriod> groupContiguous = new HashMap<String, TVGroupTimePeriod>();  

        for (TVTimePeriod period : orderedPeriods) {
            TVGroupTimePeriod group = groupByNameStartDuration.get(period.getGroupingKey());
            if (group == null) {
                group = new TVGroupTimePeriod(period);
                groupByNameStartDuration.put(period.getGroupingKey(), group);
            } else {
                group.addPeriod(period);
            }
        }
        
        
        return new ArrayList<TVGroupTimePeriod>(groupByNameStartDuration.values());
    }

}
