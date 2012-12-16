package com.ausloeser.screens;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.ausloeser.views.SliderSwitch;
import com.ausloeser.views.Utils;

public class CableRemoteActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cable_remote);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		// setup slider switch to select the modes
		String[] labels = { "Simple", "Delay", "Bulb", "Hold" }; 
		SliderSwitch sliderSwitch = (SliderSwitch) findViewById(R.id.sliderSwitch);
		sliderSwitch.setLabelTexts(labels);
		sliderSwitch.setOnSliderSwitchChangeListener(new SliderSwitch.OnSliderSwitchChangeListener() {

			@Override
			public void onProgressChanged(int snappedPosition) {
				// When the given tab is selected, show the tab contents in the container
				SherlockFragment fragment = null;
				switch (snappedPosition)
				{
				case 0:
					fragment = new SimpleCableRemoteFragment();
					break;
				case 1:
					fragment = new DelayCableRemoteFragment();
					break;
				default:
					//fragment = new SherlockFragment();		
					return;
				}
				if (fragment != null)
					getSupportFragmentManager().beginTransaction()
						.replace(R.id.content, fragment)
						.commit();
			}

			@Override
			public void onStartTrackingTouch(int snappedPosition) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(int snappedPosition) {
				// TODO Auto-generated method stub
				
			}
			
		});
		

		sliderSwitch.setSliderPosition(0);
		
		// apply fonts
		Utils.applyFonts(findViewById(R.id.titleStripe),Typeface.createFromAsset(getAssets(),"fonts/eurostile.ttf"));
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_cable_remote, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
