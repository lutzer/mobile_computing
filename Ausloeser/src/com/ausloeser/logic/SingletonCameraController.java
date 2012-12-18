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
					triggerStop();
				}
				signalGen = new SignalGenerationThread();
				new Thread(signalGen).start();
				isRunning = true;
			}
			
			public void triggerExposure(long exposureTime){
				if(isRunning){
					triggerStop();
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
					triggerStop();
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
			public void triggerStop(){
				
				if(signalGen!= null){
					signalGen.stopSound();
					isRunning = false;
				}
				
				cancelAllTimers();
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
					triggerStop();
				}
				//long totalTimelapseTime = intervalTim
			}
			
			public void stopTimelapse(){
				cancelAllTimers();
			}
	
	/**
	 * Takes the parameters to generate the Timelapse.
	 * @param timelapseLength
	 * @param intervalTime
	 * @param exposureTime
	 */
	private void generateTimelapse (long intervalTime, final long exposureTime, int amountPictures){
//		timelapseTimer= new CountDownTimer(intervalTime, 40){
//			@Override
//			public void onTick(long millisUntilFinished) {
//			}
//			
//			@Override
//			public void onFinish() {
//				Log.d(TAG, "Timelapse finished");
//				generateExposure(exposureTime);
//				sendTimerTimelapse(millisUntilFinished, intervalsLeft);
//				sendTimerTimelapse(0, 0);
//				
//			}
//			
//		}.start();
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

	/**
	 * Timer to trigger for a certain exposure time, other Timer to send events
	 * to calling class
	 * 
	 * @param exposureTime
	 */
	private void generateExposure(final long exposureTime){
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
		    	 sendTimerExposure(0, exposureTime);
		    	 triggerStop();
		    	 //TODO sendExposureCdTimer.cancel();
		     }
		  }.start();
		
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
	
	public void sendTimerTimelapse(long timeLeft, int intervalsLeft){
		for(OnDelayExposureTimerListener listener:listeners){
			listener.onTimerTimelapseUpdate(timeLeft, intervalsLeft);
		}
	}
}
