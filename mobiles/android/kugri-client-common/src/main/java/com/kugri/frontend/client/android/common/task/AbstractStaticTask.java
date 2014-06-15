/*=============================================================================================*/
/* Class            : AbstractStaticTask                                                       */
/*                                                                                             */
/* Description      : AbstractStaticTask class to handle a static task.                        */
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
 * AbstractStaticTask class to handle a static task.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AbstractStaticTask extends AsyncTask<AbstractRequest, Void, AbstractResponse>{

	/** The abstract request of the task */
	protected AbstractRequest request;
	/** The abstract response of the task */
	protected AbstractResponse response;
	/** The timeout value of the request in the task */
	private int timeout;
	
	protected boolean failed;
	
	/**
	 * Called to build the instance
	 * 
	 * @param handler the task handler instance
	 * @param timeout the timeout value of the task
	 */
	public AbstractStaticTask( int timeout) {
		this.timeout = timeout;
		this.failed = false;
	}
	
	/**
	 * Called to execute the request on the client
	 * 
	 * @param httpClient the http client that executes the request
	 * @param request the request that is executed
	 * @return AbstractResponse that contains the response to the executed request
	 */
	public abstract AbstractResponse executer(DefaultHttpClient httpClient, AbstractRequest request);
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	/**
	 * Called to launch the actual background process
	 * 
	 * @param params the request that a forwarded to the background process
	 * @return AbstractResponse that contains the response to the executed request
	 */
	@Override
	protected AbstractResponse doInBackground(AbstractRequest... params) {
		
		AbstractResponse result = null;
		this.request = params[0];
		this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_INITIATE);
		HttpParams parameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameters, this.timeout);
		HttpConnectionParams.setSoTimeout(parameters, this.timeout);        
		DefaultHttpClient httpClient = new DefaultHttpClient(parameters);
		BasicCookieStore cookieStore = new BasicCookieStore();
		httpClient.setCookieStore(cookieStore);
		try{
			result = executer(httpClient, this.request);
		}catch (Exception e) {
			Context.LOGGER.pushErrors(e, "AbstractStaticTask" + Log.getStackTraceString(e));
			this.failed = true;
		}
		
		return result;
	}
	
	/**
	 * Called to do some post execution actions
	 * 
	 * @param result the response from the executed request
	 */
	@Override
	protected void onPostExecute(AbstractResponse result) {
		super.onPostExecute(this.response);
		this.response = result;
		this.request.getHandler().sendEmptyMessage(RequestCode.PROGRESS_DYNAMIC_END);
		if(this.request != null && this.response != null){
			Context.LOGGER.pushDebugs("AbstractStaticTask Request: "+this.request.toJson());
			Context.LOGGER.pushDebugs("AbstractStaticTask Response: "+this.response.toJson());
			Message message = new Message();
			Bundle bundle = new Bundle();
			message.what = this.request.getREQUEST_CODE();
			bundle.putParcelable("REQUEST", this.request);
			bundle.putParcelable("RESPONSE", this.response);
			message.setData(bundle);
			this.request.getHandler().sendMessage(message);
		}else{
			Context.LOGGER.pushErrors(new Exception(), "AbstractStaticTask" + (this.request == null?"Request is null and ":"")+ (this.response == null?"Response is null":""));
			this.failed = true;
		}
	}
	
	public boolean getFailed(){
		return failed;
	}
}
