package hs.pages;

import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class CalendarPage extends Page {

	@Override
	public void initializeComponents(PageManager pageManager) {
		// add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", null);
		
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
		

}

}
