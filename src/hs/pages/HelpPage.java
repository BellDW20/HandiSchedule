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
	
	private ImageView loginShot;
	private Button loginHelpButton;
	private Text[] loginLabels;
	private Text[] loginDesc;
	
	private ImageView courseSearchShot;
	private Button courseSearchHelpButton;
	
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
		
		createScreenshots(pageManager);
		createLabels(pageManager);
		
		hideLoginHelp();
		hideCourseSearchHelp();
	}
	
	private void showLoginHelp() {
		hideCourseSearchHelp();
		loginShot.setVisible(true);
		for(Text l : loginLabels) {
			l.setVisible(true);
		}
		for(Text l : loginDesc) {
			l.setVisible(true);
		}
	}
	
	private void hideLoginHelp() {
		loginShot.setVisible(false);
		for(Text l : loginLabels) {
			l.setVisible(false);
		}
		for(Text l : loginDesc) {
			l.setVisible(false);
		}
	}
	
	private void showCourseSearchHelp() {
		hideLoginHelp();
		courseSearchShot.setVisible(true);
	}
	
	private void hideCourseSearchHelp() {
		courseSearchShot.setVisible(false);
	}
	
	private void createScreenshots(PageManager pageManager) {
		loginShot = generateScreenshot(pageManager.getPage("LoginPage"));
		courseSearchShot = generateScreenshot(pageManager.getPage("CourseSearch"));
	}
	
	private void createLabels(PageManager pageManager) {
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
		loginDesc[1] = addLabel("loginD2", 1000, 280,
				"2 - A password can be put in this field by clicking\n"
			  + "	 on it and typing. Used for both logging in and\n"
			  + "	 registering as a new user"
		);
		loginDesc[2] = addLabel("loginD3", 1000, 380,
				"3 - Clicking here will attempt to login with the\n"
			  + "	 username and password provided in the\n"
			  + "	 above fields."
		);
		loginDesc[3] = addLabel("loginD4", 1000, 480,
				"4 - Clicking here will attempt to create a new\n"
			  + "	 account with the username and password\n"
			  + "	 provided in the above fields."
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
