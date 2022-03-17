package hs.simplefx;

import hs.core.Course;

public class ViewableCourse extends Page {
	
	public ViewableCourse(Course course, int w, int h, int pad) {
		super();
		drawRect(0, 0, w, h);
		drawText(pad, pad, course.getDepartment() + " " + course.getCourseCode() + " "
				+ course.getSection() + ": " 
				+ course.getCourseName() + "\n");
		if (!course.getMeetingTimes().isEmpty()) {
			String meetings = "";
			for (int i = 0; i < course.getMeetingTimes().size(); i++) {
				meetings += course.getMeetingTimes().get(i).toString();
				if (i != course.getMeetingTimes().size() - 1) {
					meetings += " and ";
				}
			}
			drawText(pad, pad + 16, meetings);
		}
		else {
			drawText(pad, pad + 16, "Non-standard Class Meetings");
		}
		
		drawText(pad, pad + 32, course.getCreditHours() + " credits");
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {}

}
