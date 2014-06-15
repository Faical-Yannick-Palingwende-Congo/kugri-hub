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

import java.util.Date;

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
public class EnvMessageSend {
	
	/** The envelope field access */
	public String access;
	/** The envelope field stamp */
	public String stamp;
	/** The envelope field receiver */
	public String receiver;
	/** The envelope field content */
	public String content;
	
	/**
	 * Called to check if all the mandatory fields have been provided
	 * 
	 * @return boolean that is true if they are all provided
	 */
	public boolean sanity() {
		return access != null && receiver != null && content != null;
	}
	
	/**
	 * Called to build the instance
	 * 
	 */
	public EnvMessageSend() {
		
	}

	/**
	 * Called to build the instance
	 * 
	 * @param payload the request payload
	 */
	public EnvMessageSend(String payload) {
		super();
		try{
			String parts[] = payload.split("#");
			this.stamp = AbstractEndpoint.dateFormat.format(new Date());
			this.access = parts[0];
			this.receiver = parts[1];
			this.content = parts[2];
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
	 * Called to get the receiver field
	 * 
	 * @return String that contains the envelop receiver field
	 */
	public String getReceiver(){
		return receiver;
	}
	
	/**
	 * Called to get the stamp field
	 * 
	 * @return String that contains the envelop stamp field
	 */
	public String getStamp(){
		return stamp;
	}

	/**
	 * Called to get the content field
	 * 
	 * @return String that contains the envelop content field
	 */
	public String getContent() {
		return content;
	}
}
