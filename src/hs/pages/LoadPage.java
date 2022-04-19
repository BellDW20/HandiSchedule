package hs.pages;

import java.io.File;
import java.util.ArrayList;

import hs.simplefx.Page;
import hs.simplefx.PageManager;

public class LoadPage extends Page {
	
	//Dimensions of this sub page
	public static final int WIDTH = 540;
	public static final int HEIGHT = 595;
	//Padding constant for visuals
	private static final int BUFFER = 10;
	
	private CourseSearchPage courseSearchPage; //Reference to the course search page
	private CalendarPage calendarPage; //Reference to the calendar page
	private int loadButtonCount; //How many schedules are saved
	private String pageFrom; //The page from which we came from before loading
	
	/**
	 * Refreshes this page to show all load options
	 * @param pageManager PageManager to properly load a schedule
	 * @param pageFrom The page from which we came to get to the load page
	 */
	public void refresh(PageManager pageManager, String pageFrom) {
		//remember what page we came from
		this.pageFrom = pageFrom;
		
		//removes all of the old load buttons
		for(int i=0; i<loadButtonCount; i++) {
			removeComponent(Page.BUTTON, "Load "+i);
		}
		
		
		//Gets all saved schedule files
		File schedulesFolder = new File(CourseSearchPage.getSaveDirPath());
		File[] schedules = schedulesFolder.listFiles();
		
		ArrayList<File> tempSchedules = new ArrayList<>();
		for (int i = 0; i < schedules.length; i++) {
			tempSchedules.add(schedules[i]);
		}
		
		loadButtonCount = schedules.length;
		
		//For each one, add a button to load that schedule
		for (int i = 0; i < loadButtonCount; i++) {
			int tempWidth = WIDTH - (BUFFER * 6);
			int tempHeight = 40;
			int y = BUFFER + (tempHeight * (i + 1));
			int x = BUFFER;
			int maxIndex = 0;
			
			for (int j = 0; j < tempSchedules.size(); j++) {
				if (tempSchedules.get(maxIndex).lastModified() <= tempSchedules.get(j).lastModified()) {
					maxIndex = j;
				}
			}
			
			String name = tempSchedules.get(maxIndex).getName().replace(CourseSearchPage.SAVE_EXT, "");
			
			tempSchedules.remove(maxIndex);
			
			addButton("Load " + Integer.toString(i), x, y, tempWidth, tempHeight, name, ()-> {
				courseSearchPage.loadSchedule(name, pageManager);
				
				//If we came from the calendar page, we need to update the
				//calendar appropriately
				if(pageFrom.equals("CalendarPage")) {
					calendarPage.updateCalendarImage(courseSearchPage.getCurrentSchedule().getAsCalendar());
				}
				
				pageManager.goToPage(pageFrom);
			});
		}
	}
	
	@Override
	public void initializeComponents(PageManager pageManager) {
		// add back button
		addButton("backButton", 1150, 5, 120, 40, "Back", () -> {
			//Navigates back to the page we came from
			pageManager.goToPage(pageFrom);
		});
		
		drawRect(0, 0, WIDTH, HEIGHT);
		drawText(BUFFER, BUFFER, "Schedules");
		
		//gets references to the two other pages
		courseSearchPage = (CourseSearchPage)pageManager.getPage("CourseSearch");
		calendarPage = (CalendarPage)pageManager.getPage("CalendarPage");
	}
	
}
