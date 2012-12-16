package com.ausloeser.logic;

public class Values {

	/**
	 * @return exposure times in miliseconds
	 */
	public static int[] getExposureTimes() {
		int times[] = { 1000/10, 1000/8, 1000/5, 1000/3, 1000/2 , 1000,
						1000*2, 1000*3, 1000*5, 1000*10, 1000*15, 1000*20,
						1000*25, 1000*30, 1000*40, 1000*50, 1000*60, 1000*90, 
						1000*120, 1000*150, 1000* 180, 1000*240, 1000* 300, 1000 * 600};
		return times;
	}
	
	public static int getExposureTime(int i) {
		return getExposureTimes()[i];
	}
}
