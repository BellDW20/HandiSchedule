package hs.core;

/**
 * This class contains information related to courses for use in other classes as an object.
 * @author CONRADLJ19
 *
 */
public class Course {
	private String courseName;
	private String department;
	private int courseCode;
	private int creditHours;
	private TimeFrame time;
	
	public Course(String courseName, String department, int courseCode, int creditHours, TimeFrame time) {
		this.courseName = courseName;
		this.department = department;
		this.courseCode = courseCode;
		this.creditHours = creditHours;
		this.time = time;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getDepartment() {
		return department;
	}

	public int getCourseCode() {
		return courseCode;
	}

	public int getCreditHours() {
		return creditHours;
	}
	
	public TimeFrame getTime() {
		return time;
	}
}	
