package com.ausloeser.screens;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ausloeser.dialogs.BaseDialogBuilder;
import com.ausloeser.dialogs.NumberInputDialogBuilder;
import com.ausloeser.logic.OnDelayExposureTimerListener;
import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.logic.Values;
import com.ausloeser.views.Utils;

public class TimelapseActivity extends SherlockActivity implements OnClickListener, OnSeekBarChangeListener, OnDelayExposureTimerListener {
	
	View controlLayout,progressLayout;
	ToggleButton shutterButton;
	SingletonCameraController cameraControler;
	
	ToggleButton buttonExposure;
	SeekBar sliderExposure;
	ProgressBar progressExposure;
	TextView labelExposure,labelExposureProgress;
	int exposureTime;
	
	SeekBar sliderIntervall;
	ProgressBar progressIntervall;
	TextView labelIntervall,labelIntervallProgress;
	int intervallTime;
	
	SeekBar sliderNumber;
	ProgressBar progressNumber;
	TextView labelNumber,labelNumberProgress;
	int numberOfPictures;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timelapse);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//init camera controller
		cameraControler = SingletonCameraController.INSTANCE;
		cameraControler.setOnTimerUpdateListener(this);
		
		//get main layouts
		controlLayout = findViewById(R.id.ControlsLayout); // holds sliders and buttons
		progressLayout = findViewById(R.id.ProgressLayout); // holds progress bars
		shutterButton = (ToggleButton) findViewById(R.id.ButtonShutter);
		
		// get view vars
		sliderExposure = (SeekBar) findViewById(R.id.SliderExposure);
		buttonExposure = (ToggleButton) findViewById(R.id.ButtonExposure);
		labelExposure = (TextView) findViewById(R.id.LabelExposure);
		progressExposure = (ProgressBar) findViewById(R.id.ProgressExposure);
		labelExposureProgress = (TextView) findViewById(R.id.LabelExposureProgress);
		
		sliderIntervall = (SeekBar) findViewById(R.id.SliderIntervall);
		labelIntervall = (TextView) findViewById(R.id.LabelIntervall);
		progressIntervall = (ProgressBar) findViewById(R.id.ProgressIntervall);
		labelIntervallProgress = (TextView) findViewById(R.id.LabelIntervallProgress);
		
		sliderNumber = (SeekBar) findViewById(R.id.SliderNumber);
		labelNumber = (TextView) findViewById(R.id.LabelNumber);
		progressNumber = (ProgressBar) findViewById(R.id.ProgressNumber);
		labelNumberProgress = (TextView) findViewById(R.id.LabelNumberProgress);
		
		//setup listeners
		final Button buttonExposureSelect = (Button) findViewById(R.id.ButtonExposureSelect);
		buttonExposureSelect.setOnClickListener(this);
		shutterButton.setOnClickListener(this);
		sliderExposure.setOnSeekBarChangeListener(this);
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				buttonExposureSelect.setEnabled(arg1);
				setExposure(exposureTime);
			}
			
		});
		sliderIntervall.setOnSeekBarChangeListener(this);
		((Button) findViewById(R.id.ButtonIntervallSelect)).setOnClickListener(this);
		sliderNumber.setOnSeekBarChangeListener(this);
		((Button) findViewById(R.id.ButtonNumberSelect)).setOnClickListener(this); 
		
		//setup views
		buttonExposure.toggle();
		sliderExposure.setMax(Values.getExposureTimes().length-1);
		sliderIntervall.setMax(Values.getIntervallTimes().length-1);
		
		
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

	@Override
	public void onTimerExposureUpdate(long exposureLeft, long exposureTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerDelayUpdate(long delayLeft, long delayTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerTimelapseUpdate(long timeLeft, int intervalsLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		switch( arg0.getId() ){
		case R.id.SliderExposure:
			setExposure(Values.getExposureTime(arg1));
			break;
		case R.id.SliderIntervall:
			setIntervall(Values.getIntervallTime(arg1));
			break;
		case R.id.SliderNumber:
			if (arg1 == 501)
				setNumber(-1);
			else	
				setNumber(arg1);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		
		switch( v.getId() ){
		
		case R.id.ButtonShutter:
			
			if (shutterButton.isChecked()) {
				this.startTriggerCamera();
			} else {
				this.stopTriggerCamera();
			}
			break;
			
		case R.id.ButtonExposureSelect:
			// create a dialog for picking the exposure
			final NumberInputDialogBuilder builder = new NumberInputDialogBuilder(this,"Set Exposure Time (s)",
					exposureTime/1000.0F,0.0F,99999.9F);
			
			builder.SetDialogResultListener(new BaseDialogBuilder.OnDialogResultListener() {

				@Override
				public void OnDialogResultListener(boolean result) {
					if (result) {
						if (builder.getValue() > 0)
						sliderExposure.setProgress(0);
						setExposure((int) (builder.getValue()*1000.0F));
					}
				}

			});
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case R.id.ButtonIntervallSelect:
			// create a dialog for picking the exposure
			final NumberInputDialogBuilder builder2 = new NumberInputDialogBuilder(this,"Set Intervall Time (s)",
					intervallTime/1000.0F,0.0F,99999.9F);
			
			builder2.SetDialogResultListener(new BaseDialogBuilder.OnDialogResultListener() {

				@Override
				public void OnDialogResultListener(boolean result) {
					if (result) {
						if (builder2.getValue() > 0)
						sliderIntervall.setProgress(0);
						setIntervall((int) (builder2.getValue()*1000.0F));
					}
				}

			});
			AlertDialog dialog2 = builder2.create();
			dialog2.show();
			break;
		case R.id.ButtonNumberSelect:
			// create a dialog for picking the exposure
			final NumberInputDialogBuilder builder3 = new NumberInputDialogBuilder(this,"Set Number of Pictures",
					numberOfPictures,0,9999999);
			
			builder3.SetDialogResultListener(new BaseDialogBuilder.OnDialogResultListener() {

				@Override
				public void OnDialogResultListener(boolean result) {
					if (result) {
						if (builder3.getValue() > 0)
						sliderNumber.setProgress(0);
						setNumber((int) (builder3.getValue()));
					}
				}

			});
			AlertDialog dialog3 = builder3.create();
			dialog3.show();
			break;
		}
	}
	
	private void setExposure(int value) {
		exposureTime = value;
		if (buttonExposure.isChecked())
			labelExposure.setText((value/1000.0F)+"s");
		else
			labelExposure.setText("Cam");	
	}
	
	private void setIntervall(int value) {
		intervallTime = value;
		labelIntervall.setText((value/1000.0F)+"s");
	}
	
	private void setNumber(int value) {
		numberOfPictures = value;
		if (value == -1)
			labelNumber.setText("unlimited");
		else
			labelNumber.setText(value+"");
	}

	protected void startTriggerCamera() {
		controlLayout.setVisibility(View.GONE);
		progressLayout.setVisibility(View.VISIBLE);
		cameraControler.triggerExposure(exposureTime);
	}
	
	protected void stopTriggerCamera() {
		controlLayout.setVisibility(View.VISIBLE);
		progressLayout.setVisibility(View.GONE);
		shutterButton.setChecked(false);
		cameraControler.triggerStop();
		
	}
	

}
