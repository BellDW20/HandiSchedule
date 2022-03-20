package hs.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;

public class Schedule {
	
	private String title;
	private Date dateLastModified;
	private ArrayList<Course> courses;
	
	public Schedule(String title) {
		this.title = title;
		courses = new ArrayList<Course>();
	}
	
	public void addCourse(Course course) {
		if(courses.contains(course)) {return;}
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
	
	public int calculateTotalCreditHours() {
		int totalCreditHours = 0;
		for(int i = 0; i < courses.size(); i++) {
			totalCreditHours += courses.get(i).getCreditHours();
		}
		return totalCreditHours;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getDateLastModified() {
		return dateLastModified;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Course c : courses) {
			sb.append(c.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public void saveSchedule(String path) {
		try {
			
			ObjectOutputStream dataOut = new ObjectOutputStream(new FileOutputStream(new File(path)));
			dataOut.writeObject(title);
			dataOut.writeObject(courses);
			dateLastModified = new Date(System.currentTimeMillis());
			dataOut.writeObject(dateLastModified);
			dataOut.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Schedule loadSchedule(String path) {	
		Schedule loadedSchedule = new Schedule("");
		try {
			ObjectInputStream dataIn = new ObjectInputStream(new FileInputStream(new File(path)));
			loadedSchedule.title = (String) dataIn.readObject();
			loadedSchedule.courses = (ArrayList<Course>) dataIn.readObject();
			loadedSchedule.dateLastModified = (Date) dataIn.readObject();
			dataIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return loadedSchedule;
	}
	
	public BufferedImage getAsCalendar() {
		int width = 800;
		int height = 600;
		BufferedImage calendar = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = calendar.getGraphics();
		Color blue = new Color(173, 216, 230);
		g.setColor(blue);
		
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

		int start = min.getHour();
		int end;
		
		if (max.getMinutes() != 0) {
			end = max.getHour() + 1;
		}
		else {
			end = max.getHour();
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
		
		Color lightBlue = new Color(173, 216, 230);

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
			int classHeight;
			int classWidth;
			int coursePadding;
			
			for (int j = 0; j < temp.getMeetingTimes().size(); j++) {
				startHour = temp.getMeetingTimes().get(j).getTimeFrame().getStartTime().getHour();
				startMinute = temp.getMeetingTimes().get(j).getTimeFrame().getStartTime().getMinutes();
				endHour = temp.getMeetingTimes().get(j).getTimeFrame().getEndTime().getHour();
				endMinute = temp.getMeetingTimes().get(j).getTimeFrame().getEndTime().getMinutes();
				classY = (((startHour - min.getHour()) * timeBlockHeight) + ((timeBlockHeight / 5) * (startMinute / 12))) + headerHeight;
				classEndY = (((endHour - min.getHour()) * timeBlockHeight) + ((timeBlockHeight / 5) * (endMinute / 12))) + headerHeight;
				classHeight = classEndY - classY;
				classWidth = headerWidth;
				coursePadding = 30;
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("M")) {
					g.drawRect(bufferWidth, classY, classWidth, classHeight);
					g.setColor(lightBlue);
					//g.fillRect(bufferWidth, classY, classWidth, classHeight);
					g.setColor(blue);
					String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
					g.drawString(text, bufferWidth + coursePadding, classY + (classHeight / 2));
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("T")) {
					g.drawRect(bufferWidth + headerWidth, classY, classWidth, classHeight);
					g.setColor(lightBlue);
					//g.fillRect(bufferWidth + headerWidth, classY, classWidth, classHeight);
					g.setColor(blue);
					String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
					g.drawString(text, bufferWidth + headerWidth + coursePadding, classY + (classHeight / 2));
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("W")) {
					g.drawRect(bufferWidth + headerWidth * 2, classY, classWidth, classHeight);
					g.setColor(lightBlue);
					//g.fillRect(bufferWidth + headerWidth * 2, classY, classWidth, classHeight);
					g.setColor(blue);
					String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
					g.drawString(text, bufferWidth + (headerWidth * 2) + coursePadding, classY + (classHeight / 2));
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("R")) {
					g.drawRect(bufferWidth + headerWidth * 3, classY, classWidth, classHeight);
					g.setColor(lightBlue);
					//g.fillRect(bufferWidth + headerWidth * 3, classY, classWidth, classHeight);
					g.setColor(blue);
					String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
					g.drawString(text, bufferWidth + (headerWidth * 3) + coursePadding, classY + (classHeight / 2));
				}
				
				if (temp.getMeetingTimes().get(j).getDaysOfWeekString().contains("F")) {
					g.drawRect(bufferWidth + headerWidth * 4, classY, classWidth, classHeight);
					g.setColor(lightBlue);
					//g.fillRect(bufferWidth + headerWidth * 4, classY, classWidth, classHeight);
					g.setColor(blue);
					String text = courses.get(i).getDepartment() + " " + courses.get(i).getCourseCode() + " " + courses.get(i).getSection();
					g.drawString(text, bufferWidth + (headerWidth * 4) + coursePadding, classY + (classHeight / 2));
				}
			}
		}
		
		
		return calendar;
	}
}
