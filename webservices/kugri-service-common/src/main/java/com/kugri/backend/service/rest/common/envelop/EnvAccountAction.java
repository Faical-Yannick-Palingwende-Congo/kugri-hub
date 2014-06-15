/*=============================================================================================*/
/* Class            : EnvAccountAccess                                                         */
/*                                                                                             */
/* Description      : EnvAccountAccess class to load the account access envelop.               */
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
 * EnvAccountAccess class to load the account access envelop.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvAccountAction {
	
	/** The envelope field access */
	public String access;
	/** The envelop field accounts */
	public String accounts;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return access != null && accounts != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvAccountAction() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the request payload
	 */
	public EnvAccountAction(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.access = parts[0];
			this.accounts = parts[1];
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}
	
	/**
	 * Called to get the access field
	 * 
	 * @return String that contains the envelop access field
	 */
	public String getAccess(){
		return this.access;
	}
	
	/**
	 * Called to get the accounts field
	 * 
	 * @return String that contains the envelop accounts field
	 */
	public String getAccounts() {
		return accounts;
	}
}
