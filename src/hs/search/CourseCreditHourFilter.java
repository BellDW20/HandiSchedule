package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCreditHourFilter extends CourseSearchFilter{
	
	boolean zeroCredit;
	boolean oneCredit;
	boolean twoCredit;
	boolean threeCredit;
	boolean fourCredit;
	boolean fiveCredit;
	
	public CourseCreditHourFilter(boolean selected, boolean selected2, boolean selected3, boolean selected4,
			boolean selected5, boolean selected6) {
		
		this.zeroCredit = selected;
		this.oneCredit = selected2;
		this.twoCredit = selected3;
		this.threeCredit = selected4;
		this.fourCredit = selected5;
		this.fiveCredit = selected6;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void narrowResults(ArrayList<Course> courseList) {
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
