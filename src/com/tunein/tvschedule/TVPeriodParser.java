/**
 * 
 */
package com.tunein.tvschedule;

/**
 * @author Everton Rosario (erosario@gmail.com)
 */
public class TVPeriodParser {

    public static TVTimePeriod parsePeriod(String... lines) {
        if (lines.length != 4) {
            throw new IllegalArgumentException("At least 4 lines must be informed for TVPeriodParser.parsePeriod(String... lines).");
        }

        String shortName = lines[0];
        String weekDay = lines[1];
        String startTime = lines[2];
        String duration = lines[3];

        return new TVTimePeriod(shortName, weekDay, startTime, duration);
    }
    
    public static void main(String[] args) {
        TVPeriodParser.parsePeriod(new String[] {"Car Racing", "Tuesday", "6pm", "1h"});
    }
}
