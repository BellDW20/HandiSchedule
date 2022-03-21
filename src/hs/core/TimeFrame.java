package hs.core;

import java.io.Serializable;

public class TimeFrame implements Serializable {

	private static final long serialVersionUID = 351803354426374556L;
	
	private Time startTime;
	private Time endTime;
	
	public TimeFrame(Time startTime, Time endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public boolean isOverlappingWith(TimeFrame timeFrame) {
		return getStartTime().fallsWithin(timeFrame) || timeFrame.getStartTime().fallsWithin(this); 
	}
	
	public boolean fallsWithin(TimeFrame timeFrame) {
		return startTime.fallsWithin(timeFrame) && endTime.fallsWithin(timeFrame);
	}
	
	public int getTimeInMinutes() {
		return (endTime.getTimeAsInt() - startTime.getTimeAsInt());
	}
	
	@Override
	public String toString() {
		return startTime.toString() + " - " + endTime.toString();
	}
	
}
