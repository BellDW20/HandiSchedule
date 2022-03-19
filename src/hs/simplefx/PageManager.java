package hs.simplefx;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageManager {

	private HashMap<String, Page> pages;
	private HashMap<String, Scene> pageScenes;
	private Stage stage;
	private int windowWidth, windowHeight;
	
	public PageManager(Stage stage, int windowWidth, int windowHeight) {
		this.pages = new HashMap<>();
		this.pageScenes = new HashMap<>();
		this.stage = stage;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	public void addPageToProgram(Page page, String pageName) {
		pages.put(pageName, page);
		pageScenes.put(pageName, new Scene(page.getPane(), windowWidth, windowHeight));
	}
	
	public void goToPage(String pageName) {
		stage.setScene(pageScenes.get(pageName));
		stage.show();
	}
	
	public Page getPage(String pageName) {
		return pages.get(pageName);
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
}
