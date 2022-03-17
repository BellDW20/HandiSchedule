package hs.simplefx;

import java.util.ArrayList;

import hs.core.Course;
import javafx.scene.Node;

public class ViewableCourseList extends Page {
	
	private static final int COURSE_HEIGHT = 64;
	private static final int PAD = 2;

	private int w,h;
	private ArrayList<String> courses;
	
	public ViewableCourseList(int w, int h) {
		super();
		this.w = w;
		this.h = h;
		this.courses = new ArrayList<>();
	}

	@Override
	public void initializeComponents(PageManager pageManager) {
		
	}
	
	public void addCourseToDisplay(Course course) {
		if(courses.contains(course.getCourseName())) {return;}
		courses.add(course.getCourseName());
		addSubPage(course.getCourseName(), new ViewableCourse(course, w-22, COURSE_HEIGHT, PAD), 
				   PAD, PAD+(courses.size()-1)*(COURSE_HEIGHT+PAD), w, COURSE_HEIGHT,
				   false
		);
	}
	
	public void addButtonToCourse(String course, String buttonName, int x, int y, int w, int h, String text, Runnable onClick) {
		getSubPage(course).addButton(buttonName, x+PAD, y+PAD, w, h, text, onClick);
	}

	public void clear() {
		while(!courses.isEmpty()) {
			String s = courses.remove(0);
			removeComponent(SUB_PAGE, s);
		}
	}
	
	public void removeCourseToDisplay(String course) {
		int index = courses.indexOf(course);
		
		courses.remove(index);
		removeComponent(SUB_PAGE, course);
		
		for(int i=index; i<courses.size(); i++) {
			Node n = getPane().getChildren().get(i);
			n.setLayoutY(n.getLayoutY()-COURSE_HEIGHT-PAD);
		}
	}
	
	public int getW() { 
		return w;
	}
	
	public int getH() {
		return h;
	}
	
}
