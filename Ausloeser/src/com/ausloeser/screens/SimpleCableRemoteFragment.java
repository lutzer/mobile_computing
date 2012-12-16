package com.ausloeser.screens;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
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
public class SimpleCableRemoteFragment extends SherlockFragment implements OnClickListener, OnSeekBarChangeListener{
	
	//range that is selectable by the slider
	//TODO not used: public static final int MAX_EXPOSURE = 30;
	//TODO not used: public static final int MIN_EXPOSURE = 1;
	
	private TextView textExposureTime;
	private SeekBar sliderExposure;
	private long exposureTime;
	private static final String TAG = "SimpleCableRemoteFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_simple_cable_remote, container, false);
		
		final ToggleButton buttonExposure = (ToggleButton) view.findViewById(R.id.ButtonExposure);
		
		sliderExposure = (SeekBar) view.findViewById(R.id.SliderExposure);
		sliderExposure.setEnabled(false);
		
		final Button buttonExposureSelect = (Button) view.findViewById(R.id.ButtonExposureSelect);
		final Button buttonTrigger = (Button) view.findViewById(R.id.triggerButton);
		textExposureTime = (TextView) view.findViewById(R.id.textExposureTime);
		
		
		buttonExposureSelect.setOnClickListener(this);
		buttonTrigger.setOnClickListener(this);
		sliderExposure.setOnSeekBarChangeListener(this);
		buttonExposure.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sliderExposure.setEnabled(arg1);
				if(!arg1){
					sliderExposure.setProgress(0);
				}
				buttonExposureSelect.setEnabled(arg1);
			}
			
		});
		
		// apply fonts
		Utils.applyFonts(view.findViewById(R.id.mainLayout),Typeface.createFromAsset(getActivity().getAssets(),"fonts/eurostile.ttf"));


		return view;
	}
	
	@Override
	public void onClick(View v) {
		
		switch( v.getId() ){
		
		case R.id.triggerButton:
			if(exposureTime == 0){
				SingletonCameraController.INSTANCE.triggerSimple();
			}else{
				Log.d(TAG, Integer.toString(sliderExposure.getProgress()*1000));
				SingletonCameraController.INSTANCE.triggerExposure(exposureTime);
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		exposureTime = (long) progress*1000;
		textExposureTime.setText(progress+"s");
		
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
