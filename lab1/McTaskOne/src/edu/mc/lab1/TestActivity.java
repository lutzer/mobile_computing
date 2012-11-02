package edu.mc.lab1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SimpleBookManager manager = new SimpleBookManager();
		
		for (int i=0;i<manager.count();i++)
			Log.e("TEST", manager.getBookString(i));
		
	}
	
}
