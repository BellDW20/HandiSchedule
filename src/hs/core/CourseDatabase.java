package hs.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CourseDatabase {

	private ArrayList<Course> fullCourseList;
	
	public CourseDatabase() {
		this.fullCourseList = new ArrayList<>();
	}
	
	public void registerCourse(Course course) {
		fullCourseList.add(course);
	}
	
	public ArrayList<Course> getCopyOfAllCourses() {
		return new ArrayList<Course>(fullCourseList);
	}
	
	public static CourseDatabase loadFromFile(String databasePath) {
		CourseDatabase db = new CourseDatabase();
		
		HashMap<String,Course> courseMap = new HashMap<>();
		
		try {
			Scanner fileScanner = new Scanner(new File(databasePath));
			
			fileScanner.nextLine(); //Skip CSV column name header
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(",");
				
				//Line Format: CourseCode, ShortTitle, LongTitle, BeginTime, EndTime, Meets, Building, Room, Enrollment, Capacity
				String courseSection = lineScanner.next();
				boolean alreadySeenSection = courseMap.containsKey(courseSection);
				String[] deptAndCode = courseSection.split("\\s+");
				
				String shortName = lineScanner.next();
				if(shortName.startsWith("\"")) {
					while(!shortName.endsWith("\"")) {
						shortName+=", "+lineScanner.next();
					}
				}
				
				String courseName = lineScanner.next();
				if(courseName.startsWith("\"")) {
					while(!courseName.endsWith("\"")) {
						courseName+=", "+lineScanner.next();
					}
					courseName = courseName.substring(1,courseName.length()-1);
				}
				
				String start = lineScanner.next();
				String end = lineScanner.next();
				String daysOfWeek = lineScanner.next();
				lineScanner.next(); //Skip building
				lineScanner.next(); //Skip room
				lineScanner.next(); //Skip enrollment
				lineScanner.next(); //Skip capacity
				
				String department = deptAndCode[0];
				int courseCode = Integer.parseInt(deptAndCode[1]);
				String section = deptAndCode[2];
				
				if(!alreadySeenSection) {
					courseMap.put(courseSection, new Course(courseName, department, section.charAt(0), courseCode));
				}
				
				if(!start.equals("NULL")) {
					TimeFrame timeFrame = new TimeFrame(new Time(start), new Time(end));
					courseMap.get(courseSection).addMeetingTime(timeFrame, daysOfWeek, !courseName.equals("LABORATORY"));
				}
				
				lineScanner.close();
			}
			
			fileScanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Convert map to sorted list (by dept, course no, then section)
		db.fullCourseList = new ArrayList<Course>(courseMap.values());
		Collections.sort(
			(List<Course>)db.fullCourseList,
			new Comparator<Course>() {

				@Override
				public int compare(Course a, Course b) {
					int dept = a.getDepartment().compareTo(b.getDepartment());
					if(dept != 0) {return dept;}
					int no = Integer.compare(a.getCourseCode(), b.getCourseCode());
					if(no != 0) {return no;}
					
					return Character.compare(a.getSection(), b.getSection());
				}
				
			}
		);
		
		return db;
	}
	
}
