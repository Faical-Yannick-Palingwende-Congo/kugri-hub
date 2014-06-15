/*=============================================================================================*/
/* Class            : Context                                                                  */
/*                                                                                             */
/* Description      : Context class for the configuration loading.                             */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Locale;

import android.content.SharedPreferences;

import com.kugri.frontend.client.android.common.utils.AES;
import com.kugri.frontend.client.android.common.utils.Logger;
import com.kugri.frontend.client.android.common.entity.Scope;
import com.kugri.frontend.client.android.common.entity.Status;
import com.kugri.frontend.client.android.common.entity.Index;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Context class for the configuration loading.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Context {
	/** The logger instance context */
	public static final Logger LOGGER = new Logger();
	/** The date format instance context */
	public static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS",Locale.US);
	
	/** The account access context */
	public static String access = new String("");
	/** The account email context */
	public static String email = new String("");
	/** The account password context */
	public static String password = new String("");
	/** The account scope context */
	public static String scope = new String("");
	/** The account access status */
	public static String status = new String("");

	/** The base statuses list instance context */
	public static LinkedList<Status> statuses = new LinkedList<Status>();
	/** The base scopes list instance context */
	public static LinkedList<Scope> scopes = new LinkedList<Scope>();
	/** The base indexes list instance context */
	public static LinkedList<Index> indexes = new LinkedList<Index>();
	
	/** The preferences file name context */
	public static final String PREFS_NAME = "Kugri";
	/** The base app tag context */
	public static String TAG = "KUGRI-CLIENT-COMMON";
	
	/** The base url of the service */
	public static String baseUrl = "";
	
	/**
	 * Called to get the status from its id
	 * 
	 * @param id the identifier of the status
	 * @return String that contains the value of the status
	 */
	public static String getStatusfromId(String id){
		for(Status status: statuses){
			if(status.getId().equals(id)) return status.getValue();
		}
		return "";
	}
	
	/**
	 * Called to get the scope from its id
	 * 
	 * @param id the identifier of the scope
	 * @return String that contains the value of the scope
	 */
	public static String getscopefromId(String id){
		for(Scope scope: scopes){
			if(scope.getId().equals(id)) return scope.getValue();
		}
		return "NONE";
	}
	
	/**
	 * Called to get the scope id from its value
	 * 
	 * @param value the value of the scope
	 * @return String that contains the id of the scope
	 */
	public static String getIdfromStatus(String value){
		for(Status status: statuses){
			if(status.getValue().equals(value)) return status.getId();
		}
		return "0";
	}
	
	/** 
	 * Called to push the statuses into the list
	 * 
	 * */
	public static void StringToStatuses() {
		statuses.add(new Status("1", "REGISTER"));
		statuses.add(new Status("2", "RECOVER"));
		statuses.add(new Status("3", "LOGIN"));
		statuses.add(new Status("4", "LOGOUT"));
		statuses.add(new Status("5", "SENT"));
		statuses.add(new Status("6", "SEEN"));
		statuses.add(new Status("7", "TRASH"));
	}
	
	/** 
	 * Called to push the scopes into the list
	 * 
	 * */
	public static void StringToScopes() {
		scopes.add(new Scope("1", "PUBLIC"));
		scopes.add(new Scope("2", "USER"));
		scopes.add(new Scope("3", "ADMIN"));
	}
	
//	/**
//	 * Called to get the account from its id
//	 * 
//	 * @param id the account's id
//	 * @return Account that contains the instance for that id
//	 */
//	public static Account getAccountfromId(String id){
//		for(Account account: accounts){
//			if(account.getId().equals(id)) return account;
//		}
//		return new Account("0", "", "", "0");
//	}
	
	/**
	 * Called to get the index of the table
	 * 
	 * @param table the name of the index table
	 * @return Index that contains the index refering that table
	 */
	public static Index getIndex(String table){
		Index index = null;
		for(Index idx: indexes){
			if(idx.getTable().equals(table)){
				index = idx;
			}
		}
		return index;
	}
	
	/**
	 * Called to do an aes 256 encryption
	 * 
	 * @param plaintext the clear text to encrypt
	 * @param encryptionKey the key to use for the encryption
	 * @return String that contains the encrypted text
	 */
	public static String aes256Encrypt(String plaintext, String encryptionKey){
		byte[] cipher;
		String encrypted = "";
		try {
			cipher = AES.encrypt(packager(plaintext), encryptionKey);
			encrypted = byteToHex(cipher);
		} catch (Exception e) {
			LOGGER.pushErrors(e, "Encryption failed.");
		}
		return encrypted;
	}
	
	/**
	 * Called to do an aes 256 decryption
	 * 
	 * @param encrypted the encrypted text to decrypt
	 * @param encryptionKey the key to use for the encryption
	 * @return String that contains the clear text
	 */
	public static String aes256Decrypt(String encrypted, String encryptionKey){
	    String decrypted = "";
		try {
			decrypted = AES.decrypt(HexTobyte(encrypted), encryptionKey);
		} catch (Exception e) {
			LOGGER.pushErrors(e, "Decryption failed.");
		}
		return decrypted.split("~")[0];
	}
	
	/**
	 * Called to do some character reverting for the encryption
	 * 
	 * @param data the content to fill into
	 * @return String that contains the data with some hexadecimal separated with 0's
	 */
	public static String packager(String data){
		int complete = data.length() % 16;
		String result = data;
		if(complete >= 1) result+= "~";
		for(int i = 0; i<(15-complete);i++){
		  result+="0";
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * Called to do an sha 256 encryption
	 * 
	 * @param unique the clear text to encrypt
	 * @param mixer the key to use for the encryption
	 * @return String that contains the encrypted text
	 */
	public static String generateKey(String unique, String mixer) {
		String newKey = sha256(unique, mixer);
		return newKey;
	}
	
	/**
	 * Called to do perform the actual sha 256 encryption
	 * 
	 * @param clear the clear text to encrypt
	 * @param sel the key to use for the encryption
	 * @return String that contains the encrypted text
	 */
	public static String sha256(String clear, String sel) {
		String hash = clear;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			hash = byteToHex(md.digest((clear + sel).getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hash;
	}

	/**
	 * Called to convert a binary array to an hexadecimal string
	 * 
	 * @param bits the binary array
	 * @return String that contains the hexadecimal corresponding string
	 */
	public static String byteToHex(byte[] bits) {
		if (bits == null) {
			return null;
		}
		StringBuffer hex = new StringBuffer(bits.length * 2); // encod(1_bit) =>
																// 2 digits
		for (int i = 0; i < bits.length; i++) {
			if (((int) bits[i] & 0xff) < 0x10) { // 0 < .. < 9
				hex.append("0");
			}
			hex.append(Integer.toString((int) bits[i] & 0xff, 16)); // [(bit+256)%256]^16
		}
		return hex.toString();
	}
	
	/**
	 * Called to do convert an hexadecimal string to a binary array
	 * 
	 * @param s the hexadecimal string
	 * @return byte[] that contains the binary array
	 */
	public static byte[] HexTobyte (String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }
	
	/**
	 * Called to do load some preference parameters
	 * 
	 * @param preferences the shared preferences instance
	 */
	public static void loadData(SharedPreferences preferences){
	    access = preferences.getString("key", "");
	    email = preferences.getString("email", "");
	    password = preferences.getString("password", "");
	    scope = preferences.getString("scope", "0");
	    status = preferences.getString("status", "0");
	}
	
	/**
	 * Called to do load some preference parameters
	 * 
	 * @param preferences the shared preferences instance
	 */
	public static void printData(SharedPreferences preferences){
		LOGGER.pushInfos("SharedPreferences - key: "+access);
		LOGGER.pushInfos("SharedPreferences - email: "+email);
		LOGGER.pushInfos("SharedPreferences - password: "+password);
		LOGGER.pushInfos("SharedPreferences - scope: "+scope);
		LOGGER.pushInfos("SharedPreferences - status: "+status);
	}
}
