package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCreditHourFilter extends CourseSearchFilter{
	private int creditHour;
	
	public CourseCreditHourFilter(int creditHour) {
		this.creditHour = creditHour;
	}
	
	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		for (int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getCreditHours() != creditHour) {
				courseList.remove(i);
				i--;
			}
		}
	}

}
