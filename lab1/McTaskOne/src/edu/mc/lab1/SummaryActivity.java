package edu.mc.lab1;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;

public class SummaryActivity extends SherlockActivity {

	SimpleBookManager bookManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        
        //enable back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        bookManager = SimpleBookManager.getInstance();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_summary, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {

         switch (item.getItemId())
         {
         case android.R.id.home:
        	 finish();
             //Toast.makeText(MainActivity.this, "Open Summary activity", Toast.LENGTH_SHORT).show();
             return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
