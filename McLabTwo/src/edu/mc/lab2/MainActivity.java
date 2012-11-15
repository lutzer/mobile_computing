package edu.mc.lab2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends SherlockFragmentActivity 
	implements ActionBar.TabListener, BookListFragment.OnBookSelectedListener {
	
	SimpleBookManager bookManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);  //enable home button
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  //enable tab bar
        
        //add tabs
        actionBar.addTab(actionBar.newTab().setText("Book List").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Summary").setTabListener(this));
        
        //connect to database and load books
        bookManager = SimpleBookManager.getInstance();
        bookManager.connectDatabase(getApplicationContext());
        try {
        	bookManager.loadData();
        } catch (Exception e) {
        	Log.e("Error","Cannot load data from database");
       	 	finish();
        }
    }
    
    
    @Override
    protected void onSaveInstanceState (Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	try {
			bookManager.saveChanges();
		} catch (Exception e) {
			Log.e("ERROR",e.getMessage());
		}	
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	bookManager.closeDatabase();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);;
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
         switch (item.getItemId())
         {
         case R.id.Add:
        	 Intent intent = new Intent(getApplicationContext(), BookEditActivity.class);
        	 intent.putExtra("addBook", true);
        	 startActivity(intent);
        	 return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// When the given tab is selected, show the tab contents in the container
		SherlockFragment fragment;
		switch (tab.getPosition())
		{
		case 0:
			fragment = new BookListFragment();
			break;
		case 1:
			fragment = new SummaryFragment();
			break;
		default:
			fragment = new SherlockFragment();		
			return;
		}
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * gets called by the BookListFragment, if the user selects a book from the list
	 */
	public void onBookSelected(int position) {
		Intent intent = new Intent(this, BookDetailActivity.class);
		intent.putExtra("bookId", position);
        startActivity(intent);
	}
}
