package hs.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import hs.pages.CalendarPage;
import hs.pages.CourseSearchPage;
import hs.pages.LoadPage;
import hs.pages.LoginPage;
import hs.simplefx.PageManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches HandiSchedule as a JavaFX application, setting up
 * each page which the user can navigate to.
 * @author Amy Cunningham, Levi Conrad, and Douglas Bell
 */

public class Launcher extends Application {
	
	public static void main(String[] args) {
		launch(); // Default to start any JavaFX application
		//NOTE: start(Stage stage) will be called by JavaFX on startup
	}

	@Override
	public void start(Stage stage) throws Exception {
		PageManager pageManager = new PageManager(stage, 1280, 720);
		
		// Store location of css file to use with each separate scene (page)
//		String css = this.getClass().getResource("PageStyling.css").toExternalForm();
//		HashMap<String, Scene> pages = pageManager.getPageScenes();
//        Iterator<Entry<String, Scene>> hmIterator = pages.entrySet().iterator();
//
//        // Iterate through each page and add css functionality
//		while (hmIterator.hasNext()) {
//            Map.Entry<String, Scene> hmElement = (Map.Entry<String, Scene>)hmIterator.next();
//            hmElement.getValue().getStylesheets().add(css);
//		}
		
		//Create and set up the login page
		LoginPage loginPage = new LoginPage();
		loginPage.initializeComponents(pageManager);
		pageManager.addPageToProgram(loginPage, "LoginPage");
		
		//Create and set up the course search page
		CourseSearchPage csp = new CourseSearchPage();
		csp.initializeComponents(pageManager);
		pageManager.addPageToProgram(csp, "CourseSearch");
		
		//Create and set up the calendar view page
		CalendarPage cp = new CalendarPage();
		cp.initializeComponents(pageManager);
		pageManager.addPageToProgram(cp, "CalendarPage");
		
		//Create and set up the page for loading schedules
		LoadPage lp = new LoadPage();
		lp.initializeComponents(pageManager);
		pageManager.addPageToProgram(lp,  "LoadPage");
		
		//Start from the login page
		pageManager.goToPage("LoginPage");
		
		//Give the window a title and don't allow resizing the window
		stage.setTitle("HandiSchedule");
		stage.setResizable(false);
	}
	
}
