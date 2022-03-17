package hs.pages;

import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class FilterOptionsPage extends Page {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 595;
	
	public FilterOptionsPage(PageManager pageManager) {
		super(pageManager);
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		drawRect(0, 0, WIDTH, HEIGHT);
		drawText(10, 10, "Filters");
		
		//Course code filters
		drawText(20, 30, "Course Code Levels");
		addCheckBox("100Level", 20, 50, "100");
		addCheckBox("200Level", 100, 50, "200");
		addCheckBox("300Level", 180, 50, "300");
		addCheckBox("400Level", 260, 50, "400");
		
	}

}
