package com.ausloeser.screens;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.views.Utils;

/**
 * as long as user holds down the button this class allows to fire as fast as possible in a row
 * 
 * @author Lutz Reiter & Arnim Jepsen
 *
 */
public class SmsTriggerActivity extends SherlockActivity {
	
	public class SmsReceiver extends BroadcastReceiver
	{
		
	    @Override
	    public void onReceive(Context context, Intent intent) 
	    {
	        //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        String str = "";            
	        if (bundle != null)
	        {
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];            
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	    			Log.e("INFO","sms received in sms receiver.");
	                
	    			// send event
	    			/*msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                str += "SMS from " + msgs[i].getOriginatingAddress();                     
	                str += " :";
	                str += msgs[i].getMessageBody().toString();
	                str += "\n";*/
	    			OnSmsReceived(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody().toString());
	    			Toast.makeText(context, msgs[i].getMessageBody().toString(), Toast.LENGTH_SHORT).show();
	            }
	        }                         
	    }
	}
	
	ToggleButton shutterButton;
	
	SingletonCameraController cameraControler;
	SmsReceiver smsReceiver;
	
	View controlLayout,progressLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_trigger);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		cameraControler = SingletonCameraController.INSTANCE;
		smsReceiver = new SmsReceiver();
		
		controlLayout = findViewById(R.id.ControlsLayout); // holds sliders and buttons
		progressLayout = findViewById(R.id.ProgressLayout); // holds progress bars
		shutterButton = (ToggleButton) findViewById(R.id.ButtonShutter);
		shutterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (shutterButton.isChecked()) {
					startSmsTriggerMode();
				} else {
					stopSmsTriggerMode();
				}
			}
		});
		
		
		// apply fonts
		Utils.applyFonts(findViewById(R.id.mainLayout),Typeface.createFromAsset(getAssets(),"fonts/eurostile.ttf"));
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_cable_remote, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		//android.provider.Telephony.SMS_RECEIVED
		//register sms receiver
		//this.registerReceiver(smsReceiver, new IntentFilter(TelephonyManager.);
		
		
		// load settings
		SharedPreferences prefs = getSharedPreferences(
				"com.ausloeser.app", this.MODE_PRIVATE);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		// save settings
		SharedPreferences prefs = getSharedPreferences(
				"com.ausloeser.app", this.MODE_PRIVATE);
		
	}
	
	public void startSmsTriggerMode() {
		controlLayout.setVisibility(View.GONE);
		progressLayout.setVisibility(View.VISIBLE);
	}
	
	public void stopSmsTriggerMode() {
		controlLayout.setVisibility(View.VISIBLE);
		progressLayout.setVisibility(View.GONE);
		shutterButton.setChecked(false);
	}
	
	public void OnSmsReceived(String sender, String msg) {
		Log.e("INFO", "JAAA MANN! msg:" + msg + " sender: "+sender);
		if (msg.contains("+trigger"))
			Log.e("INFO","FETT!");
	}

}
