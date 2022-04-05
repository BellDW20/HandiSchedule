package hs.simplefx;

import hs.core.Course;

/**
 * A subpage component to be added to a page which visualizes
 * all relevant information regarding a particular course
 * @author Levi Conrad
 */

public class ViewableCourse extends Page {
	
	private String uniqueString;
	
	/**
	 * Creates a viewable course from a given course
	 * @param course Course to make into a visual
	 * @param w Width of the visual
	 * @param h Height of the visual
	 * @param pad Padding to be used in the visual
	 */
	public ViewableCourse(Course course, int w, int h, int pad) {
		super();
		this.uniqueString = course.getUniqueString();
		//Make a background rectangle
		drawRect(0, 0, w, h);
		
		//Draw all relevant course information
		drawText(pad, pad, course.getDepartment() + " " + course.getCourseCode() + " "
				+ course.getSection() + ": " 
				+ course.getCourseName() + "\n");
		
		//Draw all of the meeting times this course has as text
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
		
		//Draw the number of credits the course is
		drawText(pad, pad + 32, course.getCreditHours() + " credits");
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {}

	@Override
	public String toString() {
		return uniqueString;
	}
	
}
