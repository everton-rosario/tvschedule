package com.tunein.tvschedule.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.tunein.tvschedule.TVGroupTimePeriod;
import com.tunein.tvschedule.TVSchedule;
import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.parser.TVPeriodParser;

public class PeriodGrouperTest {

    @Test
    public void testT1_grouperWeekdays() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        String expected = "Car Racing, S/M/T/W/T/F/S, 6pm, 1hr";
        
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }

    @Test
    public void testT1b_grouperWeekdays() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "7pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "8pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "9pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "8pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "9pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        String expected = "Car Racing, S/M/T/W/T/F/S, 6pm, 1hr\n" +
                          "Car Racing, S, 7pm, 3h\n" +
                          "Car Racing, W, 8pm, 2h";
        
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }

    
    @Test
    public void testT2_grouperContinuousSameday() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12am", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        
        String expected = "Car Racing, M, 12am, 7hr";
        
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }
    
    @Test
    public void testT2b_grouperContinuousSameday() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12am", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "2hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        
        String expected = "Car Racing, M, 12am, 7hr";
        
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, group.get(0).getStringRepresentation());
    }

    @Test
    public void testT3_contiguousThenWeekdays() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12am", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "7pm", "1hr"})));
        
        String expected = "Car Racing, M, 12am, 7hr\n" +
                          "Car Racing, S/T/W/T/F/S, 6pm, 1hr";
        
        String result = "";
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        for (TVTimePeriod grouppedPeriod : group) {
            if (StringUtils.isNotBlank(result)) {
                result += "\n";
            }
            result += grouppedPeriod.getStringRepresentation();
        }
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, result);
    }
    
    @Test
    public void testT4_weekdaysLargerThanContiguous() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        
        String expected = "Car Racing, M, 1pm, 5hr\n" +
                          "Car Racing, S/M/T/W/T/F/S, 6pm, 1hr";
        
        String result = "";
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        for (TVTimePeriod grouppedPeriod : group) {
            if (StringUtils.isNotBlank(result)) {
                result += "\n";
            }
            result += grouppedPeriod.getStringRepresentation();
        }
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, result);
    }
    
    
    @Test
    public void testT5_NotBestSolutionButBiggestFirst() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "3pm", "1hr"})));

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "4pm", "1hr"})));

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "7pm", "1hr"})));

        String expected = "Car Racing, S/M/T/W/T/F/S, 3pm, 1hr\n" +
                          "Car Racing, M, 1pm, 2hr\n" +
                          "Car Racing, M, 4pm, 2hr\n" +
                          "Car Racing, T, 4pm, 4hr";
        
        String result = "";
        
        List<TVTimePeriod> group = TVSchedule.optmize(periods);
        for (TVTimePeriod grouppedPeriod : group) {
            if (StringUtils.isNotBlank(result)) {
                result += "\n";
            }
            result += grouppedPeriod.getStringRepresentation();
        }
        
        
        Assert.assertEquals("expected grouping all day in one occurrence.", expected, result);
    }
    

}
