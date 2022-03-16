package hs.search;

import java.util.ArrayList;
import hs.core.Course;

public abstract class CourseSearchFilter {

	public abstract void narrowResults(ArrayList<Course> courseList);
	
}