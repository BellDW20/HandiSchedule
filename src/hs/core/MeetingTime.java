package hs.core;

public class MeetingTime {

	private TimeFrame timeFrame;
	private String daysOfWeek;
	private boolean countedTowardsCreditHours;
	
	public MeetingTime(TimeFrame timeFrame, String daysOfWeek, boolean countedTowardsCreditHours) {
		this.timeFrame = timeFrame;
		this.daysOfWeek = daysOfWeek;
		this.countedTowardsCreditHours = countedTowardsCreditHours;
	}
	
	public TimeFrame getTimeFrame() {
		return timeFrame;
	}
	
	public char[] getDaysOfWeek() {
		return daysOfWeek.toCharArray();
	}
	
	public boolean isCountedTowardsCreditHours() {
		return countedTowardsCreditHours;
	}
	
	@Override
	public String toString() {
		return timeFrame+" "+daysOfWeek;
	}
	
}
