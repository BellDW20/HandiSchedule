package hs.core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import hs.math.CartesianProduct;

/*
 * This class contains information that allows a schedule to be created by a user.
 * Author: Andrew Beichner, Levi Conrad
 */
public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 8304598421321818363L;
	
	private String title;
	private ArrayList<Course> courses;
	private int creditHours;
	
	//constructor method for Schedule. Takes in the name of a schedule
	public Schedule(String title) {
		this.title = title;
		courses = new ArrayList<Course>();
		creditHours = 0;
	}
	
	public Schedule(Schedule s) {
		if (!s.title.contains("(copy)")) {
			this.title = s.title += " (copy)";
		}
		else if (s.title.endsWith("(copy)")){
			this.title = s.title += " 2";
		}
		else {
			int i = 3;
			boolean found = false;
			while (!found) {
				if (s.title.endsWith(" " + Integer.toString(i - 1))) {
					this.title = s.title.replace(" (copy) " + Integer.toString(i-1), " (copy) " + Integer.toString(i));
					found = true;
				}
				else {
					i++;
				}
			}
		}
		this.creditHours = s.creditHours;
		this.courses = new ArrayList<Course>();
		for (int i = 0; i < s.courses.size(); i++) {
			this.courses.add(s.courses.get(i));
		}
	}
	
	private Schedule(ArrayList<Course> c) {
		this.courses = c;
	}
	
	public int getCreditHours() {
		return creditHours;
	}
	
	/*
	 * Method adds a course to the schedule
	 */
	public void addCourse(Course course) {
		if(courses.contains(course)) {return;}
		courses.add(course);
		creditHours += course.getCreditHours();
	}
	
	/*
	 * Method removes a course from the schedule
	 */
	public void removeCourse(Course course) {
		if (courses.remove(course)) {
			creditHours -= course.getCreditHours();
		}
	}
	
	//sets the title of the schedule
	public void setTitle(String title) {
		this.title = title;
	}
	
	//getter method for the schedule title.
	public String getTitle() {
		return title;
	}

	//getter for the courses added to the schedule
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	//returns all of the courses in the schedule in string form
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Course c : courses) {
			sb.append(c.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public boolean isConflicting() {
		for(int i = 0; i < getCourses().size(); i++) {
			for(int j = i+1; j < getCourses().size(); j++) {
				if(getCourses().get(i).isConflictingWith(getCourses().get(j))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean resolveSchedule(CourseDatabase db) {
		if(!isConflicting()) {
			return true;
		}
		
		ArrayList<Course> allCourses = db.getCopyOfAllCourses();
		ArrayList<ArrayList<Course>> possibleSections = new ArrayList<ArrayList<Course>>();
		ArrayList<ArrayList<Course>> possibleSchedules = new ArrayList<ArrayList<Course>>();
		
		for(int i = 0; i < getCourses().size(); i++) {
			ArrayList<Course> sections = new ArrayList<Course>();
			sections.add(getCourses().get(i));
			for(int j = 0; j < allCourses.size(); j++) {
				if(getCourses().get(i).differsOnlyBySection(allCourses.get(j))) {
					sections.add(allCourses.get(j));
				}
			}
			possibleSections.add(sections);
		}
		
		boolean conflicting = true;
		int schIndex = 0;
		possibleSchedules = CartesianProduct.cartesianProduct(possibleSections);
		
		for (schIndex = 0; schIndex < possibleSchedules.size(); schIndex++) {
			Schedule potentialSchedule = new Schedule(possibleSchedules.get(schIndex));
			if(!potentialSchedule.isConflicting()) {
				conflicting = false;
				break;
			}
		}
		
		if(!conflicting) {
			courses.clear();
			creditHours = 0;
			
			for(Course course : possibleSchedules.get(schIndex)) {
				addCourse(course);
			}
		}
		
		return !conflicting;
	}
	
	/*
	 * Method creates a calendar showing the courses in the schedule.
	 * Returns a buffered image with calendar info to be displayed to the user.
	 */
	public BufferedImage getAsCalendar() {
		int width = 800;
		int height = 600;
		BufferedImage calendar = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = calendar.getGraphics();
		Color blue = new Color(173, 216, 230);
		Color black = new Color(0,0,0);
		g.setColor(black);
		
		int headerWidth = width/6;
		int headerHeight = 20;
		int bufferWidth = 50;
		g.drawRect(0, 0, bufferWidth, headerHeight);
		String[] days = {"M", "T", "W", "R", "F"};
		for (int i = 0; i < days.length; i++) {
			g.drawRect(bufferWidth + (i * headerWidth), 0, headerWidth, headerHeight);
			g.drawString(days[i], (bufferWidth + (i * headerWidth)) + (headerWidth / 2), 15);
		}

		Time min = new Time(11, 59, Time.PM);
		Time max = new Time(12, 00, Time.AM);

		for (int i = 0; i < courses.size(); i++) {
			for (int j = 0; j < courses.get(i).getMeetingTimes().size(); j++) {
				Time tempStart = courses.get(i).getMeetingTimes().get(j).getTimeFrame().getStartTime();
				Time tempEnd = courses.get(i).getMeetingTimes().get(j).getTimeFrame().getEndTime();
				if (tempEnd.isAfter(max)) {
					max = tempEnd;
				}

				if (tempStart.isBefore(min)) {
					min = tempStart;
				}
			}
		}

		int start = min.getMilitaryHour();
		int end;
		
		if (max.getMinute() != 0) {
			end = max.getMilitaryHour() + 1;
		}
		else {
			end = max.getMilitaryHour();
		}
		
		int timeBlockHeight = ((height - headerHeight) / (end - start)) - 1;
		int tempY = 0;
		
		
		for (int i = 0; i < end - start; i++) {
			tempY = i * timeBlockHeight + headerHeight;
			g.drawRect(0, tempY, bufferWidth, timeBlockHeight);
			String tempTime = Integer.toString((start + i) % 12);
			if ((start + i) % 12 == 0) {
				tempTime = Integer.toString(12);
			}
			
			if ((start + i) / 12 > 0) {
				tempTime += " PM";
			}
			else {
				tempTime += " AM";
			}
			
			int tempx = 12;
			if (tempTime.length() > 4) {
				tempx = 8;
			}
			g.drawString(tempTime, tempx, tempY + timeBlockHeight / 2);
		}
		
		for (int i = 0; i < days.length; i++) {
			g.drawRect(bufferWidth + (i * headerWidth), headerHeight, headerWidth, (tempY + timeBlockHeight) - headerHeight);
		}

		ArrayList<MeetingTime> calendarTimes = new ArrayList<>();
		
		for (int i = 0; i < courses.size(); i++) {
			Course temp = courses.get(i);
			if (temp.getMeetingTimes().isEmpty()) {
				continue;
			}
			
			int startHour;
			int startMinute;
			int endHour;
			int endMinute;
			int classY;
			int classEndY;
			int stringY;
			int classHeight;
			int classWidth;
			int coursePadding;
			int stringBuffer;
			
			for (int j = 0; j < temp.getMeetingTimes().size(); j++) {
				startHour = temp.getMeetingTimes().get(j).getTimeFrame().getStartTime().getMilitaryHour();
				startMinute = temp.getMeetingTimes().get(j).getTimeFrame().getStartTime().getMinute();
				endHour = temp.getMeetingTimes().get(j).getTimeFrame().getEndTime().getMilitaryHour();
				endMinute = temp.getMeetingTimes().get(j).getTimeFrame().getEndTime().getMinute();
				classY = (((startHour - min.getMilitaryHour()) * timeBlockHeight) + ((timeBlockHeight / 5) * (startMinute / 12))) + headerHeight;
				stringY = classY + 8;
				classEndY = (((endHour - min.getMilitaryHour()) * timeBlockHeight) + ((timeBlockHeight / 5) * (endMinute / 12))) + headerHeight;
				classHeight = classEndY - classY;
				classWidth = headerWidth;
				coursePadding = 30;
				
//				boolean conflicting = false;
				String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
				
//				int conflictingIndex = 0;
//				int numConflicts = 0;
				for (int k = 0; k < calendarTimes.size(); k++) {
					if(temp.getMeetingTimes().get(j).isConflictingWith(calendarTimes.get(k))) {
						BufferedImage bad = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
						Graphics g2 = bad.getGraphics();
						g2.setColor(black);
						g2.drawString("NO CALENDAR IS AVAILABLE: YOU HAVE A CONFLICT IN YOUR SCHEDULE", width / 3, height / 2);
						return bad;
//						conflicting = true;
//						stringY += 16;
//						conflictingIndex = k;
//						numConflicts++;
					}
				}
				
				//stringY += 32 * (numConflicts - 1);
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("M")) {
					g.drawRect(bufferWidth, classY, classWidth, classHeight);
//					g.setColor(blue);
//					g.fillRect(bufferWidth, classY, classWidth, classHeight);
//					g.setColor(black);
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("M")) {
//						g.drawString("CONFLICTS WITH", bufferWidth + (coursePadding / 2), stringY + (classHeight / 2));
//						stringY += 16;
//					}
					g.drawString(text, bufferWidth + coursePadding, stringY + (classHeight / 2));
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("M")) {
//						stringY -= 16;
//					}
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("T")) {
					g.drawRect(bufferWidth + headerWidth, classY, classWidth, classHeight);
//					g.setColor(blue);
//					g.fillRect(bufferWidth + headerWidth, classY, classWidth, classHeight);
//					g.setColor(black);
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("T")) {
//						g.drawString("CONFLICTS WITH", bufferWidth +  headerWidth + (coursePadding / 2), stringY + (classHeight / 2));
//						stringY += 16;
//					}
					g.drawString(text, bufferWidth + headerWidth + coursePadding, stringY + (classHeight / 2));
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("T")) {
//						stringY -= 16;
//					}
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("W")) {
					g.drawRect(bufferWidth + headerWidth * 2, classY, classWidth, classHeight);
//					g.setColor(blue);
//					g.fillRect(bufferWidth + headerWidth * 2, classY, classWidth, classHeight);
//					g.setColor(black);
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("W")) {
//						g.drawString("CONFLICTS WITH", bufferWidth + (headerWidth * 2) + (coursePadding / 2), stringY + (classHeight / 2));
//						stringY += 16;
//					}
					g.drawString(text, bufferWidth + (headerWidth * 2) + coursePadding, stringY + (classHeight / 2));
					
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("W")) {
//						stringY -= 16;
//					}
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("R")) {
					g.drawRect(bufferWidth + headerWidth * 3, classY, classWidth, classHeight);
//					g.setColor(blue);
//					g.fillRect(bufferWidth + headerWidth * 3, classY, classWidth, classHeight);
//					g.setColor(black);
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("R")) {
//						g.drawString("CONFLICTS WITH", bufferWidth + (headerWidth * 3) + (coursePadding / 2), stringY + (classHeight / 2));
//						stringY += 16;
//					}
					g.drawString(text, bufferWidth + (headerWidth * 3) + coursePadding, stringY + (classHeight / 2));
					
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("R")) {
//						stringY -= 16;
//					}
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("F")) {
					g.drawRect(bufferWidth + headerWidth * 4, classY, classWidth, classHeight);
//					g.setColor(blue);
//					g.fillRect(bufferWidth + headerWidth * 4, classY, classWidth, classHeight);
//					g.setColor(black);
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("F")) {
//						g.drawString("CONFLICTS WITH", bufferWidth + (headerWidth * 4) + (coursePadding / 2), stringY + (classHeight / 2));
//						stringY += 16;
//					}
					g.drawString(text, bufferWidth + (headerWidth * 4) + coursePadding, stringY + (classHeight / 2));
					
//					if (conflicting && calendarTimes.get(conflictingIndex).getDaysOfWeekString().contains("F")) {
//						stringY -= 16;
//					}
				}
				
				calendarTimes.add(temp.getMeetingTimes().get(j));
			}
		}
		
		return calendar;
	}
	
}
