package com.ausloeser.screens;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.ausloeser.logic.SingletonCameraController;
import com.ausloeser.views.Utils;

/**
 * Fragment holding the interface to do a simple trigger
 * 
 * @author Arnim Jepsen & Lutz Reiter
 *
 */
public class SimpleCableRemoteFragment extends SherlockFragment implements OnClickListener{

	ToggleButton buttonExposure;
	SeekBar sliderExposure;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_simple_cable_remote, container, false);
	    
		buttonExposure = (ToggleButton) view.findViewById(R.id.ButtonExposure);
		sliderExposure = (SeekBar) view.findViewById(R.id.SliderExposure);
		final Button buttonExposureSelect = (Button) view.findViewById(R.id.ButtonExposureSelect);
		final Button buttonTrigger = (Button) view.findViewById(R.id.triggerButton);

		buttonTrigger.setOnClickListener(this);
		
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				buttonExposureSelect.setEnabled(arg1);
			}
			
		});
		buttonExposure.toggle();
		
		// apply fonts
		Utils.applyFonts(view.findViewById(R.id.mainLayout),Typeface.createFromAsset(getActivity().getAssets(),"fonts/eurostile.ttf"));


		return view;
	}
	
	@Override
	public void onClick(View v) {
		
		switch( v.getId() ){
		
		case R.id.triggerButton:
			SingletonCameraController.INSTANCE.triggerSimple();
			break;
		
		}
		
	}
	
	/**
	 *  loads the shared preferences settings
	 */
	private void loadSettings() {
		// read preferences
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		sliderExposure.setProgress(prefs.getInt("CableRemoteExposure", 0));
		buttonExposure.setChecked(prefs.getBoolean("CoableRemoteExposureExposureChecked", false));
	}
	
	
	/**
	 *  saves the shared preferences settings
	 */
	private void saveSettings() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		prefs.edit().putInt("CableRemoteExposure", sliderExposure.getProgress()).commit();
		prefs.edit().putBoolean("CoableRemoteExposureExposureChecked", buttonExposure.isChecked()).commit();

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
}
