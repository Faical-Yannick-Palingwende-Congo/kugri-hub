package com.kugri.frontend.client.android.helloworld;

import java.util.ArrayList;
import java.util.LinkedList;

import com.kugri.frontend.client.android.common.Context;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.task.KugriBlockingTask;
import com.kugri.frontend.client.android.common.task.KugriDynamicTask;
import com.kugri.frontend.client.android.helloworld.adapter.MessageAdapter;
import com.kugri.frontend.client.android.helloworld.entity.Message;
import com.kugri.frontend.client.android.helloworld.handler.AccountHandler;
import com.kugri.frontend.client.android.helloworld.handler.MessageHandler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class HelloAndroidActivity extends Activity implements OnItemSelectedListener, OnClickListener {

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	
	//public static final String PREFS_NAME = Context.PREFS_NAME+"Helloworld-PrefsFile";
	
	KugriDynamicTask accountAllTask;
	KugriRequest accountAllRequest;
	AccountHandler accountAllHandler;
	
	KugriDynamicTask messageRecvTask;
	KugriRequest messageRecvRequest;
	MessageHandler messageRecvHandler;
	
	Spinner messagesBox;
	Spinner accountsSpinner;
	
	SharedPreferences preferences;
	
	EditText msg;
	Button send;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context.LOGGER.pushInfos("Inside the HelloAndroidActivity onCreate...");
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activitymain);
		
		accountsSpinner = (Spinner) findViewById(R.id.spinnerreceivervew);
		
		msg = (EditText) findViewById(R.id.editcontent);
		send = (Button) findViewById(R.id.buttonsend);
		
		send.setOnClickListener(this);
		
		messagesBox = (Spinner) findViewById(R.id.spinnerbox);
		ArrayList<String> boxes = new ArrayList<String>();
		boxes.add("Received");
		boxes.add("Sent");
		boxes.add("Trashed");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, boxes);
		messagesBox.setAdapter(adapter);
		messagesBox.setOnItemSelectedListener(this);
		
		Context.LOGGER.pushDebugs("Loading the preferences fields");
		preferences = getSharedPreferences(Context.PREFS_NAME, 0);
		Context.printData(preferences);
		Context.StringToStatuses();
		Context.LOGGER.pushDebugs("The preferences fields have been loaded");
		
		Context.LOGGER.pushDebugs("Setting up the thread policy for the tasks execution");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		Context.LOGGER.pushDebugs("The thread policy has been set up");
		
		Toast.makeText(this, Context.getStatusfromId("1"), Toast.LENGTH_LONG).show();
		
		Context.LOGGER.pushDebugs("Setting up the handlers");
		accountAllHandler = new AccountHandler(this);
		messageRecvHandler = new MessageHandler(this);
		
		Context.LOGGER.pushDebugs("The handlers has been setup");
		
		Context.LOGGER.pushDebugs("Populating the accounts spinner");
		populateAccounts();
		Context.LOGGER.pushDebugs("The accounts spinner has been populated");
		
		Context.LOGGER.pushDebugs("Checking for new messages");
		messageReceiver();//Make messages as seen before loading them
		Context.LOGGER.pushDebugs("Checked for new messages");
	}
	
	public void populateAccounts(){
		Context.LOGGER.pushDebugs("Executing the account all request");
		accountAllRequest = new KugriRequest(accountAllHandler, RequestCode.generateStamp(), Context.baseUrl+"account/all", Context.access, com.kugri.frontend.client.android.common.RequestCode.REQUEST_ALL);
		accountAllTask = new KugriDynamicTask(15000, 5000);
		accountAllTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, accountAllRequest);
		Context.LOGGER.pushDebugs("Finished executing the account all request");
	}
	
	public void messageReceiver(){
		Context.LOGGER.pushDebugs("Executing the message receive request");
		messageRecvRequest = new KugriRequest(messageRecvHandler, RequestCode.generateStamp(), Context.baseUrl+"message/receive", Context.access, RequestCode.REQUEST_RECEIVE);
		messageRecvTask = new KugriDynamicTask(25000, 5000);
		messageRecvTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, messageRecvRequest);
		Context.LOGGER.pushDebugs("Finished executing the message receive request");
	}
	
	public void sendMessages(int index, String content){
		Context.LOGGER.pushDebugs("Executing the message send request");
		MessageHandler sendhandler = new MessageHandler(this, "Sending...");
		KugriRequest sendMessageRequest = new KugriRequest(sendhandler, RequestCode.generateStamp(), Context.baseUrl+"message/send", Context.access+"#"+com.kugri.frontend.client.android.helloworld.Context.accounts.get(index).getEmail()+"#"+content, RequestCode.REQUEST_SEND);
		KugriBlockingTask messageSendTask = new KugriBlockingTask(5000);
		messageSendTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sendMessageRequest);
		Context.LOGGER.pushDebugs("Finished executing the message send request");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		//logout();
		this.accountAllTask.cancel();
		messageRecvTask.cancel();
		this.accountAllHandler.stopDynamics();
		super.onStop();
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		ListView messagesList = (ListView) findViewById(R.id.message_list);
		MessageAdapter adapter;
		switch(position){
			case 0:
				LinkedList<Message> recevbox = new LinkedList<Message>();
				for(Integer index: com.kugri.frontend.client.android.helloworld.Context.recvbox){
					recevbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
				}
				adapter =new MessageAdapter(this, recevbox);
				messagesList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			case 1:
				LinkedList<Message> sentbox = new LinkedList<Message>();
				for(Integer index: com.kugri.frontend.client.android.helloworld.Context.sentbox){
					sentbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
				}
				
				adapter = new MessageAdapter(this, sentbox);
				messagesList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				LinkedList<Message> trashbox = new LinkedList<Message>();
				for(Integer index: com.kugri.frontend.client.android.helloworld.Context.trashbox){
					trashbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
				}
				
				adapter =new MessageAdapter(this, trashbox);
				messagesList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			default:
				LinkedList<Message> defaultbox = new LinkedList<Message>();
				for(Integer index: com.kugri.frontend.client.android.helloworld.Context.recvbox){
					defaultbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
				}
				adapter =new MessageAdapter(this, defaultbox);
				messagesList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
		}
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
		//Nothing
	}

	public void onClick(View v) {
		if(v == send){
			String content = "" + msg.getText();
			if(content.equals("")){
				Toast.makeText(this, "Message content must not empty.", Toast.LENGTH_LONG).show();
			}else{
				sendMessages(accountsSpinner.getSelectedItemPosition(), content);
			}
		}
	}
}
