package hs.search;

import java.util.ArrayList;

import hs.core.Course;
import hs.core.TimeFrame;

public class CourseTimeFrameFilter extends CourseSearchFilter{
	private TimeFrame time;
	
	public CourseTimeFrameFilter(TimeFrame timeFrame) {
		time = timeFrame;
	}

	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		boolean remove = true;
		for(int i = 0; i < courseList.size(); i++) {
			for (int j = 0; j < courseList.get(i).getMeetingTimes().size(); j++) {
				if(courseList.get(i).getMeetingTimes().get(j).getTimeFrame().fallsWithin(time)) {
					remove = false;
					break;
				}
			}
			if (remove) {
				courseList.remove(i);
				i--;
			}
			else {
				remove = true;
			}
		}
		
	}
}
