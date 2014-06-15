/*=============================================================================================*/
/* Class            : AbstractDynamicTask                                                      */
/*                                                                                             */
/* Description      : AbstractDynamicTask class to handle a dynamically changing task.         */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-CLIENT  */
/*=============================================================================================*/
package com.kugri.frontend.client.android.common.task;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.kugri.frontend.client.android.common.Context;
import com.kugri.frontend.client.android.common.RequestCode;
import com.kugri.frontend.client.android.common.request.AbstractRequest;
import com.kugri.frontend.client.android.common.response.AbstractResponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AbstractDynamicTask class to handle a dynamically changing task.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractDynamicTask extends AsyncTask<AbstractRequest, Void, AbstractResponse>{
	
	/** The abstract request of the task */
	protected AbstractRequest request;
	/** The abstract response of the task */
	protected AbstractResponse response;
	/** The state of the request handled by the task */
	private boolean canceled;
	/** The waiting time of the request in the task */
	private long wait;
	/** The timeout value of the request in the task */
	private int timeout;
	
	private boolean requestable = false;
	
	/**
	 * Called to build the instance
	 * 
	 * @param wait the waiting value of the task
	 * @param timeout the timeout value of the task
	 */
	public AbstractDynamicTask(long wait, int timeout, boolean requestable) {
		this.canceled = false;
		this.wait = wait;
		this.timeout = timeout;
		this.requestable = requestable;
	}
	
	public boolean isCanceled(){
		return this.canceled;
	}
	
	/**
	 * Called to cancel the current request
	 * 
	 */
	public void cancel(){
		this.canceled = true;
	}
	
	/**
	 * Called to execute the request on the client
	 * 
	 * @param httpClient the http client that executes the request
	 * @return AbstractResponse that contains the response to the executed request
	 */
	public abstract AbstractResponse executer(DefaultHttpClient httpClient);
	
	/**
	 * Called to execute the request on the client
	 * 
	 * @param httpClient the http client that executes the request
	 * @param request the request that is executed
	 * @return AbstractResponse that contains the response to the executed request
	 */
	public abstract AbstractResponse executer(DefaultHttpClient httpClient, AbstractRequest request);
	
	/**
	 * Called to launch the actual background process
	 * 
	 * @param params the request that a forwarded to the background process
	 * @return AbstractResponse that contains the response to the executed request
	 */
	@Override
	protected AbstractResponse doInBackground(AbstractRequest... params) {
		this.request = params[0];
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, this.timeout);
		HttpConnectionParams.setSoTimeout(parameters, this.timeout);        
		DefaultHttpClient httpClient = new DefaultHttpClient(parameters);
		BasicCookieStore cookieStore = new BasicCookieStore();
		httpClient.setCookieStore(cookieStore);
		
		boolean keepRunning = true;
		
		try {
			while(keepRunning && !this.canceled){
				Message message = new Message();
				this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_INITIATE);
				if(!requestable) this.response = executer(httpClient, this.request);
				else this.response = executer(httpClient);
				this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_END);
				if(this.request != null && this.response != null){
					Log.d("AbstractDynamicTask", "Request: "+this.request.toJson());
					Log.d("AbstractDynamicTask", "Response: "+this.response.toJson());
					message = new Message();
					Bundle bundle = new Bundle();
					message.what = this.request.getREQUEST_CODE();
					bundle.putParcelable("REQUEST", this.request);
					bundle.putParcelable("RESPONSE", this.response);
					message.setData(bundle);
					this.request.getHandler().sendMessage(message);
					
				}else{
					Log.e("AbstractDynamicTask", (this.request == null?"Request is null and ":"")+ (this.response == null?"Response is null":""));
				}
				//this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_INITIATE);
				Thread.sleep(this.wait);
				//this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_END);
			}
		} catch (Exception e) {
			Context.LOGGER.pushErrors(e, "AbstractDynamicTask: "+Log.getStackTraceString(e));
		}
		
		return this.response;
	}
	
	/**
	 * Called to do some post execution actions
	 * 
	 * @param result the response from the executed request
	 */
	@Override
	protected void onPostExecute(AbstractResponse result) {
		super.onPostExecute(this.response);
		
		Context.LOGGER.pushDebugs("AbstractDynamicTask Stopped.");
//		this.response = result;
//		Message message = new Message();
//		Bundle bundle = new Bundle();
//		message.what = this.request.getREQUEST_CODE();
//		bundle.putParcelable("RESPONSE", this.response);
//		this.request.getHandler().sendMessage(message);
	}
	 
}