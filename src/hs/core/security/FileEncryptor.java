package hs.core.security;

import static hs.core.security.HSSecurity.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileEncryptor {

	public static void writeEncryptedFile(String path, byte[] contents, char[] password) {
		//creates a salt specific to the file
		byte[] fileSalt = generateSalt();
		//encrypts the contents of the file
		byte[] encrypted = encryptData(password, fileSalt, contents);
		
		//writes out the encrypted file
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(path)));
			out.write(fileSalt);
			out.writeInt(contents.length);
			out.write(encrypted);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readEncryptedFile(String path, char[] password) {
		try {
			//reads in the encrypted file
			DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
			//gets the salt in the file
			byte[] fileSalt = in.readNBytes(HSSecurity.SALT_LEN);
			int contentsLength = in.readInt();
			
			try {
				//decrypts the file using the salt and the password given
				byte[] decrypted = decryptData(password, fileSalt, in.readAllBytes());
				in.close();
				return Arrays.copyOf(decrypted, contentsLength);
			} catch (Exception e) {
				//a wrong password will not open the file correctly (right hash not given to decrypt)
				System.out.println("Invalid decryption");
			}
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
