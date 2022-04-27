package hs.pages;

import java.awt.image.BufferedImage;
import java.io.File;

import hs.pages.subPages.CalendarView;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class CalendarPage extends Page {

	private CalendarView calendarView;
	private TextField scheduleTitleField;
	private Button resolveButton, pdfButton;
	
	/*
	 * Creates/initializes the components of the calendar page GUI
	 */
	@Override
	public void initializeComponents(PageManager pageManager) {
		// add logout button
		addButton("logoutButton", 10, 5, 80, 40, "Logout", ()->{
			LoginPage.loggedInUser = null;
			LoginPage.loggedInPassword = null;
			putBackCourseTitle(pageManager);
			pageManager.goToPage("LoginPage");
			((CourseSearchPage)pageManager.getPage("CourseSearch")).resetGUI();
		});
		
		// add Load button
		addButton("loadButton", 100, 5, 80, 40, "Load", ()->{
			((LoadPage)pageManager.getPage("LoadPage")).refresh(pageManager, "CalendarPage");
			pageManager.goToPage("LoadPage");
		});
		
		// add New button
		addButton("newButton", 190, 5, 80, 40, "New", ()->{
			CourseSearchPage csp = ((CourseSearchPage)pageManager.getPage("CourseSearch"));
			csp.createNewSchedule(pageManager);
			updateCalendarImage(csp.getCurrentSchedule().getAsCalendar());
		});
		
		// add button to switch to the course search page
		addButton("courseSearchSwitchButton", 1020, 5, 120, 40, "Class Search", () -> {
			//JavaFx workaround ;)
			putBackCourseTitle(pageManager);
			
			//then go to course search page
			pageManager.goToPage("CourseSearch");
		
		});
		
		// add button to switch to the calendar page
		addButton("calendarSwitchButton", 1150, 5, 120, 40, "Calendar", () -> {
			pageManager.goToPage("CalendarPage");
		});
		
		// add resolve schedule button
		resolveButton = addButton("resolveScheduleButton", 645, 670, 200, 40, "Resolve Schedule", ()->{
			((CourseSearchPage)pageManager.getPage("CourseSearch")).resolveCurrentSchedule(this);
		});
		
		// add resolve schedule button
		pdfButton = addButton("printScheduleButton", 435, 670, 200, 40, "Print to PDF", ()->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Print to PDF");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(null);
			
			if (file != null) {
				((CourseSearchPage)pageManager.getPage("CourseSearch")).getCurrentSchedule().printAsPdf(file.getAbsolutePath());
            }
		});
		
		//creates a new calendar view that will be displayed on the calendar page
		calendarView = new CalendarView();
		addSubPage("calendarView", calendarView, 240, 60, 800, 600, false);
	}

	//updates the calendar view with the newest calendar image based on the current schedule being worked on
	public void updateCalendarImage(BufferedImage img) {
		calendarView.updateCalendarImage(img);
	}
	
	public void updateResolveVisuals(boolean show) {
		resolveButton.setVisible(show);
		if(show) {
			pdfButton.setLayoutX(435);
		} else {
			pdfButton.setLayoutX(540);
		}
	}
	
	//setter for the schedule title field of the calendar page.
	public void setScheduleTitleField(TextField scheduleTitleField) {
		this.scheduleTitleField = scheduleTitleField;
	}
	
	private void putBackCourseTitle(PageManager pageManager) {
		CourseSearchPage courseSearchPage = (CourseSearchPage)pageManager.getPage("CourseSearch");
		courseSearchPage.getPane().getChildren().add(scheduleTitleField);
		getPane().getChildren().remove(scheduleTitleField);
	}
	
}
