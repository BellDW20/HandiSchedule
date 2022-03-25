package hs.core;

import hs.pages.CalendarPage;
import hs.pages.CourseSearchPage;
import hs.pages.LoadPage;
import hs.simplefx.PageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		PageManager pageManager = new PageManager(stage, 1280, 720);
		
		CourseSearchPage csp = new CourseSearchPage(); //CourseSearch
		csp.initializeComponents(pageManager);
		pageManager.addPageToProgram(csp, "CourseSearch");
		
		CalendarPage cp = new CalendarPage(); //CalendarPage
		cp.initializeComponents(pageManager);
		pageManager.addPageToProgram(cp, "CalendarPage");
		
		LoadPage lp = new LoadPage(); //LoadPage
		lp.initializeComponents(pageManager);
		pageManager.addPageToProgram(lp,  "LoadPage");
		
		pageManager.goToPage("CourseSearch");
		stage.setTitle("HandiSchedule");
		stage.setResizable(false);
	}
	
}
