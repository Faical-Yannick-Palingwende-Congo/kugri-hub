/*=============================================================================================*/
/* Class            : Scope                                                                    */
/*                                                                                             */
/* Description      : Scope class for loading the scope entity instances.                      */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.entity;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Scope class for loading the scope entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Scope {
	
	/** The Scope entity id field*/
	public String id;
	/** The Scope entity value field*/
	public String value;
	
	/**
	 * Called to build the scope instance
	 * 
	 */
	public Scope() {
		
	}
	
	public Scope(String payload){
		String block[] = payload.split("%");
		this.id = block[0];
		this.value = block[1];
	}

	/**
	 * Called to build the scope instance
	 * 
	 * @param id the scope instance identifier
	 * @param value the scope instance value
	 */
	public Scope(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	/**
	 * Called to get the scope instance's id
	 * 
	 * @return String that is the corresponding instance's id according to the remote db
	 */
	public String getId() {
		return id;
	}

	/**
	 * Called to get the scope instance's value
	 * 
	 * @return String that is the corresponding instance's value
	 */
	public String getValue() {
		return value;
	}
}
