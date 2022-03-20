package hs.pages.subPages;

import hs.core.Course;
import hs.core.Schedule;
import hs.simplefx.PageManager;
import hs.simplefx.ViewableCourseList;

public class CourseSearchList extends ViewableCourseList {

	private CourseScheduleList scheduleList;
	private Schedule currentSchedule;
	
	public CourseSearchList(Schedule schedule) {
		super(500, 590);
		this.currentSchedule = schedule;
	}

	public void setScheduleList(CourseScheduleList scheduleList) {
		this.scheduleList = scheduleList;
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		drawText(200, 30, "No Courses Found");
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		addButtonToCourse(course, "addToSchedule", 430, 14, 32, 32, "+", ()->{
			scheduleList.addCourseToDisplay(course);
			currentSchedule.addCourse(course);
		});
	}

}
