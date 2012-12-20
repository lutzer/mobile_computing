package com.ausloeser.screens;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ausloeser.views.Utils;

/**
 * Starting point of the application, handling the main list, etc
 *
 * @author Lutze Reiter und Arnim Jepsne
 *
 */
public class MainActivity extends SherlockActivity {
	
	private class Mode {
		
		String title,description,imgPath;
		
		public Mode(String title, String descpription, String imgPath) {
			this.title = title;
			this.description = descpription;
			this.imgPath = imgPath;
		}
		
	}
	
	private class ListAdapter extends ArrayAdapter<Mode> {

	    private ArrayList<Mode> items;

	    public ListAdapter(Context context, ArrayList<Mode> items) {
	            super(context, R.layout.list_adapter_mode, items);
	            this.items = items;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	            View v = convertView;
	            if (v == null) {
	                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                v = vi.inflate(R.layout.list_adapter_mode, null);
	            }
	            Mode mode = items.get(position);
	            if (mode != null) {
	            	((TextView) v.findViewById(R.id.title)).setText(mode.title);
	            	((TextView) v.findViewById(R.id.description)).setText(mode.description);
	            	
	            	try {
	            		InputStream ims = getAssets().open(mode.imgPath);
	            		Drawable img = Drawable.createFromStream(ims, null);
	            		((ImageView) v.findViewById(R.id.image)).setImageDrawable(img);
	            	} catch(Exception e) {
	            		;
	            	}
	            }
	            
	            // apply font
	            Utils.applyFonts(v,Typeface.createFromAsset(getAssets(),"fonts/eurostile.ttf"));
	            return v;
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ActionBar actionBar = getSupportActionBar();
		
		populateList();
		
		// apply fonts
		Utils.applyFonts(findViewById(R.id.titleStripe),Typeface.createFromAsset(getAssets(),"fonts/eurostile.ttf"));
		
	}
	
	private void populateList() {
		
		ArrayList<Mode> modes = new ArrayList<Mode>();
		modes.add(new Mode(
				"Cable Remote",
				"Lets you trigger your camera in simple, bulb or delayed mode.",
				"img/modes/cable_select.png"));
		modes.add(new Mode("Timelapse",
				"Set your remote up for recording timelapses",
				"img/modes/timelapse.png"));
		modes.add(new Mode("Just Fire",
				"Exposes as fast as your camera supports.",
				"img/modes/cannon.png"));
		modes.add(new Mode("Soundtrap", "Dont' make a noise!",
				"img/modes/clapping.png"));
		modes.add(new Mode("Cameratrap",
				"Triggers whenever someone walks into the frame",
				"img/modes/siren.png"));
		modes.add(new Mode("SMS-Triggering",
				"The camera is triggered when a SMS arrives",
				"img/modes/sms.png"));
		
		modes.add(new Mode(
				"Cable Remote",
				"Lets you trigger your camera in simple, bulb or delayed mode.",
				"img/modes/cable_select.png"));
		modes.add(new Mode("Timelapse",
				"Set your remote up for recording timelapses",
				"img/modes/timelapse.png"));
		modes.add(new Mode("Just Fire",
				"Exposes as fast as your camera supports.",
				"img/modes/cannon.png"));
		modes.add(new Mode("Soundtrap", "Dont' make a noise!",
				"img/modes/clapping.png"));
		modes.add(new Mode("Cameratrap",
				"Triggers whenever someone walks into the frame",
				"img/modes/siren.png"));
		modes.add(new Mode("SMS-Triggering",
				"The camera is triggered when a SMS arrives",
				"img/modes/sms.png"));



		ListView listView =  ((ListView) findViewById(R.id.listView));
		listView.setAdapter(new ListAdapter(this,modes));
		
		// link list with opening the new activity
        listView.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		        if (position==0) { 
		        	Intent intent = new Intent(getApplicationContext(), CableRemoteActivity.class);
		        	startActivity(intent);
		        }else if(position == 1){
		        	Intent intent = new Intent(getApplicationContext(), TimelapseActivity.class);
		        	startActivity(intent);
		        }else if(position == 2){
		        	Intent intent = new Intent(getApplicationContext(), JustFireActivity.class);
		        	startActivity(intent);
		        }else if(position == 3){
	        	Intent intent = new Intent(getApplicationContext(), SoundDetectionActivity.class);
	        	startActivity(intent);
	        }
			}
        });
	}

	public boolean onOptionsItemSelected(MenuItem item)
    {
         /*switch (item.getItemId())
         {
         case R.id.Remove:
        	 
        	 /;
        	 return true;
         case R.id.Edit:
        	 ;
        	 return true;
         case android.R.id.home:
        	 ;
             return true;
        default:
            return super.onOptionsItemSelected(item);
        }*/
		return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);;
        return super.onCreateOptionsMenu(menu);
    }
}