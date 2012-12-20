package tools;

import java.util.ArrayList;

import android.util.Log;


public enum SmsBroadcaster {
	INSTANCE;
	
	
	
	public interface OnSmsReceivedListener {
		void onSmsReceived(String sender, String msg);
	}
	
	ArrayList<OnSmsReceivedListener> listeners = new ArrayList<OnSmsReceivedListener>();

	
	public void receivedSms(String sender, String msg) {
		Log.e("INFO","received sms in broadcaster");
		for(OnSmsReceivedListener listener:listeners){
			listener.onSmsReceived(sender, msg);
		}
	}
	
	public void setOnSmsReceivedListener(OnSmsReceivedListener listener){
		this.listeners.add(listener);
	}
	
	public void removeOnSmsReceivedListener(OnSmsReceivedListener listener){
		this.listeners.remove(listener);
	}
	
}
