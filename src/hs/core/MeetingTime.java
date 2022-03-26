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
	 * This method checks to see if time frames conflict
	 */
	public boolean isConflictingWith(MeetingTime time) {
		boolean daysOverlap = false;
		
		//If any of the days of the two meeting times overlap,
		//there is a potential for conflict
		for(char c : time.getDaysOfWeek()) {
			if(daysOfWeek.contains(""+c)) {
				daysOverlap = true;
				break;
			}
		}
		
		//Otherwise, there is no conflict
		if(!daysOverlap) {
			return false;
		}
		
		//If there could be a conflict, check if the time frames overlap
		return timeFrame.isOverlappingWith(time.getTimeFrame());
	}
	
	//returns the time frame and days of the week each class occurs in string form
	@Override
	public String toString() {
		return timeFrame+" "+daysOfWeek;
	}
	
}
