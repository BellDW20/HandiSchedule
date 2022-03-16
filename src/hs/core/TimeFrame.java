package hs.core;

public class TimeFrame {

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
	
	public boolean fallsWithin(TimeFrame timeFrame) {
		return startTime.fallsWithin(timeFrame) && endTime.fallsWithin(timeFrame);
	}
	
	public int getTotalTimeInMinutes() {
		return (endTime.getTimeAsInt() - startTime.getTimeAsInt());
	}
	
	@Override
	public String toString() {
		return startTime.toString() + " - " + endTime.toString();
	}
	
}
