package hs.simplefx;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class Page {
	
	private Pane pane;
	private Scene scene;
	protected String name;
	
	private HashMap<String, Node> nodeMap;
	
	public Page(PageManager pageManager, String name) {
		this.pane = new Pane();
		this.scene = new Scene(pane, pageManager.getWindowWidth(), pageManager.getWindowHeight());
		this.name = name;
		this.nodeMap = new HashMap<>();
		initializeComponents(pageManager);
		pageManager.addPageToProgram(name, this);
	}
	
	public abstract void initializeComponents(PageManager pageManager);
	
	public void addButton(String buttonName, int x, int y, int w, int h, String text, Runnable onClick) {
		Button button = new Button(text);
		setupLayout(button, x, y, w, h);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(onClick != null) {
					onClick.run();
				}
			}
		});
		
		nodeMap.put("button-"+buttonName, button);
		pane.getChildren().add(button);
	}
		
	public void addTextField(String textFieldName, int x, int y, int w, int h, String promptText) {
		TextField textField = new TextField();
		setupLayout(textField, x, y, w, h);
		textField.setPromptText(promptText);
		
		nodeMap.put("textField-"+textFieldName, textField);
		pane.getChildren().add(textField);
	}
	
	public void addCheckBox(String checkBoxName, int x, int y, String text) {
		CheckBox checkBox = new CheckBox(text);
		checkBox.setLayoutX(x);
		checkBox.setLayoutY(y);
		
		nodeMap.put("checkBox-"+checkBoxName, checkBox);
		pane.getChildren().add(checkBox);
	}
	
	public void addDropDown(String dropDownName, int x, int y, String... options) {
		ComboBox dropDown = new ComboBox();
		dropDown.getItems().addAll(options);
		dropDown.getSelectionModel().selectFirst();
		dropDown.setLayoutX(x);
		dropDown.setLayoutY(y);

		nodeMap.put("dropDown-"+dropDownName, dropDown);
		pane.getChildren().add(dropDown);
	}
	
	public void addSpecial(String specialName, Node special) {
		nodeMap.put("dropDown-"+specialName, special);
		pane.getChildren().add(special);
	}
	
	public void addSubPage(Page page, int x, int y, int w, int h, boolean visible) {
		
	}
	
	public Scene getScene() {
		return scene;
	}
	
	private static void setupLayout(Region region, int x, int y, int w, int h) {
		region.setLayoutX(x);
		region.setLayoutY(y);
		region.setMinSize(w, h);
		region.setMaxSize(w, h);
		region.setPrefSize(w, h);
	}
	
}
