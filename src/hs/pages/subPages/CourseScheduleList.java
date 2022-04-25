package hs.pages.subPages;

import java.util.ArrayList;

import hs.core.Course;
import hs.core.Schedule;
import hs.pages.CourseSearchPage;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {
	
	private CourseSearchPage courseSearchPage;
	
	public CourseScheduleList(ArrayList<Course> allCourses) {
		super(allCourses, 500, 590);
	}
	
	/**
	 * Updates the reference to the current schedule
	 * @param currentSchedule The new reference to the current schedule
	 */
	public void setCourseSearchPage(CourseSearchPage courseSearchPage) {
		this.courseSearchPage = courseSearchPage;
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		//When a course is added to the schedule, we also want to add
		//the visual component in the schedule course list from which we
		//can remove the added course
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
			Schedule currentSchedule = courseSearchPage.getCurrentSchedule();
			currentSchedule.removeCourse(course);
			courseSearchPage.asynchronouslySaveCurrentSchedule();
			courseSearchPage.getLabel("scheduleCredits").setText("Current Credits: " + currentSchedule.getCreditHours());
			courseSearchPage.updateListVisuals();
		});
	}

}
