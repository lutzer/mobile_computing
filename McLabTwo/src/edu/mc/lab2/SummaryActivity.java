package edu.mc.lab2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.widget.TextView;

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
        
        //set texts
        ((TextView) findViewById(R.id.number_of_books)).setText("There are " + bookManager.count() + " books in your library.");
        ((TextView) findViewById(R.id.total_costs)).setText("" + bookManager.getTotalCost());
        ((TextView) findViewById(R.id.min_price)).setText("" + bookManager.getMinPrice());
        ((TextView) findViewById(R.id.max_price)).setText("" + bookManager.getMaxPrice());
        ((TextView) findViewById(R.id.avg_price)).setText("" + bookManager.getMeanPrice());
          
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
			      return true;
			 default:
			    return super.onOptionsItemSelected(item);
		}
    }
}
