package hs.core.security;

import static hs.core.security.HSSecurity.*;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5968183860693676614L;
	
	private String username;
	private byte[] passwordHash; //Hash from the users password and stored salt
	private byte[] passwordSalt; //The stored salt for this user (to combine with password on login)
	
	public User(String username, String password) {
		this.username = username;
		this.passwordSalt = generateSalt(); //creates a salt
		//stores the hash of the password and salt for verification
		this.passwordHash = generateSaltedHash(password.toCharArray(), passwordSalt);
	}
	
	public boolean isValidLogin(String password) {
		//use the given password and saved salt to generate a hash
		byte[] passwordAttemptHash = generateSaltedHash(password.toCharArray(), passwordSalt);
		
		//if the hash matches the one from when the account was created...
		int bytesMatching = 0;
		for(int i=0; i<passwordHash.length; i++) {
			if(passwordAttemptHash[i] == passwordHash[i]) {
				bytesMatching++;
			}
		}
		
		//then the login is valid
		return (bytesMatching == passwordHash.length);
	}

	public String getUsername() {
		return username;
	}
	
	public byte[] getPasswordHash() {
		return passwordHash;
	}
	
	public byte[] getPassowrdSalt() {
		return passwordSalt;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Username: ");
		sb.append(username);
		sb.append("\n    Password Salt: ");
		sb.append(toHexString(passwordSalt));
		sb.append("\n    Password Hash: ");
		sb.append(toHexString(passwordHash));
			
		return sb.toString();
	}
	
}
