package hs.pages.subPages;

import hs.core.Course;
import hs.simplefx.ViewableCourseList;

public class CourseScheduleList extends ViewableCourseList {

	private CourseSearchList searchList;
	
	public CourseScheduleList() {
		super(500, 590);
	}

	public void setSearchList(CourseSearchList searchList) {
		this.searchList = searchList;
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
		});
	}

}
