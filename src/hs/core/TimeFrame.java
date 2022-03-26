package hs.core;

import java.io.Serializable;

public class TimeFrame implements Serializable {

	private static final long serialVersionUID = 351803354426374556L;
	
	private Time startTime;
	private Time endTime;
	
	//Constructor method takes in a start time and an end time for a class
	public TimeFrame(Time startTime, Time endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	//getter for a class's start time
	public Time getStartTime() {
		return startTime;
	}
	
	//getter for a class's end time
	public Time getEndTime() {
		return endTime;
	}
	
	//checks to see if two times/courses are overlapping. Returns true if yes, false if no.
	public boolean isOverlappingWith(TimeFrame timeFrame) {
		return getStartTime().fallsWithin(timeFrame) || timeFrame.getStartTime().fallsWithin(this); 
	}
	
	/*
	 * Checks to see if two time-frames fall within each other/overlap at all.
	 * Returns true if the time-frames overlap.
	 * Returns false otherwise.
	 */
	public boolean fallsWithin(TimeFrame timeFrame) {
		return startTime.fallsWithin(timeFrame) && endTime.fallsWithin(timeFrame);
	}
	
	//getter for the time in minutes of the course
	public int getTimeInMinutes() {
		return (endTime.getTimeAsInt() - startTime.getTimeAsInt());
	}
	
	//returns the start and end time of a class in a readable string
	@Override
	public String toString() {
		return startTime.toString() + " - " + endTime.toString();
	}
	
}
