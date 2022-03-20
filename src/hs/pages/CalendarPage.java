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
		
		addTextField("scheduleTitle", 200, 5, 350, 40, "[Enter schedule title here]");
		getTextField("scheduleTitle").textProperty().addListener((obs, oldText, newText) -> {
			((CourseSearchPage)pageManager.getPage("CourseSearch")).updateScheduleTitle(newText);
		});
		
		// add courseSearch view button
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		
		});
		// add calendar View button
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			pageManager.goToPage("CalendarPage");
		});
		
		updateScheduleTitle(
			((CourseSearchPage)pageManager.getPage("CourseSearch")).getCurrentSchedule().getTitle()
		);
	}
	
	public void updateScheduleTitle(String title) {
		getTextField("scheduleTitle").setText(title);
	}

}
