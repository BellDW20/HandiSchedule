package hs.core;

import java.io.Serializable;

public class Time implements Serializable {
	
	private static final long serialVersionUID = -4083991327056493548L;
	
	public static final int AM = 0;
	public static final int PM = 1;
	
	private int hour;
	private int minute;
	private int amOrPm;
	
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
	
	public int getHour() {
		if (amOrPm == 0) {
			return hour;
		}
		else {
			return hour + 12;
		}
	}
	
	public int getMinutes() {
		return minute;
	}
	
	public boolean fallsWithin(TimeFrame timeFrame) {
		return isAfter(timeFrame.getStartTime()) && isBefore(timeFrame.getEndTime());
	}
	
	public boolean isAfter(Time time) {
		return time.getTimeAsInt() <= getTimeAsInt();
	}
	
	public boolean isBefore(Time time) {
		return time.getTimeAsInt() >= getTimeAsInt();
	}
	
	public int getTimeAsInt() {
		int normalizedHour = hour % 12;
		return (normalizedHour + (amOrPm==PM?12:0))*60+minute;
	}
	
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
