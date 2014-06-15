/*=============================================================================================*/
/* Class            : Index                                                                    */
/*                                                                                             */
/* Description      : Index class for loading the index entity instances.                      */
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
 * Index class for loading the index entity instances.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class Index {
	
	/** The Index entity id field*/
	public String id;
	/** The Index entity table field*/
	public String table;
	/** The Index entity next field*/
	public String next;
	
	/**
	 * Called to build the index instance
	 * 
	 */
	public Index() {
		
	}
	
	public Index(String payload){
		String block[] = payload.split("%");
		this.id = block[0];
		this.table = block[1];
		this.next = block[2];
	}

	/**
	 * Called to build the index instance
	 * 
	 * @param id the identifier of the index instance on the remote db
	 * @param table the identifier of the table in the remote db
	 * @param next the next identifier that should have the next raw on the table identified
	 */
	public Index(String id, String table, String next) {
		super();
		this.id = id;
		this.table = table;
		this.next = next;
	}

	/**
	 * Called to get the index instance id
	 * 
	 * @return String that is the index's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Called to get the index instance table identifier
	 * 
	 * @return String that is the index's table 
	 */
	public String getTable() {
		return table;
	}

	/**
	 * Called to get the index instance next
	 * 
	 * @return String that is the index's next
	 */
	public String getNext() {
		return next;
	}
}
