/*=============================================================================================*/
/* Class            : Account                                                                  */
/*                                                                                             */
/* Description      : Account class to present the account endpoint and actions.               */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.endpoint;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kugri.backend.service.rest.common.entity.EntAccount;
import com.kugri.backend.service.rest.common.entity.EntSession;
import com.kugri.backend.service.rest.common.entity.Index;
import com.kugri.backend.service.rest.common.envelop.EFResponse;
import com.kugri.backend.service.rest.common.envelop.EnvAccountAccess;
//import com.kugri.backend.service.rest.common.envelop.EnvAccountAction;
import com.kugri.backend.service.rest.common.envelop.EnvAccountLogin;
import com.kugri.backend.service.rest.common.envelop.EnvAccountLost;
import com.kugri.backend.service.rest.common.envelop.EnvAccountRecover;
import com.kugri.backend.service.rest.common.envelop.EnvAccountRegister;
import com.kugri.backend.service.rest.common.envelop.Request;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * Account class to present the account endpoint and actions.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
@Path("account")
public class Account extends AbstractEndpoint {
	
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
				envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not load from account table: "+e.getMessage();
				entered = false;
			}
			if(entered){
				try{
					sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not load from session table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered){
				try{
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not load from index table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered){
				try{
					statuses = StringToStatuses(adapter.read("status", "id,value"));
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not load from status table: "+e.getMessage();
					entered = false;
				}
			}
			if(entered) userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(AccessObject.getAccess()).getAccount()).getScope());
			if(isActionGrant(userScope) && entered){
				Index index  = getIndex("account");
				String accountTableIndex = index.getId();
				long accountID = Long.parseLong(index.getNext());
				try{
					this.adapter.write("account", "id,email,password,scope", accountID +",blablabla@blibilbi.com" +","+generateKey("fakeOne", "ACCOUNTHELLOWORLD2014")+",1");
				}catch(Exception e){
					envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not write into account table: "+e.getMessage();
					entered = false;
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", accountTableIndex, "next", ""+(accountID+1));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				long statusID = Long.parseLong(getIdfromStatus("REGISTER"));
				index = getIndex("session");
				String sessionTableIndex = index.getId();
				long sessionID = Long.parseLong(index.getNext());
				String accessKey = generateKey("blablabla@blibilbi.com", "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
				if(entered){
					try{
						this.adapter.write("session", "id,account,stamp,access,status", sessionID +","+ accountID +","+dateFormat.format(stamp)+","+accessKey+","+statusID);
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not write into session table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", sessionTableIndex, "next", ""+(sessionID+1));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						this.adapter.delete("account", "id",""+(accountID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not delete from account table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", accountTableIndex, "next", ""+(accountID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						this.adapter.delete("session", "id",""+(sessionID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not delete from session table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered){
					try{
						adapter.sync("indexer", "id", sessionTableIndex, "next", ""+(sessionID));
					}catch(Exception e){
						envelop = ResponseCode.RESPONSE_SERVICE_KO+"#The endpoint [:account] could not sync into index table: "+e.getMessage();
						entered = false;
					}
				}
				if(entered) envelop = ResponseCode.RESPONSE_SERVICE_OK+"#The endpoint [:account] is up and running.";
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
	 * Called to request account/register
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/register")
	@Consumes({ "application/xml", "application/json" })
	public Response register(Request request){
		actionScope = 1;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountRegister RegisterObject = new EnvAccountRegister(payload);
		if (RegisterObject.sanity()) {
			try{
				indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				statuses = StringToStatuses(adapter.read("status", "id,value"));
				scopes = StringToScopes(adapter.read("scope", "id,value"));
				if(email2Account(RegisterObject.getEmail()) == 0){
					Index index  = getIndex("account");
					long accountID = Long.parseLong(index.getNext());
					if(RegisterObject.getHidden()!=null){
						if(isFirstAdmin()){
							if(RegisterObject.getHidden().equals("HelloworldWebService2014")){
								this.adapter.write("account", "id,email,password,scope", accountID +","+ RegisterObject.getEmail() +","+generateKey(RegisterObject.getPassword(), "ACCOUNTHELLOWORLD2014")+",3");
								adapter.sync("indexer", "id", index.getId(), "next", ""+(accountID+1));
								long statusID = Long.parseLong(getIdfromStatus("REGISTER"));
								index = getIndex("session");
								long sessionID = Long.parseLong(index.getNext());
								String accessKey = generateKey(RegisterObject.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
								this.adapter.write("session", "id,account,stamp,access,status", sessionID +","+ accountID +","+dateFormat.format(stamp)+","+accessKey+","+statusID);
								adapter.sync("indexer", "id", index.getId(), "next", ""+(sessionID+1));
								envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. New account created.";
								//sendMail(type, from, to, subject, content);
								sendMail("TEXT", "no_reply@kugri.com", RegisterObject.getEmail(), "Account creation", "Your account have been created successfully.");
							}else{
								envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Creation denied.";
							}
						}else{
							if(!getSessionfromAccessKey(RegisterObject.getHidden()).getId().equals("0")){
								this.adapter.write("account", "id,email,password,scope", accountID +","+ RegisterObject.getEmail() +","+generateKey(RegisterObject.getPassword(), "ACCOUNTHELLOWORLD2014")+",3");
								adapter.sync("indexer", "id", index.getId(), "next", ""+(accountID+1));
								long statusID = Long.parseLong(getIdfromStatus("REGISTER"));
								index = getIndex("session");
								long sessionID = Long.parseLong(index.getNext());
								String accessKey = generateKey(RegisterObject.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
								this.adapter.write("session", "id,account,stamp,access,status", sessionID +","+ accountID +","+dateFormat.format(stamp)+","+accessKey+","+statusID);
								adapter.sync("indexer", "id", index.getId(), "next", ""+(sessionID+1));
								envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. New account created.";
								sendMail("TEXT", "no_reply@kugri.com", RegisterObject.getEmail(), "Account creation", "Your account have been created successfully.");
							}else{
								envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Creation denied.";
							}
						}
					}else{
						this.adapter.write("account", "id,email,password,scope", accountID +","+ RegisterObject.getEmail() +","+generateKey(RegisterObject.getPassword(), "ACCOUNTHELLOWORLD2014")+",2");
						adapter.sync("indexer", "id", index.getId(), "next", ""+(accountID+1));
						long statusID = Long.parseLong(getIdfromStatus("REGISTER"));
						index = getIndex("session");
						long sessionID = Long.parseLong(index.getNext());
						String accessKey = generateKey(RegisterObject.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
						this.adapter.write("session", "id,account,stamp,access,status", sessionID +","+ accountID +","+dateFormat.format(stamp)+","+accessKey+","+statusID);
						adapter.sync("indexer", "id", index.getId(), "next", ""+(sessionID+1));
						envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. New account created.";
						sendMail("TEXT", "no_reply@kugri.com", RegisterObject.getEmail(), "Account creation", "Your account have been created successfully.");
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is already an account associated to this email.";
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
	 * Called to request account/login
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/login")
	@Consumes({ "application/xml", "application/json" })
	public Response login(Request request){
		actionScope = 1;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountLogin LoginObject = new EnvAccountLogin(payload);
		if (LoginObject.sanity()) {
			try{
			accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
			int userScope = Integer.parseInt(getAccountfromId(""+email2Account(LoginObject.getEmail())).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					scopes = StringToScopes(adapter.read("scope", "id,value"));
					if(login(LoginObject.getEmail(), LoginObject.getPassword())){
						int accountId = email2Account(LoginObject.getEmail());
						if(accountId == 0){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this email.";
						}else{
							long statusId = Long.parseLong(getIdfromStatus("LOGIN"));
							String accessKey = generateKey(LoginObject.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
							this.adapter.sync("session", "id",getSessionfromAccountId(""+accountId).getId(),"stamp,access,status", dateFormat.format(stamp) +","+ accessKey +","+ statusId);
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+accessKey;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown email and password.";
					}
					response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
				} else {
					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
				}
			}catch(Exception e){
				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
			}
		}else{
			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
		}
		response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * Called to request account/logout
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/logout")
	@Consumes({ "application/xml", "application/json" })
	public Response logout(Request request){
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
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(AccessObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(AccessObject.getAccess()).getAccount());
						long statusId = Long.parseLong(getIdfromStatus("LOGOUT"));
						String accessKey = generateKey(account.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
						this.adapter.sync("session", "id",getSessionfromAccountId(""+account.getId()).getId(),"stamp,access,status", dateFormat.format(stamp) +","+ accessKey +","+ statusId);
						envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. Logout Succeeded.";
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
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
	 * Called to request account/lost
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/lost")
	@Consumes({ "application/xml", "application/json" })
	public Response lost(Request request){
		actionScope = 1;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountLost LostObject = new EnvAccountLost(payload);
		if (LostObject.sanity()) {
			try{
				indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				statuses = StringToStatuses(adapter.read("status", "id,value"));
				int accountId = email2Account(LostObject.getEmail());
				if(accountId != 0){
					EntSession session = getSessionfromAccountId(""+accountId);
					long statusId = Long.parseLong(getIdfromStatus("RECOVER"));
					String accessKey = generateKey(LostObject.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
					this.adapter.sync("session", "id",session.getId(),"stamp,access,status", dateFormat.format(stamp) +","+ accessKey +","+ statusId);
					envelop = ResponseCode.RESPONSE_SUCCESS+"#"+accessKey;
					sendMail("TEXT", "no_reply@kugri.com", LostObject.getEmail(), "Helloworld Service Messaging", "You just issued an account recovery that returned the key: "+accessKey);
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this email.";
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
	 * Called to request account/recover
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/recover")
	@Consumes({ "application/xml", "application/json" })
	public Response recover(Request request){
		actionScope = 2;
		Date stamp = new Date();
		EFResponse response;
		String envelop = "";
		String payload = request.getPayload();
		EnvAccountRecover RecoverObject = new EnvAccountRecover(payload);
		if (RecoverObject.sanity()) {
			try{
				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(RecoverObject.getAccess()).getAccount()).getScope());
				if(isActionGrant(userScope)){
					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					EntSession session = getSessionfromAccessKey(RecoverObject.getAccess());
					if(!session.getId().equals("0")){
						if(getStatusfromId(session.getStatus()).equals("RECOVER")){
							EntAccount account = getAccountfromId(session.getAccount());
							long statusId = Long.parseLong(getIdfromStatus("LOGOUT"));
							String accessKey = generateKey(account.getEmail(), "SESSIONHELLOWORLD2014"+dateFormat.format(stamp));
							this.adapter.sync("account", "id",account.getId(),"password", generateKey(RecoverObject.getPassword(), "ACCOUNTHELLOWORLD2014"));
							this.adapter.sync("session", "id",session.getId(),"stamp,access,status", dateFormat.format(stamp) +","+ accessKey +","+ statusId);
							envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. Account Recovering Succeeded. Please login with your new password. :)";
							sendMail("TEXT", "no_reply@kugri.com", account.getEmail(), "Account modification", "Your account password has been changed successfully.");
						}else{
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key. Or you need to declare lost first.";
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
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
	 * Called to request account/unregister
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
	@POST
	@Path("/unregister")
	@Consumes({ "application/xml", "application/json" })
	public Response unregister(Request request){
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
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(AccessObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(AccessObject.getAccess()).getAccount());
						if(account == null){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							this.adapter.delete("account", "id", account.getId());
							this.adapter.delete("session", "account", account.getId());
							this.adapter.delete("message", "receiver", account.getId());
							this.adapter.sync("message", "sender", account.getId(),"sender","0");
							envelop = ResponseCode.RESPONSE_SUCCESS+"#Request succeeded. Unregistering succeeded.";
							sendMail("TEXT", "no_reply@kugri.com", account.getEmail(), "Account deletion", "Your account password has been deleted successfully.");
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key. Or you need to login first.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
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
	 * Called to request account/check
	 * 
	 * @param request the received request
	 * @return Response that contains the response to the request
	 */
//	@POST
//	@Path("/check")
//	@Consumes({ "application/xml", "application/json" })
//	public Response check(Request request){
//		actionScope = 2;
//		Date stamp = new Date();
//		EFResponse response;
//		String envelop = "";
//		String payload = request.getPayload();
//		EnvAccountAction ActionObject = new EnvAccountAction(payload);
//		if (ActionObject.sanity()) {
//			try{
//				accounts = StringToAccounts(adapter.read("account", "id,email,password,scope"));
//				sessions = StringToSessions(adapter.read("session", "id,account,stamp,access,status"));
//				int userScope = Integer.parseInt(getAccountfromId(""+getSessionfromAccessKey(ActionObject.getAccess()).getAccount()).getScope());
//				if(isActionGrant(userScope)){
//					indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
//					statuses = StringToStatuses(adapter.read("status", "id,value"));
//					scopes = StringToScopes(adapter.read("scope", "id,value"));
//					if(checkAccess(ActionObject.getAccess())){
//						String accountsblock[] = ActionObject.getAccounts().split("&");
//						String toSend = "";
//						int sep = 0;
//						for(String account: accountsblock){
//							if(sep > 0) toSend += "&";
//							EntAccount acc = getAccountfromId(account);
//							if(userScope >= Integer.parseInt(acc.getScope()))
//								toSend += getscopefromId(acc.getScope())+"%"+acc.getEmail();
//							sep++;
//						}
//						envelop = ResponseCode.RESPONSE_SUCCESS+"#"+toSend;
//					}else{
//						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key.";
//					}
//				}else{
//					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
//				}
//			}catch(Exception e){
//				envelop = ResponseCode.RESPONSE_FAILURE_BROKEN_PERSISTENCE+"#Request failed. Consistency breach. Check the endpoint status.";
//			}
//			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
//		} else {
//			envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_MALFORMED+"#Request failed. Bad request format.";
//			response = new EFResponse(AbstractEndpoint.dateFormat.format(stamp),envelop);
//		}
//		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
//	}
	
	/**
	 * Called to request account/all
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
					statuses = StringToStatuses(adapter.read("status", "id,value"));
					if(checkAccess(AccessObject.getAccess())){
						EntAccount account = getAccountfromId(getSessionfromAccessKey(AccessObject.getAccess()).getAccount());
						if(account.getId().equals("0")){
							envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. Unknown account.";
						}else{
							String list = "";
							int separe = 0;
							for(EntAccount acc: this.accounts){
								//if(!acc.getId().equals(account.getId())){
									if(separe > 0) list += "&";
									list += acc.getId()+ "%"+ acc.getEmail();
									separe++;
								//}
							}
							envelop = ResponseCode.RESPONSE_SUCCESS+"#"+list;
						}
					}else{
						envelop = ResponseCode.RESPONSE_FAILURE_BROAD+"#Request failed. There is no account associated to this key.";
					}
				}else{
					envelop = ResponseCode.RESPONSE_FAILURE_REQUEST_DENIED+"#Request failed. Access dinied.";
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
