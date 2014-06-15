/*=============================================================================================*/
/* Class            : AbstractHandler                                                          */
/*                                                                                             */
/* Description      : AbstractHandler class to handle the activity-requester interactions.     */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.handler;


import com.kugri.frontend.client.android.common.Context;
import com.kugri.frontend.client.android.common.RequestCode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractHandler class to handle the activity-requester interactions.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractHandler extends Handler{
	
	/** The activity concerned by the handler */
	protected Activity activity;
	/** The handler message instance */
	protected Message message;
	
	protected ProgressDialog progress;
	
	/**
	 * Called to perform the handling
	 * 
	 */
	public abstract void handler();
	
	/**
	 * Called to build the handler
	 * 
	 * @param activity the related activity
	 */
	public AbstractHandler(Activity activity) {
		this.activity = activity;
		this.progress = null;
	}
	
	/**
	 * Called to build the handler
	 * 
	 * @param activity the related activity
	 * @param title the progress title
	 */
	public AbstractHandler(Activity activity, String title, String message) {
		this.activity = activity;
		this.progress = new ProgressDialog(this.activity);
		this.progress.setTitle(title);
		this.progress.setMessage(message);
		this.progress.setIndeterminate(true);
		this.progress.setCancelable(false);
	}
	
	/**
	 * Called to handle a message passed to the handler
	 * 
	 * @param msg the received message
	 */
	@Override
	public void handleMessage(Message msg) {
		this.message = msg;
		switch(msg.what){
			case RequestCode.PROGRESS_DYNAMIC_INITIATE:
				Context.LOGGER.pushDebugs("Message received related to the Progression.");
				Context.LOGGER.pushDebugs("Showing the non blocking progress");
				getActivity().setProgressBarIndeterminateVisibility(true);
				break;
			case RequestCode.PROGRESS_DYNAMIC_END:
				Context.LOGGER.pushDebugs("Message received related to the Progression.");
				Context.LOGGER.pushDebugs("Dismissing the non blocking progress");
				getActivity().setProgressBarIndeterminateVisibility(false);
				break;
			case RequestCode.PROGRESS_STATIC_INITIATE:
				Context.LOGGER.pushDebugs("Message received related to the Progression.");
				Context.LOGGER.pushDebugs("Showing the blocking progress");
				if(this.progress != null) {
                	if(!this.progress.isShowing()) {
                		this.progress.show();
                	}
            	}
				else Context.LOGGER.pushErrors(new Exception(), "Non blocking handler. Missing progress bar instnace.");//Characterise exceptions
				break;
			case RequestCode.PROGRESS_STATIC_END:
				Context.LOGGER.pushDebugs("Message received related to the Progression.");
				Context.LOGGER.pushDebugs("Dismissing the blocking progress");
				if(this.progress != null) {
                	if(this.progress.isShowing()) {
                		this.progress.dismiss();
                	}
            	}
				else Context.LOGGER.pushErrors(new Exception(), "Non blocking handler. Missing progress bar instance.");//Characterise exceptions
				break;
			default:
				handler();
				break;
		}
		
	}
	
	/**
	 * Called to get the activity concerned by this handler
	 * 
	 * @return Activity that represents the activity instance
	 */
	public Activity getActivity() {
		return activity;
	}
	
	public ProgressDialog getDialog(){
		return this.progress;
	}
}
