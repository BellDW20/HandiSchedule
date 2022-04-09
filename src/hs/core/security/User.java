package hs.core.security;

import static hs.core.security.HSSecurity.*;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5968183860693676614L;
	
	private String username;
	private byte[] passwordHash;
	private byte[] passwordSalt;
	
	public User(String username, String password) {
		this.username = username;
		this.passwordSalt = generateSalt();
		this.passwordHash = generateSaltedHash(password.toCharArray(), passwordSalt);
		System.out.println("Created user:");
		System.out.println("	PHash: "+toHexString(passwordHash));
	}
	
	public boolean isValidLogin(String password) {
		byte[] passwordAttemptHash = generateSaltedHash(password.toCharArray(), passwordSalt);
		System.out.println("Attempting to login as user:");
		System.out.println("	AttemptHash: "+toHexString(passwordAttemptHash));
		
		int bytesMatching = 0;
		for(int i=0; i<passwordHash.length; i++) {
			if(passwordAttemptHash[i] == passwordHash[i]) {
				bytesMatching++;
			}
		}
		
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
