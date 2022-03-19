package hs.simplefx;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Page {
	
	public static final String LABEL = "label-";
	public static final String BUTTON = "button-";
	public static final String TEXT_FIELD = "textField-";
	public static final String CHECK_BOX = "checkBox-";
	public static final String DROP_DOWN = "dropDown-";
	public static final String SCROLL_BAR = "scrollBar-";
	public static final String SUB_PAGE = "subPage";
	
	private Pane pane;
	
	private HashMap<String, Node> nodeMap;
	private HashMap<String, Page> subPages;
	
//	public Page(PageManager pageManager, String name) {
//		this.pane = new Pane();
//		this.scene = new Scene(pane, pageManager.getWindowWidth(), pageManager.getWindowHeight());
//		this.name = name;
//		this.nodeMap = new HashMap<>();
//		this.subPages = new HashMap<>();
//		initializeComponents(pageManager);
//		pageManager.addPageToProgram(name, this);
//	}
//	
//	public Page(PageManager pageManager) {
//		this();
//		initializeComponents(pageManager);
//	}
	
	public Page() {
		this.pane = new Pane();
//		this.scene = null;
//		this.name = null;
		this.nodeMap = new HashMap<>();
		this.subPages = new HashMap<>();
	}
	
	public void initializeComponents(PageManager pageManager) {
		return;
	}
	
	public void hideComponent(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			pane.getChildren().remove(subPages.get(componentName).getPane());
		} else {
			nodeMap.get(type+componentName).setVisible(false);
		}
	}
	
	public void showComponent(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			Pane subPagePane = subPages.get(componentName).getPane();
			if(!pane.getChildren().contains(subPagePane)) {
				pane.getChildren().add(subPagePane);
			}
		} else {
			nodeMap.get(type+componentName).setVisible(true);
		}
	}
	
	public void toggleComponentVisibility(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			Pane subPagePane = subPages.get(componentName).getPane();
			if(!pane.getChildren().contains(subPagePane)) {
				pane.getChildren().add(subPagePane);
			} else {
				pane.getChildren().remove(subPagePane);
			}
		} else {
			Node n = nodeMap.get(type+componentName);
			n.setVisible(!n.isVisible());
		}
	}
	
	public void removeComponent(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			pane.getChildren().remove(subPages.get(componentName).getPane());
			subPages.remove(componentName);
		} else {
			pane.getChildren().remove(nodeMap.get(type+componentName));
			nodeMap.remove(type+componentName);
		}
	}
	
	public void drawRect(int x, int y, int w, int h) {
		Rectangle rect = new Rectangle(x,y,w,h);
		rect.setStrokeWidth(1);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);
		pane.getChildren().add(rect);
	}
	
	public void drawText(int x, int y, String text) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		pane.getChildren().add(label);
	}
	
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
		
		nodeMap.put(BUTTON+buttonName, button);
		pane.getChildren().add(button);
	}
		
	public void addTextField(String textFieldName, int x, int y, int w, int h, String promptText) {
		TextField textField = new TextField();
		setupLayout(textField, x, y, w, h);
		textField.setPromptText(promptText);
		
		nodeMap.put(TEXT_FIELD+textFieldName, textField);
		pane.getChildren().add(textField);
	}
	
	public void addCheckBox(String checkBoxName, int x, int y, String text) {
		CheckBox checkBox = new CheckBox(text);
		checkBox.setLayoutX(x);
		checkBox.setLayoutY(y);
		
		nodeMap.put(CHECK_BOX+checkBoxName, checkBox);
		pane.getChildren().add(checkBox);
	}
	
	public void addDropDown(String dropDownName, int x, int y, String... options) {
		ComboBox dropDown = new ComboBox();
		dropDown.getItems().addAll(options);
		dropDown.getSelectionModel().selectFirst();
		dropDown.setLayoutX(x);
		dropDown.setLayoutY(y);

		nodeMap.put(DROP_DOWN+dropDownName, dropDown);
		pane.getChildren().add(dropDown);
	}
	
	public void addSubPage(String subPageName, Page subPage, int x, int y, int w, int h, boolean allowScroll) {
		if(allowScroll) {
			ScrollPane container = new ScrollPane(subPage.getPane());
			container.setLayoutX(x);
			container.setLayoutY(y);
			container.setPrefSize(w, h);
			pane.getChildren().add(container);
		} else {
			subPage.getPane().setLayoutX(x);
			subPage.getPane().setLayoutY(y);
			clipRegion(subPage.getPane(), w, h);
			pane.getChildren().add(subPage.getPane());
		}
			
		subPages.put(subPageName, subPage);
	}
	
	public Button getButton(String buttonName) {
		return (Button)nodeMap.get(BUTTON+buttonName);
	}
	
	public TextField getTextField(String textFieldName) {
		return (TextField)nodeMap.get(TEXT_FIELD+textFieldName);
	}
	
	public CheckBox getCheckBox(String checkBoxName) {
		return (CheckBox)nodeMap.get(CHECK_BOX+checkBoxName);
	}
	
	public Page getSubPage(String subPageName) {
		return subPages.get(subPageName);
	}
	
	public Pane getPane() {
		return pane;
	}
	
	private static void setupLayout(Region region, int x, int y, int w, int h) {
		region.setLayoutX(x);
		region.setLayoutY(y);
		region.setMinSize(w, h);
		region.setMaxSize(w, h);
		region.setPrefSize(w, h);
	}
	
	private static void clipRegion(Region region, int w, int h) {
		Rectangle clippingRect = new Rectangle();
		clippingRect.setWidth(w);
		clippingRect.setHeight(h);
		
		region.setClip(clippingRect);
	}
	
}
