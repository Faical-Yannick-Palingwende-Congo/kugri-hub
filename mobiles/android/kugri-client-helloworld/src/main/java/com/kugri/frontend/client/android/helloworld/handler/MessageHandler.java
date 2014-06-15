package com.kugri.frontend.client.android.helloworld.handler;

import java.util.Collections;
import java.util.LinkedList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kugri.frontend.client.android.helloworld.Context;
import com.kugri.frontend.client.android.helloworld.R;
import com.kugri.frontend.client.android.helloworld.RequestCode;
import com.kugri.frontend.client.android.helloworld.adapter.MessageAdapter;
import com.kugri.frontend.client.android.helloworld.entity.Message;
import com.kugri.frontend.client.android.common.ResponseCode;
import com.kugri.frontend.client.android.common.handler.AbstractHandler;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.response.KugriResponse;
import com.kugri.frontend.client.android.common.task.KugriStaticTask;

public class MessageHandler extends AbstractHandler{

	private Spinner messagesBox;
	
	public MessageHandler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	public MessageHandler(Activity activity, String message) {
		super(activity, "Message Management", message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Called to load accounts instances from the payload
	 * 
	 * @param payload the accounts formated list
	 */
	public void loadMessages(String payload){
		String fields[] = payload.split("&");
		Context.messages.clear();
		for(String msg: fields){
			String parts[] = msg.split("%");
			Context.messages.add(new Message("-1", Context.id2Email(parts[1]), Context.id2Email(parts[4]), parts[5],parts[2], parts[3]));
		}
	}
	
	/**
	 * Called to load accounts instances from the payload
	 * 
	 * @param payload the accounts formated list
	 */
	public String messagesToString(LinkedList<Message> msgs){
		String msgs_st = "";
		int sep = 0;
		for(Message msg: msgs){
			if(sep > 0) msgs_st += "#";
			msgs_st += msg.getId()+"%"+msg.getSender()+"%"+msg.getReceiver()+"%"+msg.getContent()+"%"+msg.getStamp()+"%"+msg.getStatus();
			sep++;
		}
		return msgs_st;
	}
	
	public void populateMessages(){
		Context.LOGGER.pushDebugs("Executing the message all request");
		MessageHandler messageAllHandler = new MessageHandler(this.activity);
		KugriRequest messageAllRequest = new KugriRequest(messageAllHandler, RequestCode.generateStamp(), Context.baseUrl+"message/all", Context.access, RequestCode.REQUEST_ALL);
		KugriStaticTask messageAllTask = new KugriStaticTask(5000);
		messageAllTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, messageAllRequest);
		Context.LOGGER.pushDebugs("Finished executing the message all request");
	}

	@Override
	public void handler() {
		Context.LOGGER.pushDebugs("Message received not related to the Progression.");
		
		Bundle bundle = this.message.getData();
		String fields[];
		int what = this.message.what;
		KugriRequest request = bundle.getParcelable("REQUEST");
		KugriResponse response = bundle.getParcelable("RESPONSE");
		
		if(request != null && response != null){
		
			Context.LOGGER.pushDebugs("MessageHandler REQUEST: "+request.toJson());
			Context.LOGGER.pushDebugs("MessageHandler RESPONSE: "+response.toJson());
			
			SharedPreferences preferences = this.activity.getSharedPreferences(Context.PREFS_NAME, 0);
			SharedPreferences.Editor editor = preferences.edit();
			
			if(response != null && response.getCode().equals(ResponseCode.RESPONSE_SUCCESS)){
				switch (what) {
					case RequestCode.REQUEST_ALL:
						Context.LOGGER.pushDebugs("Loading messages data");
						loadMessages(response.getPayload());
						
						editor.putString("messages", response.getPayload());
						editor.commit();
						Context.LOGGER.pushDebugs("Commiting messages data");
						
						Context.loadData(preferences);
						
						for(Message msg: Context.messages){
							Context.LOGGER.pushDebugs("Message: "+msg.getSender()+" - "+msg.getReceiver()+" - "+msg.getContent());
						}
						
						messagesBox = (Spinner) this.activity.findViewById(R.id.spinnerbox);
						ListView messagesList = (ListView) this.activity.findViewById(R.id.message_list);
						MessageAdapter adapter;
						switch(messagesBox.getSelectedItemPosition()){
							case 0:
								LinkedList<Message> recevbox = new LinkedList<Message>();
								for(Integer index: com.kugri.frontend.client.android.helloworld.Context.recvbox){
									recevbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
								}
								
								Collections.reverse(recevbox);
								
								adapter = new MessageAdapter(this.activity, recevbox);
								messagesList.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								break;
							case 1:
								LinkedList<Message> sentbox = new LinkedList<Message>();
								for(Integer index: com.kugri.frontend.client.android.helloworld.Context.sentbox){
									sentbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
								}
								
								Collections.reverse(sentbox);
								
								adapter = new MessageAdapter(this.activity, sentbox);
								messagesList.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								break;
							case 2:
								LinkedList<Message> trashbox = new LinkedList<Message>();
								for(Integer index: com.kugri.frontend.client.android.helloworld.Context.trashbox){
									trashbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
								}
								
								Collections.reverse(trashbox);
								
								adapter =new MessageAdapter(this.activity, trashbox);
								messagesList.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								break;
							default:
								LinkedList<Message> defaultbox = new LinkedList<Message>();
								for(Integer index: com.kugri.frontend.client.android.helloworld.Context.recvbox){
									defaultbox.add(com.kugri.frontend.client.android.helloworld.Context.messages.get(index));
								}
								Collections.reverse(defaultbox);
								adapter =new MessageAdapter(this.activity, defaultbox);
								messagesList.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								break;
						}
						
						break;
					case RequestCode.REQUEST_CHECK:
						break;
					case RequestCode.REQUEST_RECEIVE:
						
						break;
					case RequestCode.REQUEST_RESTORE:
						break;
					case RequestCode.REQUEST_SEND:
						
						Context.LOGGER.pushDebugs("Populating the messages list");
						populateMessages();
						Context.LOGGER.pushDebugs("The messages list has been populated");
						break;
					case RequestCode.REQUEST_TRASH:
						break;
					default:
						break;
				}
			}else Toast.makeText(this.activity, (response == null?"Null response from web service":response.getCode()+"#"+response.getPayload()), Toast.LENGTH_LONG).show();
		}else{
			Context.LOGGER.pushErrors(new Exception(), "MessageHandler"+(request == null?"Request is null ":"")+ (response == null?"Response is null ":""));
		}
		
	}

}
