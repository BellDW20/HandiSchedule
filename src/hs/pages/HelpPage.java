package hs.pages;

import hs.simplefx.Page;
import hs.simplefx.PageManager;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class HelpPage extends Page {

	private Text getStartedText;
	private Text getStartedText2;
	private Rectangle outline;
	private Button backButton;
	
	private ImageView loginShot;
	private Button loginHelpButton;
	private Text[] loginLabels;
	private Text[] loginDesc;
	
	private ImageView courseSearchShot;
	private Button courseSearchHelpButton;
	private Text[] cspLabels;
	private Text[] cspDesc;
	
	private ImageView calendarShot;
	private Button calendarhHelpButton;
	private Text[] calendarLabels;
	private Text[] calendarDesc;
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		getStartedText = addLabel("gettingStartedText", 10, 24, "Getting Started with HandiSchedule...");
		getStartedText.setFont(Font.font("Veranda", FontPosture.ITALIC, 24.0));
		getStartedText2 = addLabel("getStartedText2", 10, 48, 
				"Handischedule is a useful tool for creating course schedules and resolving conflicting schedules.\n"
			  + "If you'd like to know how to use any part of the program, simply click on one of the buttons below\n"
			  + "to see more information."
		);
		outline = drawRect(180, 180, 800, 450);
		outline.setFill(null);
		
		loginHelpButton = addButton("loginHelpButton", 10, 128, 160, 40, "Logging In", ()->{
			showLoginHelp();
		});
		
		courseSearchHelpButton = addButton("courseSearchHelpButton", 10, 178, 160, 40, "Searching for Courses", ()->{
			showCourseSearchHelp();
		});
		
		courseSearchHelpButton = addButton("calendarHelpButton", 10, 228, 160, 40, "The Calendar", ()->{
			showCalendarHelp();
		});
		
		backButton = addButton("backButton", 1150, 10, 120, 40, "Back to Login", ()->{
			pageManager.goToPage("LoginPage");
		});
		
		createScreenshots(pageManager);
		createLabels(pageManager);
		
		setLoginVisibility(false);
		setCSPVisibility(false);
		setCalendarVisibility(false);
	}
	
	private void showLoginHelp() {
		setLoginVisibility(true);
		setCSPVisibility(false);
		setCalendarVisibility(false);
	}
	
	private void showCourseSearchHelp() {
		setLoginVisibility(false);
		setCSPVisibility(true);
		setCalendarVisibility(false);
	}
	
	private void showCalendarHelp() {
		setLoginVisibility(false);
		setCSPVisibility(false);
		setCalendarVisibility(true);
	}
	
	private void setLoginVisibility(boolean visible) {
		loginShot.setVisible(visible);
		for(Text l : loginLabels) {
			l.setVisible(visible);
		}
		for(Text l : loginDesc) {
			l.setVisible(visible);
		}
	}
	
	private void setCSPVisibility(boolean visible) {
		courseSearchShot.setVisible(visible);
		for(Text l : cspLabels) {
			l.setVisible(visible);
		}
		for(Text l : cspDesc) {
			l.setVisible(visible);
		}
	}
	
	private void setCalendarVisibility(boolean visible) {
		calendarShot.setVisible(visible);
		for(Text l : calendarLabels) {
			l.setVisible(visible);
		}
		for(Text l : calendarDesc) {
			l.setVisible(visible);
		}
	}
	
	private void createScreenshots(PageManager pageManager) {
		loginShot = generateScreenshot(pageManager.getPage("LoginPage"));
		courseSearchShot = generateScreenshot(pageManager.getPage("CourseSearch"));
		calendarShot = generateScreenshot(pageManager.getPage("CalendarPage"));
	}
	
	private void createLabels(PageManager pageManager) {
		//Login labels
		loginLabels = new Text[4];
		loginLabels[0] = addLabel("loginL1", 180+310, 180+205, "1");
		loginLabels[1] = addLabel("loginL2", 180+310, 180+245, "2");
		loginLabels[2] = addLabel("loginL3", 180+360, 180+305, "3");
		loginLabels[3] = addLabel("loginL4", 180+430, 180+305, "4");
		
		loginDesc = new Text[4];
		loginDesc[0] = addLabel("loginD1", 1000, 180, 
				"1 - A username can be put in this field by clicking\n"
			  + "	 on it and typing. Used for both loggin in and\n"
			  + "	 registering as a new user"
		);
		loginDesc[1] = addLabel("loginD2", 1000, 240,
				"2 - A password can be put in this field by clicking\n"
			  + "	 on it and typing. Used for both logging in and\n"
			  + "	 registering as a new user"
		);
		loginDesc[2] = addLabel("loginD3", 1000, 300,
				"3 - Clicking here will attempt to login with the\n"
			  + "	 username and password provided in the\n"
			  + "	 above fields."
		);
		loginDesc[3] = addLabel("loginD4", 1000, 360,
				"4 - Clicking here will attempt to create a new\n"
			  + "	 account with the username and password\n"
			  + "	 provided in the above fields."
		);
		
		//Course search labels
		cspLabels = new Text[10];
		cspLabels[0] = addLabel("cspL0", 180+30, 180+28, "1");
		cspLabels[1] = addLabel("cspL1", 180+85, 180+28, "2");
		cspLabels[2] = addLabel("cspL2", 180+140, 180+28, "3");
		cspLabels[3] = addLabel("cspL3", 180+280, 180+28, "4");
		cspLabels[4] = addLabel("cspL4", 180+670, 180+28, "5");
		cspLabels[5] = addLabel("cspL5", 180+755, 180+28, "6");
		cspLabels[6] = addLabel("cspL6", 180+155, 180+68, "7");
		cspLabels[7] = addLabel("cspL7", 180+340, 180+68, "8");
		cspLabels[8] = addLabel("cspL8", 180+395, 180+68, "9");
		cspLabels[9] = addLabel("cspL9", 180+395, 180+400, "10");
		
		cspDesc = new Text[10];
		cspDesc[0] = addLabel("cspD0", 1000, 120, 
				"1 - Clicking here will save your schedule and\n"
			  + "	 log you out of your account, bringing you\n"
			  + "	 back to the login page."
		);
		cspDesc[1] = addLabel("cspD1", 1000, 180,
				"2 - Clicking here will bring you to a page\n"
			  + "	 from which you can load any of the schedules\n"
			  + "	 you had worked on previously."
		);
		cspDesc[2] = addLabel("cspD2", 1000, 240,
				"3 - Clicking here will save your schedule and\n"
			  + "	 open an new, untitled, blank schedult to\n"
			  + "	 work on."
		);
		cspDesc[3] = addLabel("cspD3", 1000, 300,
				"4 - Clicking here will & typing will change the\n"
			  + "	 name of the schedule you are currently\n"
			  + "	 working on, autosaving these changes."
		);
		cspDesc[4] = addLabel("cspD4", 1000, 360,
				"5 - Clicking here from any page will bring\n"
			  + "	 you back to this page - the course search\n"
			  + "	 page."
		);
		cspDesc[5] = addLabel("cspD5", 1000, 420,
				"6 - Clicking here from any page will bring\n"
			  + "	 you the calendar page.\n"
		);
		cspDesc[6] = addLabel("cspD6", 1000, 465,
				"7 - Clicking here, typing, and pressing enter\n"
			  + "	 or clicking the \"Search\" button will\n"
			  + " 	 search for courses with a similar name\n"
			  + "	 or course code."
		);
		cspDesc[7] = addLabel("cspD7", 1000, 540,
				"8 - Clicking here will perform a search\n"
			+   "	 for courses based on the current search\n"
			+   "	 term and the set filters"
		);
		cspDesc[8] = addLabel("cspD8", 1000, 600,
				"9 - Clicking here will toggle a list of filters\n"
			+   "	 which can be used to narrow your search.\n"
			+   "	 These filters can be removed at any time."
		);
		cspDesc[9] = addLabel("cspD9", 1000, 660,
				"10 - Clicking here will delete the schedule that\n"
			+   "	  is currently being edited."
		);
		
		//Calendar Labels
		calendarLabels = new Text[1];
		calendarLabels[0] = addLabel("clndrL0", 180+400, 180+225, "1");
		
		calendarDesc = new Text[2];
		calendarDesc[0] = addLabel("clndrD0", 1000, 180, 
				"1 - When you switch to the calendar view,\n"
			+ 	"	 a calendar containing a weekly schedule\n"
			+ 	"	 will appear here.");
		calendarDesc[calendarDesc.length-1] = addLabel("clndrDNote", 10, 690, 
				"Note: The buttons along the top function the same as they do in"
			+   "the course search page. Check the help section for the course"
			+   "course search page for more details."
		);
	}
	
	private ImageView generateScreenshot(Page page) {
		Image img = page.getPane().snapshot(new SnapshotParameters(), null);
		ImageView view = new ImageView(img);
		view.setFitWidth(800);
		view.setPreserveRatio(true);
		view.setSmooth(true);
		view.setCache(true);
		view.setLayoutX(180);
		view.setLayoutY(180);
		getPane().getChildren().add(view);
		return view;
	}
	
}
