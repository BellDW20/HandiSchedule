package hs.simplefx;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Manages JavaFX components in a more "friendly" way akin to
 * what many novice GUI programmers might be used to. Makes
 * further development using JavaFX slightly more streamlined /
 * easy to manage.
 * 
 * @author Douglas Bell, Amy Cunningham
 */

public class Page {
	
	//Name prefixes for every type of component able to be added to a Page
	public static final String LABEL = "label-";
	public static final String BUTTON = "button-";
	public static final String TEXT_FIELD = "textField-";
	public static final String CHECK_BOX = "checkBox-";
	public static final String DROP_DOWN = "dropDown-";
	public static final String SUB_PAGE = "subPage";
	
	private Pane pane; //JavaFX pane containing all nodes
	
	//A map from the name of the nodes on the page to the node itself
	private HashMap<String, Node> nodeMap;
	
	//A map from the name of the sub-pages on the page to the sub-page itself
	private HashMap<String, Page> subPages;
	
	/**
	 * Creates a blank page
	 */
	public Page() {
		this.pane = new Pane();
		this.nodeMap = new HashMap<>();
		this.subPages = new HashMap<>();
	}
	
	/**
	 * Intended to be (optionally) implemented by child classes.
	 * Builds the items on the page given the PageManager it resides in.
	 * @param pageManager The PageManager the page resides in
	 */
	public void initializeComponents(PageManager pageManager) {
		return;
	}
	
