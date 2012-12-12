package com.ausloeser.screens;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {
	
	private class Mode {
		
		int id;
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
	                /*TextView author = (TextView) v.findViewById(R.id.author);
	                TextView title = (TextView) v.findViewById(R.id.title);
	                if (author != null)
	                	author.setText(book.getAuthor());
	                if(title != null)
	                	title.setText(book.getTitle());*/
	                
	            }
	            return v;
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ActionBar actionBar = getSupportActionBar();
	}
	
	private void populateList() {
		//ListView listView =  ((ListView) view.findViewById(R.id.listView));
    	//listView.setAdapter(adapter);
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