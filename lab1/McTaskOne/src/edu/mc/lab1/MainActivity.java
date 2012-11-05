package edu.mc.lab1;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
	
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
        
        bookManager = SimpleBookManager.getInstance();
        
    	
    	ArrayList<Book> books = bookManager.getAllBooks(); 
    	
    	 adapter = new BookAdapter(this,books);
         ((ListView)findViewById(R.id.listView)).setAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
