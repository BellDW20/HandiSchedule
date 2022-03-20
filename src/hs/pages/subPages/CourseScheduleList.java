package hs.pages.subPages;

import hs.core.Course;
import hs.core.Schedule;
import hs.pages.CourseSearchPage;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {
	
	private Schedule currentSchedule;
	
	public CourseScheduleList() {
		super(500, 590);
	}
	
	public void setCurrentSchedule(Schedule currentSchedule) {
		this.currentSchedule = currentSchedule;
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
			currentSchedule.removeCourse(course);
			currentSchedule.saveSchedule(CourseSearchPage.getSavePath(currentSchedule.getTitle()));
		});
	}

}
