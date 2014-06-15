/*=============================================================================================*/
/* Class            : EFResponse                                                               */
/*                                                                                             */
/* Description      : EFResponse class to load the response envelope to be sent.               */
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

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * EFResponse class to load the response envelope to be sent. 
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class EFResponse {
	
	/** The response instance stamp */
	public String stamp;
	/** The response instance payload */
	public String payload;

	/**
	 * Called to build the instance
	 * 
	 */
	public EFResponse() {
	}

	/**
	 * Called to build the instance
	 * 
	 * @param stamp the response stamp
	 * @param payload the response payload
	 */
	public EFResponse(String stamp, String payload) {
		this.stamp = stamp;
		this.payload = payload;
	}

	/**
	 * Called to get the instance stamp
	 * 
	 * @return String that contains the response stamp
	 */
	public String getStamp() {
		return stamp;
	}

	/**
	 * Called to get the payload
	 * 
	 * @return String that is the payload content
	 */
	public String getContent() {
		return payload;
	}
}
