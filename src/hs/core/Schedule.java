package hs.core;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.ArrayList;
import java.awt.Graphics;

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
	

}
