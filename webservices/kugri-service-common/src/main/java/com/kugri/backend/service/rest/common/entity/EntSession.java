/*=============================================================================================*/
/* Class            : EntSession                                                                  */
/*                                                                                             */
/* Description      : EntSession class for loading the session entity instances.                  */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.entity;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Session class for loading the session entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class EntSession {
	
	/** The Session entity id field*/
	public String id;
	/** The Session entity account field*/
	public String account;
	/** The Session entity stamp field*/
	public String stamp;
	/** The Session entity access field*/
	public String access;
	/** The Session entity status field*/
	public String status;
	
	/**
	 * Called to build the session's instance
	 * 
	 */
	public EntSession() {
		
	}
	
	public EntSession(String payload){
		String block[] = payload.split("%");
		this.id = block[0];
		this.account = block[1];
		this.stamp = block[2];
		this.access = block[3];
		this.status = block[4];
	}

	/**
	 * Called to build the session's instance
	 * 
	 * @param id the session instance identifier
	 * @param account the session instance account
	 * @param stamp the session instance stamp date time
	 * @param access the session instance access
	 * @param status the session instance status
	 */
	public EntSession(String id, String account, String stamp, String access, String status) {
		super();
		this.id = id;
		this.account = account;
		this.stamp = stamp;
		this.access = access;
		this.status = status;
	}
	
	/**
	 * Called to get the session instance's id
	 * 
	 * @return String that is the corresponding instance's id according to the remote db
	 */
	public String getId() {
		return id;
	}

	/**
	 * Called to get the session account identifier
	 * 
	 * @return String that is the session's account
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * Called to get the session stamp date and time
	 * 
	 * @return String that is the session's stamp date and time
	 */
	public String getStamp(){
		return stamp;
	}
	
	/**
	 * Called to get the session access key
	 * 
	 * @return String that is the session's access
	 */
	public String getAccess(){
		return access;
	}
	
	/**
	 * Called to get the session's status identifier
	 * 
	 * @return String that is the session's status identifier
	 */
	public String getStatus(){
		return status;
	}
}
