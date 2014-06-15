/*=============================================================================================*/
/* Class            : KugriRequest                                                             */
/*                                                                                             */
/* Description      : KugriRequest class for easing the access to the kugri service request.   */
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

import com.kugri.frontend.client.android.common.handler.AbstractHandler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * KugriRequest class for easing the access to the kugri service request.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class KugriRequest extends AbstractRequest{
	
	/** The request static creator instance */
	public static final Parcelable.Creator<KugriRequest> CREATOR = new Creator<KugriRequest>() {

		/**
		 * Called to create a request instance from a parcel
		 * 
		 * @param source the request instance parcel source
		 * @return KugriRequest that contains the request instance
		 */
		public KugriRequest createFromParcel(Parcel source) {
			return new KugriRequest(source);
		}

		/**
		 * Called to create a new request array
		 * 
		 * @param size the request array size
		 * @return KugriRequest[] that contains the KugriRequest array used by the creator
		 */
		public KugriRequest[] newArray(int size) {
			return new KugriRequest[size];
		}
	};
	
	/**
	 * Called to build the instance
	 * 
	 * @param handler the request handler
	 */
	public KugriRequest(AbstractHandler handler) {
		super(handler);
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
	public KugriRequest(AbstractHandler handler, String stamp, String endpoint, String payload,
			int REQUEST_CODE) {
		super(handler, stamp, endpoint, payload, REQUEST_CODE);
	}

	/**
	 * Called to build the instance from a parcel
	 * 
	 * @param source the parcel that is used to build the request
	 */
	public KugriRequest(Parcel source) {
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
