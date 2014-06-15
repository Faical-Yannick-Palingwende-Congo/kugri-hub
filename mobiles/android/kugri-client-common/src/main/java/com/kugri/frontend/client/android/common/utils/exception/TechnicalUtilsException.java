/*=============================================================================================*/
/* Class            : TechnicalUtilsException                                                  */
/*                                                                                             */
/* Description      : TechnicalUtilsException class for handling technical exception.          */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.utils.exception;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * TechnicalUtilsException class for handling technical exception.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class TechnicalUtilsException extends Exception
{
	/** Serial Version UID of Technical Utils Exception */
	private static final long serialVersionUID = -9140726020170336484L;

	/**
	 * Called to build the object
	 * 
	 * @param message the message of the exception
	 */
	public TechnicalUtilsException(String message){
		super(message);
	}
	
	/**
	 * Called to build the object
	 * 
	 * @param message the message of the exception
	 * @param cause the throwable of the exception
	 */
	public TechnicalUtilsException(String message, Throwable cause){
		super(message, cause);
	}
}
