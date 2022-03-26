package hs.search;

import java.util.ArrayList;

import hs.core.Course;

public class CourseCreditHourFilter extends CourseSearchFilter{
	
	//Whether or not the zero, one, two, three, four, and five
	//credit courses should be included in the search
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
		//If we've made any selection, filter. Otherwise, keep everything
		if(zeroCredit || oneCredit || twoCredit || threeCredit || fourCredit || fiveCredit) {
			//For every course left in the search...
			for (int i = 0; i < courseList.size(); i++) {
				boolean keep = false;
				int creditHours = courseList.get(i).getCreditHours();
				
				//If the course has any of the credit hour values filtered by, keep it
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
				
				//If the course didn't match the filter, remove it
				if(!keep) {
					courseList.remove(i);
					i--;
				}
				
			}
		}
	}

}
