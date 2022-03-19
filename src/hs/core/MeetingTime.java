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
	
	public boolean isConflictingWith(MeetingTime time) {
		boolean daysOverlap = false;
		for(char c : time.getDaysOfWeek()) {
			if(daysOfWeek.contains(""+c)) {
				daysOverlap = true;
				break;
			}
		}
		
		if(!daysOverlap) {
			return false;
		}
		
		return timeFrame.fallsWithin(time.getTimeFrame());
	}
	
	@Override
	public String toString() {
		return timeFrame+" "+daysOfWeek;
	}
	
}
