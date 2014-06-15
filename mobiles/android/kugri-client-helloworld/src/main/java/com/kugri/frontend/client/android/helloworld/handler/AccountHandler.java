package com.kugri.frontend.client.android.helloworld.handler;

import java.util.ArrayList;

import com.kugri.frontend.client.android.helloworld.Context;
import com.kugri.frontend.client.android.common.ResponseCode;
import com.kugri.frontend.client.android.common.entity.Account;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.response.KugriResponse;
import com.kugri.frontend.client.android.common.task.KugriStaticTask;
import com.kugri.frontend.client.android.helloworld.HelloAndroidActivity;
import com.kugri.frontend.client.android.helloworld.LoginActivity;
import com.kugri.frontend.client.android.helloworld.R;
import com.kugri.frontend.client.android.helloworld.RequestCode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AccountHandler extends com.kugri.frontend.client.android.common.handler.AccountHandler{

	private KugriStaticTask messageAllTask = null;
	
	public AccountHandler(Activity activity) {
		super(activity);
	}
	
	
	public AccountHandler(Activity activity, String message) {
		super(activity, message);
	}
	
	public void populateMessages(){
		Context.LOGGER.pushDebugs("Executing the message all request");
		MessageHandler messageAllHandler = new MessageHandler(this.activity);
		KugriRequest messageAllRequest = new KugriRequest(messageAllHandler, RequestCode.generateStamp(), Context.baseUrl+"message/all", Context.access, RequestCode.REQUEST_ALL);
		this.messageAllTask = new KugriStaticTask(5000);
		messageAllTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, messageAllRequest);
		Context.LOGGER.pushDebugs("Finished executing the message all request");
	}
	
	public void stopDynamics(){
		//this.messageAllTask.cancel();
	}

	@Override
	public void afterAll(KugriResponse arg0) {
		Context.LOGGER.pushDebugs("Loading the accounts");
		SharedPreferences preferences = this.activity.getSharedPreferences(Context.PREFS_NAME, 0);
		Context.loadData(preferences);
		
		Spinner accountsSpinner = (Spinner) this.activity.findViewById(R.id.spinnerreceivervew);
		ArrayList<String> objects = new ArrayList<String>();
		for(Account account: Context.accounts){
			objects.add(account.getEmail());
			Context.LOGGER.pushDebugs("Account #"+account.getId()+": "+account.getEmail());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity, android.R.layout.simple_dropdown_item_1line, objects);
		accountsSpinner.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		Context.LOGGER.pushDebugs("Accounts loaded");
		
		//if(this.messageAllTask == null){
		Context.LOGGER.pushDebugs("Populating the messages list");
		populateMessages();
		Context.LOGGER.pushDebugs("The messages list has been populated");
		//}
	}

	@Override
	public void afterCheck(KugriResponse arg0) {
		// TODO Auto-generated method stub
		
		SharedPreferences preferences = this.activity.getSharedPreferences(Context.PREFS_NAME, 0);
		Context.loadData(preferences);
	}

	@Override
	public void afterLogin(KugriResponse arg0) {
		if(arg0.getCode().equals(ResponseCode.RESPONSE_SUCCESS)){
			Intent Home = new Intent(this.activity, HelloAndroidActivity.class);
			Context.LOGGER.pushDebugs("Starting the Home activity");
			this.activity.startActivityForResult(Home, RequestCode.REQUEST_LOGIN);
		}else{
			Toast.makeText(this.activity, arg0.getPayload(), Toast.LENGTH_LONG).show();
			Context.LOGGER.pushDebugs("Login failed: " + arg0.getPayload());
		}
	}

	@Override
	public void afterLogout(KugriResponse arg0) {
		SharedPreferences preferences = this.activity.getSharedPreferences(LoginActivity.PREFS_NAME, 0);
		Context.printData(preferences);
		
		if(arg0.getCode().equals(ResponseCode.RESPONSE_SUCCESS)){
			Toast.makeText(this.activity, arg0.getPayload(), Toast.LENGTH_LONG).show();
			Context.LOGGER.pushDebugs("Finishing the activity");
			this.activity.finish();
		}else{
			Toast.makeText(this.activity, arg0.getPayload(), Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public void afterLost(KugriResponse arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRecover(KugriResponse arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRegister(KugriResponse arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUnregister(KugriResponse arg0) {
		// TODO Auto-generated method stub
		
	}

}
