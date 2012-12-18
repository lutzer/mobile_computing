package com.ausloeser.screens;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.logic.Values;
import com.ausloeser.views.Utils;

/**
 * Fragment holding the interface to do a simple trigger
 * 
 * @author Arnim Jepsen & Lutz Reiter
 *
 */

public class SimpleCableRemoteFragment extends AbstractCableRemoteFragment {
	
	final static String TAG ="SimpleCableRemoteFragment";
	
	ToggleButton buttonExposure;
	SeekBar sliderExposure;
	ProgressBar progressExposure;
	TextView labelExposure,labelExposureProgress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_simple_cable_remote, container, false);
		
		//init camera controller
		cameraControler = SingletonCameraController.INSTANCE;
		cameraControler.setOnTimerUpdateListener(this);
		
		//get main layouts
		controlLayout = view.findViewById(R.id.ControlsLayout); // holds sliders and buttons
		progressLayout = view.findViewById(R.id.ProgressLayout); // holds progress bars
		shutterButton = (ToggleButton) view.findViewById(R.id.ButtonShutter);
		// get view vars
		sliderExposure = (SeekBar) view.findViewById(R.id.SliderExposure);
		buttonExposure = (ToggleButton) view.findViewById(R.id.ButtonExposure);
		labelExposure = (TextView) view.findViewById(R.id.LabelExposure);
		progressExposure = (ProgressBar) view.findViewById(R.id.ProgressExposure);
		labelExposureProgress = (TextView) view.findViewById(R.id.LabelExposureProgress);
		
		//setup listeners
		final Button buttonExposureSelect = (Button) view.findViewById(R.id.ButtonExposureSelect);
		buttonExposureSelect.setOnClickListener(this);
		shutterButton.setOnClickListener(this);
		sliderExposure.setOnSeekBarChangeListener(this);
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				buttonExposureSelect.setEnabled(arg1);
			}
			
		});
		
		//setup views
		buttonExposure.toggle();
		sliderExposure.setMax(Values.getExposureTimes().length-1);
		
		// apply fonts
		Utils.applyFonts(view.findViewById(R.id.mainLayout),Typeface.createFromAsset(getActivity().getAssets(),"fonts/eurostile.ttf"));

		return view;
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
			
//		case R.id.ButtonExposure:
//			
//			break;
		}
	}
	
	/**
	 *  loads the shared preferences settings
	 */
	@Override
	protected void loadSettings() {
		// read preferences
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		sliderExposure.setProgress(prefs.getInt("CableRemoteExposure", 0));
		buttonExposure.setChecked(prefs.getBoolean("CoableRemoteExposureExposureChecked", false));
	}
	
	
	/**
	 *  saves the shared preferences settings
	 */
	@Override
	protected void saveSettings() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		prefs.edit().putInt("CableRemoteExposure", sliderExposure.getProgress()).commit();
		prefs.edit().putBoolean("CoableRemoteExposureExposureChecked", buttonExposure.isChecked()).commit();
	}
	/*
	public void updateProgressBars(long millisUntilFinished, int exposureTime) {
		progressExposure.setProgress((int)(millisUntilFinished*100.0/exposureTime));
		labelExposureProgress.setText(millisUntilFinished/1000+" / "+exposureTime/1000+"s");
	}*/
	
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		switch( arg0.getId() ){
		case R.id.SliderExposure:
			labelExposure.setText(Values.getExposureTime(arg1)/1000.0+"s");
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveSettings();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadSettings();
	}

	@Override
	public void onTimerExposureUpdate(long exposureLeft, long exposureTime) {
		if (exposureLeft == 0) {
			stopTriggerCamera();

		}else{
			int actProgress = (int) (exposureLeft * 100 / exposureTime);
			progressExposure.setProgress(actProgress);
			labelExposureProgress.setText(exposureLeft/1000+" / "+exposureTime/1000+"s");
		}

	}
	

	@Override
	public void onTimerTimelapseUpdate(long timeLeft, int intervalsLeft) {
		Log.d(TAG, "timeLeft: "+timeLeft);
		Log.d(TAG, "intervalsLeft: "+intervalsLeft);
		
		
	}
	
	@Override
	protected void startTriggerCamera() {

		if (!buttonExposure.isChecked()) {
			cameraControler.triggerExposure(50);
		} else {
			controlLayout.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
			final int exposureTime = Values.getExposureTime(sliderExposure.getProgress());
			//cameraControler.triggerExposure(exposureTime);
			cameraControler.triggerTimeLapse(2000, 1000, 10);
		}
	}

	@Override
	protected void stopTriggerCamera() {
		controlLayout.setVisibility(View.VISIBLE);
		progressLayout.setVisibility(View.GONE);
		shutterButton.setChecked(false);
		cameraControler.triggerStop();
		
	}

}
