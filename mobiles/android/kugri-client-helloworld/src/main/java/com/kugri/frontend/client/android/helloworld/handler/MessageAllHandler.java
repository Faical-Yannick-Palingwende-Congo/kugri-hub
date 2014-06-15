/*=============================================================================================*/
/* Class            : MessageAllHandler                                                        */
/*                                                                                             */
/* Description      : MessageAllHandler class to handle the Message all request manipulations. */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.helloworld.handler;

import com.kugri.frontend.client.android.common.ResponseCode;
import com.kugri.frontend.client.android.common.handler.AbstractHandler;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.response.KugriResponse;
import com.kugri.frontend.client.android.helloworld.Context;
import com.kugri.frontend.client.android.helloworld.RequestCode;
import com.kugri.frontend.client.android.helloworld.entity.Message;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * MessageAllHandler class to handle the Message all request manipulations.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class MessageAllHandler extends AbstractHandler{

	/**
	 * Called to build the handler
	 * 
	 * @param activity the related activity
	 */
	public MessageAllHandler(Activity activity, String message) {
		super(activity, "Messages list loading", message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Called to load messages instances from the payload
	 * 
	 * @param payload the messages formated list
	 */
	public void loadMessages(String payload){
		String fields[] = payload.split("&");
		Context.messages.clear();
		for(String message: fields){
			String parts[] = message.split("%");
			Context.messages.add(new Message(parts[1], parts[2], parts[3], parts[4], parts[5]));
		}
	}

	/**
	 * Implemented the handler performing block
	 * Base on the request code apply the appropriate actions to handle the response
	 */
	@Override
	public void handler() {
		Bundle bundle = this.message.getData();
		int what = this.message.what;
		KugriRequest request = bundle.getParcelable("REQUEST");
		KugriResponse response = bundle.getParcelable("RESPONSE");
		
		if(request != null && response != null){
		
			Log.i("MessageAllHandler", "REQUEST: "+request.toJson());
			Log.i("MessageAllHandler", "RESPONSE: "+response.toJson());
			
			//SharedPreferences preferences = this.activity.getSharedPreferences(HelloAndroidActivity.PREFS_NAME, 0);
			//SharedPreferences.Editor editor = preferences.edit();
			
			if(response != null && response.getCode().equals(ResponseCode.RESPONSE_SUCCESS)){
				switch (what) {
					
					case RequestCode.PROGRESS_DYNAMIC_INITIATE:
						getActivity().setProgressBarIndeterminateVisibility(true);
						break;
						
					case RequestCode.PROGRESS_DYNAMIC_END:
						getActivity().setProgressBarIndeterminateVisibility(false);
						break;
					
					case RequestCode.REQUEST_ALL:
						loadMessages(response.getPayload());
						break;
	
					default:
						break;
				}
			}
			//Context.loadData(preferences);
		}else{
			Log.e("MessageAllHandler", (request == null?"Request is null and ":"")+ (response == null?"Response is null":""));
		}
	}

}
