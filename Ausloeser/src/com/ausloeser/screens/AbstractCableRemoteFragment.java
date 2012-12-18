package com.ausloeser.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.ausloeser.logic.OnDelayExposureTimerListener;
import com.ausloeser.logic.SingletonCameraController;

/**
 * Fragment holding the interface to do a simple trigger
 * 
 * @author Arnim Jepsen & Lutz Reiter
 *
 */

public abstract class AbstractCableRemoteFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener, OnDelayExposureTimerListener{
	
	View controlLayout,progressLayout;
	
	ToggleButton shutterButton;
	
	SingletonCameraController cameraControler;
	
	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	@Override
	public abstract void onClick(View v);
	
	/**
	 *  loads the shared preferences settings
	 */
	protected abstract void loadSettings();
	
	
	/**
	 *  saves the shared preferences settings
	 */
	protected abstract void saveSettings();
	
	protected abstract void startTriggerCamera();
	
	protected abstract void stopTriggerCamera();
	
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		
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
	public void onPause() {
		super.onPause();
		saveSettings();
		//cameraControler.triggerStop();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadSettings();
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
}
