package hs.core;

public class Time {
	
	static final int AM = 0;
	static final int PM = 1;
	
	int hour;
	int minute;
	int amOrPm;
	
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
	
	public boolean fallsWithin(TimeFrame timeFrame) {
		return isAfter(timeFrame.getStartTime()) && isBefore(timeFrame.getEndTime());
	}
	
	public boolean isAfter(Time time) {
		if(time.hour < this.hour) {
			return true;
		}
		else if(time.hour == this.hour) {
			if(time.minute < this.minute) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean isBefore(Time time) {
		if(time.hour > this.hour) {
			return true;
		}
		else if(time.hour == this.hour) {
			if(time.minute > this.minute) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public int getTimeAsInt() {
		int normalizedHour = hour % 12;
		return (normalizedHour + (amOrPm==PM?12:0))*60+minute;
	}

}
