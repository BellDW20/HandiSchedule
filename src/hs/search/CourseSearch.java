package hs.search;

import java.util.ArrayList;

import hs.core.Course;
import hs.core.CourseDatabase;

/**
 * Class contains an array list of searchResults that can be updated 
 * based on filters or searched term.
 * @author CONRADLJ19
 *
 */
public class CourseSearch {
	private CourseDatabase db;
	private ArrayList<Course> searchResults;
	private ArrayList<CourseSearchFilter> filters;
	
	/**
	 * 
	 * @param db - course database
	 * @param searchTerm - matches with course name
	 */
	public CourseSearch(CourseDatabase db) {
		this.db = db;
		filters = new ArrayList<>();
		searchResults = new ArrayList<>();
	}
	
	public void addSearchFilter(CourseSearchFilter filter) {
		filters.add(filter);
	}
	
	/**
	 * removes all filters
	 */
	public void clearFilters() {
		filters.clear();
	}
	
	public ArrayList<Course> getSearchResults() {
		return searchResults;
	}
	
	/**
	 * Updates search results by applying all filters to list of all courses,
	 * then filtering further based on search term.
	 */
	public void updateSearch() {
		searchResults = db.getCopyOfAllCourses();
		for (int i = 0; i < filters.size(); i++) {
			filters.get(i).narrowResults(searchResults);
		}
	}
}
