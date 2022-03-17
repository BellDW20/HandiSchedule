package hs.simplefx;

import java.util.HashMap;

import javafx.stage.Stage;

public class PageManager {

	private HashMap<String, Page> pages;
	private Stage stage;
	private int windowWidth, windowHeight;
	
	public PageManager(Stage stage, int windowWidth, int windowHeight) {
		this.pages = new HashMap<>();
		this.stage = stage;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	public void addPageToProgram(String pageName, Page page) {
		pages.put(pageName, page);
	}
	
	public void goToPage(String pageName) {
		stage.setScene(pages.get(pageName).getScene());
		stage.show();
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
}
