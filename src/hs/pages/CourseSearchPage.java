package hs.pages;

import java.io.File;

import hs.core.Course;
import hs.core.CourseDatabase;
import hs.core.Schedule;
import hs.core.Time;
import hs.core.TimeFrame;
import hs.pages.subPages.CourseScheduleList;
import hs.pages.subPages.CourseSearchList;
import hs.search.CourseCodeFilter;
import hs.search.CourseCreditHourFilter;
import hs.search.CourseDepartmentFilter;
import hs.search.CourseNameFilter;
import hs.search.CourseSearch;
import hs.search.CourseTimeFrameFilter;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CourseSearchPage extends Page {
	
	private static final String SAVE_DIR = "./schedules/";
	private static final String DEFAULT_SCHEDULE_NAME = "Untitled Schedule";
	private static final String SAVE_EXT = ".schd";
	
	private CourseSearchList searchList;
	private CourseScheduleList scheduleList;
	
	private CourseDatabase db;
	private CourseSearch currentSearch;
	private Schedule currentSchedule;
	
	private boolean setupFinished = false;
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		//Make sure schedule folder exists
		(new File(SAVE_DIR)).mkdirs();
		
		//Load course database
		db = CourseDatabase.loadFromFile("CourseDB_CSV.csv");
		currentSearch = new CourseSearch(db);
		
		//Initialize search course list and schedule course list
		searchList = new CourseSearchList();
		searchList.initializeComponents(pageManager);
		scheduleList = new CourseScheduleList();
		searchList.setScheduleList(scheduleList);
		addSubPage("searchList", searchList, 10, 120, searchList.getW(), searchList.getH(), true);
		addSubPage("scheduleList", scheduleList, 770, 120, scheduleList.getW(), scheduleList.getH(), true);
		
		//add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", ()->{
			hideComponent(SUB_PAGE, "filterOptions");
		});
		
		//add New button
		addButton("newButton", 105, 5, 80, 40, "New", ()->{
			saveCurrentSchedule();
			currentSchedule = new Schedule(getFirstUnusedScheduleName());
			saveCurrentSchedule();
			setupFinished = false;
			loadMostRecentlyEditedSchedule(pageManager);
		});
		
		//add text field for editing schedule title
		addTextField("scheduleTitle", 200, 5, 350, 40, "[Enter schedule title here]");
		
		getTextField("scheduleTitle").textProperty().addListener((obs, oldText, newText) -> {
			if((new File(getSavePath(newText))).exists() && setupFinished) {
				Alert renameError = new Alert(AlertType.ERROR);
				renameError.setHeaderText("Schedule Rename Failed");
				renameError.setContentText("A schedule with the name '"+newText+"' already exists.");
				renameError.showAndWait();
				getTextField("scheduleTitle").setText(getFirstUnusedScheduleName());
				return;
			}
			
			renameCurrentSchedule(newText);
			currentSchedule.setTitle(newText);
			saveCurrentSchedule();
			
			CalendarPage calendarPage = ((CalendarPage)pageManager.getPage("CalendarPage"));
			if(calendarPage != null) {
				calendarPage.updateScheduleTitle(currentSchedule.getTitle());
			}
			setupFinished = true;
		});
		
		//add course search view button
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		
		});
		
		//add calendar view button
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			pageManager.goToPage("CalendarPage");
		});
		
		//Add area to enter name to search by and button to complete search
		addTextField("searchField", 10, 70, 500, 40, "Search...");
		getTextField("searchField").setOnAction((event)->{
			performSearch();
		});
		
		addButton("searchButton", 510, 70, 40, 40, "Search", ()->{
			performSearch();
		});
		
		//Add ability to see filter options for search
		addButton("filterButton", 560, 70, 40, 40, "Filter", ()->{
			toggleComponentVisibility(SUB_PAGE, "filterOptions");
		});
		
		FilterOptionsPage filterOptionsPage = new FilterOptionsPage(db);
		filterOptionsPage.initializeComponents(pageManager);
		addSubPage("filterOptions", filterOptionsPage, 10, 120, FilterOptionsPage.WIDTH, FilterOptionsPage.HEIGHT, false);
		hideComponent(SUB_PAGE, "filterOptions");
		
		loadMostRecentlyEditedSchedule(pageManager);
	}
	
	private void performSearch() {
		searchList.clear();
		hideComponent(SUB_PAGE, "filterOptions");
		currentSearch.clearFilters();
		currentSearch.addSearchFilter(new CourseNameFilter(getTextField("searchField").getText()));

		Page temp = getSubPage("filterOptions");
		currentSearch.addSearchFilter(new CourseCodeFilter(
				temp.getCheckBox("100Level").isSelected(),
				temp.getCheckBox("200Level").isSelected(),
				temp.getCheckBox("300Level").isSelected(),
				temp.getCheckBox("400Level").isSelected()
		));
		
		currentSearch.addSearchFilter(new CourseCreditHourFilter(
				temp.getCheckBox("0Credits").isSelected(),
				temp.getCheckBox("1Credit").isSelected(),
				temp.getCheckBox("2Credits").isSelected(),
				temp.getCheckBox("3Credits").isSelected(),
				temp.getCheckBox("4Credits").isSelected(),
				temp.getCheckBox("5Credits").isSelected()
		));
		
		String fromChoice = (String)temp.getDropDown("From:").getValue();
		String fromAMorPMChoice = (String)temp.getDropDown("AM or PMFrom:").getValue();
		String toChoice = (String)temp.getDropDown("To:").getValue();
		String toAMorPMChoice = (String)temp.getDropDown("AM or PMTo:").getValue();
		
		if (!fromChoice.equals("From") && 
				!fromAMorPMChoice.equals("AM or PM") &&
				!toChoice.equals("To") &&
				!toAMorPMChoice.equals("AM or PM")) {
			currentSearch.addSearchFilter(new CourseTimeFrameFilter(
				new TimeFrame(
					new Time(Integer.parseInt(fromChoice),0,Time.getAMOrPMFromString(fromAMorPMChoice)),
					new Time(Integer.parseInt(toChoice),0,Time.getAMOrPMFromString(toAMorPMChoice))
				)
			));
		}
		
		String deptChoice = (String)temp.getDropDown("Department").getValue();
		if (!deptChoice.equals("All")) {
			currentSearch.addSearchFilter(new CourseDepartmentFilter(deptChoice));
		}
		
		currentSearch.updateSearch();
		for (int i = 0; i < currentSearch.getSearchResults().size(); i++) {
			searchList.addCourseToDisplay(currentSearch.getSearchResults().get(i));
		}
	}

	public void updateScheduleTitle(String title) {
		getTextField("scheduleTitle").setText(title);
		renameCurrentSchedule(title);
		currentSchedule.setTitle(title);
		saveCurrentSchedule();
	}
	
	public Schedule getCurrentSchedule() {
		return currentSchedule;
	}
	
	private String getFirstUnusedScheduleName() {
		File[] savedSchedules = (new File(SAVE_DIR)).listFiles();
		
		int maxUntitled = -1;
		
		for(File file : savedSchedules) {
			String fileName = file.getName().replace(SAVE_EXT, "");
			if(fileName.startsWith(DEFAULT_SCHEDULE_NAME)) {
				fileName = fileName.replace(DEFAULT_SCHEDULE_NAME, "").trim();
				try {
					int scheduleNumber = Integer.parseInt(fileName);
					if(scheduleNumber > maxUntitled) {
						maxUntitled = scheduleNumber;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		
		return DEFAULT_SCHEDULE_NAME + " " + (maxUntitled+1);
	}
	
	private void loadSchedule(String scheduleTitle, PageManager pageManager) {
		currentSchedule = Schedule.loadSchedule(getSavePath(scheduleTitle));
		getTextField("scheduleTitle").setText(scheduleTitle);
				
		scheduleList.clear();
		for(Course c : currentSchedule.getCourses()) {
			scheduleList.addCourseToDisplay(c);
		}

		searchList.setCurrentSchedule(currentSchedule);
		scheduleList.setCurrentSchedule(currentSchedule);
	}
	
	private void loadMostRecentlyEditedSchedule(PageManager pageManager) {
		File[] savedSchedules = (new File(SAVE_DIR)).listFiles();
		if(savedSchedules.length == 0) {
			currentSchedule = new Schedule(DEFAULT_SCHEDULE_NAME+" 0");
			saveCurrentSchedule();
			loadMostRecentlyEditedSchedule(pageManager);
			return;
		}
		
		int mostRecent = 0;
		long mostRecentTime = -1;
		for(int i=0; i<savedSchedules.length; i++) {
			if(savedSchedules[i].lastModified() > mostRecentTime) {
				mostRecent = i;
				mostRecentTime = savedSchedules[i].lastModified();
			}
		}
		
		loadSchedule(savedSchedules[mostRecent].getName().replace(SAVE_EXT, ""), pageManager);
	}
	
	private void saveCurrentSchedule() {
		currentSchedule.saveSchedule(getSavePath(currentSchedule.getTitle()));
	}
	
	private void renameCurrentSchedule(String newTitle) {
		(new File(getSavePath(currentSchedule.getTitle()))).renameTo(new File(SAVE_DIR+newTitle+SAVE_EXT));
	}
	
	public static String getSavePath(String scheduleTitle) {
		return SAVE_DIR+scheduleTitle+SAVE_EXT;
	}
	
}
