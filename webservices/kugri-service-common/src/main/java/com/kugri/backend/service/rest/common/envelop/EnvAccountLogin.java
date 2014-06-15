/*=============================================================================================*/
/* Class            : EnvAccountLogin                                                          */
/*                                                                                             */
/* Description      : EnvAccountLogin class to load the account login envelop.                 */
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
 * EnvAccountLogin class to load the account login envelop. 
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvAccountLogin {
	
	/** The envelope field email */
	public String email;
	/** The envelope field password */
	public String password;
	
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
	public EnvAccountLogin() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the envelop payload
	 */
	public EnvAccountLogin(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.email = parts[0];
			this.password = parts[1];
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}

	/**
	 * Called to get the envelop email field
	 * 
	 * @return String that is the email field 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Called to get the envelop password field
	 * 
	 * @return String that is the password field
	 */
	public String getPassword() {
		return password;
	}
}
