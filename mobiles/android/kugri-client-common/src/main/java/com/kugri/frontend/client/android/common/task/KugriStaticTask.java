/*=============================================================================================*/
/* Class            : AccountTask                                                              */
/*                                                                                             */
/* Description      : AccountTask class to handle the account related requests.                */
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.kugri.frontend.client.android.common.Context;
import com.kugri.frontend.client.android.common.ResponseCode;
import com.kugri.frontend.client.android.common.request.AbstractRequest;
import com.kugri.frontend.client.android.common.response.AbstractResponse;
import com.kugri.frontend.client.android.common.response.KugriResponse;
import com.kugri.frontend.client.android.common.utils.StringUtils;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AccountTask class to handle the account related requests.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class KugriStaticTask extends AbstractStaticTask{

	/**
	 * Called to build the account static task instance
	 * 
	 * @param handler the task handler instance
	 * @param timeout the timeout value of the task
	 */
	public KugriStaticTask(int timeout) {
		super(timeout);
	}

	/**
	 * Called to execute the request on the client statically
	 * 
	 * @param httpClient the http client that executes the request
	 * @param request the request that is executed
	 * @return AbstractResponse that contains the response to the executed request
	 */
	@Override
	public AbstractResponse executer(DefaultHttpClient httpClient,
			AbstractRequest request) {
		KugriResponse response = null;
		this.request = request;
		
		String contentType = "application/json";
		HttpPost httpPost = new HttpPost(this.request.getEndpoint());
		
		try {
			StringEntity entity = new StringEntity(this.request.toJson().toString(), HTTP.UTF_8);
		    entity.setContentType(contentType);
		    httpPost.setEntity(entity);

		    httpPost.setHeader("Content-Type", contentType);
		    httpPost.setHeader("Accept", contentType);
		} catch (UnsupportedEncodingException e) {
		    Context.LOGGER.pushErrors(e, "KugriStaticTask - UnsupportedEncodingException: " + Log.getStackTraceString(e));
		}

		try {
		    HttpResponse httpResponse = httpClient.execute(httpPost);
		    HttpEntity httpEntity = httpResponse.getEntity();

		    if (httpEntity != null) {
		        InputStream is = httpEntity.getContent();
		        String result = StringUtils.convertStreamToString(is);
		        //When i change the code of the response from parkingchum to be a field this will be easier.
		        JSONObject jsonResult = new JSONObject(result);
		        String metaload = jsonResult.getString("payload");
		        String metapart[] = metaload.split("#");
		        String code = "";
		        String pay = "";
		        if(metapart.length == 1){
		        	code = (metaload.contains("#")?metapart[0]:ResponseCode.RESPONSE_FAILURE_BROAD);
				    response = new KugriResponse(jsonResult.getString("stamp"), code, pay);
				    Context.LOGGER.pushInfos("KugriStaticTask - Result: " + result);
		        }else if(metapart.length == 2){
		        	code = (metaload.contains("#")?metapart[0]:ResponseCode.RESPONSE_FAILURE_BROAD);
				    pay = (metaload.contains("#")?metapart[1]:metaload);
				    response = new KugriResponse(jsonResult.getString("stamp"), code, pay);
				    Context.LOGGER.pushInfos("KugriStaticTask - Result: " + result);
		        }else{
		        	Context.LOGGER.pushErrors(new Exception(), "KugriStaticTask - KugriStaticTask - Weird envelope.");
		        }
		  
		    }
		} catch (ClientProtocolException e) {
		    Context.LOGGER.pushErrors(e, "KugriStaticTask - ClientProtocolException: " + Log.getStackTraceString(e));
		} catch (IOException e) {
		    Context.LOGGER.pushErrors(e, "KugriStaticTask - IOException: " + Log.getStackTraceString(e));
		} catch (JSONException e) {
			Context.LOGGER.pushErrors(e, "KugriStaticTask - JSONException: " + Log.getStackTraceString(e));
		}
		
		return response;
	}
}