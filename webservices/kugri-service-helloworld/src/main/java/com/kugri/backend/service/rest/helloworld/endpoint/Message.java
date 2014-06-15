/*=============================================================================================*/
/* Class            : Message                                                                  */
/*                                                                                             */
/* Description      : Message class to present the message endpoint and actions.               */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.helloworld.endpoint;

import java.util.Date;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kugri.backend.service.rest.helloworld.endpoint.AbstractEndpoint;
import com.kugri.backend.service.rest.common.endpoint.ResponseCode;
import com.kugri.backend.service.rest.common.entity.EntAccount;
import com.kugri.backend.service.rest.helloworld.entity.EntMessage;
import com.kugri.backend.service.rest.common.entity.Index;
import com.kugri.backend.service.rest.common.envelop.EnvAccountAccess;
import com.kugri.backend.service.rest.common.envelop.EFResponse;
import com.kugri.backend.service.rest.helloworld.envelop.EnvMessageAction;
import com.kugri.backend.service.rest.helloworld.envelop.EnvMessageSend;
import com.kugri.backend.service.rest.common.envelop.Request;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Message class to present the message endpoint and actions.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@Path("message")
public class Message extends AbstractEndpoint {
	
	/**
	 * Called to check the endpoint status
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@Override
	public Response status(Request request) {
		actionScope = 3;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		int userScope = 0;
		EnvAccountAccess AccessObject = new EnvAccountAccess(payload);
		if(AccessObject.sanity()){
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not load from account table: "+e.getMessage();
				entered = false;
			}
			if(entered){
				try{
					sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not load from session table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered){
				try{
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not load from index table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered){
				try{
					statuses = StringToStatuses(adapter.read("status", "id,value"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not load from status table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered){
				try{
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not load from message table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered) userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(AccessObject.getAccess()).getAccount()).getScope());
			if(isActionGrant(userScope) && entered){
				Index index = getIndex("message");
				String messageTableIndex = index.getId();
				long messageID = Long.parseLong(index.getNext());
				try{
					this.adapter.write("message", "id,sender,receiver,content,stamp,status", messageID+",0,0,ping,"+dateFormat.format(stamp)+",SENT");
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not write into message table: "+e.getMessage();
					entered = false;
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", messageTableIndex, "next", ""+(messageID+1));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						adapter.sync("message", "id", ""+messageID, "status", "SEEN");
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not sync into message table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						this.adapter.delete("message", "id",""+(messageID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not delete from message table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", messageTableIndex, "next", ""+(messageID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#The endpoint [:message] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered) envelop = ResponseCode.RESPONSE_SUCCESS+"#The endpoint [:message] is up and running.";
			}else{
				if(entered) envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
			}
		}else{
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
		}
		
		response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/send
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/send")
	@Consumes({ "application/xml", "application/json" })
	public Response send(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvMessageSend SendObject = new EnvMessageSend(payload);
		if (SendObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(SendObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(SendObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(SendObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							Index index = getIndex("message");
							long messageID = Long.parseLong(index.getNext());
							this.adapter.write("message", "id,sender,receiver,content,stamp,status", messageID+","+account.getId()+","+email2Account(SendObject.getReceiver())+","+SendObject.getContent()+","+SendObject.getStamp()+","+getIdfromStatus("SENT"));
							adapter.sync("indexer", "id", index.getId(), "next", ""+(messageID+1));
							envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. Message sent.";
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/trash
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/trash")
	@Consumes({ "application/xml", "application/json" })
	public Response trash(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvMessageAction ActionObject = new EnvMessageAction(payload);
		if (ActionObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(ActionObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
					if(checkAccess(ActionObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(ActionObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							LinkedList<EntMessage> selectedMessages = new LinkedList<EntMessage>();
							String messagesblock[] = ActionObject.getMessages().split("&");
							for(String message: messagesblock){
								selectedMessages.add(getMessagefromId(message));
							}
							String toSend = "";
							int sep = 0;
							for(EntMessage msg: selectedMessages){
								if(sep > 0) toSend += "&";
								if(msg.getSender().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount()) ||
										msg.getReceiver().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount())){
									if(getStatusfromId(msg.getStatus()).equals("SEEN")){
										this.adapter.sync("message", "id", msg.getId(), "status", getIdfromStatus("TRASH"));
										toSend += "OK";
									}else{
										toSend += "KO";
									}
								}else{
									toSend += "KO";
								}
								sep++;
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+toSend;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/restore
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/restore")
	@Consumes({ "application/xml", "application/json" })
	public Response restore(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvMessageAction ActionObject = new EnvMessageAction(payload);
		if (ActionObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(ActionObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
					if(checkAccess(ActionObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(ActionObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							LinkedList<EntMessage> selectedMessages = new LinkedList<EntMessage>();
							String messagesblock[] = ActionObject.getMessages().split("&");
							for(String message: messagesblock){
								selectedMessages.add(getMessagefromId(message));
							}
							String toSend = "";
							int sep = 0;
							for(EntMessage msg: selectedMessages){
								if(sep > 0) toSend += "&";
								if(msg.getSender().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount()) ||
										msg.getReceiver().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount())){
									if(getStatusfromId(msg.getStatus()).equals("TRASH")){
										this.adapter.sync("message", "id", msg.getId(), "status", getIdfromStatus("SEEN"));
										toSend += "OK";
									}else{
										toSend += "KO";
									}
								}else{
									toSend += "KO";
								}
								sep++;
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+toSend;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/check
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/check")
	@Consumes({ "application/xml", "application/json" })
	public Response check(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvMessageAction ActionObject = new EnvMessageAction(payload);
		if (ActionObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(ActionObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
					if(checkAccess(ActionObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(ActionObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							LinkedList<EntMessage> selectedMessages = new LinkedList<EntMessage>();
							String messagesblock[] = ActionObject.getMessages().split("&");
							for(String message: messagesblock){
								selectedMessages.add(getMessagefromId(message));
							}
							String toSend = "";
							int sep = 0;
							for(EntMessage msg: selectedMessages){
								if(sep > 0) toSend += "&";
								if(msg.getSender().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount()) ||
										msg.getReceiver().equals(getSessionfromAccessKey(ActionObject.getAccess()).getAccount())){
									if(getStatusfromId(msg.getStatus()).equals("SEEN")){
										toSend += "OK";
									}else{
										toSend += "KO";
									}
								}else{
									toSend += "KO";
								}
								sep++;
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+toSend;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/receive
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/receive")
	@Consumes({ "application/xml", "application/json" })
	public Response receive(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountAccess AccessObject = new EnvAccountAccess(payload);
		if (AccessObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(AccessObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(AccessObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(AccessObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							String list = "";
							int separe = 0;
							for(EntMessage sms: this.messages){
								if(getStatusfromId(sms.getStatus()).equals("SENT") && sms.getReceiver().equals(account.getId())){
									this.adapter.sync("message", "id", sms.getId(), "status", getIdfromStatus("SEEN"));
									if(separe > 0) list += "&";
									list += sms.getId()+"%"+sms.getStamp()+"%"+sms.getSender()+"%"+sms.getContent();
									separe++;
								}
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+list;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request message/all
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/all")
	@Consumes({ "application/xml", "application/json" })
	public Response all(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountAccess AccessObject = new EnvAccountAccess(payload);
		if (AccessObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(AccessObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					messages = StringToMessages(adapter.read("message", "id,sender,receiver,content,stamp,status"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(AccessObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(AccessObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							String list = "";
							int separe = 0;
							for(EntMessage sms: this.messages){
								if(sms.getSender().equals(account.getId())){
									if(separe > 0) list += "&";
									if(getStatusfromId(sms.getStatus()).equals("TRASH"))
										list += "TRASHBOX%"+sms.getId()+"%"+sms.getStamp()+"%"+getStatusfromId(sms.getStatus())+"%"+sms.getReceiver()+"%"+sms.getContent();
									else
										list += "SENTBOX%"+sms.getId()+"%"+sms.getStamp()+"%"+getStatusfromId(sms.getStatus())+"%"+sms.getReceiver()+"%"+sms.getContent();
									separe++;
								}
								if(sms.getReceiver().equals(account.getId())){
									if(separe > 0) list += "&";
									if(getStatusfromId(sms.getStatus()).equals("TRASH"))
										list += "TRASHBOX%"+sms.getId()+"%"+sms.getStamp()+"%"+getStatusfromId(sms.getStatus())+"%"+sms.getSender()+"%"+sms.getContent();
									else
										list += "RECVBOX%"+sms.getId()+"%"+sms.getStamp()+"%"+getStatusfromId(sms.getStatus())+"%"+sms.getSender()+"%"+sms.getContent();
									separe++;
								}
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+list;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key. Or you need to login first.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
}
