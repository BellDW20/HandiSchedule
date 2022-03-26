package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCodeFilter extends CourseSearchFilter {
	
	//Whether or not the one, two, three, or four
	//hundred level course codes should be included in the search
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
		//If we've made any selection, filter. Otherwise, keep everything
		if (one || two || three || four) {
			//For every course left in the search...
			for(int i=0; i<courseList.size(); i++) {
				boolean accepted = false;
				Course course = courseList.get(i);
				int code = course.getCourseCode();
				
				//If the course falls within any filtered
				//course code level range, keep it
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
				
				//If the course didn't match the filter, remove it
				if (!accepted) {
					courseList.remove(i);
					i--;
				}
			}
		}
	}

}
