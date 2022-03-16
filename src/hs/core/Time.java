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
