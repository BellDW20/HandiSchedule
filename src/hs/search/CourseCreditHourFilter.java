package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCreditHourFilter extends CourseSearchFilter{
	
	private boolean zeroCredit;
	private boolean oneCredit;
	private boolean twoCredit;
	private boolean threeCredit;
	private boolean fourCredit;
	private boolean fiveCredit;
	
	public CourseCreditHourFilter(boolean zeroCredit, boolean oneCredit, boolean twoCredit, boolean threeCredit, boolean fourCredit, boolean fiveCredit) {
		this.zeroCredit = zeroCredit;
		this.oneCredit = oneCredit;
		this.twoCredit = twoCredit;
		this.threeCredit = threeCredit;
		this.fourCredit = fourCredit;
		this.fiveCredit = fiveCredit;
	}

	@Override
	public void narrowResults(ArrayList<Course> courseList) {
		if(zeroCredit || oneCredit || twoCredit || threeCredit || fourCredit || fiveCredit) {
			for (int i = 0; i < courseList.size(); i++) {
				boolean keep = false;
				int creditHours = courseList.get(i).getCreditHours();
				
				if(zeroCredit && creditHours == 0) {
					keep = true;
				} else if(oneCredit && creditHours == 1) {
					keep = true;
				} else if(twoCredit && creditHours == 2) {
					keep = true;
				} else if(threeCredit && creditHours == 3) {
					keep = true;
				} else if(fourCredit && creditHours == 4) {
					keep = true;
				} else if(fiveCredit && creditHours == 5) {
					keep = true;
				}
				
				
				if(!keep) {
					courseList.remove(i);
					i--;
				}
				
			}
		}
	}

}
