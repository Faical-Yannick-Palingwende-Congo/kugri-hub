/*=============================================================================================*/
/* Class            : KugriResponse                                                            */
/*                                                                                             */
/* Description      : KugriResponse class for easing the access to the kugri service response. */
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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * KugriResponse class for easing the access to the kugri service response.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class KugriResponse extends AbstractResponse{

	/** The response static creator instance */
	public static final Parcelable.Creator<KugriResponse> CREATOR = new Creator<KugriResponse>() {

		/**
		 * Called to create a response instance from a parcel
		 * 
		 * @param source the response instance parcel source
		 * @return KugriResponse that contains the response instance
		 */
		public KugriResponse createFromParcel(Parcel source) {
			return new KugriResponse(source);
		}

		/**
		 * Called to create a new response array
		 * 
		 * @param size the response array size
		 * @return KugriResponse[] that contains the KugriResponse array used by the creator
		 */
		public KugriResponse[] newArray(int size) {
			return new KugriResponse[size];
		}
	};
	
	/**
	 * Called to build the instance
	 * 
	 */
	public KugriResponse() {
		super();
	}

	/**
	 * Called to build the instance
	 * 
	 * @param stamp the response stamp date and time
	 * @param code the web service response code
	 * @param payload the web service response payload
	 */
	public KugriResponse(String stamp, String code, String payload) {
		super(stamp, code, payload);
	}

	/**
	 * Called to build the instance from a parcel
	 * 
	 * @param source the parcel that is used to build the response
	 */
	public KugriResponse(Parcel source) {
		super(source);
	}
	
	/**
	 * Called to get the contents description identifier
	 * 
	 * @return int that represent the contents description identifier
	 */
	public int describeContents() {
		return 0;
	}

}