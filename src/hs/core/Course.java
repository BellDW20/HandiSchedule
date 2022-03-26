package hs.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains information related to courses for use in other classes as an object.
 * @author Levi Conrad
 */
public class Course implements Serializable {
	
	private static final long serialVersionUID = -7756127829025002535L;
	
	private String courseName; // Name of the course
	private String department; // Department of the course
	private char section; //Section of the course
	private int courseCode; //Number of the course (ex 101, 222, etc.)
	private int creditHours; //Estimated number of credit hours of the course
	
	private ArrayList<MeetingTime> meetingTimes; //Times at which the course meets
	
	//constructor takes in a name, department, section, and code for each course.
	public Course(String courseName, String department, char section, int courseCode) {
		this.courseName = courseName;
		this.department = department;
		this.section = section;
		this.courseCode = courseCode;
		meetingTimes = new ArrayList<>();
	}
	
	/*
	 * This method adds a meeting time for a course during the week.
	 * It takes in a time of day the course occurs, the days it occurs,
	 * and a boolean.
	 */
	public void addMeetingTime(TimeFrame timeFrame, String daysOfWeek, boolean countedTowardsDayOfWeek) {
		meetingTimes.add(new MeetingTime(timeFrame, daysOfWeek, countedTowardsDayOfWeek));
		calculateCreditHours(); // recalculates the credit hours based on the meeting times
	}
	
	/*
	 * Method calculates the total number of credits a course is worth
	 */
	private void calculateCreditHours() {
		int totalHours = 0;
		
		for(MeetingTime meetingTime : meetingTimes) {
			//As long as the course is not something such as a lab,
			//its credit hours are estimated and added to the running total
			if(meetingTime.isCountedTowardsCreditHours()) {
				totalHours += meetingTime.getTimeFrame().getTimeInMinutes()*meetingTime.getDaysOfWeek().length;
			}
		}
		
		creditHours = (int)Math.ceil(totalHours/60.0);
	}
	
	//getter method for the course name
	public String getCourseName() {
		return courseName;
	}

	//getter method for the course department
	public String getDepartment() {
		return department;
	}
	
	//getter method for the course section
	public char getSection() {
		return section;
	}

	//getter method for the course code
	public int getCourseCode() {
		return courseCode;
	}

	//getter metho for the number of hours a course is worth
	public int getCreditHours() {
		return creditHours;
	}
	
	//getter for the meeting times of a course
	public ArrayList<MeetingTime> getMeetingTimes() {
		return meetingTimes;
	}
	
	/*
	 * This method takes in a course, and looks to see if that course
	 * has conflicting time slots with another time for another potential course
	 */
	public boolean isConflictingWith(Course course) {
		//If any of our meeting times conflict, there is a conflict
		for(MeetingTime myTime : meetingTimes) {
			for(MeetingTime otherTime : course.getMeetingTimes()) {
				if(myTime.isConflictingWith(otherTime)) {
					return true;
				}
			}
		}
		
		//Otherwise, there is not a conflict
		return false;
	}
	
	//returns the department, code, and section of a particular course delimited by spaces.
	public String getUniqueString() {
		return department+" "+courseCode+" "+section;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {return true;}
		if(!(o instanceof Course)) {
			return false;
		}
		Course c = (Course)o;
		return c.getCourseName().equals(courseName) && c.getSection()==section;
	}
	
	
	/*
	 * This method creates a string of all of a course's important/relevant information.
	 * This include course department, code, section, name, meeting times, and credit hours.
	 * Generally used for debugging course creation / loading
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//add the department, code, section, and course name to the stringbuilder
		sb.append(department+" "+courseCode+" "+section+" | "+courseName+" | ");
		
		int i;
		for(i=0; i<meetingTimes.size()-1; i++) {
			sb.append(meetingTimes.get(i)+", "); //add the meeting times to stringbuilder
		}
		
		//makes sure that meeting times are only added if they exist
		//this handles classes like Study Abroads
		if(meetingTimes.size() == 0) {
			sb.append("No set meeting time | Variable credit hours");
		} else {
			sb.append(meetingTimes.get(i));
			sb.append(" | "+creditHours+" credit hour(s)"); //add credit hours to stringbuilder
		}
		
		//return the created string with all of the course information
		return sb.toString();
	}

}	
