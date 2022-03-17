package hs.pages;

import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class CourseSearchPage extends Page {
	public CourseSearchPage(PageManager pageManager, String name) {
		super(pageManager, name);
	}

	@Override
	public void initializeComponents(PageManager pageManager) {
		// add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", ()->{
			hideComponent(SUB_PAGE, "filterOptions");
		});
		
		// add New button
		addButton("newButton", 105, 5, 80, 40, "New", null);
		
		addTextField("scheduleTitle", 200, 5, 350, 40, "[Untitled Schedule]");
		
		// add courseSearch view button
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		
		});
		// add calendar View button
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			pageManager.goToPage("CalendarPage");
		});
		
		addTextField("searchField", 10, 70, 500, 40, "Search...");
		addButton("searchButton", 510, 70, 40, 40, "Search", ()->{
			hideComponent(SUB_PAGE, "filterOptions");
		});
		
		addButton("filterButton", 560, 70, 40, 40, "Filter", ()->{
			toggleComponentVisibility(SUB_PAGE, "filterOptions");
		});
		
		addSubPage("filterOptions", new FilterOptionsPage(pageManager), 10, 120, FilterOptionsPage.WIDTH, FilterOptionsPage.HEIGHT, false);
		hideComponent(SUB_PAGE, "filterOptions");
		//this.addSubPage("searchList", null, 0, 200, 400, 400, false);
		
	}

}
