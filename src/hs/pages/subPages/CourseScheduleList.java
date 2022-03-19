package hs.pages.subPages;

import hs.core.Course;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {
	
	public CourseScheduleList() {
		super(500, 590);
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
		});
	}

}
