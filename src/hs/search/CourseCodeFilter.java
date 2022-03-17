package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCodeFilter extends CourseSearchFilter {

	private boolean one;
	private boolean two;
	private boolean three;
	private boolean four;
	
	public CourseCodeFilter(boolean one, boolean two, boolean three, boolean four) {
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		if (one || two || three || four) {
			for(int i=0; i<courseList.size(); i++) {
				boolean accepted = false;
				Course course = courseList.get(i);
				int code = course.getCourseCode();

				if(one) {
					if (code < 200) {
						accepted = true;
					}
				}
				if (two) {
					if (code < 300 && code >= 200) {
						accepted = true;
					}
				}
				if (three) {
					if (code < 400 && code >= 300) {
						accepted = true;
					}
				}
				if (four) {
					if (code < 500 && code >= 400) {
						accepted = true;
					}
				}
				
				if (!accepted) {
					courseList.remove(i);
					i--;
				}
			}
		}
	}

}
