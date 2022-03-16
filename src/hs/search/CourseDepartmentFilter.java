package hs.search;

import java.util.ArrayList;
import hs.core.Course;

public class CourseDepartmentFilter extends CourseSearchFilter {

	private String department;
	
	public CourseDepartmentFilter(String department) {
		this.department = department;
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		for(int i=0; i<courseList.size(); i++) {
			if(!courseList.get(i).getDepartment().equals(department)) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
