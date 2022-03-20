package hs.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;

public class Schedule {
	
	private String title;
	private Date dateLastModified;
	private ArrayList<Course> courses;
	
	public Schedule() {
		this.title = "Untitled Schedule";
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
	
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
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
	
	public void saveSchedule(String path) {
		try {
			
			ObjectOutputStream dataOut = new ObjectOutputStream(new FileOutputStream(new File(path)));
			dataOut.writeObject(title);
			dataOut.writeObject(courses);
			dataOut.writeObject(new Date(System.currentTimeMillis()));
			dataOut.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Schedule loadSchedule(String path) {		
		return null;
	}
	
}
