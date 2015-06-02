package com.resourcelibrary;

import android.test.InstrumentationTestCase;

import com.resourcelibrary.model.logic.TimeUnit;

/**
 * Created by User on 5/26/2015.
 */
public class TestTimeUnit extends InstrumentationTestCase {

    public void testNow() {
        assertEquals("JUST NOW", TimeUnit.change(0));
    }

    public void testOneYear() {
        String example = "1 year ago";
        oneTest(example, TimeUnit.SECOND_OF_YEAR);
    }

    public void testTwoYears() {
        String example = "2 years ago";
        twoTest(example, TimeUnit.SECOND_OF_YEAR);
    }

    public void testOneMonth() {
        String example = "1 month ago";
        oneTest(example, TimeUnit.SECOND_OF_MONTH);
    }

    public void testTwoMonths() {
        String example = "2 months ago";
        twoTest(example, TimeUnit.SECOND_OF_MONTH);
    }

    public void testOneDay() {
        String example = "1 day ago";
        oneTest(example, TimeUnit.SECOND_OF_DAY);
    }

    public void testTwoDays() {
        String example = "2 days ago";
        twoTest(example, TimeUnit.SECOND_OF_DAY);
    }

    public void testOneHour() {
        String example = "1 hour ago";
        oneTest(example, TimeUnit.SECOND_OF_HOUR);
    }

    public void testTwoHours() {
        String example = "2 hours ago";
        twoTest(example, TimeUnit.SECOND_OF_HOUR);
    }

    public void testOneMin() {
        String example = "1 min ago";
        oneTest(example, TimeUnit.SECOND_OF_MIN);
    }

    public void testTwoMins() {
        String example = "2 mins ago";
        twoTest(example, TimeUnit.SECOND_OF_MIN);
    }

    public void testSecond() {
        assertEquals("1 second ago", TimeUnit.change(TimeUnit.SECOND));
        assertEquals("JUST NOW", TimeUnit.change(TimeUnit.SECOND - 1));
        assertEquals("2 seconds ago", TimeUnit.change(TimeUnit.SECOND + 1));
    }

    public void testUsability() {
        for (long i = 0; i < TimeUnit.SECOND_OF_YEAR * 10; i++) ;
    }

    private void oneTest(String example, long time) {
        assertEquals(example, TimeUnit.change(time));
        assertEquals(example, TimeUnit.change(time * 2 - 1));
        assertEquals(example, TimeUnit.change(time + 1));
    }

    private void twoTest(String example, long time) {
        assertEquals(example, TimeUnit.change(time * 2));
        assertEquals(example, TimeUnit.change(time * 3 - 1));
        assertEquals(example, TimeUnit.change(time * 2 + 1));
    }

}
