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
package com.kugri.frontend.client.android.common;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class RequestCode {
	
	/** The REGISTER action request code */
	public static final int REQUEST_REGISTER= 0;
	/** The LOST action request code */
	public static final int REQUEST_LOST = 1;
	/** The RECOVER action request code */
	public static final int REQUEST_RECOVER = 2;
	/** The LOGIN action request code */
	public static final int REQUEST_LOGIN = 3;
	/** The LOGOUT action request code */
	public static final int REQUEST_LOGOUT = 4;
	/** The UNREGISTER action request code */
	public static final int REQUEST_UNREGISTER = 5;
	/** The ALL action request code */
	public static final int REQUEST_ALL = 6;
	/** The CHECK action request code */
	public static final int REQUEST_CHECK = 7;
	
	/** The PROGRESS static action initiate code */
	public static final int PROGRESS_STATIC_INITIATE= 100;
	
	/** The PROGRESS static action end code */
	public static final int PROGRESS_STATIC_END= 101;
	
	/** The PROGRESS dynamic action initiate code */
	public static final int PROGRESS_DYNAMIC_INITIATE= 200;
	
	/** The PROGRESS dynamic action end code */
	public static final int PROGRESS_DYNAMIC_END= 201;
	
	/**
	 * Called to get the formated stamp of the current date
	 * 
	 * @return String that is the formated stamp
	 */
	@SuppressLint("SimpleDateFormat")
	public static String generateStamp(){
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
		return dateFormat.format(new Date());
	}
}
