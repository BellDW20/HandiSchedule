package hs.pages.subPages;

import java.util.ArrayList;

import hs.core.Course;
import hs.core.Schedule;
import hs.pages.CourseSearchPage;
import hs.simplefx.ViewableCourseList;
import javafx.scene.control.Button;

public class CourseScheduleList extends ViewableCourseList {
	
	private CourseSearchPage courseSearchPage;
	private Button resolveButton;
	
	public CourseScheduleList(ArrayList<Course> allCourses) {
		super(allCourses, 500, 590);
		resolveButton = super.addButton("resolveButton", getW()/2-100, ViewableCourseList.PAD, 200, 40, "Resolve Schedule", ()->{
			courseSearchPage.resolveCurrentSchedule(null);
		});
		resolveButton.setVisible(false);
	}
	
	/**
	 * Updates the reference to the current schedule
	 * @param currentSchedule The new reference to the current schedule
	 */
	public void setCourseSearchPage(CourseSearchPage courseSearchPage) {
		this.courseSearchPage = courseSearchPage;
	}
	
	@Override
	public void addCourseToDisplay(Course course) {
		super.addCourseToDisplay(course);
		resolveButton.setLayoutY(super.getListHeight()+ViewableCourseList.PAD);
	}
	
	@Override
	public void updateCourseVisuals(Schedule schedule, ArrayList<Course> results) {
		super.updateCourseVisuals(schedule, results);
		resolveButton.setVisible(schedule.isConflicting());
	}
	
	@Override
	protected void onCourseAdd(Course course) {
		//When a course is added to the schedule, we also want to add
		//the visual component in the schedule course list from which we
		//can remove the added course
		addButtonToCourse(course, "removeFromSchedule", 430, 14, 32, 32, "-", ()->{
			removeCourseToDisplay(course);
			Schedule currentSchedule = courseSearchPage.getCurrentSchedule();
			currentSchedule.removeCourse(course);
			courseSearchPage.asynchronouslySaveCurrentSchedule();
			courseSearchPage.getLabel("scheduleCredits").setText("Current Credits: " + currentSchedule.getCreditHours());
			courseSearchPage.updateListVisuals();
			resolveButton.setLayoutY(super.getListHeight()+ViewableCourseList.PAD);
		});
	}

}
