package com.example.ausloeserapp_v1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteControlActivity extends Activity implements OnClickListener, OnTouchListener, OnSeekBarChangeListener, OnCheckedChangeListener, OnDelayExposureTimerListener {
	
	private static final String TAG = "RemoteControlActivity";
	
	private Button holdButton, delayButton, triggerButton, bulbButton;
	private SeekBar exposureSlider, delaySlider;
	private CheckBox exposureCheckBox;
	private ProgressBar progressBarDelay, progressBarExposure;
	private TextView textViewTimeOpen;
	
	private static final int EXPOSURE_MAX = 10000; 
	private static final int DELAY_MAX = 10000; 
	
	long exposureTime;
	long delayTime;
	boolean isBulbActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);
        
        //Assign the buttons to their listener
        triggerButton = (Button) findViewById(R.id.buttonTrigger);
        triggerButton.setOnClickListener(this);
        
        delayButton = (Button) findViewById(R.id.buttonDelay);
        delayButton.setOnClickListener(this);
        
        bulbButton = (Button) findViewById(R.id.buttonBulb);
        bulbButton.setOnClickListener(this);
        
        holdButton = (Button) findViewById(R.id.buttonHold);
        holdButton.setOnTouchListener(this);
        
        exposureSlider = (SeekBar) findViewById(R.id.seekBarExposureTime);
        exposureSlider.setMax(EXPOSURE_MAX);
        exposureSlider.setEnabled(false);
        exposureSlider.setOnSeekBarChangeListener(this);
        
        delaySlider = (SeekBar) findViewById(R.id.seekBarDelayTime);
        delaySlider.setMax(DELAY_MAX);
        delaySlider.setOnSeekBarChangeListener(this);
        
        exposureCheckBox = (CheckBox) findViewById(R.id.checkBoxExposure);
        exposureCheckBox.setOnCheckedChangeListener(this);
        
        textViewTimeOpen = (TextView) findViewById(R.id.textViewTimeOpen);
        
        progressBarDelay = (ProgressBar) findViewById(R.id.progressBarDelay);
        progressBarDelay.setProgress(100);
        
        progressBarExposure = (ProgressBar) findViewById(R.id.progressBarExposure);
        progressBarExposure.setProgress(100);
        
        SingletonCameraController.INSTANCE.setOnTimerUpdateListener(this);
        
    }
    
    
    @Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(!isChecked){
			exposureSlider.setEnabled(false);
			exposureSlider.setProgress(0);
			exposureTime = 0;
		}else{
			exposureSlider.setEnabled(true);
		}
	}
    
	//Listener for the SeekBar, exposureSlider, delaySlider
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		switch(seekBar.getId()){
		case R.id.seekBarExposureTime:
			exposureTime =  progress;
			TextView t = new TextView(this);
			t = (TextView) findViewById(R.id.textViewExposure);
			t.setText(String.valueOf(progress));
			//Log.d(TAG, String.valueOf(progress));
			
			break;
		case R.id.seekBarDelayTime:
			delayTime = progress;
			TextView tD = new TextView(this);
			tD = (TextView) findViewById(R.id.textViewDelay);
			tD.setText(String.valueOf(progress));
			//Log.d(TAG, String.valueOf(progress));
			
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
    
    //Listener for the Hold Button
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch ( event.getAction() ) {
        case MotionEvent.ACTION_DOWN: 
        	holdBulbSignal();
        	break;
        case MotionEvent.ACTION_UP: 
        	stopSignal();
        	break;
        }
        return true;
    }
	
	//Listener for remaining buttons
    @Override
	public void onClick(View v) {
    	 switch (v.getId()) {
         case R.id.buttonTrigger:
        	 //TODO maybe change second paramenter to 1, in case the camera is not triggered
        	 simpleDelaySignal(exposureTime, 1);
             Toast.makeText(getApplicationContext(), "Plain simple Trigger is clicked", Toast.LENGTH_SHORT).show(); 
             break;
         case R.id.buttonDelay:
        	 simpleDelaySignal(exposureTime, delayTime);
             Toast.makeText(getApplicationContext(),"DELAY mode is clicked, click as long as the camera should trigger.", Toast.LENGTH_SHORT).show();
             break;
         case R.id.buttonHold:
        	 holdBulbSignal();
             Toast.makeText(getApplicationContext(),"HOLD mode is clicked, click as long as the camera should trigger.", Toast.LENGTH_SHORT).show();
             break;
         case R.id.buttonBulb:
        	 if(!isBulbActive){
        		 holdBulbSignal();
        		 isBulbActive = true;
        	 }else{
        		 stopSignal();
        		 isBulbActive = false;
        	 }
        	 
             Toast.makeText(getApplicationContext(), "BULB Mode is Clicked, click again to stop", Toast.LENGTH_SHORT).show();
             break;
    	 }
	}
    
    /**
     * Taking the exposureTime and delayTime in milliseconds a new thread for sound generation is started
     * 
     * @param exposureTime
     * @param delayTime
     */
    public void simpleDelaySignal(long exposureTime, long delayTime){

		SingletonCameraController.INSTANCE.triggerExposureDelay(exposureTime, delayTime);
		Log.d(TAG, "Simple oder Delay wurde getriggert!");
		
    	    	}
    /**
     * A new thread for undefined sound generation is started
     */
    public void holdBulbSignal(){
    	SingletonCameraController.INSTANCE.triggerUnlimited();
    	Log.d(TAG, "Hold oder Bulb wurde getriggert!");
	    	}
    
	/**
	 * The sound generation is stopped by a call to abstract classes SendSignalThreadAbstract stop method
	 */
	public void stopSignal(){
		SingletonCameraController.INSTANCE.triggerStop();
		Log.d(TAG, "Hold oder Bulb wurde beendet!");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_remote_control, menu);
        return true;
    }


	@Override
	public void onTimerExposureUpdate(long exposureLeft, long exposureTime) {
		if (exposureLeft == 0) {
			progressBarExposure.setProgress(progressBarExposure.getMax());

		}else{
		int actProgress = (int) (exposureLeft * 100 / exposureTime);
		progressBarExposure.setProgress(actProgress);
		}

	}


	/* 
	 * Handles what happens everytime the CountDown during the Delay passing is triggered
	 * @see com.example.ausloeserapp_v1.OnDelayExposureTimerListener#onTimerDelayUpdate(long, long)
	 */
	@Override
	public void onTimerDelayUpdate(long delayLeft, long delayTime) {
		if (delayLeft == 0) {
			progressBarDelay.setProgress(progressBarDelay.getMax());

		}else{
		int actProgress = (int) (delayLeft * 100 / delayTime);
		progressBarDelay.setProgress(actProgress);
		}
	}
}
//implement timelapse
//repait countdown exposure
// sound keeps on triggering when the movie is rotated while triggering