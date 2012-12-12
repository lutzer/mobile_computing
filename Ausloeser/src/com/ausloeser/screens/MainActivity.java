package com.ausloeser.screens;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ausloeser.views.Utils;

public class MainActivity extends SherlockActivity {
	
	private class Mode {
		
		int id;
		String title,description,imgPath;
		
		public Mode(int id, String title, String descpription, String imgPath) {
			this.id = id;
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
	            }
	            return v;
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ActionBar actionBar = getSupportActionBar();
		
		Utils.applyFonts(findViewById(R.id.titleStripe),Typeface.createFromAsset(getAssets(),"fonts/eurostile.ttf"));
		
	}
	
	private void populateList() {
		ListView listView =  ((ListView) findViewById(R.id.listView));
		
		ArrayList<Mode> modes = new ArrayList<Mode>();
		
		modes.add(new Mode(0,"Cable Release","Lets you remote-trigger your camera in simple, bulb or delayed mode.","test"))
		
		
    	//listView.setAdapter(new ListAdapter(,modes));
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