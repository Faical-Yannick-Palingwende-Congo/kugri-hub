package com.kugri.frontend.client.android.helloworld;

import com.kugri.frontend.client.android.helloworld.Context;
import com.kugri.frontend.client.android.common.request.KugriRequest;
import com.kugri.frontend.client.android.common.task.KugriBlockingTask;
import com.kugri.frontend.client.android.helloworld.handler.AccountHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	
	public static final String PREFS_NAME = Context.PREFS_NAME+"Helloworld-PrefsFile";
	
	EditText email;
	EditText password;
	Button login;
	Button cancel;
	
	Button register;
	Button lost;
	
	AccountHandler loginhandler;
	KugriRequest loginRequest;
	KugriBlockingTask loginTask;
	
	KugriBlockingTask logoutTask;
	KugriRequest logoutRequest;
	AccountHandler logouthandler;
	
	AccountHandler registerhandler;
	KugriRequest registerRequest;
	KugriBlockingTask registerTask;
	
	AccountHandler losthandler;
	KugriRequest lostRequest;
	KugriBlockingTask lostTask;
	
	SharedPreferences preferences;
	
	ProgressDialog loginProgress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		
		setContentView(R.layout.login);
		
		Context.LOGGER.pushDebugs("Entered LoginActivity onCreate");
		
		Context.LOGGER.pushDebugs("Instanciating the views");
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		
		login = (Button) findViewById(R.id.login);
		cancel = (Button) findViewById(R.id.cancel);
		register = (Button) findViewById(R.id.register);
		lost = (Button) findViewById(R.id.lost);
		
		Context.LOGGER.pushDebugs("All the views have been instanciated");
		
		Context.LOGGER.pushDebugs("Setting up the baseurl");
		Context.baseUrl = "http://www.kugri.com/kugri-service-helloworld/";
		Context.LOGGER.pushDebugs("The baseurl has been setup");
		
		Context.LOGGER.pushDebugs("Loading the statuses and the scopes");
		Context.StringToStatuses();
		Context.StringToScopes();
		Context.LOGGER.pushDebugs("The statuses and the scopes have been loaded");
		
		Context.LOGGER.pushDebugs("Loading the preferences fields");
		preferences = getSharedPreferences(Context.PREFS_NAME, 0);
		Context.loadData(preferences);
		Context.LOGGER.pushDebugs("The preferences fields have been loaded");
		
		Context.LOGGER.pushDebugs("Setting up the thread policy for the tasks execution");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		Context.LOGGER.pushDebugs("The thread policy has been set up");
		
		//Toast.makeText(this, Context.getStatusfromId("1"), Toast.LENGTH_LONG).show();
		
		Context.LOGGER.pushDebugs("Initializing the Sensitisation the login button");
		login.setEnabled(false);
		login.setClickable(false);
		
		register.setEnabled(false);
		register.setClickable(false);
		
		lost.setEnabled(false);
		lost.setClickable(false);
		
		if(isAccount()){
			login.setEnabled(true);
			login.setClickable(true);
		}else{
			login.setEnabled(false);
			login.setClickable(false);
		}
		Context.LOGGER.pushDebugs("The initial sensitization of the login button is done");
		
		Context.LOGGER.pushDebugs("Creating the editext watcher for the login button sensitization");
		TextWatcher watcher = new TextWatcher(){

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            	Context.LOGGER.pushDebugs("Inside beforeTextChanged");
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            	Context.LOGGER.pushDebugs("Inside onTextChanged");
            }

			public void afterTextChanged(Editable s) {
				Context.LOGGER.pushDebugs("Inside afterTextChanged");
				login.setEnabled(true);
				login.setClickable(true);
				
				register.setEnabled(true);
				register.setClickable(true);
				
				lost.setEnabled(true);
				lost.setClickable(true);
			}
        };
		
		email.addTextChangedListener(watcher);
		Context.LOGGER.pushDebugs("The editext has been created and affected to the email editext");
		
		Context.LOGGER.pushDebugs("Setting up the buttons listeners");
		login.setOnClickListener(this);
		cancel.setOnClickListener(this);
		register.setOnClickListener(this);
		lost.setOnClickListener(this);
		Context.LOGGER.pushDebugs("The buttons onclick listeners have been set up ");
		
		Context.LOGGER.pushDebugs("Setting up the handler");
		loginhandler = new AccountHandler(this, "Login...");
		logouthandler = new AccountHandler(this, "Logout...");
		registerhandler = new AccountHandler(this, "Registering...");
		losthandler = new AccountHandler(this, "Declaring lost...");
		Context.LOGGER.pushDebugs("The handler has been setup");
		
		Context.LOGGER.pushDebugs("Leaving the onCreate of LoginActivity");
	}
	
	public void login(){
		Context.LOGGER.pushDebugs("Executing the login request");
		loginRequest = new KugriRequest(loginhandler, RequestCode.generateStamp(), Context.baseUrl+"account/login", Context.email+"#"+Context.password, RequestCode.REQUEST_LOGIN);
		loginTask = new KugriBlockingTask(5000);
		loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginRequest);
		Context.LOGGER.pushDebugs("Finished executing the login request");
	}
	
	public void logout(){
		Context.LOGGER.pushDebugs("Executing the logout request");
		logoutRequest = new KugriRequest(logouthandler, RequestCode.generateStamp(), Context.baseUrl+"account/logout", Context.access, RequestCode.REQUEST_LOGOUT);
		logoutTask = new KugriBlockingTask(5000);
		logoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, logoutRequest);
		Context.LOGGER.pushDebugs("Finished executing the logout request");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Context.LOGGER.pushDebugs("Creating the Menu");
		getMenuInflater().inflate(R.menu.main, menu);
		Context.LOGGER.pushDebugs("The Menu has been created");
		return true;
	}
	
	private boolean isAccount(){
		Context.LOGGER.pushDebugs("Checking if the account is setup");
		if(Context.email.equals("")){//Remember to check that the password is not empty.
			Context.LOGGER.pushDebugs("Finished checking if the account is setup");
			return false;
		}else{
			email.setText(Context.email);
			password.setText(Context.password);
			Context.LOGGER.pushDebugs("Finished checking if the account is setup");
			return true;
		}
	}

	public void accountRegister(){
		Context.LOGGER.pushDebugs("Executing the register request");
		registerRequest = new KugriRequest(registerhandler, RequestCode.generateStamp(), Context.baseUrl+"account/register", Context.email+"#"+Context.password, RequestCode.REQUEST_REGISTER);
		registerTask = new KugriBlockingTask(5000);
		registerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, registerRequest);
		Context.LOGGER.pushDebugs("Finished executing the register request");
	}
	
	public void accountLost(){
		Context.LOGGER.pushDebugs("Executing the lost request");
		lostRequest = new KugriRequest(losthandler, RequestCode.generateStamp(), Context.baseUrl+"account/lost", Context.email, RequestCode.REQUEST_LOST);
		lostTask = new KugriBlockingTask(5000);
		lostTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, lostRequest);
		Context.LOGGER.pushDebugs("Finished executing the lost request");
	}
	
	public void onClick(View v) {
		if(v == login){
			if(password.getText().equals("")){
				Toast.makeText(this, "password must not be empty", Toast.LENGTH_LONG).show();
				Context.LOGGER.pushDebugs("Password empty error toasted");
			}else{
				Context.email = "" + email.getText();
				Context.password = "" + password.getText();
				Context.LOGGER.pushDebugs("Launching the login action");
				login();
			}
		}else if(v == cancel){
			if(!com.kugri.frontend.client.android.common.Context.access.equals("")){
				logout();
			}else this.finish();
		}else if(v == register){
			if(password.getText().equals("")){
				Toast.makeText(this, "password must not be empty", Toast.LENGTH_LONG).show();
				Context.LOGGER.pushDebugs("Password empty error toasted");
			}else{
				Context.email = "" + email.getText();
				Context.password = "" + password.getText();
				Context.LOGGER.pushDebugs("Launching the register action");
				accountRegister();
			}
		}else if(v == lost){
			accountLost();
		}else{
			Context.LOGGER.pushDebugs("Toasting the view wierd case");
			Toast.makeText(this, "Weird! Unknown view!!!", Toast.LENGTH_LONG).show();
			Context.LOGGER.pushDebugs("The view weird case toasted");
		}
	}
}
