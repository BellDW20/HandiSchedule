package hs.pages.subPages;

import hs.core.Course;
import hs.core.Schedule;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {
	
	Schedule currentSchedule;
	
	public CourseScheduleList(Schedule schedule) {
		super(500, 590);
		this.currentSchedule = schedule;
		
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
			currentSchedule.removeCourse(course);
		});
	}

}
