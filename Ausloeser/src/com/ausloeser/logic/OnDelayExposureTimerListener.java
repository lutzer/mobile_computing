package com.ausloeser.logic;

public interface OnDelayExposureTimerListener {
	void onTimerExposureUpdate(long exposureLeft, long exposureTime);
	void onTimerDelayUpdate(long delayLeft, long delayTime);
	void onTimerTimelapseUpdate(long totaltimeLeft, long millisUntilFinished, int shotsDone);
}
