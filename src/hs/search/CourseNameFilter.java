package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseNameFilter extends CourseSearchFilter {

	private String[] terms;
	
	public CourseNameFilter(String courseName) {
		this.terms = courseName.toLowerCase().split("\\s+");
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		for(int i=0; i<courseList.size(); i++) {
			
			String courseName = courseList.get(i).getCourseName().toLowerCase();
			
			boolean match = false;
			for(String term : terms) {
				if(courseName.contains(term)) {
					match = true;
					break;
				}
			}
			
			if(!match) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
