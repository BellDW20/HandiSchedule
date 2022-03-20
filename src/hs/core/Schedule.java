package hs.core;

import java.sql.Date;
import java.util.ArrayList;

public class Schedule {
	
	private String title;
	private Date dateLastModified;
	private ArrayList<Course> courses;
	
	public Schedule() {
		this.title = "Untitled Schedule";
		this.dateLastModified = new Date(System.currentTimeMillis());
		courses = new ArrayList<Course>();
	}
	
	public void addCourse(Course course) {
		if(courses.contains(course)) {return;}
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
	
	public int calculateTotalCreditHours() {
		int totalCreditHours = 0;
		for(int i = 0; i < courses.size(); i++) {
			totalCreditHours += courses.get(i).getCreditHours();
		}
		return totalCreditHours;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getDateLastModified() {
		return dateLastModified;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Course c : courses) {
			sb.append(c.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
