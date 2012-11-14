package com.tunein.tvschedule.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tunein.tvschedule.TVGroupTimePeriod;
import com.tunein.tvschedule.TVSchedule;
import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.parser.TVPeriodParser;

public class PeriodGrouperTest {

    @Test
    public void testSimpleGrouper() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        String expected = "Car Racing, S/M/T/W/T/F/S, 6pm, 1hr";
        
        
        List<TVGroupTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }


    @Test
    public void testSimpleGrouperContiguous() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12am", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        
        String expected = "Car Racing, M, 12am, 7hr";
        
        
        List<TVGroupTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }
}
