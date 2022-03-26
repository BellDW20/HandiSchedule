package hs.pages.subPages;

import hs.core.Course;
import hs.core.Schedule;
import hs.pages.CourseSearchPage;
import hs.simplefx.PageManager;
import hs.simplefx.ViewableCourseList;

public class CourseSearchList extends ViewableCourseList {

	private CourseScheduleList scheduleList; //Reference to the visual schedule course list
	private Schedule currentSchedule; //Reference to the current schedule
	
	public CourseSearchList() {
		super(500, 590);
	}
	
	/**
	 * Updates the reference to the current schedule
	 * @param currentSchedule The new reference to the current schedule
	 */
	public void setCurrentSchedule(Schedule currentSchedule) {
		this.currentSchedule = currentSchedule;
	}

	/**
	 * Updates the reference to the current schedule course list
	 * @param scheduleList The new reference to the schedule course list
	 */
	public void setScheduleList(CourseScheduleList scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		//An empty search will notify the user of no results
		drawText(200, 30, "No Courses Found");
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		//When a course is added to the search list, we also want to add
		//the visual component in the search list from which we
		//can added the given course to the schedule
		addButtonToCourse(course, "addToSchedule", 430, 14, 32, 32, "+", ()->{
			scheduleList.addCourseToDisplay(course);
			currentSchedule.addCourse(course);
			currentSchedule.saveSchedule(CourseSearchPage.getSavePath(currentSchedule.getTitle()));
		});
	}

}
