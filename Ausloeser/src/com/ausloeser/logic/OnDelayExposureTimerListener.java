package com.ausloeser.logic;

public interface OnDelayExposureTimerListener {
	void onTimerExposureUpdate(long exposureLeft, long exposureTime);
	void onTimerDelayUpdate(long delayLeft, long delayTime);
	void onTimerTimelapseUpdate(int shotsLeft, int totalShots);
}
