/*=============================================================================================*/
/* Class            : Account                                                                  */
/*                                                                                             */
/* Description      : Account class for loading the account entity instances.                  */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.entity;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Account class for loading the account entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Account {
	
	/** The Account entity id field*/
	public String id;
	/** The Account entity email field*/
	public String email;
	/** The Account entity password field*/
	public String password;
	/** The Account entity scope field*/
	public String scope;
	
	/**
	 * Called to build the account instance
	 * 
	 */
	public Account() {
		
	}
	
	/**
	 * Called to build the account instance
	 * 
	 * @param payload the formated content of the account instance
	 */
	public Account(String payload){
		String block[] = payload.split("%");
		this.id = block[0];
		this.email = block[1];
		this.password = block[2];
		this.scope = block[3];
	}
	
	/**
	 * Called to build the account instance
	 * 
	 * @param id the identifier of the account instance on the remote db
	 * @param email the account mail
	 */
	public Account(String id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	/**
	 * Called to build the account instance
	 * 
	 * @param id the identifier of the account instance on the remote db
	 * @param email the account mail
	 * @param password the account hashed password SHA-256
	 * @param scope the account rights scope
	 */
	public Account(String id, String email, String password, String scope) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.scope = scope;
	}
	
	/**
	 * Called to get the account instance id
	 * 
	 * @return String that is the id of the account instance
	 */
	public String getId() {
		return id;
	}

	/**
	 * Called to get the account instance mail
	 * 
	 * @return String that is the account's mail
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Called to get the account instance hashed SHA-256 password 
	 * 
	 * @return String that is the hashed SHA-256 password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Called to get the account instance rights scope
	 * 
	 * @return String that is the acount's scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Called to set the account instance mail
	 * 
	 * @param email that is the account's mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Called to set the account instance hashed SHA-256 password 
	 * 
	 * @param pasword that is the hashed SHA-256 password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Called to set the account instance rights scope
	 * 
	 * @param scope that is the acount's scope
	 */
	public void getScope(String scope) {
		this.scope = scope;
	}
}
