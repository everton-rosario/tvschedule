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
        TVTimePeriod p2 = TVPeriodParser.parsePeriod(Arrays.asList(new String[] {"Car Racing", "Sunday", "6pm", "1hr"}));
        
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
}
