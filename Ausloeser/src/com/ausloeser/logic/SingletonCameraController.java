package com.ausloeser.logic;

import java.util.ArrayList;

import android.os.CountDownTimer;
import android.util.Log;


/**
 * singletonCameraController, only one instance is allowed
 * 
 * @author Lutz Reiter & Arnim Jepsen
 *
 */
public enum SingletonCameraController{
	INSTANCE;

	private SignalGenerationThread signalGen;
	private static final String TAG = "SingletonCameraController";
	private CountDownTimer sendDelayCdTimer, sendExposureCdTimer, timelapseTimer;

	private boolean isRunning;

	//takes all registered listeners
	ArrayList<OnDelayExposureTimerListener> listeners = new ArrayList<OnDelayExposureTimerListener>();

	/**
	 * Camera is just triggered once ()
	 * 
	 */
	public void triggerSimple(){

		triggerExposure(100);
	}

	/**
	 * Camera is triggered unlimited
	 */
	public void triggerUnlimited(){
		if(isRunning){
			triggerStop(true);
		}
		signalGen = new SignalGenerationThread();
		new Thread(signalGen).start();
		isRunning = true;
	}

	public void triggerExposure(long exposureTime){
		if(isRunning){
			triggerStop(true);
		}
		signalGen = new SignalGenerationThread();
		generateExposure(exposureTime);
		isRunning = true;
	}

	/**
	 * Trigger with exposure and delay in milliseconds
	 * 
	 * @param exposureTime
	 * @param delayTime
	 */
	public void triggerExposureDelay(long exposureTime, long delayTime){
		if(isRunning){
			triggerStop(true);
		}

		if (delayTime <= 0) {
			delayTime = 1;
		}

		signalGen = new SignalGenerationThread();
		generateDelay(exposureTime, delayTime);
		isRunning = true;
	}

	/**
	 * stops the triggering of the Sound
	 */
	public void triggerStop(boolean cancelTimers){

		if(signalGen!= null){
			signalGen.stopSound();
			isRunning = false;
		}

		if (cancelTimers)
			cancelAllTimers();
	}
	
	public void triggerStop(){
		triggerStop(true);
	}


	/**
	 * Prevents unused Threads hanging around and stops/cancels Countdown timers
	 */
	private void cancelAllTimers(){
		if(sendDelayCdTimer!=null){
			sendDelayCdTimer.cancel();
		}
		if(sendExposureCdTimer!=null){
			sendExposureCdTimer.cancel();
		}
		if(timelapseTimer!=null){
			timelapseTimer.cancel();
		}
	}



	/**
	 * The calling class asks to generate a timelapse
	 * @param intervalTime
	 * @param exposureTime
	 * @param amountPictures
	 */
	public void triggerTimelapse(long intervalTime, long exposureTime, int amountPictures){
		if(isRunning){
			triggerStop(true);
		}
		
		generateTimelapse(intervalTime, exposureTime, amountPictures);
	}

	/**
	 * Takes the parameters to generate the Timelapse.
	 * @param timelapseLength
	 * @param intervalTime
	 * @param exposureTime
	 */
	private void generateTimelapse (final long intervalTime, final long exposureTime, final int amountPictures){

		final long totalTimelapseTime = intervalTime * (amountPictures+1);
		
		timelapseTimer= new CountDownTimer(totalTimelapseTime, intervalTime){
			//counts the amountPictures up
			int i = amountPictures-1;
			@Override
			public void onTick(long millisUntilFinished) {
				Log.d(TAG, "Timelapse triggered millisUntilFinished: "+millisUntilFinished+" i: "+i);
				generateExposure(exposureTime,true);
				sendTimerTimelapse(i--, amountPictures);
				generateIntervall(intervalTime);
			}

			@Override
			public void onFinish() {
				//generateExposure(exposureTime,false);
				triggerStop();
				Log.d(TAG, "Timelapse finished");
			}

		}.start();
	}



	/**
	 * Timer to trigger after a certain Delay, other Timer to send events to calling class
	 * 
	 * @param exposureTime
	 * @param delayTime
	 */  
	private void generateDelay(final long exposureTime, final long delayTime){
		//sends the exposureTime once to update the prob
		sendTimerExposure(exposureTime, exposureTime);

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
	
	private void generateIntervall(final long intervallTime){
		//sends the exposureTime once to update the prob

		sendDelayCdTimer = new CountDownTimer(intervallTime, 40) {

			public void onTick(long millisUntilFinished) {
				sendTimerDelay(millisUntilFinished, intervallTime);
			}

			public void onFinish() {
				sendTimerDelay(0, intervallTime); //reset intervall
			}
		}.start();
	}

	/**
	 * Timer to trigger for a certain exposure time, other Timer to send events
	 * to calling class
	 * 
	 * @param exposureTime
	 */
	private void generateExposure(final long exposureTime,final boolean isTimelapse){
		if(sendDelayCdTimer != null){
			sendDelayCdTimer.cancel();
		}
		//Camera is triggered
		triggerUnlimited();

		//makes it send every 40 milliseconds (ca 35 frames/sec on the statusbar)
		sendExposureCdTimer = new CountDownTimer(exposureTime, 40) {

			public void onTick(long millisUntilFinished) {
				sendTimerExposure(millisUntilFinished, exposureTime);
			}

			public void onFinish() {
				if (isTimelapse) {
					sendTimerExposure(exposureTime, exposureTime);
					triggerStop(false);
				} else {
					sendTimerExposure(0, exposureTime);
					triggerStop(true);
				}
			}
		}.start();

	}
	
	private void generateExposure(final long exposureTime) {
		generateExposure(exposureTime,false);
	}


	//add subscribed listeners
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

	public void sendTimerTimelapse(int shotsLeft, int totalShots){
		for(OnDelayExposureTimerListener listener:listeners){
			listener.onTimerTimelapseUpdate(shotsLeft, totalShots);
		}
	}
}
