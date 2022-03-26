package hs.search;

import java.util.ArrayList;
import hs.core.Course;

public abstract class CourseSearchFilter {
	
	/**
	 * An abstract method to implemented by various filters. Takes in
	 * the remaining courses in the search and removes elements from
	 * the list if they do not match the filter criteria.
	 * @param courseList List of courses still in the search results
	 */
	public abstract void narrowResults(ArrayList<Course> courseList);
	
}