package hs.pages.subPages;

import hs.core.CourseDatabase;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FilterOptionsPage extends Page {

	//Width and height of this subpage
	public static final int WIDTH = 500;
	public static final int HEIGHT = 595;
	public String styleString = "-fx-font-family: Arial, Helvetica, sans-serif";
	
	private CourseDatabase db; //A reference to the course database
	
	public FilterOptionsPage(CourseDatabase db) {
		this.db = db;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initializeComponents(PageManager pageManager) {
		drawRect(0, 0, WIDTH, HEIGHT);
		addLabel("filterLabel", 10, 10, "Filters");
		getLabel("filterLabel").setFont(Font.font("Arial", FontWeight.BOLD, 12.0));
		
		//Add course code filters
		addLabel("courseCodeLabel", 20, 30, "Course Code Levels");
		getLabel("courseCodeLabel").setFont(Font.font("Arial", 12.0));
		addCheckBox("100Level", 20, 50, "100");
		getCheckBox("100Level").setStyle(styleString);
		addCheckBox("200Level", 100, 50, "200");
		getCheckBox("200Level").setStyle(styleString);
		addCheckBox("300Level", 180, 50, "300");
		getCheckBox("300Level").setStyle(styleString);
		addCheckBox("400Level", 260, 50, "400");
		getCheckBox("400Level").setStyle(styleString);

		
		//Add credit hour filters
		addLabel("creditHourLabel", 20, 80, "Course Credit Hours");
		getLabel("creditHourLabel").setFont(Font.font("Arial", 12.0));
		addCheckBox("0Credits", 20, 100, "0");
		getCheckBox("0Credits").setStyle(styleString);

		addCheckBox("1Credit", 100, 100, "1");
		getCheckBox("1Credit").setStyle(styleString);

		addCheckBox("2Credits", 180, 100, "2");
		getCheckBox("2Credits").setStyle(styleString);

		addCheckBox("3Credits", 260, 100, "3");
		getCheckBox("3Credits").setStyle(styleString);

		addCheckBox("4Credits", 340, 100, "4");
		getCheckBox("4Credits").setStyle(styleString);

		addCheckBox("5Credits", 420, 100, "5");
		getCheckBox("5Credits").setStyle(styleString);

		
		//Adds drop downs for selecting a time frame to filter by
		addLabel("courseTimeLabel", 20, 130, "Course Times");
		getLabel("courseTimeLabel").setFont(Font.font("Arial", 12.0));
		addDropDown("From:", 20, 150, "From",
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		getDropDown("From:").setStyle(styleString);
		addDropDown("AM or PMFrom:", 100, 150, "AM or PM", "AM", "PM");
		getDropDown("AM or PMFrom:").setStyle(styleString);
		addDropDown("To:", 240, 150, "To", 
					"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"
		);
		getDropDown("To:").setStyle(styleString);
		addDropDown("AM or PMTo:", 320, 150, "AM or PM", "AM", "PM");
		getDropDown("AM or PMTo:").setStyle(styleString);
		
		//Adds the department filter (with an "All" departments option)
		addLabel("departmentLabel", 20, 180, "Departments:");
		getLabel("departmentLabel").setFont(Font.font("Arial", 12.0));
		
		String[] depts = db.getAllDepartmentsAsArray();
		String[] allAndDepts = new String[depts.length+1];
		for(int i=0; i<depts.length; i++) {
			allAndDepts[i+1] = depts[i];
		}
		allAndDepts[0] = "All";
		addDropDown("Department", 20, 200, allAndDepts);
		getDropDown("Department").setStyle(styleString);
		
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
