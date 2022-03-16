package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCodeFilter extends CourseSearchFilter {

	private int courseCode;
	
	public CourseCodeFilter(int courseCode) {
		this.courseCode = courseCode;
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		for(int i=0; i<courseList.size(); i++) {
			Course course = courseList.get(i);
			if(course.getCourseCode() != courseCode) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
