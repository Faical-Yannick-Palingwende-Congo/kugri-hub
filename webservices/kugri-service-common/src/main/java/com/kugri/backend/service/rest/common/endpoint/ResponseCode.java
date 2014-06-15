/*=============================================================================================*/
/* Class            : ResponseCode                                                             */
/*                                                                                             */
/* Description      : ResponseCode class to handle the response codes.                         */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.endpoint;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * ResponseCode class to handle the response codes.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class ResponseCode {
	
	/** The action SUCCESS response code */
	public static final String RESPONSE_SUCCESS = "100";
	/** The action endpoint STATUS OK response code */
	public static final String RESPONSE_SERVICE_OK = "101";
	/** The action endpoint STATUS KO response code */
	public static final String RESPONSE_SERVICE_KO = "102";
	/** The action MALFORMED response code */
	public static final String RESPONSE_FAILURE_REQUEST_MALFORMED = "300";
	/** The action resources DENIED response code */
	public static final String RESPONSE_FAILURE_REQUEST_DENIED= "301";
	/** The action MISSING request field response code */
	public static final String RESPONSE_FAILURE_MISSING_FIELD = "302";
	/** The action BROKEN response code */
	public static final String RESPONSE_FAILURE_BROKEN_ACTION = "303";
	/** The action BROKEN persistence link response code */
	public static final String RESPONSE_FAILURE_BROKEN_PERSISTENCE = "304";
	/** The action BROAD failure response code */
	public static final String RESPONSE_FAILURE_BROAD = "500";
}
