package hs.pages;

import java.awt.image.BufferedImage;
import hs.pages.subPages.CalendarView;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.control.TextField;

public class CalendarPage extends Page {

	private CalendarView calendarView;
	private TextField scheduleTitleField;
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		// add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", null);
		
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
		
		calendarView = new CalendarView();
		addSubPage("calendarView", calendarView, 240, 60, 800, 600, false);
	}

	public void updateCalendarImage(BufferedImage img) {
		calendarView.updateCalendarImage(img);
	}
	
	public void setScheduleTitleField(TextField scheduleTitleField) {
		this.scheduleTitleField = scheduleTitleField;
	}
	
}
