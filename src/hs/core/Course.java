package hs.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains information related to courses for use in other classes as an object.
 * @author CONRADLJ19
 *
 */
public class Course implements Serializable {
	
	private static final long serialVersionUID = -7756127829025002535L;
	
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
	
	public boolean isConflictingWith(Course course) {
		for(MeetingTime myTime : meetingTimes) {
			for(MeetingTime otherTime : course.getMeetingTimes()) {
				if(myTime.isConflictingWith(otherTime)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getUniqueString() {
		return department+" "+courseCode+" "+section;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Course)) {
			return false;
		}
		Course c = (Course)o;
		return c.getCourseName().equals(courseName) && c.getSection()==section;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(department+" "+courseCode+" "+section+" | "+courseName+" | ");
		
		int i;
		for(i=0; i<meetingTimes.size()-1; i++) {
			sb.append(meetingTimes.get(i)+", ");
		}
		
		if(meetingTimes.size() == 0) {
			sb.append("No set meeting time | Variable credit hours");
		} else {
			sb.append(meetingTimes.get(i));
			sb.append(" | "+creditHours+" credit hour(s)");
		}
		
		return sb.toString();
	}

}	
