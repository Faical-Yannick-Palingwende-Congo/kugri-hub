/*=============================================================================================*/
/* Class            : EntMessage                                                               */
/*                                                                                             */
/* Description      : EntMessage class for loading the message entity instances.               */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.helloworld.entity;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * EntMessage class for loading the message entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class EntMessage {
	
	/** The Message entity id field*/
	public String id;
	/** The Message entity sender field*/
	public String sender;
	/** The Message entity receiver field*/
	public String receiver;
	/** The Message entity content field*/
	public String content;
	/** The Message entity stamp field*/
	public String stamp;
	/** The Message entity status field*/
	public String status;
	
	/**
	 * Called to build the account instance
	 * 
	 * @param payload the account formated string
	 */
	public EntMessage() {
		
	}

	/**
	 * Called to build the message instance
	 * 
	 * @param id the message instance identifier
	 * @param sender the sender of the message
	 * @param receiver the receiver of the message
	 * @param content the message content
	 * @param stamp the message stamp date and time
	 * @param status the message status
	 */
	public EntMessage(String id, String sender, String receiver, String content, String stamp, String status) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.stamp = stamp;
		this.status = status;
	}
	
	/**
	 * Called to get the message id
	 * 
	 * @return String that is the message identifier
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Called to get the message sender
	 * 
	 * @return String that contains the sender id
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Called to get the message receiver
	 * 
	 * @return String that contains the receiver id
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Called to get the message content
	 * 
	 * @return String that contains the message content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Called to get the message stamp
	 * 
	 * @return String that contains the message stamp
	 */
	public String getStamp(){
		return stamp;
	}
	
	/**
	 * Called to get the message status
	 * 
	 * @return String to get the message status
	 */
	public String getStatus() {
		return status;
	}
}
