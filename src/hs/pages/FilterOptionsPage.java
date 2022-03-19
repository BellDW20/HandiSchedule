package hs.pages;

import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class FilterOptionsPage extends Page {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 595;
	
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
		
		
		
		
		drawText(20, 80, "Course Credit Hours");
		addCheckBox("0Credits", 20, 100, "0");
		addCheckBox("1Credit", 100, 100, "1");
		addCheckBox("2Credits", 180, 100, "2");
		addCheckBox("3Credits", 260, 100, "3");
		addCheckBox("4Credits", 340, 100, "4");
		addCheckBox("5Credits", 420, 100, "5");
	}

}
