package com.example.ausloeserapp_v1;

public interface OnDelayExposureTimerListener {
	void onTimerExposureUpdate(long exposureLeft, long exposureTime);
	void onTimerDelayUpdate(long delayLeft, long delayTime);
}
