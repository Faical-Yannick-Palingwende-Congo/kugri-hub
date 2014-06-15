/*=============================================================================================*/
/* Class            : AbstractResponse                                                         */
/*                                                                                             */
/* Description      : AbstractResponse class to handle the web service responses.               */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.response;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractResponse class to handle the web service responses.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractResponse implements Parcelable{
	
	/** The response date and time stamp */
	private String stamp;
	/** The response code */
	private String code;
	/** The response payload */
	private String payload;
	
	/**
	 * Called to build the instance
	 * 
	 */
	public AbstractResponse() {
	}
	
	/**
	 * Called to build the instance
	 * 
	 * @param stamp the response stamp date and time
	 * @param code the web service response code
	 * @param payload the web service response payload
	 */
	public AbstractResponse(String stamp, String code, String payload) {
		this.stamp = stamp;
		this.code = code;
		this.payload = payload;
	}
	
	/**
	 * Called to build the instance from a parcel
	 * 
	 * @param in the parcel that is used to build the response
	 */
	public AbstractResponse(Parcel in){
		String[] data = new String[3];
		
		in.readStringArray(data);
		
		this.stamp = data[0];
		this.code = data[1];
		this.payload = data[2];
	}
	
	/**
	 * Called to write the response instance fields to the parcel
	 * 
	 * @param dest the parcel to be written to
	 * @param flags the parcel flags
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {this.stamp, this.code, this.payload});
	}
	
	/**
	 * Called to get the response stamp
	 * 
	 * @return String that is the response instance stamp
	 */
	public String getStamp() {
		return stamp;
	}
	
	/**
	 * Called to get the response code
	 * 
	 * @return String that is the response instance code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Called to get the response payload
	 * 
	 * @return String that contains the response instance payload
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * Called to get the Json Object of the response
	 * 
	 * @return JSONObject that contains the response JSON Object
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
