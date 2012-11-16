/**
 * 
 */
package com.tunein.tvschedule.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.tunein.tvschedule.TVTimePeriod;
import com.tunein.tvschedule.parser.TVPeriodParser;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVTimePeriodTest {

    /*
     * Tests for method {@link com.tunein.tvschedule.TVTimePeriod#isContiguous(com.tunein.tvschedule.TVTimePeriod)}.
     */
    @Test
    public void testT1a_isContiguous() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "7pm", "1hr"}));
        
        assertTrue("p1 should be contiguous to p2", p1.isContiguous(p2));
    }

    @Test
    public void testT1b_isContiguousLongerPeriod() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "2hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "8pm", "2hr"}));
        
        assertTrue("p1 should be contiguous to p2", p1.isContiguous(p2));
    }

    @Test
    public void testT1c_isNotContiguousByTime() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "8pm", "1hr"}));
        
        assertFalse("p1 should NOT be contiguous to p2", p1.isContiguous(p2));
    }

    /*
     * Test method for {@link com.tunein.tvschedule.TVTimePeriod#isContiguous(com.tunein.tvschedule.TVTimePeriod)}.
     */
    @Test
    public void testT1d_isNotContiguousByTVProgram() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Sunday", "7pm", "1hr"}));
        
        assertFalse("p1 should NOT be contiguous to p2", p1.isContiguous(p2));
    }

    
    
    
    /*
     * Tests for method {@link com.tunein.tvschedule.TVTimePeriod#hasConflict(com.tunein.tvschedule.TVTimePeriod)}.
     */
    @Test
    public void testT2a_HasConflict() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Sunday", "6pm", "1hr"}));
        
        assertTrue("p1 should be in conflict with p2", p1.hasConflict(p2) && p2.hasConflict(p1));
    }

    @Test
    public void testT2b_HasNoConflict() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "7pm", "1hr"}));
        
        assertFalse("p1 should NOT be in conflict with p2", p1.hasConflict(p2) && p2.hasConflict(p1));
    }

    @Test
    public void testT2c_HasConflictLongerPeriod() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "2hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "7pm", "1hr"}));
        
        assertTrue("p1 should be in conflict with p2", p1.hasConflict(p2) && p2.hasConflict(p1));
    }

    @Test
    public void testT2d_HasConflictDiffentTVProgram() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Sunday", "6pm", "1hr"}));
        
        assertTrue("p1 should be in conflict with p2", p1.hasConflict(p2) && p2.hasConflict(p1));
    }

    @Test
    public void testT2e_HasConflictLongerPeriodDifferentTVProgram() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "2hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Sunday", "7pm", "1hr"}));
        
        assertTrue("p1 should be in conflict with p2", p1.hasConflict(p2) && p2.hasConflict(p1));
    }


    
    
    /*
     * Tests for method {@link com.tunein.tvschedule.TVTimePeriod#isGroupableWeekday(com.tunein.tvschedule.TVTimePeriod)}.
     */
    @Test
    public void testT3a_IsGroupableWeekday() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"}));
        
        assertTrue("p1 should be groupable with p2", p1.isGroupable(p2) && p2.isGroupable(p1));
    }

    @Test
    public void testT3b_IsNotGroupableWeekday() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "7pm", "1hr"}));
        
        assertFalse("p1 should NOT be groupable with p2", p1.isGroupable(p2) && p2.isGroupable(p1));
    }

    @Test
    public void testT3c_IsNotGroupableWeekdayDifferentTVShow() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"NFL Game", "Tuesday", "6pm", "1hr"}));
        
        assertFalse("p1 should NOT be groupable with p2", p1.isGroupable(p2) && p2.isGroupable(p1));
    }

    @Test
    public void testT3d_IsNotGroupableWeekdayLongerPeriod() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "2hr"}));
        
        assertFalse("p1 should NOT be groupable with p2", p1.isGroupable(p2) && p2.isGroupable(p1));
    }


    
    /*
     * Tests for method {@link com.tunein.tvschedule.TVTimePeriod#group(com.tunein.tvschedule.TVTimePeriod)}.
     */
    @Test
    public void testT4a_Group() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"}));
        
        String expected = "Car Racing, Sunday/Tuesday, 6pm, 1hr";
        TVTimePeriod grouped = p1.group(p2);
        
        assertEquals("p1 should be grouped correctly with p2", expected, grouped.getStringRepresentation());
    }


    @Test
    public void testT4b_Group3Levels() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"}));
        TVTimePeriod p3 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"}));
        
        String expected = "Car Racing, Sunday/Tuesday/Thursday, 6pm, 1hr";
        TVTimePeriod grouped = p1.group(p2);
        grouped = grouped.group(p3);
        
        assertEquals("p1 should be grouped correctly with p2, then p3", expected, grouped.getStringRepresentation());
    }

    @Test
    public void testT4c_Group2Groups() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "6pm", "1hr"}));
        TVTimePeriod p3 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Thursday", "6pm", "1hr"}));
        TVTimePeriod p4 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "6pm", "1hr"}));
        
        String expected = "Car Racing, Sunday/Tuesday/Wednesday/Thursday, 6pm, 1hr";
        TVTimePeriod group1 = p1.group(p2);
        TVTimePeriod group2 = p3.group(p4);

        TVTimePeriod grouped = group1.group(group2);
        
        assertEquals("p1 should be grouped correctly with p2 and then p3 with p4 into only ", expected, grouped.getStringRepresentation());
    }


    @Test
    public void testT4a_GroupContiguous() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "7pm", "1hr"}));
        
        String expected = "Car Racing, Sunday, 6pm, 2hr";
        TVTimePeriod grouped = p1.group(p2);
        
        assertEquals("p1 should be grouped correctly with p2", expected, grouped.getStringRepresentation());
    }


    @Test
    public void testT4b_Group3LevelsContiguous() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "3pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "4pm", "1hr"}));
        TVTimePeriod p3 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Tuesday", "5pm", "1hr"}));
        
        String expected = "Car Racing, Tuesday, 3pm, 3hr";
        TVTimePeriod grouped = p1.group(p2);
        grouped = grouped.group(p3);
        
        assertEquals("p1 should be grouped correctly with p2, then p3", expected, grouped.getStringRepresentation());
    }

    @Test
    public void testT4c_Group2GroupsContiguous() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "1pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "2pm", "1hr"}));
        TVTimePeriod p3 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "3pm", "1hr"}));
        TVTimePeriod p4 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "4pm", "1hr"}));
        
        String expected = "Car Racing, Wednesday, 1pm, 4hr";
        TVTimePeriod group1 = p1.group(p2);
        TVTimePeriod group2 = p3.group(p4);

        TVTimePeriod grouped = group1.group(group2);
        
        assertEquals("p1 should be grouped correctly with p2 and then p3 with p4 into only ", expected, grouped.getStringRepresentation());
    }
    
    

    @Test
    public void testT5a_compareTo0() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "1pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "1pm", "1hr"}));
        
        assertEquals("compareTo() should be 0", 0, p1.compareTo(p2));
    }
    @Test
    public void testT5a_compareTo1() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "2pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "1pm", "1hr"}));
        
        assertEquals("compareTo() should be 1", 1, p1.compareTo(p2));
    }
    @Test
    public void testT5a_compareToMinus1() {
        TVTimePeriod p1 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "1pm", "1hr"}));
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Wednesday", "2pm", "1hr"}));
        
        assertEquals("compareTo() should be -1", -1, p1.compareTo(p2));
    }
}
