package com.example.ausloeserapp_v1;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * Class providing all the functionality to trigger and stop the sound
 * 
 * @author Lutz Reiter & Arnim Jepsen
 * 
 */
public class SignalGenerationThread implements Runnable {

	private static final String TAG = "SendSignalThread";

	public static final Integer MIN_BUFFER_SIZE = android.media.AudioTrack
			.getMinBufferSize(48000, AudioFormat.CHANNEL_OUT_MONO,
					AudioFormat.ENCODING_PCM_16BIT);

	public static final AudioTrack MY_TRACK = new AudioTrack(
			AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_MONO,
			AudioFormat.ENCODING_PCM_16BIT, MIN_BUFFER_SIZE,
			AudioTrack.MODE_STREAM);

	private short samples[] = new short[1024];
	private boolean isFinished;


	public void run(){
			genSound();
	};

	
	/**
	 * generates the sound with a frequency max supported by the chip (?)
	 */
	public void genSound() {
		MY_TRACK.play();
		while (!isFinished) {
			for (int i = 0; i < samples.length; i++) {
				samples[i] = Short.MAX_VALUE;
			}
			MY_TRACK.write(samples, 0, samples.length);
			Log.d(TAG, "isMakingASound");
		}

	}

	/**
	 * stops the sound-generation and flushes
	 */
	public void stopSound() {

		isFinished = true;

		MY_TRACK.pause();
		MY_TRACK.flush();

		Log.d(TAG, "soundGen is not running anymore");
	}
}
