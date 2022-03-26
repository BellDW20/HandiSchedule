package hs.search;

import java.util.ArrayList;
import hs.core.Course;

public class CourseDepartmentFilter extends CourseSearchFilter {

	private String department; //Department to filter by
	
	public CourseDepartmentFilter(String department) {
		this.department = department;
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		//For every course left in the search...
		for(int i=0; i<courseList.size(); i++) {
			//If the department is not of the correct type, remove it
			if(!courseList.get(i).getDepartment().equals(department)) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
