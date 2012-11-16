package com.tunein.tvschedule.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
        
        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr"
        };

        
        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
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
        
        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "Car Racing, Sunday, 7pm, 3hr",
                "Car Racing, Wednesday, 8pm, 2hr"
        };
        
        
        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }

    
    @Test
    public void testT2_grouperContinuousSameday() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        
        String[] expected = new String [] {
                "Car Racing, Monday, 12pm, 7hr"
        };
        

        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }
    
    @Test
    public void testT2b_grouperContinuousSameday() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "2hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        
        String[] expected = new String [] {
                "Car Racing, Monday, 12pm, 7hr"
        };
        
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
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

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "7pm", "1hr"})));
        
        String[] expected = new String [] {
                "Car Racing, Sunday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "Car Racing, Monday, 12pm, 8hr"
        };

        
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());

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
        

        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "Car Racing, Monday, 1pm, 5hr"
        };

        
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
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

        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 3pm, 1hr",
                "Car Racing, Monday, 1pm, 2hr",
                "Car Racing, Monday/Tuesday, 4pm, 1hr",
                "Car Racing, Monday, 5pm, 1hr",
                "Car Racing, Thursday, 4pm, 4hr",
        };

        
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());

    }

    
    @Test
    public void testT5_grouperContinuousDifferentDays() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "12pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "5pm", "1hr"})));
        
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "12pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "2pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "3pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "5pm", "1hr"})));

        String[] expected = new String [] {
                "Car Racing, Monday, 12pm, 6hr",
                "Car Racing, Friday, 12pm, 6hr",
        };
        

        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }

    @Test
    public void testT5_GroupOfGroupTest() {
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

        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "4pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "5pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "7pm", "1hr"})));

        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 3pm, 1hr",
                "Car Racing, Monday, 1pm, 2hr",
                "Car Racing, Monday/Tuesday/Thursday/Friday, 4pm, 1hr",
                "Car Racing, Monday/Thursday/Friday, 5pm, 1hr",
                "Car Racing, Thursday/Friday, 6pm, 1hr",
                "Car Racing, Thursday/Friday, 7pm, 1hr",
        };

        
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());

    }
    
    @Test
    public void testT6_Conflict() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Saturday", "6pm", "1hr"})));
        
        String[] expected = new String [] {
                "Conflict in Period[Car Racing, Saturday, 6pm, 1hr] with Period[NFL Game, Saturday, 6pm, 1hr]",
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "NFL Game, Saturday, 6pm, 1hr",
               
        };

        
        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }
    
    @Test
    public void testT6_NoConflict() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Saturday", "10pm", "1hr"})));
        
        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "NFL Game, Saturday, 10pm, 1hr",
        };

        
        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }

    @Test
    public void testT7_DualProgram() {
        List<TVTimePeriod> periods = new ArrayList<TVTimePeriod>();
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Monday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Friday", "6pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Saturday", "6pm", "1hr"})));
        
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Saturday", "1pm", "1hr"})));
        periods.add(TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Saturday", "2pm", "3hr"})));
        
        String[] expected = new String [] {
                "Car Racing, Sunday/Monday/Tuesday/Wednesday/Thursday/Friday/Saturday, 6pm, 1hr",
                "NFL Game, Saturday, 1pm, 4hr",
        };

        
        // Method in test
        List<TVTimePeriod> groups = TVSchedule.optmize(periods);
        List<String> result = TVTimePeriod.format(groups);
        
        Assert.assertArrayEquals("expected grouping all day in one occurrence.", expected, result.toArray());
    }
}
