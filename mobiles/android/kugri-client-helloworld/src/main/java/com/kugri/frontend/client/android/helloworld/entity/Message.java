/*=============================================================================================*/
/* Class            : Message                                                                  */
/*                                                                                             */
/* Description      : Message class for loading the message entity instances.                  */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.helloworld.entity;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Message class for loading the account entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Message {
	
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
	 */
	public Message() {
		
	}
	
	/**
	 * Called to build the message instance
	 * 
	 * @param payload the formated content of the message instance
	 */
	public Message(String payload){
		String block[] = payload.split("%");
		this.id = block[0];
		this.sender = block[1];
		this.receiver = block[2];
		this.content = block[3];
		this.stamp = block[4];
		this.status = block[5];
	}
	
	/**
	 * Called to build the message instance
	 * 
	 * @param id the identifier of the message instance on the remote db
	 * @param sender the message sender
	 * @param receiver the message receiver
	 * @param content the message content
	 * @param stamp the message stamp date time
	 * @param status the message status
	 */
	public Message(String sender, String receiver, String content, String stamp, String status) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.stamp = stamp;
		this.status = status;
	}

	/**
	 * Called to build the message instance
	 * 
	 * @param sender the message sender
	 * @param receiver the message receiver
	 * @param content the message content
	 * @param stamp the message stamp date time
	 * @param status the message status
	 */
	public Message(String id, String sender, String receiver, String content, String stamp, String status) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.stamp = stamp;
		this.status = status;
	}
	
	/**
	 * Called to get the message instance id
	 * 
	 * @return String that is the id of the message instance
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Called to get the message instance sender id
	 * 
	 * @return String that is the id of the message instance sender
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Called to get the account instance receiver id
	 * 
	 * @return String that is the id of the message instance receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Called to get the message instance content
	 * 
	 * @return String that is the content of the message instance
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Called to get the message instance stamp
	 * 
	 * @return String that is the stamp of the message instance
	 */
	public String getStamp(){
		return stamp;
	}
	
	/**
	 * Called to get the message instance status
	 * 
	 * @return String that is the status of the message instance
	 */
	public String getStatus() {
		return status;
	}
}
