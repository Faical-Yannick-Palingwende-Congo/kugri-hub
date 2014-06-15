/*=============================================================================================*/
/* Class            : EnvAccountLost                                                           */
/*                                                                                             */
/* Description      : EnvAccountLost class to load the account lost envelop.                   */
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
 * EnvAccountLost class to load the account lost envelop.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvAccountLost {
	
	/** The envelope field email */
	public String email;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return email != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvAccountLost() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the envelop
	 */
	public EnvAccountLost(String payload) {
		super();
		try{
			this.email = payload;
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}
	
	/**
	 * Called to get the envelop email field
	 * 
	 * @return String that is the email field
	 */
	public String getEmail(){
		return this.email;
	}
}
