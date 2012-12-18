package com.ausloeser.screens;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.views.Utils;

/**
 * Fragment holding the interface to do a simple trigger
 * 
 * @author Arnim Jepsen & Lutz Reiter
 *
 */

public class BulbCableRemoteFragment extends AbstractCableRemoteFragment {
	

	TextView labelExposureProgress;
	int exposureTime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bulb_cable_remote, container, false);
		
		//init camera controller
		cameraControler = SingletonCameraController.INSTANCE;
		cameraControler.setOnTimerUpdateListener(this);
		
		//get main layouts
		controlLayout = view.findViewById(R.id.ControlsLayout); // holds sliders and buttons
		progressLayout = view.findViewById(R.id.ProgressLayout); // holds progress bars
		shutterButton = (ToggleButton) view.findViewById(R.id.ButtonShutter);

		labelExposureProgress = (TextView) view.findViewById(R.id.LabelExposureProgress);
		
		//setup listeners
		shutterButton.setOnClickListener(this);
		
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
		}	
	}
	
	/**
	 *  loads the shared preferences settings
	 */
	@Override
	protected void loadSettings() {
		
	}
	
	
	/**
	 *  saves the shared preferences settings
	 */
	@Override
	protected void saveSettings() {	
	}


	@Override
	public void onTimerExposureUpdate(long exposureLeft, long exposureTime) {
		labelExposureProgress.setText((exposureTime - exposureLeft)/1000+"s");
	}

	@Override
	protected void startTriggerCamera() {
		controlLayout.setVisibility(View.GONE);
		progressLayout.setVisibility(View.VISIBLE);
		cameraControler.triggerExposure(Long.MAX_VALUE);
	}

	@Override
	protected void stopTriggerCamera() {
		controlLayout.setVisibility(View.VISIBLE);
		progressLayout.setVisibility(View.GONE);
		shutterButton.setChecked(false);
		cameraControler.triggerStop();
		
	}
	
}