package hs.core.tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import hs.core.Time;
import hs.core.TimeFrame;

/**
 * Tests methods in Time and Time Frame classes
 * @author Amy Cunningham
 *
 */
public class TimeTimeFrameTests {
	@Test
	public void fallsWithinTest() {
		try {
			TimeFrame tf1 = new TimeFrame(new Time(1, 30, 1), new Time (2, 50, 1));
			TimeFrame tf2 = new TimeFrame(new Time(1, 45, 1), new Time (2, 30, 1));
			assertEquals(true, tf2.fallsWithin(tf1));
		}
		catch (Exception e) {
			fail("fallsWithin test does not accurately detect if one time frame is within"
					+ "another");
		}
	}
	
	@Test
	public void isAfterTest() {
		try {
			Time t1 = new Time(1, 0, 1);
			Time t2 = new Time(12, 0, 1);
			assertEquals(true, t1.isAfter(t2));
		}
		catch (Exception e) {
			fail("isAfter method in Time class does not accurately compare times in order to "
					+ "determine if one time is after another");
		}
	}
	
	@Test
	public void isBeforeTest() {
		try {
			Time t1 = new Time(1, 0, 1);
			Time t2 = new Time(12, 0, 1);
			assertEquals(false, t1.isBefore(t2));
		}
		catch (Exception e) {
			fail("isAfter method in Time class does not accurately compare times in order to "
					+ "determine if one time is before another");
		}
	}
	
	@Test
	public void isOverlappingWithTest() {
		try {
			TimeFrame tf1 = new TimeFrame(new Time(1, 30, 1), new Time (1, 50, 1));
			TimeFrame tf2 = new TimeFrame(new Time(1, 45, 1), new Time (2, 50, 1));
			assertEquals(true, tf2.isOverlappingWith(tf1));
		}
		catch (Exception e) {
			fail("fallsWithin test does not accurately detect if one time frame overlaps with "
					+ "another");
		}
	}
	
}
