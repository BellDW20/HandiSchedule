package hs.pages;

import hs.core.CourseDatabase;
import hs.core.Schedule;
import hs.pages.subPages.CourseScheduleList;
import hs.pages.subPages.CourseSearchList;
import hs.search.CourseCodeFilter;
import hs.search.CourseCreditHourFilter;
import hs.search.CourseNameFilter;
import hs.search.CourseSearch;
import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class CourseSearchPage extends Page {
	
	private CourseSearchList searchList;
	private CourseScheduleList scheduleList;
	
	private CourseDatabase db;
	private CourseSearch currentSearch;
	private Schedule currentSchedule;

	@Override
	public void initializeComponents(PageManager pageManager) {
		//Load course database
		db = CourseDatabase.loadFromFile("CourseDB_CSV.csv");
		currentSearch = new CourseSearch(db);
		
		//Initialize search course list and schedule course list
		currentSchedule = new Schedule();
		searchList = new CourseSearchList(currentSchedule);
		scheduleList = new CourseScheduleList(currentSchedule);
		searchList.setScheduleList(scheduleList);
		addSubPage("searchList", searchList, 10, 120, searchList.getW(), searchList.getH(), true);
		addSubPage("scheduleList", scheduleList, 770, 120, scheduleList.getW(), scheduleList.getH(), true);
		
		//add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", ()->{
			hideComponent(SUB_PAGE, "filterOptions");
		});
		
		//add New button
		addButton("newButton", 105, 5, 80, 40, "New", null);
		
		//add text field for editing schedule title
		addTextField("scheduleTitle", 200, 5, 350, 40, "[Untitled Schedule]");
		
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
		addButton("searchButton", 510, 70, 40, 40, "Search", ()->{
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
			
			//TODO: add in rest filters HERE
			currentSearch.updateSearch();
			for (int i = 0; i < currentSearch.getSearchResults().size(); i++) {
				searchList.addCourseToDisplay(currentSearch.getSearchResults().get(i));
			}
		});
		
		//Add ability to see filter options for search
		addButton("filterButton", 560, 70, 40, 40, "Filter", ()->{
			toggleComponentVisibility(SUB_PAGE, "filterOptions");
		});
		
		FilterOptionsPage filterOptionsPage = new FilterOptionsPage();
		filterOptionsPage.initializeComponents(pageManager);
		addSubPage("filterOptions", filterOptionsPage, 10, 120, FilterOptionsPage.WIDTH, FilterOptionsPage.HEIGHT, false);
		hideComponent(SUB_PAGE, "filterOptions");
	}

}
