package edu.mc.lab1;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends SherlockActivity {
	
	/*
	 *  Class holds the books for the listview
	 */
	private class BookAdapter extends ArrayAdapter<Book> {

	    private ArrayList<Book> items;

	    public BookAdapter(Context context, ArrayList<Book> items) {
	            super(context, R.layout.book_row, items);
	            this.items = items;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	            View v = convertView;
	            if (v == null) {
	                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                v = vi.inflate(R.layout.book_row, null);
	            }
	            Book book = items.get(position);
	            if (book != null) {
	                TextView author = (TextView) v.findViewById(R.id.author);
	                TextView title = (TextView) v.findViewById(R.id.title);
	                if (author != null)
	                	author.setText(book.getAuthor());
	                if(title != null)
	                	title.setText(book.getTitle());
	                
	            }
	            return v;
	    }
	}

	SimpleBookManager bookManager;
	
	// holds the items for the listview
	BookAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //enable back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        bookManager = SimpleBookManager.getInstance();
    	ArrayList<Book> books = bookManager.getAllBooks(); 
    	 adapter = new BookAdapter(this,books);
         ((ListView)findViewById(R.id.listView)).setAdapter(adapter);
        
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
         case R.id.Summary:
        	 Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
        	 startActivity(intent);
             //Toast.makeText(MainActivity.this, "Open Summary activity", Toast.LENGTH_SHORT).show();
             return true;
  
         case R.id.Menu:
             Toast.makeText(MainActivity.this, "Menu is Selected", Toast.LENGTH_SHORT).show();
             return true;
             
         case R.id.submenu_1:
             Toast.makeText(MainActivity.this, "Sub Menu is Selected", Toast.LENGTH_SHORT).show();
             return true;        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
