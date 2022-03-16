package hs.core;

public class Time {
	
	//test commit
	
	static final int AM = 0;
	static final int PM = 1;
	
	int hour;
	int minute;
	int amOrPm;
	
	public Time(int hour, int minute, int amOrPm) {
		hour = this.hour;
		minute = this.minute;
		amOrPm = this.amOrPm;
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
	
	

}
