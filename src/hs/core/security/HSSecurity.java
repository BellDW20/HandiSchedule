package hs.core.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class HSSecurity {

	public static final int PBKDF2_ITERATIONS = 65536;
	public static final int PBKDF2_LEN = 256;
	public static final int SALT_LEN = 64;
	
	public static byte[] generateSalt() {
		//creates a salt of a given length
		//using secure random number generation
		SecureRandom srand = new SecureRandom();
		byte[] bytes = new byte[SALT_LEN];
		srand.nextBytes(bytes);
		return bytes;
	}
	
	public static byte[] generateSaltedHash(char[] password, byte[] salt) {
		try {
			//creates a secret key using the passowrd and salt
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec keySpec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, PBKDF2_LEN);
			SecretKey key = keyFactory.generateSecret(keySpec);
			//returns the encoding of the key
			return key.getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SecretKey getEncryptionKey(char[] password, byte[] salt) {
		//creates a password based encryption key
		return new SecretKeySpec(generateSaltedHash(password, salt), "AES");
	}
	
	public static byte[] encryptData(char[] password, byte[] salt, byte[] data) {
		//encrypts a byte array with a password and salt
		return useCipher(password, salt, data, Cipher.ENCRYPT_MODE);
	}
	
	public static byte[] decryptData(char[] password, byte[] salt, byte[] data) {
		//decrypts a byte array with a password and salt
		return useCipher(password, salt, data, Cipher.DECRYPT_MODE);
	}
	
	private static byte[] useCipher(char[] password, byte[] salt, byte[] data, int cipherMode) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKey encryptionKey = getEncryptionKey(password, salt);
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(salt, 0, 16));
			cipher.init(cipherMode, encryptionKey, iv);
			
			byte[] padded = Arrays.copyOf(data, (int)(Math.ceil(data.length/16.0)*16));
			return cipher.doFinal(padded);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash); 
  
        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16));
  
        return hexString.toString(); 
    }
	
}
