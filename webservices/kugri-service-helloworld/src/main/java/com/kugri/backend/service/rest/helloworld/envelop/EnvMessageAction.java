/*=============================================================================================*/
/* Class            : EnvMessageSend                                                           */
/*                                                                                             */
/* Description      : EnvMessageSend class to load the message send envelop.                   */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.helloworld.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.kugri.backend.service.rest.common.endpoint.AbstractEndpoint;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * EnvMessageSend class to load the message send envelop.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EnvMessageAction {
	
	/** The envelope field access */
	public String access;
	/** The messages ids */
	public String messages;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return access != null && messages != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvMessageAction() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the request payload
	 */
	public EnvMessageAction(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.access = parts[0];
			this.messages = parts[1];
		}catch(Exception e){
			AbstractEndpoint.LOGGER.pushErrors(e, "Error message to log.");
		}
	}
	
	/**
	 * Called to get the access field
	 * 
	 * @return String that contains the envelop access field
	 */
	public String getAccess() {
		return access;
	}
	
	/**
	 * Called to get the messages ids
	 * 
	 * @return String that contains the messages ids to perform the action on
	 */
	public String getMessages(){
		return messages;
	}
}
