package hs.core;

import java.util.ArrayList;

/**
 * This class contains information related to courses for use in other classes as an object.
 * @author CONRADLJ19
 *
 */
public class Course {
	
	private String courseName;
	private String department;
	private char section;
	private int courseCode;
	private int creditHours;
	
	private ArrayList<MeetingTime> meetingTimes;
	
	public Course(String courseName, String department, char section, int courseCode) {
		this.courseName = courseName;
		this.department = department;
		this.section = section;
		this.courseCode = courseCode;
		meetingTimes = new ArrayList<>();
	}

	public void addMeetingTime(TimeFrame timeFrame, String daysOfWeek, boolean countedTowardsDayOfWeek) {
		meetingTimes.add(new MeetingTime(timeFrame, daysOfWeek, countedTowardsDayOfWeek));
		calculateCreditHours();
	}
	
	private void calculateCreditHours() {
		int totalHours = 0;
		
		for(MeetingTime meetingTime : meetingTimes) {
			if(meetingTime.isCountedTowardsCreditHours()) {
				totalHours += meetingTime.getTimeFrame().getTimeInMinutes()*meetingTime.getDaysOfWeek().length;
			}
		}
		
		creditHours = (int)Math.ceil(totalHours/60.0);
	}
	
	public String getCourseName() {
		return courseName;
	}

	public String getDepartment() {
		return department;
	}
	
	public char getSection() {
		return section;
	}

	public int getCourseCode() {
		return courseCode;
	}

	public int getCreditHours() {
		return creditHours;
	}
	
	public ArrayList<MeetingTime> getMeetingTimes() {
		return meetingTimes;
	}
}	
