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
	private String searchTerm; //Going by class name (for now)
	private CourseDatabase db;
	private ArrayList<Course> searchResults;
	private ArrayList<CourseSearchFilter> filters;
	
	/**
	 * 
	 * @param db - course database
	 * @param searchTerm - matches with course name
	 */
	public CourseSearch(CourseDatabase db, String searchTerm) {
		this.searchTerm = searchTerm;
		this.db = CourseDatabase.loadFromFile("CourseDB_CSV.csv");
		filters = new ArrayList<>();
		searchResults = new ArrayList<>();
	}
	
	public void setSearchTerm(String term) {
		searchTerm = term;
	}
	
	public void addSearchFilter(CourseSearchFilter filter) {
		filters.add(filter);
	}
	
	/**
	 * removes filter only if it is already in filter list
	 * @param filter
	 */
	public void removeSearchFilter(CourseSearchFilter filter) {
		if (filters.contains(filter)) {
			filters.remove(filter);
		}
		else {
			return;
		}
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
		
		for (int i = 0; i < searchResults.size(); i++) {
			if (!(searchResults.get(i).getCourseName().contains(searchTerm))) {
				searchResults.remove(i);
				i--;
			}
		}
	}
}
