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
			
			Course course = courseList.get(i);
			String courseName = course.getCourseName().toLowerCase();
			String deptAndCode = (course.getDepartment()+" "+course.getCourseCode()+" "+course.getSection()).toLowerCase();
			
			boolean match = true;
			for(String term : terms) {
				if(!courseName.contains(term) && !deptAndCode.contains(term)) {
					match = false;
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
