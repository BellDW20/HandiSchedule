package hs.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A "database" which holds a list of all courses and all of the
 * departments that these courses could be in. While queries are
 * not supported directly in the database, it is still possible to
 * access the data in the database and manually search it.
 * @author Douglas Bell
 */

public class CourseDatabase {

	private ArrayList<Course> fullCourseList; // List of all courses
	private ArrayList<String> fullDepartmentsList; // List of all departments
	
	/**
	 * Constructs an empty course database containing no courses
	 * and no departments.
	 */
	public CourseDatabase() {
		this.fullCourseList = new ArrayList<>();
		this.fullDepartmentsList = new ArrayList<>();
	}
	
	/**
	 * Registers a course by adding it into the database,
	 * and adding its department to the database if not already present
	 * @param course Course to add into the database
	 */
	public void registerCourse(Course course) {
		fullCourseList.add(course);
		if(!fullDepartmentsList.contains(course.getDepartment())) {
			fullDepartmentsList.add(course.getDepartment());
		}
	}
	
	/**
	 * Sorts the course and department lists of the database.
	 * Courses are sorted by department, then course number, then section.
	 * Departments are sorted alphabetically.
	 */
	public void sortDatabase() {
		Collections.sort((List<Course>)this.fullCourseList,
			new Comparator<Course>() {

				@Override
				public int compare(Course a, Course b) {
					//compares department...
					int dept = a.getDepartment().compareTo(b.getDepartment());
					if(dept != 0) {return dept;}
					//then tries course number...
					int no = Integer.compare(a.getCourseCode(), b.getCourseCode());
					if(no != 0) {return no;}
					//otherwise resorts to section
					return Character.compare(a.getSection(), b.getSection());
				}
				
			}
		);
		
		Collections.sort((List<String>)this.fullDepartmentsList);
	}
	
	/**
	 * Returns a copy of the list of references to the courses
	 * currently registered in the database
	 * @return A copy of the list of references to the courses
	 * currently registered in the database
	 */
	public ArrayList<Course> getCopyOfAllCourses() {
		return new ArrayList<Course>(fullCourseList);
	}
	
	/**
	 * Returns a copy of the list of references to the departments
	 * currently registered in the database
	 * @return A copy of the list of references to the departments
	 * currently registered in the database
	 */
	public ArrayList<String> getCopyOfAllDepartments() {
		return new ArrayList<String>(fullDepartmentsList);
	}
	
	/**
	 * Returns the list of all departments currently registered
	 * in the database as an array of Strings.
	 * @return The list of all departments currently registered
	 * in the database as an array of Strings.
	 */
	public String[] getAllDepartmentsAsArray() {
		String[] ret = new String[fullDepartmentsList.size()];
		for(int i=0; i<ret.length; i++) {
			ret[i] = new String(fullDepartmentsList.get(i));
		}
		return ret;
	}

	/**
	 * Creates a course database from a CSV file residing
	 * at a given path.
	 * @param databasePath Path of the database to load as a CSV file
	 * @return A course database containing all courses / course info
	 * 		   as specified by the CSV file
	 */
	public static CourseDatabase loadFromFile(String databasePath) {
		//Create an empty database
		CourseDatabase db = new CourseDatabase();
		
		//Map to keep track of all courses we have encountered by
		//"name" (eg MATH 222 A)
		HashMap<String,Course> courseMap = new HashMap<>();
		try {
			//Begin reading the CSV file
			Scanner fileScanner = new Scanner(new File(databasePath));
			
			fileScanner.nextLine(); //Skip CSV column name header
			
			//Every other line is a course
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine(); //Get the next course
				
				Scanner lineScanner = new Scanner(line); //Begin scanning this course in
				lineScanner.useDelimiter(","); //Data is separated by commas
				
				//Line Format: Fall/spring, dept, course code, section, title, credit hours, m?, t?, w?, r?, f?, startTime, endTime
				boolean isFall = lineScanner.next().equals("10");
				if(!isFall) {
					lineScanner.close();
					continue;
				}
				
				String department = lineScanner.next();
				int courseCode = Integer.parseInt(lineScanner.next());
				String sectionString = lineScanner.next();
				if(sectionString.length() == 0) {
					lineScanner.close();
					continue;
				}
				char section = sectionString.charAt(0);
				
				String courseSection = department+" "+courseCode+" "+section;
				
				//If we've already seen this section of the course, we don't
				//want to add a new course to the map (in fact, we can't).
				//We will instead add this line as another meeting time of the same section
				boolean alreadySeenSection = courseMap.containsKey(courseSection);
				
				String courseName = lineScanner.next();
				//Handles if the actual name also contained commas
				if(courseName.startsWith("\"")) {
					while(!courseName.endsWith("\"")) {
						courseName+=", "+lineScanner.next();
					}
					courseName = courseName.substring(1,courseName.length()-1);
				}
				
				int creditHours = Integer.parseInt(lineScanner.next());
				
				String daysOfWeek = "";
				for(int i=0; i<5; i++) {
					daysOfWeek += lineScanner.next();
				}
				
				if(daysOfWeek.length() == 0) {
					lineScanner.close();
					continue;
				}
				
				String start = lineScanner.next();
				String end = lineScanner.next();
				
				//If we've not yet seen this course section...
				if(!alreadySeenSection) {
					//Add it as a new course to the map
					courseMap.put(courseSection, new Course(courseName, department, section, courseCode, creditHours));
				}
				
				//As long as this course actually has a meeting time...
				if(!start.equals("NULL")) {
					//Add the meeting time specified by this line to the course in question
					TimeFrame timeFrame = new TimeFrame(new Time(start, false), new Time(end, false));
					courseMap.get(courseSection).addMeetingTime(timeFrame, daysOfWeek);
				}
				
				lineScanner.close();
			}
			
			fileScanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Register all courses into the database
		for(Course course : courseMap.values()) {
			db.registerCourse(course);
		}
		
		//Sort collections to be in proper order
		db.sortDatabase();
				
		return db;
	}
	
}
