/*=============================================================================================*/
/* Class            : AbstractEndpoint                                                         */
/*                                                                                             */
/* Description      : AbstractEndpoint class to easy the endpoints manipulations.              */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.helloworld.endpoint;

import java.util.LinkedList;

import com.kugri.backend.service.rest.helloworld.entity.EntMessage;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractEndpoint class to easy the endpoints manipulations.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractEndpoint extends com.kugri.backend.service.rest.common.endpoint.AbstractEndpoint{
	
	/** The messages list instance */
	public LinkedList<EntMessage> messages;
	
	/**
	 * Called to get the hidden field
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<Index> that contains a loaded instance of indexes list
	 */
	public int stamp2Message(String stamp){
		int id = 0;
		for(EntMessage message: this.messages){
			if(message.getStamp().equals(stamp)){
				id = Integer.parseInt(message.getId());
				break;
			}
		}
		return id;
	}
	
	/**
	 * Called to get the hidden field
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<Index> that contains a loaded instance of indexes list
	 */
	public EntMessage getMessagefromId(String id){
		for(EntMessage message: this.messages){
			if(message.getId().equals(id)) return message;
		}
		return null;
	}
	
	/**
	 * Called to get the hidden field
	 * 
	 * @param content the database raws formated string 
	 * @return LinkedList<Index> that contains a loaded instance of indexes list
	 */
	public LinkedList<EntMessage> StringToMessages(String content) {
		String rows[] = content.split("\n");
		LinkedList<EntMessage> contents = new LinkedList<EntMessage>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			EntMessage message = new EntMessage(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
			contents.add(message);
		}
		return contents;
	}

}
