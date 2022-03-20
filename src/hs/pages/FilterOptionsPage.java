package hs.pages;

import hs.core.CourseDatabase;
import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class FilterOptionsPage extends Page {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 595;
	
	private CourseDatabase db;
	
	public FilterOptionsPage(CourseDatabase db) {
		this.db = db;
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		drawRect(0, 0, WIDTH, HEIGHT);
		drawText(10, 10, "Filters");
		
		//Course code filters
		drawText(20, 30, "Course Code Levels");
		addCheckBox("100Level", 20, 50, "100");
		addCheckBox("200Level", 100, 50, "200");
		addCheckBox("300Level", 180, 50, "300");
		addCheckBox("400Level", 260, 50, "400");
		
		
		drawText(20, 80, "Course Credit Hours");
		addCheckBox("0Credits", 20, 100, "0");
		addCheckBox("1Credit", 100, 100, "1");
		addCheckBox("2Credits", 180, 100, "2");
		addCheckBox("3Credits", 260, 100, "3");
		addCheckBox("4Credits", 340, 100, "4");
		addCheckBox("5Credits", 420, 100, "5");
		
		drawText(20, 130, "Course Times");
		addDropDown("From:", 20, 150, "From",
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		addDropDown("AM or PMFrom:", 100, 150, "AM or PM", "AM", "PM");
		addDropDown("To:", 240, 150, "To", 
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		addDropDown("AM or PMTo:", 320, 150, "AM or PM", "AM", "PM");
		
		drawText(20, 180, "Departments:");
		
		String[] depts = db.getAllDepartmentsAsArray();
		String[] allAndDepts = new String[depts.length+1];
		for(int i=0; i<depts.length; i++) {
			allAndDepts[i+1] = depts[i];
		}
		allAndDepts[0] = "All";
		addDropDown("Department", 20, 200, allAndDepts);
		
		
		
		
	}

}
