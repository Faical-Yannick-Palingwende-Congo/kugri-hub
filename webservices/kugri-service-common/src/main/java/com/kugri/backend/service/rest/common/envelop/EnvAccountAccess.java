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
public class EnvAccountAccess {
	
	/** The envelope field access */
	public String access;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return access != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvAccountAccess() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the request payload
	 */
	public EnvAccountAccess(String payload) {
		super();
		try{
			this.access = payload;
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
}
