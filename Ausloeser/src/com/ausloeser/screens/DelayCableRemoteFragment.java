package com.ausloeser.screens;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.ausloeser.dialogs.BaseDialogBuilder;
import com.ausloeser.dialogs.NumberInputDialogBuilder;
import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.logic.Values;
import com.ausloeser.views.Utils;

/**
 * Fragment holding the interface to do a simple trigger
 * 
 * @author Arnim Jepsen & Lutz Reiter
 *
 */

public class DelayCableRemoteFragment extends AbstractCableRemoteFragment {
	

	ToggleButton buttonExposure;
	SeekBar sliderExposure;
	ProgressBar progressExposure;
	TextView labelExposure,labelExposureProgress;
	int exposureTime;
	
	ToggleButton buttonDelay;
	SeekBar sliderDelay;
	ProgressBar progressDelay;
	TextView labelDelay,labelDelayProgress;
	int delayTime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_delay_cable_remote, container, false);
		
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
		
		sliderDelay = (SeekBar) view.findViewById(R.id.SliderDelay);
		labelDelay = (TextView) view.findViewById(R.id.LabelDelay);
		progressDelay = (ProgressBar) view.findViewById(R.id.ProgressDelay);
		labelDelayProgress = (TextView) view.findViewById(R.id.LabelDelayProgress);
		
		//setup listeners
		final Button buttonExposureSelect = (Button) view.findViewById(R.id.ButtonExposureSelect);
		buttonExposureSelect.setOnClickListener(this);
		sliderExposure.setOnSeekBarChangeListener(this);
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				buttonExposureSelect.setEnabled(arg1);
				setExposure(exposureTime);
			}
			
		});
		((Button) view.findViewById(R.id.ButtonDelaySelect)).setOnClickListener(this); 
		sliderDelay.setOnSeekBarChangeListener(this);
		shutterButton.setOnClickListener(this);
		
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
			
		case R.id.ButtonExposureSelect:
			// create a dialog for picking the exposure
			final NumberInputDialogBuilder builder = new NumberInputDialogBuilder(getActivity(),"Set Exposure Time (s)",
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
		
		case R.id.ButtonDelaySelect:
			// create a dialog for picking the exposure
			final NumberInputDialogBuilder builder2 = new NumberInputDialogBuilder(getActivity(),"Set Delay Time (s)",
					exposureTime/1000.0F,0.0F,99999.9F);
			
			builder2.SetDialogResultListener(new BaseDialogBuilder.OnDialogResultListener() {

				@Override
				public void OnDialogResultListener(boolean result) {
					if (result) {
						if (builder2.getValue() > 0)
						sliderDelay.setProgress(0);
						setDelay((int) (builder2.getValue()*1000.0F));
					}
				}

			});
			AlertDialog dialog2 = builder2.create();
			dialog2.show();
		break;
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
		exposureTime = prefs.getInt("CableRemoteExposureTime", Values.getExposureTime(0));
		
		sliderDelay.setProgress(prefs.getInt("CableRemoteDelay", 0));
		delayTime = prefs.getInt("CableRemoteDelayTime", 0);
		
		setExposure(exposureTime);
		setDelay(delayTime);
	}
	
	
	/**
	 *  saves the shared preferences settings
	 */
	@Override
	protected void saveSettings() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		prefs.edit().putInt("CableRemoteExposure", sliderExposure.getProgress()).commit();
		prefs.edit().putInt("CableRemoteExposureTime", exposureTime).commit();
		prefs.edit().putBoolean("CoableRemoteExposureExposureChecked", buttonExposure.isChecked()).commit();
		
		prefs.edit().putInt("CableRemoteDelay", sliderDelay.getProgress()).commit();
		prefs.edit().putInt("CableRemoteDelayTime", exposureTime).commit();
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		switch( arg0.getId() ){
			case R.id.SliderExposure:
				setExposure(Values.getExposureTime(arg1));
				break;
			case R.id.SliderDelay:
				setDelay(arg1*1000);
				break;
		}
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
	public void onTimerDelayUpdate(long delayLeft, long delayTime) {
		int actProgress = (int) (delayLeft * 100 / delayTime);
		progressDelay.setProgress(actProgress);
		labelDelayProgress.setText(delayLeft/1000+" / "+delayTime/1000+"s");
	}

	@Override
	protected void startTriggerCamera() {

		if (!buttonExposure.isChecked()) {
			cameraControler.triggerExposureDelay(100, delayTime);
			controlLayout.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
		} else {
			controlLayout.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
			cameraControler.triggerExposureDelay(exposureTime, delayTime);
		}
	}

	@Override
	protected void stopTriggerCamera() {
		controlLayout.setVisibility(View.VISIBLE);
		progressLayout.setVisibility(View.GONE);
		shutterButton.setChecked(false);
		cameraControler.triggerStop();
		
	}
	
	private void setExposure(int value) {
		exposureTime = value;
		if (buttonExposure.isChecked())
			labelExposure.setText((value/1000.0F)+"s");
		else
			labelExposure.setText("Cam");
			
	}
	
	private void setDelay(int value) {
		delayTime = value;
		labelDelay.setText((value/1000.0F)+"s");
	}
}
