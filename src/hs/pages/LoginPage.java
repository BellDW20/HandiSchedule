package hs.pages;

import java.io.File;
import hs.core.security.UserDatabase;
import hs.simplefx.Page;
import hs.simplefx.PageManager;
import hs.simplefx.text.CharacterTextFilter;
import hs.simplefx.text.LengthTextFilter;
import hs.simplefx.text.TextFilterer;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginPage extends Page {

	public static UserDatabase userDatabase;
	public static String loggedInUser;
	public static String loggedInPassword;
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		userDatabase = UserDatabase.loadDatabase("./users.dtb");
		userDatabase.saveDatabase();
		
		Text title = new Text();
		title.setText("HandiSchedule");
		title.setX(430);
		title.setY(200);
		title.setFont(new Font(64.0));
		getPane().getChildren().add(title);
		
		Text alert = new Text();
		alert.setX(540);
		alert.setY(500);
		alert.setVisible(false);
		getPane().getChildren().add(alert);
		
		TextFilterer lengthFilter = new TextFilterer()
			.addFilter(new LengthTextFilter(0,26))
			.addFilter(new CharacterTextFilter(CourseSearchPage.ACCEPTED_SCHEDULE_TITLE_CHARS));
		
		addTextField("usernameField", 540, 320, 200, 40, "Username");
		TextField userField = getTextField("usernameField");
		userField.setTextFormatter(lengthFilter.getAsTextFormatter());
		
		addPasswordField("passwordField", 540, 380, 200, 40, "Password");
		PasswordField passField = (PasswordField)getTextField("passwordField");
		userField.setTextFormatter(lengthFilter.getAsTextFormatter());
		
		addButton("loginButton", 540, 440, 90, 40, "Login", ()->{
			int loginStatus = userDatabase.isValidLogin(userField.getText(), passField.getText());
			
			if(loginStatus == UserDatabase.LOGIN_SUCCESS) {
				loggedInUser = userField.getText();
				loggedInPassword = passField.getText();
				userField.clear();
				passField.clear();
				alert.setVisible(false);
				(new File(CourseSearchPage.getSaveDirPath())).mkdirs();
				((CourseSearchPage)pageManager.getPage("CourseSearch")).loadMostRecentlyEditedSchedule(pageManager);
				pageManager.goToPage("CourseSearch");
			} else if(loginStatus == UserDatabase.LOGIN_INCORRECT_USERNAME) {
				alert.setStroke(Color.RED);
				alert.setText("Login failed: invalid username given");
				alert.setVisible(true);
			} else if(loginStatus == UserDatabase.LOGIN_INCORRECT_PASSWORD) {
				alert.setStroke(Color.RED);
				alert.setText("Login failed: incorrect password for username given");
				alert.setVisible(true);
			}
			
		});
		
		addButton("registerButton", 650, 440, 90, 40, "Register", ()->{
			if(userField.getText().length() == 0) {
				alert.setStroke(Color.RED);
				alert.setText("Registration failed: no username given");
				alert.setVisible(true);
				return;
			} else if(passField.getText().length() == 0) {
				alert.setStroke(Color.RED);
				alert.setText("Registration failed: no password given");
				alert.setVisible(true);
				return;
			}
			
			int registerStatus = userDatabase.registerUser(userField.getText(), passField.getText(), passField.getText());
			
			if(registerStatus == UserDatabase.REGISTER_USERNAME_SUCCESS) {
				userField.clear();
				passField.clear();
				alert.setStroke(Color.GREEN);
				alert.setText("Successfully registered");
				alert.setVisible(true);
				userDatabase.saveDatabase();
			} else if(registerStatus == UserDatabase.REGISTER_USERNAME_TAKEN) {
				alert.setStroke(Color.RED);
				alert.setText("Registration failed: username already taken");
				alert.setVisible(true);
			}
		});
		
	}
	
}
