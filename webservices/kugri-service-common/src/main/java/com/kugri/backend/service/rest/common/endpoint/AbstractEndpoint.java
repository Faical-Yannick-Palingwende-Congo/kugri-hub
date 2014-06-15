/*=============================================================================================*/
/* Class            : AbstractEndpoint                                                         */
/*                                                                                             */
/* Description      : AbstractEndpoint class to easy the endpoints manipulations.              */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.endpoint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.kugri.backend.service.rest.common.utils.AES;
import com.kugri.backend.service.rest.common.utils.Configuration;
import com.kugri.backend.service.rest.common.utils.DBAdapter;
import com.kugri.backend.service.rest.common.utils.Logger;
import com.kugri.backend.service.rest.common.utils.exception.TechnicalUtilsException;
import com.kugri.backend.service.rest.common.entity.EntAccount;
import com.kugri.backend.service.rest.common.entity.EntScope;
import com.kugri.backend.service.rest.common.entity.EntSession;
import com.kugri.backend.service.rest.common.entity.EntStatus;
import com.kugri.backend.service.rest.common.entity.Index;
import com.kugri.backend.service.rest.common.envelop.Request;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractEndpoint class to easy the endpoints manipulations.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractEndpoint {
	
	/** The logger instance */
	public static final Logger LOGGER = new Logger();
	/** The date format instance */
	public static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
	/** The accounts list instance */
	public LinkedList<EntAccount> accounts;
	/** The statusses list instance */
	public LinkedList<EntStatus> statuses;
	/** The sessions list instance */
	public LinkedList<EntSession> sessions;
	/** The scopes list instance */
	public LinkedList<EntScope> scopes;
	/** The indexes list instance */
	public LinkedList<Index> indexes;
	
	/** The endpoint action scope */
	public int actionScope = 3;//ADMIN=3 - USER=2 - PUBLIC=1
	/** The endpoint status action entered parameter to check sanity */
	public boolean entered = true;
	
	/** The configuration loader instance */
	static Configuration confloader = null;
	
	/** The instanciation of the properties configuration */
	static {
		confloader = new Configuration();
		try {
			confloader.loadProperties("internal.service.properties");
		} catch (TechnicalUtilsException e) {
			LOGGER.pushErrors(e, "Internal properties file not found.");
		}
	}
	
	/** The database base from the configuration loader */
	final String DATABASE_NAME = confloader.getLoader().getProperty("database.name.value");
	
	/** The database login name value */
	final String LOGIN_NAME = confloader.getLoader().getProperty("user.login.value");
	
	/** The database password value */
	final String LOGIN_PASSWORD = confloader.getLoader().getProperty("user.password.value");

	/** The database adapter instance */
	public DBAdapter adapter = new DBAdapter("jdbc:mysql://localhost/" + DATABASE_NAME, LOGIN_NAME, LOGIN_PASSWORD, LOGGER);
	
	/**
	 * Called to get the hidden field
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<Index> that contains a loaded instance of indexes list
	 */
	public LinkedList<Index> StringToIndexes(String content) {
		String rows[] = content.split("\n");
		LinkedList<Index> contents = new LinkedList<Index>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Index index = new Index(fields[0], fields[1], fields[2]);
			contents.add(index);
		}
		return contents;
	}
	
	/**
	 * Called to get the status from its id
	 * 
	 * @param id the identifier of the status
	 * @return String that represents the status
	 */
	public String getStatusfromId(String id){
		for(EntStatus status: this.statuses){
			if(status.getId().equals(id)) return status.getValue();
		}
		return "";
	}
	
	/**
	 * Called to get the scope from its id
	 * 
	 * @param id the identifier of the scope
	 * @return String that represents the scope
	 */
	public String getscopefromId(String id){
		for(EntScope scope: this.scopes){
			if(scope.getId().equals(id)) return scope.getValue();
		}
		return "NONE";
	}
	
	/**
	 * Called to check if the action is granted
	 * 
	 * @param userScope the user rights level
	 * @return boolean that is true if the user is granted to access this action
	 */
	public boolean isActionGrant(int userScope){
		return userScope >= actionScope;
	}
	
	/**
	 * Called to check if in the users there is more than one administrator
	 *
	 * @return boolean that is true if there is none
	 */
	public boolean isFirstAdmin(){
		int number = 0;
		for(EntAccount account: this.accounts){
			if(getscopefromId(account.getScope()).equals("ADMIN")) number++;
		}
		return (number == 0);
	}
	
	/**
	 * Called to get the status id from its value
	 * 
	 * @param value the value of the status
	 * @return String that contains the id of this status value
	 */
	public String getIdfromStatus(String value){
		for(EntStatus status: this.statuses){
			if(status.getValue().equals(value)) return status.getId();
		}
		return "0";
	}
	
	/**
	 * Called to load the database formated status raws
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<EntStatus> that contains a loaded instance of statuses list
	 */
	public LinkedList<EntStatus> StringToStatuses(String content) {
		String rows[] = content.split("\n");
		LinkedList<EntStatus> contents = new LinkedList<EntStatus>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			EntStatus status = new EntStatus(fields[0], fields[1]);
			contents.add(status);
		}
		return contents;
	}
	
	/**
	 * Called to load the database formated scopes raws
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<EntScope> that contains a loaded instance of scopes list
	 */
	public LinkedList<EntScope> StringToScopes(String content) {
		String rows[] = content.split("\n");
		LinkedList<EntScope> contents = new LinkedList<EntScope>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			EntScope scope = new EntScope(fields[0], fields[1]);
			contents.add(scope);
		}
		return contents;
	}
	
	/**
	 * Called to get the session from the account id
	 * 
	 * @param id the identifier of the account
	 * @return EntSession that contains the retrieved session instance
	 */
	public EntSession getSessionfromAccountId(String id){
		for(EntSession session: this.sessions){
			if(session.getAccount().equals(id)) return session;
		}
		return new EntSession("0", "0", "", "", "0");
	}
	
	/**
	 * Called to get the session from its access key
	 * 
	 * @param access the session access key
	 * @return EntSession that contains the retrieved session instance
	 */
	public EntSession getSessionfromAccessKey(String access){
		for(EntSession session: this.sessions){
			if(session.getAccess().equals(access)) return session;
		}
		return new EntSession("0", "0", "", "", "0");
	}
	
	/**
	 * Called to get the session from its id
	 * 
	 * @param id the session identifier
	 * @return EntSession that contains the retrieved session instance
	 */
	public EntSession getSessionfromId(String id){
		for(EntSession session: this.sessions){
			if(session.getId().equals(id)) return session;
		}
		return new EntSession("0", "0", "", "", "0");
	}
	
	/**
	 * Called to load the database formated sessions raws
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<EntSession> that contains a loaded instance of sessions list
	 */
	public LinkedList<EntSession> StringToSessions(String content) {
		String rows[] = content.split("\n");
		LinkedList<EntSession> contents = new LinkedList<EntSession>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			EntSession session = new EntSession(fields[0], fields[1], fields[2], fields[3], fields[4]);
			contents.add(session);
		}
		return contents;
	}
	
	/**
	 * Called to get the account from its id
	 * 
	 * @param id the account's identifier
	 * @return EntAccount that contains the retrieved account
	 */
	public EntAccount getAccountfromId(String id){
		for(EntAccount account: this.accounts){
			if(account.getId().equals(id)) return account;
		}
		return new EntAccount("0", "", "", "0");
	}
	
	/**
	 * Called to get the account's id from its email
	 * 
	 * @param email the account's mail
	 * @return int that is the account's id
	 */
	public int email2Account(String email){
		int id = 0;
		for(EntAccount account: this.accounts){
			if(account.getEmail().equals(email)){
				id = Integer.parseInt(account.getId());
				break;
			}
		}
		return id;
	}
	
	/**
	 * Called to check the access meaning that if the account session is LOGIN
	 * 
	 * @param access the session access key
	 * @return boolean that is true if the session status is LOGIN
	 */
	public boolean checkAccess(String access){
		for(EntSession session: this.sessions){
			if(session.getAccess().equals(access)){
				String status = getStatusfromId(session.getStatus());
				if(status != null)
					if(status.equals("LOGIN")) return true;
					else return false;
				else return false;
			}
		}
		return false;
	}
	
	/**
	 * Called to login with the email and the password
	 * 
	 * @param email the account email
	 * @param password the account password
	 * @return boolean that is true if the login succeed
	 */
	public boolean login(String email, String password){
		for(EntAccount account: this.accounts){
			if(account.getEmail().equals(email) && account.getPassword().equals(generateKey(password, "ACCOUNTHELLOWORLD2014"))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Called to load the database formated accounts raws
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<EntAccount> that contains a loaded instance of accounts list
	 */
	public LinkedList<EntAccount> StringToAccounts(String content) {
		String rows[] = content.split("\n");
		LinkedList<EntAccount> contents = new LinkedList<EntAccount>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			EntAccount account = new EntAccount(fields[0], fields[1], fields[2], fields[3]);
			contents.add(account);
		}
		return contents;
	}
	
	/**
	 * Called to get the table index
	 * 
	 * @param table that is the table name
	 * @return Index that is the table instance
	 */
	public Index getIndex(String table){
		Index index = null;
		for(Index idx: this.indexes){
			if(idx.getTable().equals(table)){
				index = idx;
			}
		}
		return index;
	}
	
	/**
	 * Called to send an email
	 * 
	 * @param type the email type that can be html or text
	 * @param from the sender email
	 * @param to the receiver email
	 * @param subject the email subject
	 * @param content the email content
	 * @return LinkedList<Index> that contains a loaded instance of indexes list
	 */
	public void sendMail(String type, String from, String to, String subject, String content) {
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			if (type.equals("html"))
				message.setHeader("Content-Type", "text/html");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(from));
			message.setSubject(subject);
			if (type.equals("html"))
				message.setContent(content, "text/html; charset=ISO-8859-1");
			else
				message.setText(content);
			Transport.send(message);
			LOGGER.pushDebugs("Sent message successfully....");
		} catch (MessagingException mex) {
			LOGGER.pushWarnings(mex,"Email sending failure. Message problem." + mex.getMessage());
		}
	}
	
	/**
	 * Called to do an aes 256 encryption
	 * 
	 * @param plaintext the clear text to encrypt
	 * @param encryptionKey the key to use for the encryption
	 * @return String that contains the encrypted text
	 */
	public String aes256Encrypt(String plaintext, String encryptionKey){
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
	public String aes256Decrypt(String encrypted, String encryptionKey){
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
	public String packager(String data){
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
	public String generateKey(String unique, String mixer) {
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
	public String sha256(String clear, String sel) {
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
	public String byteToHex(byte[] bits) {
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
	 * Called to do load some preference parameters
	 * 
	 * @param preferences the shared preferences instance
	 */
	public byte[] HexTobyte (String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }
	
	@POST
	@Path("/status")
	@Consumes({ "application/xml", "application/json" })
	public abstract Response status(Request request);
}
