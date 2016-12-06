import org.junit.Test;
import static org.junit.Assert.*;

import org.joda.time.*;

import utilities.JodaTimeUtil;
import utilities.PairDateTime;

public class JodaTimeUtilJunitTest {
    
    public JodaTimeUtilJunitTest() {
    }
    
    @Test
    public void aSimpleTest() {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : JodaTimeUtilJunitTest.aSimpleTest()n");
        if (dBug) System.out.println("");
        
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        //DateTime testDateTime = new DateTime().withDayOfWeek(DateTimeConstants.FRIDAY);
        DateTime testDateTime = new DateTime("2016-11-29");
        DateTime expectedDateTimeFirst = new DateTime("2016-11-25T21:00:00Z");
        DateTime expectedDateTimeFinal = new DateTime("2016-12-2T21:00:00Z");

        PairDateTime testPair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(testDateTime);
        
        assertTrue(testPair.getFirst().isEqual(expectedDateTimeFirst));
        assertTrue(testPair.getFinal().isEqual(expectedDateTimeFinal));
        
        if (dBug) System.out.println ("");
        if (dBug) System.out.println ("On = " + testDateTime.toString());
        if (dBug) System.out.println ("First = " + testPair.getFirst().toString());
        if (dBug) System.out.println ("Final = " + testPair.getFinal().toString("MM-dd-yyyy HH:mm:ss:mmm"));
        if (dBug) System.out.println ("");
    }
    
    @Test
    public void testLeadingEdge() {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : JodaTimeUtilJunitTest.testLeadingEdge()n");
        if (dBug) System.out.println("");
        
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        //DateTime testDateTime = new DateTime().withDayOfWeek(DateTimeConstants.FRIDAY);
        DateTime testDateTime = new DateTime("2016-11-18T21:00:00.001Z");
        DateTime expectedDateTimeFirst = new DateTime("2016-11-18T21:00:00Z");
        DateTime expectedDateTimeFinal = new DateTime("2016-11-25T21:00:00Z");

        PairDateTime testPair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(testDateTime);
        
        assertTrue(testPair.getFirst().isEqual(expectedDateTimeFirst));
        assertTrue(testPair.getFinal().isEqual(expectedDateTimeFinal));
        
        if (dBug) System.out.println ("");
        if (dBug) System.out.println ("** On = " + testDateTime.toString());
        if (dBug) System.out.println ("First = " + testPair.getFirst().toString());
        if (dBug) System.out.println ("Final = " + testPair.getFinal().toString());
        if (dBug) System.out.println ("");
    }
    
    @Test
    public void testTrailingEdge() {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : JodaTimeUtilJunitTest.testTrailingEdge()n");
        if (dBug) System.out.println("");
        
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        //DateTime testDateTime = new DateTime().withDayOfWeek(DateTimeConstants.FRIDAY);
        DateTime testDateTime = new DateTime("2016-11-18T21:00:00Z");
        DateTime expectedDateTimeFirst = new DateTime("2016-11-11T21:00:00Z");
        DateTime expectedDateTimeFinal = new DateTime("2016-11-18T21:00:00Z");

        PairDateTime testPair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(testDateTime);
        
        assertTrue(testPair.getFirst().isEqual(expectedDateTimeFirst));
        assertTrue(testPair.getFinal().isEqual(expectedDateTimeFinal));
        
        if (dBug) System.out.println ("");
        if (dBug) System.out.println ("On = " + testDateTime.toString());
        if (dBug) System.out.println ("First = " + testPair.getFirst().toString());
        if (dBug) System.out.println ("Final = " + testPair.getFinal().toString("MM-dd-yyyy HH:mm:ss:mmm"));
        if (dBug) System.out.println ("");
    }

    
    @Test
    public void testSaturday() {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : JodaTimeUtilJunitTest.testSaturday()n");
        if (dBug) System.out.println("");
        
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        //DateTime testDateTime = new DateTime().withDayOfWeek(DateTimeConstants.FRIDAY);
        DateTime testDateTime = new DateTime("2016-11-26");
        DateTime expectedDateTimeFirst = new DateTime("2016-11-25T21:00:00Z");
        DateTime expectedDateTimeFinal = new DateTime("2016-12-2T21:00:00Z");

        PairDateTime testPair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(testDateTime);
        
        assertTrue(testPair.getFirst().isEqual(expectedDateTimeFirst));
        assertTrue(testPair.getFinal().isEqual(expectedDateTimeFinal));
        
        if (dBug) System.out.println ("");
        if (dBug) System.out.println ("On = " + testDateTime.toString());
        if (dBug) System.out.println ("First = " + testPair.getFirst().toString());
        if (dBug) System.out.println ("Final = " + testPair.getFinal().toString("MM-dd-yyyy HH:mm:ss:mmm"));
        if (dBug) System.out.println ("");
    }
    
    @Test
    public void testThursday() {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : JodaTimeUtilJunitTest.testThursday()n");
        if (dBug) System.out.println("");
        
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        //DateTime testDateTime = new DateTime().withDayOfWeek(DateTimeConstants.FRIDAY);
        DateTime testDateTime = new DateTime("2016-11-24");
        DateTime expectedDateTimeFirst = new DateTime("2016-11-18T21:00:00Z");
        DateTime expectedDateTimeFinal = new DateTime("2016-11-25T21:00:00Z");

        PairDateTime testPair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(testDateTime);
        
        assertTrue(testPair.getFirst().isEqual(expectedDateTimeFirst));
        assertTrue(testPair.getFinal().isEqual(expectedDateTimeFinal));
        
        if (dBug) System.out.println ("");
        if (dBug) System.out.println ("On = " + testDateTime.toString());
        if (dBug) System.out.println ("First = " + testPair.getFirst().toString());
        if (dBug) System.out.println ("Final = " + testPair.getFinal().toString("MM-dd-yyyy HH:mm:ss:mmm"));
        if (dBug) System.out.println ("");
    }
}

