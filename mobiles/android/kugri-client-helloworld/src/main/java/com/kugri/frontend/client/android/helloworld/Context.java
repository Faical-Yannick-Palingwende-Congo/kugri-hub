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
package com.kugri.frontend.client.android.helloworld;

import java.util.LinkedList;

import android.content.SharedPreferences;

import com.kugri.frontend.client.android.common.entity.Account;
import com.kugri.frontend.client.android.helloworld.utils.Logger;
import com.kugri.frontend.client.android.helloworld.entity.Message;

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
public class Context extends com.kugri.frontend.client.android.common.Context{
	
	/** The base app tag context */
	public static String TAG = "KUGRI-CLIENT-HELLOWORLD";
	
	/** The base accounts list instance context */
	public static LinkedList<Account> accounts = new LinkedList<Account>();
	
	/** The base messages list instance context */
	public static LinkedList<Message> messages = new LinkedList<Message>();
	
	/** The base recv messages list instance context */
	public static LinkedList<Integer> sentbox = new LinkedList<Integer>();
	
	/** The base sent messages list instance context */
	public static LinkedList<Integer> recvbox = new LinkedList<Integer>();
	
	/** The base messages list instance context */
	public static LinkedList<Integer> trashbox = new LinkedList<Integer>();
	
	/** The logger instance context */
	public static final Logger LOGGER = new Logger();
	
	public static String id2Email(String id){
		for(Account account: accounts){
			if(account.getId().equals(id))
				return account.getEmail();
		}
		return "Unknown";
	}
	
	/**
	 * Called to load accounts instances from a wrapper
	 * 
	 * @param wraper the accounts formated list
	 */
	public static LinkedList<Account> loadAccounts(String wrapper){
		String fields[] = wrapper.split("&");
		LinkedList<Account> accts = new LinkedList<Account>();
		if(!wrapper.equals("")){
			for(String account: fields){
				String parts[] = account.split("%");
				accts.add(new Account(parts[0], parts[1]));
			}
		}
		return accts;
	}
	
	/**
	 * Called to load accounts instances from a wrapper
	 * 
	 * @param wraper the accounts formated list
	 */
	public static LinkedList<Message> loadMessages(String wrapper){
		String fields[] = wrapper.split("&");
		LinkedList<Message> msgs = new LinkedList<Message>();
		sentbox = new LinkedList<Integer>();
		recvbox = new LinkedList<Integer>();
		int index = 0;
		if(!wrapper.equals("")){
			for(String msg: fields){
				String parts[] = msg.split("%");
				//(String id, String sender, String receiver, String content, String stamp, String status)
				LOGGER.pushDebugs("Action: "+parts[0] +" - Id: "+parts[1] +" - Stamp: "+parts[2] +" - Status: "+parts[3] +" - Other: "+parts[4] +" - Content: "+parts[5]);
				if(parts[0].equals("SENTBOX")){
					msgs.add(new Message(parts[1], Context.email, id2Email(parts[4]), parts[5], parts[2], parts[3]));
					sentbox.add(index);
				}else if(parts[0].equals("RECVBOX")){
					msgs.add(new Message(parts[1], id2Email(parts[4]), Context.email, parts[5], parts[2], parts[3]));
					recvbox.add(index);
				}
				index++;
			}
		}
		return msgs;
	}
	
	/**
	 * Called to do load some preference parameters
	 * 
	 * @param preferences the shared preferences instance
	 */
	public static void loadData(SharedPreferences preferences){
		accounts = loadAccounts(preferences.getString("accounts", ""));
	    messages = loadMessages(preferences.getString("messages", ""));
	}
	
	/**
	 * Called to do load some preference parameters
	 * 
	 * @param preferences the shared preferences instance
	 */
	public static void printData(SharedPreferences preferences){
		LOGGER.pushInfos("SharedPreferences - accounts: "+accounts);
		LOGGER.pushInfos("SharedPreferences - messages: "+messages);
	}
}
