package hs.pages.subPages;

import hs.core.Course;
import hs.simplefx.ViewableCourseList;

public class CourseSearchList extends ViewableCourseList {

	private CourseScheduleList scheduleList;
	
	public CourseSearchList() {
		super(500, 590);
	}

	public void setScheduleList(CourseScheduleList scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "addToSchedule", 430, 14, 32, 32, "+", ()->{
			scheduleList.addCourseToDisplay(course);
		});
	}

}