	/**
	 * Hides the component specified on the page 
	 * @param type Type of the component specified
	 * @param componentName Name of the component specified
	 */
	public void hideComponent(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			pane.getChildren().remove(subPages.get(componentName).getPane());
		} else {
			nodeMap.get(type+componentName).setVisible(false);
		}
	}
	
	/**
	 * Shows the component specified on the page
	 * @param type Type of the component specified
	 * @param componentName Name of the component specified
	 */
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
	
	/**
	 * Toggles the visibility of the component specified on the page
	 * (visible turns to invisible and vice versa)
	 * @param type Type of the component specified
	 * @param componentName Name of the component specified
	 */
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
	
	/**
	 * Removes a specified component from the page
	 * @param type Type of the component specified
	 * @param componentName Name of the component specified
	 */
	public void removeComponent(String type, String componentName) {
		if(type.equals(SUB_PAGE)) {
			pane.getChildren().remove(subPages.get(componentName).getPane());
			subPages.remove(componentName);
		} else {
			pane.getChildren().remove(nodeMap.get(type+componentName));
			nodeMap.remove(type+componentName);
		}
	}
	
	/**
	 * Draws a rectangle on the page given a position and dimension
	 * @param x X-position of the rectangle
	 * @param y Y-position of the rectangle
	 * @param w Width of the rectangle
	 * @param h Height of the rectangle
	 */
	public void drawRect(int x, int y, int w, int h) {
		Rectangle rect = new Rectangle(x,y,w,h);
		rect.setStrokeWidth(1);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);
		pane.getChildren().add(rect);
	}
	
	/**
	 * Adds text at a certain position on the page
	 * @param String labelName Name of the label component
	 * @param x X-position of the text
	 * @param y Y-position of the text
	 * @param text Text to draw as a string
	 */
	public void addLabel(String labelName, int x, int y, String text) {
		Text t = new Text(text);
		t.setLayoutX(x);
		t.setLayoutY(y);
		nodeMap.put(LABEL+labelName, t);
	}
	
	/**
	 * Adds a button to the page with a given functionality on click.
	 * @param buttonName Name of the button component
	 * @param x X-position of the button
	 * @param y Y-position of the button
	 * @param w Width of the button
	 * @param h Height of the button
	 * @param text The text applied to the button
	 * @param onClick The event that occurs when the user clicks the button
	 */
	public Button addButton(String buttonName, int x, int y, int w, int h, String text, Runnable onClick) {
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
		return button;
	}
	
	/**
	 * Adds a text field to the page with a given prompt text
	 * @param textFieldName Name of the text field component
	 * @param x X-position of the text field
	 * @param y Y-position of the text field
	 * @param w Width of the text field
	 * @param h Height of the text field
	 * @param promptText The text that appears when no text is in the text field
	 */
	public void addTextField(String textFieldName, int x, int y, int w, int h, String promptText) {
		TextField textField = new TextField();
		setupLayout(textField, x, y, w, h);
		textField.setPromptText(promptText);
		
		nodeMap.put(TEXT_FIELD+textFieldName, textField);
		pane.getChildren().add(textField);
	}
	
	/**
	 * Adds a password field to the page with a given prompt text
	 * @param passwordFieldName Name of the text field component
	 * @param x X-position of the text field
	 * @param y Y-position of the text field
	 * @param w Width of the text field
	 * @param h Height of the text field
	 * @param promptText The text that appears when no text is in the password field
	 */
	public void addPasswordField(String passwordFieldName, int x, int y, int w, int h, String promptText) {
		PasswordField textField = new PasswordField();
		setupLayout(textField, x, y, w, h);
		textField.setPromptText(promptText);
		
		nodeMap.put(TEXT_FIELD+passwordFieldName, textField);
		pane.getChildren().add(textField);
	}
	
	/**
	 * Adds a check box to the page at a given position and label
	 * @param checkBoxName Name of the check box component
	 * @param x X-position of the check box
	 * @param y Y-position of the check box
	 * @param text Label to place beside the check box
	 */
	public void addCheckBox(String checkBoxName, int x, int y, String text) {
		CheckBox checkBox = new CheckBox(text);
		checkBox.setLayoutX(x);
		checkBox.setLayoutY(y);
		
		nodeMap.put(CHECK_BOX+checkBoxName, checkBox);
		pane.getChildren().add(checkBox);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	/**
	 * Adds a drop down to the page at a given position with given options
	 * @param dropDownName Name of the drop down component
	 * @param x X-position of the drop down
	 * @param y Y-position of the drop down
	 * @param options A list of options for the drop down as strings
	 */
	public void addDropDown(String dropDownName, int x, int y, String... options) {
		ComboBox dropDown = new ComboBox();
		dropDown.getItems().addAll((Object[])options);
		dropDown.getSelectionModel().selectFirst();
		dropDown.setLayoutX(x);
		dropDown.setLayoutY(y);

		nodeMap.put(DROP_DOWN+dropDownName, dropDown);
		pane.getChildren().add(dropDown);
	}
	
	/**
	 * Adds a sub page (used for organization of components) at the given position
	 * @param subPageName Name of the sub page component
	 * @param subPage The sub page to add
	 * @param x X-position of the sub page
	 * @param y Y-position of the sub page
	 * @param w Width of the sub page. Any content past this point is clipped
	 * @param h Height of the sub page. Any content past this point is clipped
	 * @param allowScroll Whether or not clipped content can be scrolled to
	 */
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
	
	public void addSubPages(ArrayList<Page> subPages, int x, int y, int w, int h, int ox, int oy) {
		ArrayList<Node> nodes = new ArrayList<>(subPages.size());
		for(int i=0; i<subPages.size(); i++) {
			Page subPage = subPages.get(i);
			subPage.getPane().setLayoutX(x+ox*i);
			subPage.getPane().setLayoutY(y+oy*i);
			clipRegion(subPage.getPane(), w, h);
			nodes.add(subPage.getPane());
			this.subPages.put(subPage.toString(), subPage);
		}
		
		pane.getChildren().addAll(nodes);
	}
	
	/**
	 * Gets the button component with the given name
	 * @param buttonName Name of the button to get
	 * @return The button component with the given name
	 */
	public Button getButton(String buttonName) {
		return (Button)nodeMap.get(BUTTON+buttonName);
	}
	
	/**
	 * Gets the text field component with the given name
	 * @param textFieldName Name of the text field to get
	 * @return The text field component with the given name
	 */
	public TextField getTextField(String textFieldName) {
		return (TextField)nodeMap.get(TEXT_FIELD+textFieldName);
	}
	
	/**
	 * Gets the check box component with the given name
	 * @param checkBoxName Name of the check box to get
	 * @return The check box component with the given name
	 */
	public CheckBox getCheckBox(String checkBoxName) {
		return (CheckBox)nodeMap.get(CHECK_BOX+checkBoxName);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Gets the drop down component with the given name
	 * @param dropDownName Name of the drop down to get
	 * @return The drop down component with the given name
	 */
	public ComboBox getDropDown(String dropDownName) {
		return (ComboBox)nodeMap.get(DROP_DOWN+dropDownName);
	}
	
	/**
	 * Gets the label component with the given name
	 * @param labelName Name of the label to get
	 * @return The label component with the given name
	 */
	public Text getLabel(String labelName) {
		return (Text)nodeMap.get(LABEL+labelName);
	}
	
	/**
	 * Gets the sub page component with the given name
	 * @param subPageName Name of the sub page to get
	 * @return The sub page component with the given name
	 */
	public Page getSubPage(String subPageName) {
		return subPages.get(subPageName);
	}
	
	/**
	 * Getter for the JavaFX pane on which all components of the page exist
	 * @return The JavaFX pane on which all components of the page exist
	 */
	public Pane getPane() {
		return pane;
	}
	
	/**
	 * Convenience method which sets the position and dimension of some JavaFX nodes.
	 * @param region JavaFX node to set the position/dimension of
	 * @param x X-position to set
	 * @param y Y-position to set
	 * @param w Width to set
	 * @param h Height to set
	 */
	private static void setupLayout(Region region, int x, int y, int w, int h) {
		region.setLayoutX(x);
		region.setLayoutY(y);
		region.setMinSize(w, h);
		region.setMaxSize(w, h);
		region.setPrefSize(w, h);
	}
	
	/**
	 * Clips some JavaFX nodes to only display a certain dimension of their full content
	 * @param region JavaFX node to clip
	 * @param w Width to clip by
	 * @param h Height to clip by
	 */
	private static void clipRegion(Region region, int w, int h) {
		Rectangle clippingRect = new Rectangle();
		clippingRect.setWidth(w);
		clippingRect.setHeight(h);
		
		region.setClip(clippingRect);
	}
	
}
