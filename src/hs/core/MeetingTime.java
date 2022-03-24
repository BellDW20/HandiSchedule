package hs.core;

import java.io.Serializable;

public class MeetingTime implements Serializable {

	private static final long serialVersionUID = -1022595768143615665L;
	
	private TimeFrame timeFrame;
	private String daysOfWeek;
	private boolean countedTowardsCreditHours;
	
	/*
	 * Constructor method. Takes in a time for the class, the days of the week the class
	 * occurs, and a boolean to tell if it is counted towards total credit hours in a 
	 * student's schedule.
	 */
	public MeetingTime(TimeFrame timeFrame, String daysOfWeek, boolean countedTowardsCreditHours) {
		this.timeFrame = timeFrame;
		this.daysOfWeek = daysOfWeek;
		this.countedTowardsCreditHours = countedTowardsCreditHours;
	}
	
	//getter for the time frame of a class
	public TimeFrame getTimeFrame() {
		return timeFrame;
	}
	
	//getter for the days of the week a course occurs on
	//returns a char representation of each day of the week
	public char[] getDaysOfWeek() {
		return daysOfWeek.toCharArray();
	}
	
	//getter for the days of the week a course occurs on. Returns the full string/name of the days
	public String getDaysOfWeekString() {
		return daysOfWeek;
	}
	
	/*
	 * Method tells whether a class is counted toward credit hours or not
	 * If yesm it returns true, else it returns false.
	 */
	public boolean isCountedTowardsCreditHours() {
		return countedTowardsCreditHours;
	}
	
	/*
	 * This method checks to see if classes are happening on the same day.
	 * It looks at the first character of each day of the week that classes occur.
	 * If the letters are the same, then the classes do overlap. Otherwise, they
	 * are on different days.
	 */
	public boolean isConflictingWith(MeetingTime time) {
		boolean daysOverlap = false;
		for(char c : time.getDaysOfWeek()) {
			if(daysOfWeek.contains(""+c)) {
				daysOverlap = true;
				break;
			}
		}
		
		if(!daysOverlap) {
			return false;
		}
		
		//returns true if the courses overlap, false otherwise
		return timeFrame.isOverlappingWith(time.getTimeFrame());
	}
	
	//returns the time frame and days of the week each class occurs in string form
	@Override
	public String toString() {
		return timeFrame+" "+daysOfWeek;
	}
	
}
