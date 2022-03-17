package hs.simplefx;

import hs.core.Course;

public class ViewableCourse extends Page {
	
	public ViewableCourse(Course course, int w, int h, int pad) {
		super();
		drawRect(0, 0, w, h);
		drawText(pad, pad, course.getDepartment() + " " + course.getCourseCode() + " "
				+ course.getSection() + ": " 
				+ course.getCourseName() + "\n");
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {}

}
