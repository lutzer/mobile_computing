package com.ausloeser.screens;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {

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