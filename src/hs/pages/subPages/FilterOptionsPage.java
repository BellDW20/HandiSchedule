package hs.pages.subPages;

import hs.core.CourseDatabase;
import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class FilterOptionsPage extends Page {

	//Width and height of this subpage
	public static final int WIDTH = 500;
	public static final int HEIGHT = 595;
	
	private CourseDatabase db; //A reference to the course database
	
	public FilterOptionsPage(CourseDatabase db) {
		this.db = db;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initializeComponents(PageManager pageManager) {
		drawRect(0, 0, WIDTH, HEIGHT);
		addLabel("filterLabel", 10, 10, "Filters");
		
		//Add course code filters
		addLabel("courseCodeLabel", 20, 30, "Course Code Levels");
		addCheckBox("100Level", 20, 50, "100");
		addCheckBox("200Level", 100, 50, "200");
		addCheckBox("300Level", 180, 50, "300");
		addCheckBox("400Level", 260, 50, "400");
		
		//Add credit hour filters
		addLabel("creditHourLabel", 20, 80, "Course Credit Hours");
		addCheckBox("0Credits", 20, 100, "0");
		addCheckBox("1Credit", 100, 100, "1");
		addCheckBox("2Credits", 180, 100, "2");
		addCheckBox("3Credits", 260, 100, "3");
		addCheckBox("4Credits", 340, 100, "4");
		addCheckBox("5Credits", 420, 100, "5");
		
		//Adds drop downs for selecting a time frame to filter by
		addLabel("courseTimeLabel", 20, 130, "Course Times");
		addDropDown("From:", 20, 150, "From",
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		addDropDown("AM or PMFrom:", 100, 150, "AM or PM", "AM", "PM");
		addDropDown("To:", 240, 150, "To", 
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		addDropDown("AM or PMTo:", 320, 150, "AM or PM", "AM", "PM");
		
		//Adds the department filter (with an "All" departments option)
		addLabel("departmentLabel", 20, 180, "Departments:");
		
		String[] depts = db.getAllDepartmentsAsArray();
		String[] allAndDepts = new String[depts.length+1];
		for(int i=0; i<depts.length; i++) {
			allAndDepts[i+1] = depts[i];
		}
		allAndDepts[0] = "All";
		addDropDown("Department", 10, 200, allAndDepts);
		
		//Adds a button which clears all set filters to their default value
		addButton("clearFilters", 20, HEIGHT-42, 96, 32, "Clear Filters", ()->{
			getCheckBox("100Level").setSelected(false);
			getCheckBox("200Level").setSelected(false);
			getCheckBox("300Level").setSelected(false);
			getCheckBox("400Level").setSelected(false);
			
			getCheckBox("0Credits").setSelected(false);
			getCheckBox("1Credit").setSelected(false);
			getCheckBox("2Credits").setSelected(false);
			getCheckBox("3Credits").setSelected(false);
			getCheckBox("4Credits").setSelected(false);
			getCheckBox("5Credits").setSelected(false);
			
			getDropDown("From:").setValue("From");
			getDropDown("AM or PMFrom:").setValue("AM or PM");
			getDropDown("To:").setValue("To");
			getDropDown("AM or PMTo:").setValue("AM or PM");
			
			getDropDown("Department").setValue("All");
		});
		
		
	}

}
