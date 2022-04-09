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
		byte[] fileSalt = generateSalt();
		byte[] encrypted = encryptData(password, fileSalt, contents);
		
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
			DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
			byte[] fileSalt = in.readNBytes(HSSecurity.SALT_LEN);
			int contentsLength = in.readInt();
			
			try {
				byte[] decrypted = decryptData(password, fileSalt, in.readAllBytes());
				return Arrays.copyOf(decrypted, contentsLength);
			} catch (Exception e) {
				System.out.println("Invalid decryption");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
