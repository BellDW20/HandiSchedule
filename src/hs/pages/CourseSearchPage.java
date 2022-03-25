package hs.pages;

import java.io.File;
import java.util.function.UnaryOperator;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class CourseSearchPage extends Page {
	
	public static final String SAVE_DIR = "./schedules/";
	public static final String DEFAULT_SCHEDULE_NAME = "Untitled Schedule";
	public static final String SAVE_EXT = ".schd";
	public static final String ACCEPTED_SCHEDULE_TITLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
	
	private CourseSearchList searchList;
	private CourseScheduleList scheduleList;
	
	private CourseDatabase db;
	private CourseSearch currentSearch;
	private Schedule currentSchedule;
	
	private TextField scheduleTitleField;
	private boolean isReplacingTitle = true;
	
	/*
	 * Creates and initializes the the components of the course search page.
	 * Takes in a page manager as a parameter
	 */
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
		
		//sub page contains the list of all courses that are the results of a search
		addSubPage("searchList", searchList, 10, 120, searchList.getW(), searchList.getH(), true);
		
		//sub page contains the courses currently added to the schedule being worked on
		addSubPage("scheduleList", scheduleList, 770, 120, scheduleList.getW(), scheduleList.getH(), true);
		
		//add Load button. This button allows the user to load new schedule.
		addButton("loadButton", 10, 5, 80, 40, "Load", ()->{
			hideComponent(SUB_PAGE, "filterOptions");
		});
		
		//add New button. This button creates a new schedule.
		addButton("newButton", 105, 5, 80, 40, "New", ()->{
			saveCurrentSchedule();
			currentSchedule = new Schedule(getFirstUnusedScheduleName());
			saveCurrentSchedule();
			loadMostRecentlyEditedSchedule(pageManager);
		});
		
		//add text field for editing schedule title
		addTextField("scheduleTitle", 200, 5, 350, 40, "[Enter schedule title here]");
		
		//when the text in the schedule title updates, auto-save the name change
		getTextField("scheduleTitle").textProperty().addListener((obs, oldText, newText) -> {			
			renameCurrentSchedule(newText);
			currentSchedule.setTitle(newText);
			saveCurrentSchedule();
		});
		
		//filter for accepting name changes to the schedule
		UnaryOperator<Change> validTitleFilter = change -> {
			if(isReplacingTitle || !change.isContentChange()) {
				return change;
			}
			
			String newText = change.getControlNewText();
			boolean correctFormat = newText.matches("["+ACCEPTED_SCHEDULE_TITLE_CHARS+"]*");
			boolean alreadyASchedule = (new File(getSavePath(newText))).exists();
			
			if(!correctFormat) {
				Alert renameError = new Alert(AlertType.ERROR);
				renameError.setHeaderText("Schedule Rename Failed");
				renameError.setContentText("Schedule titles cannot contain '"+newText.charAt(newText.length()-1)+"'.");
				renameError.showAndWait();
			} else if(alreadyASchedule) {
				Alert renameError = new Alert(AlertType.ERROR);
				renameError.setHeaderText("Schedule Rename Failed");
				renameError.setContentText("A schedule with the name '"+newText+"' already exists.");
				renameError.showAndWait();
			}
			
			if(correctFormat && !alreadyASchedule) {
				return change;
			}
			
			return null;
		};
		
		
		//applying the text filter to the schedule title field
		getTextField("scheduleTitle").setTextFormatter(new TextFormatter<>(validTitleFilter));
		getTextField("scheduleTitle").focusedProperty().addListener((obs, wasFocused, isFocused) -> {
			if(wasFocused & !isFocused) {
				if(scheduleTitleField.getText().length() == 0) {
					isReplacingTitle = true;
					scheduleTitleField.setText(getFirstUnusedScheduleName());
					isReplacingTitle = false;
				}
			}
        });
		scheduleTitleField = getTextField("scheduleTitle");
		
		//add course search view button. Takes user to the course search page.
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		});
		
		//add calendar view button. Takes user to the calendar view page.
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			CalendarPage calendarPage = (CalendarPage)pageManager.getPage("CalendarPage");
			calendarPage.updateCalendarImage(currentSchedule.getAsCalendar());
			calendarPage.setScheduleTitleField(scheduleTitleField);
			calendarPage.getPane().getChildren().add(scheduleTitleField);
			getPane().getChildren().remove(scheduleTitleField);
			pageManager.goToPage("CalendarPage");
		});
		
		//Add area to enter name to search by and button to complete search
		addTextField("searchField", 10, 70, 500, 40, "Search...");
		getTextField("searchField").setOnAction((event)->{
			performSearch();
		});
		
		/*
		 * Add search button that users click to search for courses when they have 
		 * applied filters and typed in their search.
		 */
		addButton("searchButton", 510, 70, 80, 40, "Search", ()->{
			performSearch(); //method searches the course database for valid courses
		});
		
		//Add ability to see filter options for search
		addButton("filterButton", 600, 70, 80, 40, "Filter", ()->{
			toggleComponentVisibility(SUB_PAGE, "filterOptions");
		});
		
		FilterOptionsPage filterOptionsPage = new FilterOptionsPage(db);
		filterOptionsPage.initializeComponents(pageManager);
		
		//subpage contains the filter options that users can select to further narrow their course search
		addSubPage("filterOptions", filterOptionsPage, 10, 120, FilterOptionsPage.WIDTH, FilterOptionsPage.HEIGHT, false);
		hideComponent(SUB_PAGE, "filterOptions");
		
		loadMostRecentlyEditedSchedule(pageManager);
	}
	
	/*
	 * Method performs a search on the class database and displays all of the classes in
	 * the database that match the parameters of the search and applied filters.
	 */
	private void performSearch() {
		searchList.clear(); //empty the current list of courses so any that don't match the next search are removed
		hideComponent(SUB_PAGE, "filterOptions");
		currentSearch.clearFilters(); //reset the filters
		
		//add filters that have been applied
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
	
	//getter for the current schedule
	//returns the classes added to the current schedule
	public Schedule getCurrentSchedule() {
		return currentSchedule;
	}
	
	//getter for the name of the first schedule created by a user that has not been modified
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
	
	/*
	 * This method loads a schedule. It takes in a title and a page manager, and loads
	 * the schedule in the state it was in at the end of the last session where it was 
	 * being modified.
	 */
	private void loadSchedule(String scheduleTitle, PageManager pageManager) {
		isReplacingTitle = true;
		
		currentSchedule = Schedule.loadSchedule(getSavePath(scheduleTitle));
		getTextField("scheduleTitle").setText(scheduleTitle);
				
		scheduleList.clear();
		for(Course c : currentSchedule.getCourses()) {
			scheduleList.addCourseToDisplay(c);
		}

		searchList.setCurrentSchedule(currentSchedule);
		scheduleList.setCurrentSchedule(currentSchedule);
		
		isReplacingTitle = false;
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
