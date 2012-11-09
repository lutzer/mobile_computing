package edu.mc.lab2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.widget.TextView;

public class BookDetailActivity extends SherlockActivity {

	SimpleBookManager bookManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        
        //enable back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        bookManager = SimpleBookManager.getInstance();
        
        //get book index
        int bookId = -1;
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)
        	bookId = extras.getInt("bookId");
        
        Book book = bookManager.getBook(bookId);
        
        //jump out of activity if book non existent
        if (book == null)
        	finish();
        
        //set texts
        ((TextView) findViewById(R.id.author)).setText(book.getAuthor());
        ((TextView) findViewById(R.id.title)).setText(book.getTitle());
        ((TextView) findViewById(R.id.course)).setText(book.getCourse());
        ((TextView) findViewById(R.id.price)).setText(book.getPrice() + " Û");
        ((TextView) findViewById(R.id.isbn)).setText(book.getIsbn());
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_book_detail, menu);
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
