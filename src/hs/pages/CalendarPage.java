package hs.pages;

import java.awt.image.BufferedImage;
import hs.pages.subPages.CalendarView;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.control.TextField;

public class CalendarPage extends Page {

	private CalendarView calendarView;
	private TextField scheduleTitleField;
	
	
	/*
	 * Creates/initializes the components of the calendar page GUI
	 */
	@Override
	public void initializeComponents(PageManager pageManager) {
		// add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", ()->{
			((LoadPage)pageManager.getPage("LoadPage")).refresh(pageManager);
			pageManager.goToPage("LoadPage");
		});
		
		// add New button
		addButton("newButton", 105, 5, 80, 40, "New", null);
		
		// add courseSearch view button
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			CourseSearchPage courseSearchPage = (CourseSearchPage)pageManager.getPage("CourseSearch");
			courseSearchPage.getPane().getChildren().add(scheduleTitleField);
			getPane().getChildren().remove(scheduleTitleField);
			pageManager.goToPage("CourseSearch");
		
		});
		// add calendar View button
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			pageManager.goToPage("CalendarPage");
		});
		
		//creates a new calendar view that will be displayed on the calendar page
		calendarView = new CalendarView();
		addSubPage("calendarView", calendarView, 240, 60, 800, 600, false);
	}

	//updates the calendar view with the newest image based on the current schedule being worked on
	public void updateCalendarImage(BufferedImage img) {
		calendarView.updateCalendarImage(img);
	}
	
	//setter for the schedule title field of the calendar page.
	public void setScheduleTitleField(TextField scheduleTitleField) {
		this.scheduleTitleField = scheduleTitleField;
	}
	
}
