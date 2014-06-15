/*=============================================================================================*/
/* Class            : EnvAccountRecover                                                        */
/*                                                                                             */
/* Description      : EnvAccountRecover class to load the account recover envelop.             */
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
 * EnvAccountRecover class to load the account recover envelop.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvAccountRecover {
	
	/** The envelope field access */
	public String access;
	/** The envelope field password */
	public String password;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return access != null && password != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvAccountRecover() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the envelope payload
	 */
	public EnvAccountRecover(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.access = parts[0];
			this.password = parts[1];
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}

	/**
	 * Called to get the envelop access field
	 * 
	 * @return String that is the access field
	 */
	public String getAccess() {
		return access;
	}

	/**
	 * Called to get the envelop password field
	 * 
	 * @return String that is the envelope password field
	 */
	public String getPassword() {
		return password;
	}
}
