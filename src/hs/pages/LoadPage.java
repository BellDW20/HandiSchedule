package hs.pages;

import java.io.File;

import hs.core.Schedule;
import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class LoadPage extends Page {
	public static final int WIDTH = 540;
	public static final int HEIGHT = 595;
	
	private static int BUFFER = 10;
	private CourseSearchPage courseSearchPage;
	private int loadButtonCount;
	
	public LoadPage() {}
	
	public void refresh(PageManager pageManager) {
		for(int i=0; i<loadButtonCount; i++) {
			removeComponent(Page.BUTTON, "Load "+i);
		}
		
		File schedulesFolder = new File("./schedules");
		
		int numFiles = schedulesFolder.list().length;
		
		for (int i = 0; i < numFiles; i++) {
			int tempWidth = WIDTH - (BUFFER * 6);
			int tempHeight = 40;
			int y = BUFFER + (tempHeight * (i + 1));
			int x = BUFFER;
			String name = schedulesFolder.listFiles()[i].getName();
			addButton("Load " + Integer.toString(i), x, y, tempWidth, tempHeight, name, ()-> {
				courseSearchPage.loadSchedule(name.replace(CourseSearchPage.SAVE_EXT, ""), pageManager);
				pageManager.goToPage("CourseSearch");
			});
		}
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		// add Load button
		addButton("loadButton", 10, 5, 80, 40, "Load", null);

		// add New button
		addButton("newButton", 105, 5, 80, 40, "New", null);

		// add courseSearch view button
		addButton("courseSearchSwitchButton", 1150, 5, 120, 40, "Class Search", () -> {
			pageManager.goToPage("CourseSearch");
		});
		
		drawRect(0, 0, WIDTH, HEIGHT);
		drawText(BUFFER, BUFFER, "Schedules");
		
		courseSearchPage = (CourseSearchPage)pageManager.getPage("CourseSearch");
	}
}
