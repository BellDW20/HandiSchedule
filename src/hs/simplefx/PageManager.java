package hs.simplefx;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the various pages (JavaFX scenes) in a JavaFX program
 * @author Douglas Bell, Amy Cunningham
 */

public class PageManager {

	private HashMap<String, Page> pages; //Map mapping all pages by their names
	private HashMap<String, Scene> pageScenes; //Map mapping all scenes by their names
	private Stage stage; //The primary JavaFX stage on which pages are displayed
	private int windowWidth, windowHeight; //The window width and height pages are displayed in
	
	/**
	 * Creates an empty page manager operating on a given JavaFX stage with a
	 * desired window width and height.
	 * @param stage Primary JavaFX stage to use
	 * @param windowWidth Desired window width
	 * @param windowHeight Desired window height
	 */
	public PageManager(Stage stage, int windowWidth, int windowHeight) {
		this.pages = new HashMap<>();
		this.setPageScenes(new HashMap<>());
		this.stage = stage;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	/**
	 * Adds a page to the manager, giving it an associated name
	 * @param page Page to add
	 * @param pageName Name to give the page
	 */
	public void addPageToProgram(Page page, String pageName) {
		pages.put(pageName, page);
		Scene scene = new Scene(page.getPane(), windowWidth, windowHeight);
		scene.getStylesheets().add(getClass().getResource("cssStyling.css").toExternalForm());
		getPageScenes().put(pageName, scene);
	}
	
	/**
	 * Switches to viewing a different page given its name
	 * in this page manager
	 * @param pageName Name of the page to switch to
	 */
	public void goToPage(String pageName) {
		stage.setScene(getPageScenes().get(pageName));
		stage.show();
	}
	
	/**
	 * Gets a page in this page manager by name
	 * @param pageName Name of the page to get
	 * @return The page in this page manager with the given name
	 */
	public Page getPage(String pageName) {
		return pages.get(pageName);
	}
	
	/**
	 * Gets the desired window width used by this page manager
	 * @return The desired window width
	 */
	public int getWindowWidth() {
		return windowWidth;
	}
	
	/**
	 * Gets the desired window height used by this page manager
	 * @return The desired window height
	 */
	public int getWindowHeight() {
		return windowHeight;
	}

	public HashMap<String, Scene> getPageScenes() {
		return pageScenes;
	}

	public void setPageScenes(HashMap<String, Scene> pageScenes) {
		this.pageScenes = pageScenes;
	}
	
}
