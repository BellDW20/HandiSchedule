package hs.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class CourseDatabase {

	private ArrayList<Course> fullCourseList;
	private ArrayList<String> fullDepartmentsList;
	
	public CourseDatabase() {
		this.fullCourseList = new ArrayList<>();
		this.fullDepartmentsList = new ArrayList<>();
	}
	
	public void registerCourse(Course course) {
		fullCourseList.add(course);
		if(!fullDepartmentsList.contains(course.getDepartment())) {
			fullDepartmentsList.add(course.getDepartment());
		}
	}
	
	public ArrayList<Course> getCopyOfAllCourses() {
		return new ArrayList<Course>(fullCourseList);
	}
	
	public ArrayList<String> getCopyOfAllDepartments() {
		return new ArrayList<String>(fullDepartmentsList);
	}
	
	public String[] getAllDepartmentsAsArray() {
		String[] ret = new String[fullDepartmentsList.size()];
		for(int i=0; i<ret.length; i++) {
			ret[i] = new String(fullDepartmentsList.get(i));
		}
		return ret;
	}
	
	public static CourseDatabase loadFromFile(String databasePath) {
		CourseDatabase db = new CourseDatabase();
		
		HashMap<String,Course> courseMap = new HashMap<>();
		HashSet<String> departmentSet = new HashSet<>();
		
		try {
			Scanner fileScanner = new Scanner(new File(databasePath));
			
			fileScanner.nextLine(); //Skip CSV column name header
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(",");
				
				//Line Format: CourseCode, ShortTitle, LongTitle, BeginTime, EndTime, Meets, Building, Room, Enrollment, Capacity
				String courseSection = lineScanner.next();
				String[] deptAndCode = courseSection.split("\\s+");
				boolean alreadySeenSection = courseMap.containsKey(courseSection);
				
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
				departmentSet.add(department);
				
				int courseCode = Integer.parseInt(deptAndCode[1]);
				char section = deptAndCode[2].charAt(0);
				
				if(!alreadySeenSection) {
					courseMap.put(courseSection, new Course(courseName, department, section, courseCode));
				}
				
				if(!start.equals("NULL")) {
					TimeFrame timeFrame = new TimeFrame(new Time(start), new Time(end));
					courseMap.get(courseSection).addMeetingTime(timeFrame, daysOfWeek, !courseName.contains("LABORATORY"));
				}
				
				lineScanner.close();
			}
			
			fileScanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Convert map to sorted list (by dept, course no, then section)
		db.fullCourseList = new ArrayList<Course>(courseMap.values());
		db.fullDepartmentsList = new ArrayList<String>(departmentSet);
		
		//Sort collections to be in proper order
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
		
		Collections.sort((List<String>)db.fullDepartmentsList);
		
		return db;
	}
	
}
