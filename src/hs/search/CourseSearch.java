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
	
	private CourseDatabase db; //Reference to the database to search on
	private ArrayList<Course> searchResults; //The results of the current search
	private ArrayList<CourseSearchFilter> filters; //Filters to filter search by
	
	/**
	 * Creates an empty search which operates over the given database
	 * @param db Course database to use
	 */
	public CourseSearch(CourseDatabase db) {
		this.db = db;
		filters = new ArrayList<>();
		searchResults = new ArrayList<>();
	}
	
	/**
	 * Adds a filter to the search
	 * @param filter Filter to add to the search
	 */
	public void addSearchFilter(CourseSearchFilter filter) {
		filters.add(filter);
	}
	
	/**
	 * removes all filters fom the search
	 */
	public void clearFilters() {
		filters.clear();
	}
	
	/**
	 * Returns the current search results
	 * @return The current search results
	 */
	public ArrayList<Course> getSearchResults() {
		return searchResults;
	}
	
	/**
	 * Updates search results by applying all filters to list of all courses.
	 */
	public void updateSearch() {
		//get a clean copy of all the courses
		searchResults = db.getCopyOfAllCourses();
		
		//apply every filter added to narrow the results
		for (int i = 0; i < filters.size(); i++) {
			filters.get(i).narrowResults(searchResults);
		}
	}
}
