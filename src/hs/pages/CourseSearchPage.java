package hs.pages;

import java.io.File;
import java.util.function.UnaryOperator;
import hs.core.Course;
import hs.core.CourseDatabase;
import hs.core.Launcher;
import hs.core.Schedule;
import hs.core.Time;
import hs.core.TimeFrame;
import hs.pages.subPages.CourseScheduleList;
import hs.pages.subPages.CourseSearchList;
import hs.pages.subPages.FilterOptionsPage;
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
	
	public static final String SAVE_DIR = "./schedules/"; //Directory to which schedules are saved
	public static final String DEFAULT_SCHEDULE_NAME = "Untitled Schedule"; //Default prefix for schedule titles
	public static final String SAVE_EXT = ".schd"; //Extension for schedule files
	
	//The types of characters accepted in the schedule's title
	public static final String ACCEPTED_SCHEDULE_TITLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
	
	private static CourseSearchList searchList; //The viewable list of all currently searched courses
	private static CourseScheduleList scheduleList; //The viewable list of courses in the current schedule
	
	private CourseDatabase db; //Database containing all courses/departments
	private CourseSearch currentSearch; //The current course search query and its results
	private Schedule currentSchedule; //The schedule currently being edited
	
	private TextField scheduleTitleField; //The text field for editing the schedule's title
	private boolean isReplacingTitle = true; //Magic
	
	/*
	 * Creates and initializes the the components of the course search page.
	 * Takes in a page manager as a parameter
	 */
	@Override
	public void initializeComponents(PageManager pageManager) {	
		//Load course database
		db = CourseDatabase.loadFromFile("./CourseDB_CSV.csv");
		currentSearch = new CourseSearch(db);
		
		//Initialize search course list and schedule course list
		searchList = new CourseSearchList(db.getCopyOfAllCourses());
		searchList.initializeComponents(pageManager);
		searchList.setCourseSearchPage(this);
		scheduleList = new CourseScheduleList(db.getCopyOfAllCourses());
		scheduleList.setCourseSearchPage(this);
		searchList.setScheduleList(scheduleList);
		
		//sub page contains the list of all courses that are the results of a search
		addSubPage("searchList", searchList, 10, 120, searchList.getW(), searchList.getH(), true);
		
		//sub page contains the courses currently added to the schedule being worked on
		addSubPage("scheduleList", scheduleList, 770, 120, scheduleList.getW(), scheduleList.getH(), true);
		
		//add Delete button. This button allows the user to delete the current schedule
		addButton("deleteButton", 540, 670, 200, 40, "Delete Schedule", ()->{
			deleteCurrentSchedule();
			loadMostRecentlyEditedSchedule(pageManager);
		});
		
		addButton("copyButton", 540, 620, 200, 40, "Copy Schedule", ()->{
			immediatelySaveCurrentSchedule();
			
			Schedule temp = new Schedule(currentSchedule);

			
			currentSchedule = temp;
			immediatelySaveCurrentSchedule();
			
			//load the new schedule
			loadMostRecentlyEditedSchedule(pageManager);
		});
		
		addLabel("scheduleCredits", 770, 100, "Current Credits: 0");
		
		//add Logout button. This button allows the user to logout of their account
		//and return to the login screen
		addButton("logoutButton", 10, 5, 80, 40, "Logout", ()->{
			LoginPage.loggedInUser = null;
			LoginPage.loggedInPassword = null;
			pageManager.goToPage("LoginPage");
			resetGUI();
		});
		
		//add Load button. This button allows the user to load new schedule.
		addButton("loadButton", 100, 5, 80, 40, "Load", ()->{
			((LoadPage)pageManager.getPage("LoadPage")).refresh(pageManager, "CourseSearch");
			pageManager.goToPage("LoadPage");
		});
		
		//add New button. This button creates a new schedule.
		addButton("newButton", 190, 5, 80, 40, "New", ()->{
			immediatelySaveCurrentSchedule();
			
			//make a new schedule and save it
			currentSchedule = new Schedule(getFirstUnusedScheduleName());
			immediatelySaveCurrentSchedule();
			
			//load the new schedule
			loadMostRecentlyEditedSchedule(pageManager);

			updateScheduleCredits();
		});
		
		
		//add text field for editing schedule title
		addTextField("scheduleTitle", 280, 5, 350, 40, "[Enter schedule title here]");
		
		//when the text in the schedule title updates, auto-save the name change
		getTextField("scheduleTitle").textProperty().addListener((obs, oldText, newText) -> {			
			renameCurrentSchedule(newText);
			currentSchedule.setTitle(newText);
		});
		
		//filter for accepting name changes to the schedule
		UnaryOperator<Change> validTitleFilter = change -> {
			//If we're simply replacing the title during schedule loading
			//or if the title hasn't changed, accept the change
			if(isReplacingTitle || !change.isContentChange()) {
				return change;
			}
			
			//Otherwise, make sure it matches the correct format
			//and would not overwrite an existing schedule
			String newText = change.getControlNewText();
			boolean correctFormat = newText.matches("["+ACCEPTED_SCHEDULE_TITLE_CHARS+"]*");
			boolean alreadyASchedule = (new File(getSavePath(newText))).exists();
			
			//Notify the user if their rename is invalid and why
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
			
			//If the change is valid, accept it
			if(correctFormat && !alreadyASchedule) {
				return change;
			}
			
			//Otherwise, reject it
			return null;
		};
		
		
		//applying the text filter to the schedule title field
		getTextField("scheduleTitle").setTextFormatter(new TextFormatter<>(validTitleFilter));
		getTextField("scheduleTitle").focusedProperty().addListener((obs, wasFocused, isFocused) -> {
			//If the title text field lost focus and was empty,
			//replace it with a default schedule title
			if(wasFocused & !isFocused) {
				if(scheduleTitleField.getText().length() == 0) {
					isReplacingTitle = true;
					scheduleTitleField.setText(getFirstUnusedScheduleName());
					isReplacingTitle = false;
				}
				Launcher.logger.changedScheduleName(currentSchedule);
				asynchronouslySaveCurrentSchedule();
			}
        });
		scheduleTitleField = getTextField("scheduleTitle");
		
		//add course search view button. Takes user to the course search page.
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		});
		
		//add calendar view button. Takes user to the calendar view page.
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			//JavaFX workaround
			CalendarPage calendarPage = (CalendarPage)pageManager.getPage("CalendarPage");
			calendarPage.updateCalendarImage(currentSchedule.getAsCalendar());
			calendarPage.setScheduleTitleField(scheduleTitleField);
			calendarPage.getPane().getChildren().add(scheduleTitleField);
			getPane().getChildren().remove(scheduleTitleField);
			
			//Go to calendar page
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
	}
	
	public void resetGUI() {
		searchList.clear();
		getTextField("searchField").clear();
		hideComponent(SUB_PAGE, "filterOptions");
	}
	
	/*
	 * Method performs a search on the class database and displays all of the classes in
	 * the database that match the parameters of the search and applied filters.
	 */
	private void performSearch() {
		searchList.clear(); //empty the current list of courses so any that don't match the next search are removed
		hideComponent(SUB_PAGE, "filterOptions");
		currentSearch.clearFilters(); //reset the filters
		
		//Add the filter for what was searched in the search text field
		currentSearch.addSearchFilter(new CourseNameFilter(getTextField("searchField").getText()));
		
		Page temp = getSubPage("filterOptions");
		
		//Create course code filter
		currentSearch.addSearchFilter(new CourseCodeFilter(
				temp.getCheckBox("100Level").isSelected(),
				temp.getCheckBox("200Level").isSelected(),
				temp.getCheckBox("300Level").isSelected(),
				temp.getCheckBox("400Level").isSelected()
		));
		
		//Create course credit hour filter
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
		
		//If the user selected a time frame to filter by...
		if (!fromChoice.equals("From") && 
				!fromAMorPMChoice.equals("AM or PM") &&
				!toChoice.equals("To") &&
				!toAMorPMChoice.equals("AM or PM")) {
			
			//create the time frame filter
			currentSearch.addSearchFilter(new CourseTimeFrameFilter(
				new TimeFrame(
					new Time(Integer.parseInt(fromChoice),0,Time.getAMOrPMFromString(fromAMorPMChoice)),
					new Time(Integer.parseInt(toChoice),0,Time.getAMOrPMFromString(toAMorPMChoice))
				)
			));
		}
		
		//If the user selected a department to filter by,
		//create the department filter
		String deptChoice = (String)temp.getDropDown("Department").getValue();
		if (!deptChoice.equals("All")) {
			currentSearch.addSearchFilter(new CourseDepartmentFilter(deptChoice));
		}
		
		//Perform the search and display it
		currentSearch.updateSearch();
		searchList.addCoursesToDisplay(currentSearch.getSearchResults());
	}
	
	//getter for the current schedule
	//returns the classes added to the current schedule
	public Schedule getCurrentSchedule() {
		return currentSchedule;
	}
	
	public void resolveCurrentSchedule(CalendarPage calendarPage) {
		currentSchedule.resolveSchedule(db);
		scheduleList.clear();
		scheduleList.addCoursesToDisplay(currentSchedule.getCourses());
		calendarPage.updateCalendarImage(currentSchedule.getAsCalendar());
		asynchronouslySaveCurrentSchedule();
		updateScheduleCredits();
		
	}
	
	//getter for the name of the first schedule created by a user that has not been modified
	private String getFirstUnusedScheduleName() {
		//Make sure schedule save folder exists
		File[] savedSchedules = (new File(getSaveDirPath())).listFiles();
		
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
	public boolean loadSchedule(String scheduleTitle, PageManager pageManager) {
		isReplacingTitle = true;
		
		Schedule potentialSchedule = LoginPage.userDatabase.loadEncryptedSchedule(
				getSavePath(scheduleTitle), 
				LoginPage.loggedInUser, LoginPage.loggedInPassword
		);
		if(potentialSchedule == null) {
			isReplacingTitle = false;
			return false;
		}
		currentSchedule = potentialSchedule;
		getTextField("scheduleTitle").setText(scheduleTitle);
				
		scheduleList.clear();
		for(Course c : currentSchedule.getCourses()) {
			scheduleList.addCourseToDisplay(c);
		}

		updateScheduleCredits();
		isReplacingTitle = false;
		return true;
	}
	
	private void updateScheduleCredits() {
		getLabel("scheduleCredits").setText("Current Credits: " + currentSchedule.getCreditHours());
	}
	
	/**
	 * Finds the most recently modified schedule and loads it.
	 * @param pageManager PageManager to use to properly load schedule
	 */
	public void loadMostRecentlyEditedSchedule(PageManager pageManager) {
		File[] savedSchedules = (new File(getSaveDirPath())).listFiles();
		
		//If there are no saved schedules...
		if(savedSchedules.length == 0) {
			//Make a new one and load it
			currentSchedule = new Schedule(DEFAULT_SCHEDULE_NAME+" 0");
			immediatelySaveCurrentSchedule();
			loadMostRecentlyEditedSchedule(pageManager);

			updateScheduleCredits();
			return;
		}
		
		//Otherwise, find the most recently modified...
		int mostRecent = 0;
		long mostRecentTime = -1;
		for(int i=0; i<savedSchedules.length; i++) {
			if(savedSchedules[i].lastModified() > mostRecentTime) {
				mostRecent = i;
				mostRecentTime = savedSchedules[i].lastModified();
			}
		}
		
		//And load it
		if(!loadSchedule(savedSchedules[mostRecent].getName().replace(SAVE_EXT, ""), pageManager)) {
			//If it fails since it isn't your schedule, just open a blank one
			currentSchedule = new Schedule(getFirstUnusedScheduleName());
			immediatelySaveCurrentSchedule();
			loadMostRecentlyEditedSchedule(pageManager);

			updateScheduleCredits();
		}
	}
	
	/**
	 * Saves the current schedule to its respective file
	 */
	public void asynchronouslySaveCurrentSchedule() {
		Thread thread = new Thread() {
			@Override 
			public void run() {
				synchronized(currentSchedule) {
					immediatelySaveCurrentSchedule();
				}
			}
		};
		thread.start();
	}
	
	public void immediatelySaveCurrentSchedule() {
		LoginPage.userDatabase.saveEncryptedSchedule(
			currentSchedule, getSavePath(currentSchedule.getTitle()), 
			LoginPage.loggedInUser, LoginPage.loggedInPassword
		);
	}
	
	public void deleteCurrentSchedule() {
		(new File(getSavePath(currentSchedule.getTitle()))).delete();
	}
	
	/**
	 * Renames the current schedule, affecting its associated file
	 * @param newTitle The new title of the schedule
	 */
	private void renameCurrentSchedule(String newTitle) {
		(new File(getSavePath(currentSchedule.getTitle()))).renameTo(new File(getSavePath(newTitle)));
	}
	
	/**
	 * Used for generating the default save path for a schedule
	 * @param scheduleTitle Title of the schedule
	 * @return The default save path for the schedule
	 */
	public static String getSavePath(String scheduleTitle) {
		return getSaveDirPath()+scheduleTitle+SAVE_EXT;
	}
	
	public static String getSaveDirPath() {
		return SAVE_DIR+LoginPage.loggedInUser+"/";
	}
	
}
