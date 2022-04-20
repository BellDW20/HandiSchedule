package hs.core.tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import hs.core.Course;
import hs.core.CourseDatabase;
import hs.core.Time;
import hs.core.TimeFrame;
import hs.search.*;

public class SearchTests {
	
	private static CourseDatabase createDummyDB() {
		CourseDatabase db = new CourseDatabase();
		
		Course course1 = new Course("Course 1", "COMP", 'A', 255, 2);
		course1.addMeetingTime(new TimeFrame(new Time(12,0,Time.PM), new Time(12,50,Time.PM)), "MWF");
		
		Course course2 = new Course("Course 2", "COMP", 'B', 102, 4);
		course2.addMeetingTime(new TimeFrame(new Time(8,0,Time.AM), new Time(9,30,Time.AM)), "TR");
		
		Course course3 = new Course("Course 3", "MATH", 'E', 344, 2);
		course3.addMeetingTime(new TimeFrame(new Time(1,0,Time.PM), new Time(1,50,Time.PM)), "MWRF");
		
		Course course4 = new Course("Course 4", "ART", 'C', 101, 3);
		course4.addMeetingTime(new TimeFrame(new Time(12,0,Time.PM), new Time(12,50,Time.PM)), "MWF");
		
		
		db.registerCourse(course1);
		db.registerCourse(course2);
		db.registerCourse(course3);
		db.registerCourse(course4);
		return db;
	}
	
	@Test
	public void creditHourSearchTest() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseCreditHourFilter(false, false, true, false, false, false));
			search.updateSearch();
			assertEquals(2, search.getSearchResults().size());
		} catch(Exception e) {
			fail("Credit hour filter does not find multiple courses with the same number of credit hours.");
		}
	}
	
	@Test
	public void courseCodeSearchTest() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseCodeFilter(true,false,true,false));
			search.updateSearch();
			assertEquals(3, search.getSearchResults().size());
		} catch(Exception e) {
			fail("Course code filter does not find multiple courses with the indicated number of credit hours.");
		}
	}
	
	@Test
	public void courseDepartmentSearchTest() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseDepartmentFilter("MATH"));
			search.updateSearch();
			assertEquals(1, search.getSearchResults().size());
		} catch(Exception e) {
			fail("Course department filter does not find courses with the indicated department.");
		}
	}
	
	@Test
	public void courseNameSearchTest() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseNameFilter("COMP"));
			search.updateSearch();
			assertEquals(2, search.getSearchResults().size());
			
			search.clearFilters();
			search.addSearchFilter(new CourseNameFilter("Course"));
			search.updateSearch();
			assertEquals(4, search.getSearchResults().size());
			
			search.clearFilters();
			search.addSearchFilter(new CourseNameFilter("COMP 102"));
			search.updateSearch();
			assertEquals(1, search.getSearchResults().size());
		} catch(Exception e) {
			fail("CourseSearch filter does not find multiple courses with the indicated number of credit hours.");
		}
	}
	
	@Test
	public void courseTimeFrameSearchTest() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseTimeFrameFilter(new TimeFrame(
				new Time(12,0,Time.PM),
				new Time(1,0,Time.PM)
			)));
			search.updateSearch();
			assertEquals(2, search.getSearchResults().size());
		} catch(Exception e) {
			fail("Course department filter does not find courses with the indicated department.");
		}
	}
	
	@Test
	public void multiFilterSearch() {
		try {
			CourseSearch search = new CourseSearch(createDummyDB());
			search.addSearchFilter(new CourseNameFilter("COMP"));
			search.addSearchFilter(new CourseCreditHourFilter(false, false, false, false, true, false));
			search.updateSearch();
			assertEquals(1, search.getSearchResults().size());
		} catch(Exception e) {
			fail("Course department filter does not find courses with the indicated department.");
		}
	}
	
}
