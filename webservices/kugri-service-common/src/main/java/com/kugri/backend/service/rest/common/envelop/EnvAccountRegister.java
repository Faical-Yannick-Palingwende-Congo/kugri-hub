/*=============================================================================================*/
/* Class            : EnvAccountRegister                                                       */
/*                                                                                             */
/* Description      : EnvAccountRegister class to load the account register envelop.           */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.kugri.backend.service.rest.common.endpoint.AbstractEndpoint;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * EnvAccountRegister class to load the account register envelop.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvAccountRegister {
	
	/** The envelope field email */
	public String email;
	/** The envelope field password */
	public String password;
	/** The envelope field hidden */
	public String hidden;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return email != null && password != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvAccountRegister() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the envelop payload
	 */
	public EnvAccountRegister(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.email = parts[0];
			this.password = parts[1];
			if(parts.length == 3){
				this.hidden = parts[2];
			}
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}
	
	/**
	 * Called to get the email field
	 * 
	 * @return String that is the email field
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Called to get the password field
	 * 
	 * @return String that is the envelope password field
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Called to get the hidden field
	 * 
	 * @return String that is the envelope hidden field
	 */
	public String getHidden() {
		return hidden;
	}
}
