package com.ausloeser.logic;

import java.util.ArrayList;

import android.os.CountDownTimer;


/**
 * singletonCameraController, only one instance is allowed
 * 
 * @author Lutz Reiter & Arnim Jepsen
 *
 */
public enum SingletonCameraController{
	INSTANCE;
	
	private SignalGenerationThread signalGen;

	private CountDownTimer sendDelayCdTimer, sendExposureCdTimer;
	
	//takes all registered listeners
	ArrayList<OnDelayExposureTimerListener> listeners = new ArrayList<OnDelayExposureTimerListener>();

	/**
	 * Camera is just triggered once ()
	 */
			public void triggerSimple(){
				signalGen = new SignalGenerationThread();
				new Thread(signalGen).start();
				triggerStop();
			}
			
			/**
			 * Camera is triggered unlimited
			 */
			public void triggerUnlimited(){
				signalGen = new SignalGenerationThread();
				new Thread(signalGen).start();
			}
			
			public void triggerExposure(long exposureTime){
				signalGen = new SignalGenerationThread();
				generateExposure(exposureTime);
			}
			
			public void triggerExposureDelay(long exposureTime, long delayTime){
				signalGen = new SignalGenerationThread();
				generateDelay(exposureTime, delayTime);
			}
			
			public void triggerStop(){
				
				if(signalGen!= null){
					signalGen.stopSound();
				}
			}
			
	public void triggerTimeLapse(long exposureTime, long delayTime){
		signalGen = new SignalGenerationThread();
		generateDelay(exposureTime, delayTime);
	}
	
	
	
	/**
	 * Timer to trigger after a certain Delay, other Timer to send events to calling class
	 * 
	 * @param exposureTime
	 * @param delayTime
	 */  void generateDelay(final long exposureTime, final long delayTime){
		//makes it send every 40 milliseconds (ca 35 frames/sec on the statusbar)
		 
		sendDelayCdTimer = new CountDownTimer(delayTime, 40) {

		     public void onTick(long millisUntilFinished) {
		         sendTimerDelay(millisUntilFinished, delayTime);
		     }

		     public void onFinish() {
		    	 sendTimerDelay(0, delayTime);
				generateExposure(exposureTime);
			}
		}.start();
	}

	/**
	 * Timer to trigger for a certain exposure time, other Timer to send events
	 * to calling class
	 * 
	 * @param exposureTime
	 */
	private void generateExposure(final long exposureTime){
		
		sendDelayCdTimer.cancel();
		//Camera is triggered
		triggerUnlimited();
		
		//makes it send every 40 milliseconds (ca 35 frames/sec on the statusbar)
		sendExposureCdTimer = new CountDownTimer(exposureTime, 40) {

		     public void onTick(long millisUntilFinished) {
		         sendTimerExposure(millisUntilFinished, exposureTime);
		     }

		     public void onFinish() {
		    	 sendTimerExposure(0, exposureTime);
		    	 triggerStop();
		    	 //TODO sendExposureCdTimer.cancel();
		     }
		  }.start();
		
	}
	
	
	
	public void setOnTimerUpdateListener(OnDelayExposureTimerListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Sends the remaining exposure time to registered listeners
	 * @param exposureLeft
	 * @param exposureTime
	 */
	public void sendTimerExposure(long exposureLeft, long exposureTime){
		for(OnDelayExposureTimerListener listener:listeners){
			listener.onTimerExposureUpdate(exposureLeft, exposureTime);
		}
	}
	
	/**
	 * Send the remaining delay time to registered listeners
	 * @param delayLeft
	 * @param delayTime
	 */
	public void sendTimerDelay(long delayLeft, long delayTime){
		for(OnDelayExposureTimerListener listener:listeners){
			listener.onTimerDelayUpdate(delayLeft, delayTime);
		}
	}
}
