package com.ausloeser.screens;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.ausloeser.views.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.ToggleButton;

/**
 * Class holds the fragment for the delayed cable remote mode
 * @author Arnim Jepsen and Lutz Reiter
 */
public class DelayCableRemoteFragment extends SherlockFragment {

	ToggleButton buttonExposure;
	SeekBar sliderExposure;
	
	SeekBar sliderDelay;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_delay_cable_remote, container, false);
	    
		buttonExposure = (ToggleButton) view.findViewById(R.id.ButtonExposure);
		sliderExposure = (SeekBar) view.findViewById(R.id.SliderExposure);
		sliderDelay = (SeekBar) view.findViewById(R.id.SliderDelay);
		
		final Button buttonExposureSelect = (Button) view.findViewById(R.id.ButtonExposureSelect);
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				buttonExposureSelect.setEnabled(arg1);
			}
			
		});
		buttonExposure.toggle(); //call upper method once
		
		// apply fonts
		Utils.applyFonts(view.findViewById(R.id.mainLayout),Typeface.createFromAsset(getActivity().getAssets(),"fonts/eurostile.ttf"));


		return view;
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
		sliderDelay.setProgress(prefs.getInt("CoableRemoteExposureDelay", 0));
	}
	
	
	/**
	 *  saves the shared preferences settings
	 */
	private void saveSettings() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"com.ausloeser.app", getActivity().MODE_PRIVATE);
		prefs.edit().putInt("CableRemoteExposure", sliderExposure.getProgress()).commit();
		prefs.edit().putBoolean("CoableRemoteExposureExposureChecked", buttonExposure.isChecked()).commit();
		prefs.edit().putInt("CoableRemoteExposureDelay", sliderDelay.getProgress()).commit();

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
