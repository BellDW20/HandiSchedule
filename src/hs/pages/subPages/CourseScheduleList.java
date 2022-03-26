package hs.pages.subPages;

import hs.core.Course;
import hs.core.Schedule;
import hs.pages.CourseSearchPage;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {
	
	private Schedule currentSchedule; //Reference to the current schedule
	
	public CourseScheduleList() {
		super(500, 590);
	}
	
	/**
	 * Updates the reference to the current schedule
	 * @param currentSchedule The new reference to the current schedule
	 */
	public void setCurrentSchedule(Schedule currentSchedule) {
		this.currentSchedule = currentSchedule;
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		//When a course is added to the schedule, we also want to add
		//the visual component in the schedule course list from which we
		//can remove the added course
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
			currentSchedule.removeCourse(course);
			currentSchedule.saveSchedule(CourseSearchPage.getSavePath(currentSchedule.getTitle()));
		});
	}

}
