package com.ausloeser.logic;

/**
 * Interface where all Fragments and Activities have to subscribe to to receive feedback from the SingletonCameraController
 * 
 * @author Lutz Reiter & Arnim Jepsen
 *
 */
public interface OnDelayExposureTimerListener {
	void onTimerExposureUpdate(long exposureLeft, long exposureTime);
	void onTimerDelayUpdate(long delayLeft, long delayTime);
	void onTimerTimelapseUpdate(int shotsLeft, int totalShots);
}
