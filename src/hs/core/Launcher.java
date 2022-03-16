package hs.core;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class Launcher extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Handi Schedule");
		//Testing adding a button
		Button btn = new Button();
		btn.setText("Welcome to HandiSchedule!");
		StackPane sp = new StackPane();
		sp.getChildren().add(btn);
		stage.setScene(new Scene(sp, 800, 600));
		stage.show();
	}
	
}
