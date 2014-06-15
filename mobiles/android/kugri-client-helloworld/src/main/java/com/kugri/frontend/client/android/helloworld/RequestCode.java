/*=============================================================================================*/
/* Class            : RequestCode                                                              */
/*                                                                                             */
/* Description      : RequestCode class to handle the request codes.                           */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.helloworld;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * RequestCode class to handle the request codes.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class RequestCode extends com.kugri.frontend.client.android.common.RequestCode{
	
	/** The MESSAGE action send code */
	public static final int REQUEST_SEND= 7;
	/** The MESSAGE action receive code */
	public static final int REQUEST_RECEIVE= 8;
	/** The MESSAGE action all code */
	public static final int REQUEST_ALL= 9;
	/** The CHECK action all code */
	public static final int REQUEST_CHECK= 10;
	/** The TRASH action all code */
	public static final int REQUEST_TRASH= 11;
	/** The RESTORE action all code */
	public static final int REQUEST_RESTORE= 12;
}
