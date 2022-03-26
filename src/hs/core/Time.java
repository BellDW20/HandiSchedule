package hs.core;

import java.io.Serializable;

public class Time implements Serializable {
	
	private static final long serialVersionUID = -4083991327056493548L;
	
	//int values to tell whether a class is at AM or PM
	public static final int AM = 0;
	public static final int PM = 1;
	
	private int hour; //Hour of the time
	private int minute; //Minute of the time
	private int amOrPm; //Whether the time is in the AM or PM
	
	//Constructor method takes in the hour and minute of a course, and whether its AM or PM
	public Time(int hour, int minute, int amOrPm) {
		this.hour = hour;
		this.minute = minute;
		this.amOrPm = amOrPm;
	}
	
	/**
	 * Converts military time in format HH:MM:SS to standard time
	 * in HH:MM AM/PM to be used by the Time class.
	 * @param militaryTime Time as string in military time to convert
	 */
	public Time(String militaryTime) {
		String[] parts = militaryTime.split(":");
		int hour = Integer.parseInt(parts[0]);
		this.minute = Integer.parseInt(parts[1]);
		
		//checks to see if an hour is before or after noon, and changes its value to military time based on the results
		if(hour == 0) {
			this.hour = 12;
			this.amOrPm = AM;
		} else if(hour == 12) {
			this.hour = 12;
			this.amOrPm = PM;
		} else if(hour < 12) {
			this.hour = hour;
			this.amOrPm = AM;
		} else {
			this.hour = hour-12;
			this.amOrPm = PM;
		}
	}
	
	//getter for the military hour of a certain time
	public int getMilitaryHour() {
		int normalizedHour = hour%12;
		return (normalizedHour + (amOrPm==PM?12:0));
	}
	
	//getter for the minute a class goes to
	public int getMinute() {
		return minute;
	}
	
	//returns true if a time frame falls within a certain time, false otherwise
	public boolean fallsWithin(TimeFrame timeFrame) {
		return isAfter(timeFrame.getStartTime()) && isBefore(timeFrame.getEndTime());
	}
	
	/*
	 * Checks to see if a time is after another time. 
	 * Returns true if the time parameter taken in is after the other time.
	 * Returns false otherwise.
	 */
	public boolean isAfter(Time time) {
		return time.getTimeAsInt() <= getTimeAsInt();
	}
	
	/*
	 * Checks to see if a time is before another time.
	 * Returns true if the time parameter taken in is before the other time.
	 * Returns false otherwise.
	 */
	public boolean isBefore(Time time) {
		return time.getTimeAsInt() >= getTimeAsInt();
	}
	
	/* Returns the time as a single integer (as minutes from 00:00)
	 * based on its military time values
	 */
	public int getTimeAsInt() {
		return getMilitaryHour()*60+minute;
	}
	
	//returns the time as a readable string
	@Override
	public String toString() {
		return (hour+":"+(minute<10?"0":"")+minute+((amOrPm==AM)?" AM":" PM"));
	}
	
	// Takes in choice for AM or PM dropdown, returns integer value depending
	public static int getAMOrPMFromString(String amOrPM) {
		if (amOrPM.equals("AM")) {
			return AM;
		}
		return PM;
	}

}
