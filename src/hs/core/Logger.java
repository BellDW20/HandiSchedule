package hs.core;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class Logger  {
	
	private PrintWriter pw;
	private File file = new File(System.nanoTime() + ".txt");
	
	
	public Logger() {
		try {
			pw = new PrintWriter(file);
		}
		catch(Exception e) {
			System.out.println("Oops");
		}
	}
	
	public void addedClass(Course c) {
		try {
			pw.println(("Add: " + c.getUniqueString()));
			pw.flush();
		}
		catch(Exception e) {
			System.out.println("Oops");
		}
		
	}
	
	public void removedClass(Course c) {
		try {
			pw.println(("Remove: " + c.getUniqueString()));
			pw.flush();
		}
		catch(Exception e) {
			System.out.println("OOps");
		}
	}
	
	public void resolvedSchedule(Schedule s) {
		try {
			pw.println("Resolved: " + s.getTitle());
			pw.flush();
		}
		catch(Exception e) {
			System.out.println("Oops");
		}
	}
	
	public void changedScheduleName(Schedule s) {
		try {
			pw.println("TitleChange: " + s.getTitle());
			pw.flush();
		}catch(Exception e) {
			System.out.println("Oops");
		}
	}
	
}
