package hs.core.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import hs.core.Course;
import hs.core.CourseDatabase;
import hs.core.Schedule;
import hs.core.Time;
import hs.core.TimeFrame;

/**
 * Tests for methods in the Schedule class, such as addCourse, removeCourse,
 * ...
 * @author Amy Cunningham, Douglas Bell
 *
 */
public class ScheduleTests {
	private static CourseDatabase createDummyDB() {
		CourseDatabase db = new CourseDatabase();
		
		Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
		course1.addMeetingTime(new TimeFrame(new Time(11,0,Time.AM), new Time(11,50,Time.AM)), "MWF");
		
		Course course2 = new Course("Course 2", "COMP", 'B', 102, 4);
		course2.addMeetingTime(new TimeFrame(new Time(1,0,Time.PM), new Time(1,50,Time.PM)), "MWRF");
		
		Course course3 = new Course("Course 3", "MATH", 'E', 344, 2);
		course3.addMeetingTime(new TimeFrame(new Time(1,0,Time.PM), new Time(1,50,Time.PM)), "MWRF");
		
		Course course4 = new Course("Course 3", "MATH", 'D', 344, 2);
		course4.addMeetingTime(new TimeFrame(new Time(12,0,Time.PM), new Time(12,50,Time.PM)), "MWF");
		
		
		db.registerCourse(course1);
		db.registerCourse(course2);
		db.registerCourse(course3);
		db.registerCourse(course4);
		return db;
	}
	@Test
	public void addCourseTest() {
		try {
			Course course1 = new Course("Course 1", "MATH", 'E', 344, 2);
			Schedule s = new Schedule("s");
			s.addCourse(course1);
			assertEquals(1, s.getCourses().size());
		}
		catch (Exception e) {
			fail("Add course method does not successfully add course to schedule");
		}
	}
	
	@Test
	public void deleteCourseTest() {
		try {
			Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
			Course course2 = new Course("Course 2", "COMP", 'B', 102, 4);
			Schedule s2 = new Schedule("s2");
			s2.addCourse(course1);
			s2.addCourse(course2);
			s2.removeCourse(course2);
			assertEquals(1, s2.getCourses().size());
		}
		
		catch (Exception e) {
			fail("Remove course method did not successfully remove course from schedule");
		}
	}
	
	@Test
	public void isConflictingTest() {
		try {
			Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
			Course course2 = new Course("Course 2", "COMP", 'B', 102, 4);
			course1.addMeetingTime(new TimeFrame
					(new Time(3, 0, 1), new Time(3, 50, 1)), "M");
			course2.addMeetingTime(new TimeFrame
					(new Time(3, 0, 1), new Time(3, 50, 1)), "M");
			Schedule s3 = new Schedule("s3");
			s3.addCourse(course1);
			s3.addCourse(course2);
			assertEquals(true, s3.isConflicting());
		}
		catch (Exception e) {
			fail("isConflicting does not correctly detect whether two courses in a schedule"
					+ "are conflicting by meeting time");
		}
	}
	
	@Test
	public void resolveScheduleTest() {
		try {
			Schedule s4 = new Schedule("s4");
			CourseDatabase db = createDummyDB();
			ArrayList<Course> courses = db.getCopyOfAllCourses();
			for (int i = 0; i < 3; i++) {
				s4.addCourse(courses.get(i));
			}
			assertEquals(true, s4.resolveSchedule(db));

		}
		catch (Exception e) {
			fail("The resolve schedule method did not successfully resolve the conflicted"
					+ "classes in the schedule.");
		}
	}
}
