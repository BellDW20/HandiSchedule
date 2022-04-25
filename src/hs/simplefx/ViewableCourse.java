package hs.simplefx;

import hs.core.Course;
import hs.core.Schedule;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A subpage component to be added to a page which visualizes
 * all relevant information regarding a particular course
 * @author Levi Conrad
 */

public class ViewableCourse extends Page {
	
	private static Image warningIcon = new Image("file:./warning.png");
	
	private Course course;
	private ImageView conflictingWarning;
	private Rectangle duplicateDarken;
	
	/**
	 * Creates a viewable course from a given course
	 * @param course Course to make into a visual
	 * @param w Width of the visual
	 * @param h Height of the visual
	 * @param pad Padding to be used in the visual
	 */
	public ViewableCourse(Course course, int w, int h, int pad) {
		super();
		this.course = course;
		//Make a background rectangle
		drawRect(0, 0, w, h);
		
		//Draw all relevant course information
		addLabel("courseInfo", pad, pad, course.getDepartment() + " " + course.getCourseCode() + " "
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
			addLabel("meetingInfo", pad, pad + 16, meetings);
		}
		else {
			addLabel("meetingInfo", pad, pad + 16, "Non-standard Class Meetings");
		}
		
		//Draw the number of credits the course is
		addLabel("creditInfo", pad, pad + 32, course.getCreditHours() + " credits");
		
		conflictingWarning = new ImageView(warningIcon);
		conflictingWarning.setX(w-2*(pad+40));
		conflictingWarning.setY(h/2-16);
		conflictingWarning.setVisible(false); //Warning off by default
		getPane().getChildren().add(conflictingWarning);
		
		duplicateDarken = drawRect(0,0,w,h);
		duplicateDarken.setFill(new Color(0,0,0,0.5f));
		duplicateDarken.setVisible(false); //Duplicate course off by default
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {}

	@Override
	public String toString() {
		return course.getUniqueString();
	}
	
	public void setRectangle(boolean b) {
		duplicateDarken.setVisible(b);
	}
	
	public void setWarning(boolean b) {
		conflictingWarning.setVisible(b);
	}
	
	public Course getCourse() {
		return course;
	}
	
}
