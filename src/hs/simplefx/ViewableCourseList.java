package hs.simplefx;

import java.util.ArrayList;
import java.util.HashMap;

import hs.core.Course;
import hs.core.Schedule;
import javafx.scene.Node;

/**
 * Abstract class meant to be extended for various
 * different types of course lists which are viewable.
 * Every time a course is added, a course visual is added to a
 * scrollable list. Additionally, the child class is also
 * called when a course is added to add extra functionality
 * to these course visuals.
 * @author Douglas Bell
 */

public abstract class ViewableCourseList extends Page {
	
	private static final int COURSE_HEIGHT = 64;
	private static final int PAD = 2;

	private int w,h;
	private ArrayList<Course> courses;
	private HashMap<String, ViewableCourse> cachedViewableCourses;
	
	/**
	 * Creates an empty viewable course list of a certain size
	 * @param w Width of the course list
	 * @param h Height of the course list
	 */
	public ViewableCourseList(ArrayList<Course> coursesToCache, int w, int h) {
		super();
		this.w = w;
		this.h = h;
		this.courses = new ArrayList<>();
		
		initializeCache(coursesToCache);
	}
	
	public void initializeCache(ArrayList<Course> coursesToCache) {
		cachedViewableCourses = new HashMap<String, ViewableCourse>();
		for(Course course : coursesToCache) {
			cachedViewableCourses.put(course.getUniqueString(), new ViewableCourse(course, w-22, COURSE_HEIGHT, PAD));
			onCourseAdd(course);
		}
	}

	/**
	 * Run when a course is added. Allows child classes to add extra
	 * functionality / graphics to the course visuals
	 * @param course Course that was just added
	 */
	protected abstract void onCourseAdd(Course course);
	
	/**
	 * Adds a viewable course to the list, using the child class
	 * to add functionality / visuals.
	 * @param course
	 */
	public void addCourseToDisplay(Course course) {
		if(courses.contains(course)) {return;}
		courses.add(course);
		addSubPage(course.getUniqueString(), cachedViewableCourses.get(course.getUniqueString()), 
				   PAD, PAD+(courses.size()-1)*(COURSE_HEIGHT+PAD), w, COURSE_HEIGHT,
				   false
		);
	}
	
	public void addCoursesToDisplay(ArrayList<Course> courses) {
		ArrayList<Page> pages = new ArrayList<>();
		for(int i=0; i<courses.size(); i++) {
			Course course = courses.get(i);
			if(this.courses.contains(course)) {continue;}
			this.courses.add(course);
			pages.add(cachedViewableCourses.get(course.getUniqueString()));
		}
		addSubPages(pages, PAD, PAD, w, COURSE_HEIGHT, 0, COURSE_HEIGHT+PAD);
	}
	
	/**
	 * Adds a button to a given course in the course list
	 * @param course Course to add a button to
	 * @param buttonName Name to give the added button
	 * @param x X-position of the button
	 * @param y Y-position of the button
	 * @param w Width of the button
	 * @param h Height of the button
	 * @param text The text applied to the button
	 * @param onClick The action performed when the button is clicked
	 */
	protected void addButtonToCourse(Course course, String buttonName, int x, int y, int w, int h, String text, Runnable onClick) {
		cachedViewableCourses.get(course.getUniqueString()).addButton(buttonName, x+PAD, y+PAD, w, h, text, onClick);
	}

	/**
	 * Removes all courses from the viewable list
	 */
	public void clear() {
		while(!courses.isEmpty()) {
			Course s = courses.remove(0);
			removeComponent(SUB_PAGE, s.getUniqueString());
		}
	}
	
	/**
	 * Removes a specific course from the list, adjusting all
	 * other courses accordingly.
	 * @param course Course to remove from the list
	 */
	public void removeCourseToDisplay(Course course) {
		int index = courses.indexOf(course);
		
		courses.remove(index);
		removeComponent(SUB_PAGE, course.getUniqueString());
		
		for(int i=index; i<courses.size(); i++) {
			Node n = getPane().getChildren().get(i);
			n.setLayoutY(n.getLayoutY()-COURSE_HEIGHT-PAD);
		}
	}
	
	public void updateCourseVisuals(Schedule schedule) {
		//use update visual on each course in the cache
	}
	
	/**
	 * Getter for the width of this list
	 * @return Width of the list
	 */
	public int getW() { 
		return w;
	}
	
	/**
	 * Getter for the height of this list
	 * @return height of the list
	 */
	public int getH() {
		return h;
	}
	
}
