package hs.core.tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import hs.core.Course;
import hs.core.MeetingTime;
import hs.core.Time;
import hs.core.TimeFrame;

/**
 * Tests for methods in Course and Meeting Time classes
 * @author Amy Cunningham
 *
 */
public class MeetingTimeCourseTests {
	@Test
	public void meetingTimeConflictingTest(){
		try {
			MeetingTime time1 = new MeetingTime(new TimeFrame
					(new Time(3, 0, 1), new Time(3, 50, 1)), "MWF");
			MeetingTime time2 = new MeetingTime(new TimeFrame
					(new Time(3, 30, 1), new Time(3, 50, 1)), "MWF");
			assertEquals(true, time1.isConflictingWith(time2));
		}
		catch(Exception e) {
			fail("isConflictingWith method in Meeting Time class does not correctly detect"
					+ "an overlap in two conflicting meeting times");
		}
	}
	
	@Test
	public void courseConflictingTest(){
		try {
			Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
			Course course2 = new Course("Course 2", "COMP", 'A', 342, 2);
			course1.addMeetingTime(new TimeFrame(new Time(12,0,Time.PM), new Time(12,50,Time.PM)), "MWF");
			course1.addMeetingTime(new TimeFrame(new Time(1,0,Time.PM), new Time(1,50,Time.PM)), "MWF");
			course2.addMeetingTime(new TimeFrame(new Time(12,0,Time.PM), new Time(12,50,Time.PM)), "MWF");
			course2.addMeetingTime(new TimeFrame(new Time(2,0,Time.PM), new Time(2,50,Time.PM)), "MWF");
			assertEquals(true, course1.isConflictingWith(course2));
		}
		catch(Exception e) {
			fail("isConflictingWith method in Course class does not correctly detect"
					+ "an overlap of meeting times in two conflicting courses");
		}
	}
	
	@Test
	public void differsOnlyBySectionTest(){
		try {
			Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
			Course course2 = new Course("Course 1", "COMP", 'B', 255, 2);
			assertEquals(true, course1.differsOnlyBySection(course2));
		}
		catch(Exception e) {
			fail("differsOnlyBySection method in Course class does not accurately detect when"
					+ "two courses differ only in section");
		}
	}
	
	/**
	 * Tests toString method for a course without a set meeting time or credit hours
	 */
	@Test
	public void courseToStringTestNoMT(){
		try {
			Course course1 = new Course("Course 1", "COMP", 'B', 255, 2);
			String course1String = "COMP 255 B | Course 1 | "
					+ "No set meeting time | Variable credit hours";
			assertEquals(course1String, course1.toString());
		}
		catch(Exception e) {
			fail("toString method for a course does not correctly create string version"
					+ "of a course. ");
		}
	}
	
	/**
	 * Tests toString method for a course with set meeting times
	 */
	@Test
	public void courseToStringTestWithMT(){
		try {
			Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
			course1.addMeetingTime(new TimeFrame(new Time(1,0,Time.PM), new Time(1,50,Time.PM)), "MWF");
			String course1String = "COMP 255 A | Course 1 "
					+ "| 1:00 PM - 1:50 PM MWF | 2 credit hour(s)";
			System.out.println(course1.toString());
			assertEquals(course1String, course1.toString());
		}
		catch(Exception e) {
			fail("toString method for a course does not correctly create string version"
					+ "of a course. ");
		}
	}
}
