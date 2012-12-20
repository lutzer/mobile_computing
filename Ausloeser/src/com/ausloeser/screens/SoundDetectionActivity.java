package com.ausloeser.screens;

import java.io.IOException;

import root.gast.audio.interp.SingleClapDetector;
import root.gast.audio.util.RecorderErrorLoggerListener;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
public class SoundDetectionActivity extends SherlockActivity {
	
	Button shutterButton;
	
	SingletonCameraController cameraControler;
	
	SingleClapDetector clapDetector;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound_detection);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		cameraControler = SingletonCameraController.INSTANCE;
		
		clapDetector = new SingleClapDetector();
		
		shutterButton = (Button) findViewById(R.id.ButtonShutter);
		
		shutterButton.setOnTouchListener(new Button.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN ) {
					cameraControler.triggerUnlimited();
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ) {
					cameraControler.triggerStop();
                }

				
				return false;
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
	
	
	public static MediaRecorder prepareRecorder(String sdCardPath)
			throws IOException
			{
//			if (!isStorageReady())
//			{
//			throw new IOException("SD card is not available");
//			}
			MediaRecorder recorder = new MediaRecorder();
			//set a custom listener that just logs any messages
			RecorderErrorLoggerListener recorderListener =
			new RecorderErrorLoggerListener();
			recorder.setOnErrorListener(recorderListener);
			recorder.setOnInfoListener(recorderListener);
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			//Log.d(TAG, "recording to: " + sdCardPath);
			recorder.setOutputFile(sdCardPath);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.prepare();
			return recorder;
			}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
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
}

