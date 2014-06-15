/*=============================================================================================*/
/* Class            : AccountHandler                                                           */
/*                                                                                             */
/* Description      : AccountHandler class to handle the Account request manipulations.        */
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

import java.util.LinkedList;

import com.kugri.frontend.client.android.common.Context;
import com.kugri.frontend.client.android.common.RequestCode;
import com.kugri.frontend.client.android.common.ResponseCode;
import com.kugri.frontend.client.android.common.entity.Account;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.response.KugriResponse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AccountHandler class to handle the Account request manipulations.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public abstract class AccountHandler extends AbstractHandler{

	
	/**
	 * Called to build the handler
	 * 
	 * @param activity the related activity
	 */
	public AccountHandler(Activity activity) {
		super(activity);
	}
	
	
	/**
	 * Called to build the handler
	 * 
	 * @param activity the related activity
	 */
	public AccountHandler(Activity activity, String message) {
		super(activity,"Account Management", message);
		// TODO Auto-generated constructor stub
	}
	
//	/**
//	 * Called to load accounts instances from the payload
//	 * 
//	 * @param payload the accounts formated list
//	 */
//	public void loadAccounts(String payload){
//		String fields[] = payload.split("&");
//		Context.accounts.clear();
//		for(String account: fields){
//			String parts[] = account.split("%");
//			Context.accounts.add(new Account(parts[0], parts[1]));
//		}
//	}
	
	/**
	 * Called to load accounts instances from the payload
	 * 
	 * @param payload the accounts formated list
	 */
	public String accountsToString(LinkedList<Account> accounts){
		String accounts_st = "";
		int sep = 0;
		for(Account account: accounts){
			if(sep > 0) accounts_st += "#";
			accounts_st += account.getId()+"%"+account.getEmail();
			sep++;
		}
		return accounts_st;
	}
	
	public abstract void afterRegister(KugriResponse response);
	public abstract void afterLogin(KugriResponse response);
	public abstract void afterLogout(KugriResponse response);
	public abstract void afterLost(KugriResponse response);
	public abstract void afterRecover(KugriResponse response);
	public abstract void afterUnregister(KugriResponse response);
	public abstract void afterAll(KugriResponse response);
	public abstract void afterCheck(KugriResponse response);

	/**
	 * Implemented the handler performing block
	 * Base on the request code apply the appropriate actions to handle the response
	 */
	@Override
	public void handler() {
		
		Context.LOGGER.pushDebugs("Message received not related to the Progression.");
		
		Bundle bundle = this.message.getData();
		String fields[];
		int what = this.message.what;
		KugriRequest request = bundle.getParcelable("REQUEST");
		KugriResponse response = bundle.getParcelable("RESPONSE");
		
		if(request != null && response != null){
		
			Context.LOGGER.pushDebugs("AccountHandler REQUEST: "+request.toJson());
			Context.LOGGER.pushDebugs("AccountHandler RESPONSE: "+response.toJson());
			
			SharedPreferences preferences = this.activity.getSharedPreferences(Context.PREFS_NAME, 0);
			SharedPreferences.Editor editor = preferences.edit();
			
			if(response != null && response.getCode().equals(ResponseCode.RESPONSE_SUCCESS)){
				switch (what) {
					case RequestCode.REQUEST_LOGIN:
						Context.LOGGER.pushDebugs("Opening Login infos");
						fields = request.getPayload().split("#");
						editor.putString("email", fields[0]);
						editor.putString("password", fields[1]);
						editor.putString("key", response.getPayload());
						editor.putString("status", "3");
						
						editor.commit();
						Context.LOGGER.pushDebugs("Commiting Login infos");
						
						Context.loadData(preferences);
						
						afterLogin(response);
						break;
						
					case RequestCode.REQUEST_LOGOUT:
						editor.remove("key");
						editor.putString("status", "4");
						
						editor.commit();
						
						Context.loadData(preferences);
						
						afterLogout(response);
						break;
						
					case RequestCode.REQUEST_LOST:
						editor.putString("email", request.getPayload());
						editor.remove("password");
						editor.remove("key");
						
						editor.putString("key", response.getPayload());
						editor.putString("status", "2");
						
						editor.commit();
						
						Context.loadData(preferences);
						
						afterLost(response);
						break;
						
					case RequestCode.REQUEST_RECOVER:
						fields = request.getPayload().split("#");
						editor.putString("key", fields[0]);
						editor.putString("password", fields[1]);
						editor.putString("status", "4");
						
						editor.commit();
						
						Context.loadData(preferences);
						
						afterRecover(response);
						break;
						
					case RequestCode.REQUEST_REGISTER:
						fields = request.buildFields("#");
						editor.putString("email", fields[0]);
						editor.putString("password", fields[1]);
						editor.putString("status", "1");
						
						editor.commit();
						
						Context.loadData(preferences);
						
						afterRegister(response);
						break;
						
					case RequestCode.REQUEST_UNREGISTER:
						editor.remove("key");
						editor.remove("email");
						editor.remove("password");
						editor.remove("status");
						editor.remove("scope");
						
						editor.commit();
						
						Context.loadData(preferences);
						
						afterUnregister(response);
						break;
						
					case RequestCode.REQUEST_ALL:
						Context.LOGGER.pushDebugs("Opening accounts infos");
						//loadAccounts(response.getPayload());
						
						editor.putString("accounts", response.getPayload());
						editor.commit();
						Context.LOGGER.pushDebugs("Commiting accounts infos");
						
						afterAll(response);
						break;
						
					case RequestCode.REQUEST_CHECK:
//						String ids[] = request.getPayload().split("#")[1].split("&");
//						String emails[] = response.getPayload().split("#")[1].split("&");
//						for(Account account: Context.accounts){
//							for(int index=0; index < ids.length; index++){
//								if(account.getId().equals(ids[index])){
//									account.email = emails[index];
//								}
//							}
//						}
//						
//						editor.putString("accounts", accountsToString(Context.accounts));
//						editor.commit();
						
						afterCheck(response);
						break;
						
					default:
						break;
				}
			}else Toast.makeText(this.activity, (response == null?"Null response from web service":response.getCode()+"#"+response.getPayload()), Toast.LENGTH_LONG).show();
		}else{
			Context.LOGGER.pushErrors(new Exception(), "AccountHandler"+(request == null?"Request is null ":"")+ (response == null?"Response is null ":""));
		}
	}

}