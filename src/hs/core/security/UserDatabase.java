package hs.core.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import hs.core.Schedule;

public class UserDatabase implements Serializable {

	private static final long serialVersionUID = -1537394738210338622L;
	
	public static final int REGISTER_USERNAME_SUCCESS = 0;
	public static final int REGISTER_USERNAME_TAKEN = 1;
	public static final int REGISTER_PASSWORD_MISMATCH = 2;
	
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_INCORRECT_USERNAME = 1;
	public static final int LOGIN_INCORRECT_PASSWORD = 2;
	
	private String databasePath;
	private HashMap<String, User> users;
	
	private UserDatabase(String databasePath) {
		this.databasePath = databasePath;
		this.users = new HashMap<String, User>();
	}
	
	public int registerUser(String username, String password, String confirmPassword) {
		//Reject registrations with the same name or incorrect 2nd password (not implemented on login page)
		if(users.containsKey(username)) {
			return REGISTER_USERNAME_TAKEN;
		} else if(!password.equals(confirmPassword)) {
			return REGISTER_PASSWORD_MISMATCH;
		}
		
		//add them to the database
		users.put(username, new User(username, password));
		
		return REGISTER_USERNAME_SUCCESS;
	}
	
	public int isValidLogin(String username, String password) {
		//if the user doesn't exist, the login is invalid
		if(!users.containsKey(username)) {
			return LOGIN_INCORRECT_USERNAME;
		}
		
		//otherwise, return if the user's login was correct
		return (users.get(username).isValidLogin(password) ? LOGIN_SUCCESS : LOGIN_INCORRECT_PASSWORD);
	}
	
	public boolean saveEncryptedSchedule(Schedule schedule, String path, String username, String password) {
		//require authentification
		if(isValidLogin(username, password) != LOGIN_SUCCESS) {
			return false;
		}
		
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			
			//serialize the schedule to a byte array
			objOut.writeObject(schedule);
			byte[] unencryptedSchedule = byteOut.toByteArray();
			
			//encypt the serialized byte array and write it to a file
			FileEncryptor.writeEncryptedFile(path, unencryptedSchedule, password.toCharArray());
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Schedule loadEncryptedSchedule(String path, String username, String password) {
		//require authentification
		if(isValidLogin(username, password) != LOGIN_SUCCESS) {
			return null;
		}
		
		try {
			//unencrypt the encrypted schedule file to a byte array
			byte[] unencryptedSchedule = FileEncryptor.readEncryptedFile(path, password.toCharArray());
			
			ByteArrayInputStream byteIn = new ByteArrayInputStream(unencryptedSchedule);
			ObjectInputStream objIn = new ObjectInputStream(byteIn);
			
			//deserialize the schedule from the byte array
			Schedule schedule = (Schedule)objIn.readObject();
			return schedule;
		} catch(Exception e) {
			System.out.println("Schedule decryption invalid (wrong credentials)");
		}
		
		return null;
	}
	
	public void listUsers() {
		System.out.println("Users\n============================================================");
		for(User user : users.values()) {
			System.out.println(user);
		}
		System.out.println("============================================================");
	}
	
	public void saveDatabase() {
		try {
			//Serialize this database and save it to a file
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(databasePath)));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static UserDatabase loadDatabase(String databasePath) {
		try {
			//Deserialize a database from a file and return it
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(databasePath)));
			UserDatabase db = (UserDatabase)in.readObject();
			in.close();
			return db;
		} catch (IOException | ClassNotFoundException e) {
			if(e instanceof ClassNotFoundException) {
				e.printStackTrace();
			}
		}
		return new UserDatabase(databasePath);
	}
	
}
