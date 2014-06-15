/*=============================================================================================*/
/* Class            : Request                                                                  */
/*                                                                                             */
/* Description      : Request class to load the received request.                              */
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
 * Request class to load the received request.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@XmlRootElement
public class Request {
	
	/** The request instance stamp */
	public String stamp;
	/** The request instance payload */
	public String payload;
	
	/**
	 * Called to encrypt a text using aes
	 * 
	 * @param plainText the clear text
	 * @param encryptionKey the aes encryption key
	 * @return byte[] that contains the binary array of the encrypted text
	 * @throws Exception if the encryption fails
	 */
	public Request() {
		
	}

	/**
	 * Called to encrypt a text using aes
	 * 
	 * @param plainText the clear text
	 * @param encryptionKey the aes encryption key
	 * @return byte[] that contains the binary array of the encrypted text
	 * @throws Exception if the encryption fails
	 */
	public String getStamp() {
		return stamp;
	}

	/**
	 * Called to encrypt a text using aes
	 * 
	 * @param plainText the clear text
	 * @param encryptionKey the aes encryption key
	 * @return byte[] that contains the binary array of the encrypted text
	 * @throws Exception if the encryption fails
	 */
	public String getPayload() {
		return payload;
	}
}
