package hs.search;

import java.util.ArrayList;

import hs.core.Course;
import hs.core.TimeFrame;

public class CourseTimeFrameFilter extends CourseSearchFilter {
	
	private TimeFrame time; //Time frame to filter by
	
	public CourseTimeFrameFilter(TimeFrame timeFrame) {
		time = timeFrame;
	}

	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		boolean remove = true;
		
		//For every course left in the search...
		for(int i = 0; i < courseList.size(); i++) {
			
			//If any of the meeting times of a course fall within
			//the filtered time frame, keep it.
			for (int j = 0; j < courseList.get(i).getMeetingTimes().size(); j++) {
				if(courseList.get(i).getMeetingTimes().get(j).getTimeFrame().fallsWithin(time)) {
					remove = false;
					break;
				}
			}
			
			//If the course does not fall within the time frame, remove it from the results
			if (remove) {
				courseList.remove(i);
				i--;
			}
			else {
				//reset remove flag
				remove = true;
			}
		}
		
	}
}
