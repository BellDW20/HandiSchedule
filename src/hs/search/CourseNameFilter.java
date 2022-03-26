package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseNameFilter extends CourseSearchFilter {

	//All of the search terms that the courses filtered must match
	private String[] terms;
	
	public CourseNameFilter(String courseName) {
		this.terms = courseName.toLowerCase().split("\\s+");
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		//For every course left in the search...
		for(int i=0; i<courseList.size(); i++) {
			
			Course course = courseList.get(i);
			String courseName = course.getCourseName().toLowerCase();
			String deptAndCode = (course.getDepartment()+" "+course.getCourseCode()+" "+course.getSection()).toLowerCase();
			
			//If the course name or department/code/section combo contains every search term, keep the course
			boolean match = true;
			for(String term : terms) {
				if(!courseName.contains(term) && !deptAndCode.contains(term)) {
					match = false;
					break;
				}
			}
			
			//If the course did not meet the filter criteria, remove it
			if(!match) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
