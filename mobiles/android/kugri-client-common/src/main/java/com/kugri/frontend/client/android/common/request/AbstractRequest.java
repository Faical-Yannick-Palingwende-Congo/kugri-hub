/*=============================================================================================*/
/* Class            : AbstractRequest                                                          */
/*                                                                                             */
/* Description      : AbstractRequest class to handle the web service requests.                */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.kugri.frontend.client.android.common.handler.AbstractHandler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractRequest class to handle the web service requests.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractRequest implements Parcelable{
	
	/** */
	private AbstractHandler handler;
	
	/** The request date and time stamp */
	private String stamp;
	/** The request payload */
	private String payload;
	/** The request code */
	private int REQUEST_CODE;
	/** The request endpoint */
	private String endpoint;
	
	/**
	 * Called to build the instance
	 * 
	 * @param handler the request handler
	 */
	public AbstractRequest(AbstractHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Called to build the instance
	 * 
	 * @param handler the request handler
	 * @param stamp the request stamp date and time
	 * @param endpoint the web service request endpoint
	 * @param payload the web service request payload
	 * @param REQUEST_CODE the request representing code
	 */
	public AbstractRequest(AbstractHandler handler, String stamp, String endpoint, String payload, int REQUEST_CODE) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.stamp = stamp;
		this.endpoint = endpoint;
		this.payload = payload;
		this.REQUEST_CODE = REQUEST_CODE;
	}
	
	/**
	 * Called to build the instance from a parcel
	 * 
	 * @param in the parcel that is used to build the request
	 */
	public AbstractRequest(Parcel in){
		String[] data = new String[4];
		
		in.readStringArray(data);
		
		this.stamp = data[0];
		this.endpoint = data[1];
		this.payload = data[2];
		this.REQUEST_CODE = Integer.parseInt(data[3]);
	}
	
	/**
	 * Called to write the request instance fields to the parcel
	 * 
	 * @param dest the parcel to be written to
	 * @param flags the parcel flags
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {this.stamp, this.endpoint, this.payload, ""+this.REQUEST_CODE});
	}
	
	/**
	 * Called to get the request stamp
	 * 
	 * @return String that is the request instance stamp
	 */
	public String getStamp() {
		return stamp;
	}
	
	/**
	 * Called to get the request payload
	 * 
	 * @return String that contains the request instance payload
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * Called to get the request endpoint
	 * 
	 * @return String that contains the request endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}
	
	/**
	 * Called to get the request code
	 * 
	 * @return String that is the request instance code
	 */
	public int getREQUEST_CODE() {
		return REQUEST_CODE;
	}
	
	/**
	 * Called to get the request handler
	 * 
	 * @return AbstractHandler that is the request handler instance
	 */
	public AbstractHandler getHandler() {
		return handler;
	}
	
	/**
	 * Called to get the Json Object of the request
	 * 
	 * @return JSONObject that contains the request JSON Object
	 */
	public JSONObject toJson(){
		try {
			JSONObject object = new JSONObject();
			object.put("stamp", this.stamp);
			object.put("payload", this.payload);
			return object;
		} catch (JSONException e) {
			Log.e("AbstractRequest", e.getMessage());
			return null;
		}
	}
	
	/**
	 * Called to build the String array of the fields
	 * 
	 * @return String[] that contains the string array of the fields
	 */
	public String[] buildFields(String separator){
		return this.payload.split(separator);
	}
}
