package edu.mc.lab2;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookEditActivity extends SherlockActivity {

	SimpleBookManager bookManager;
	Book book;
	int bookId;
	
	//fields to validate
	boolean inputValid;
	EditText title;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        bookManager = SimpleBookManager.getInstance();
        
        //get book index
	    bookId = -1;
	    Bundle args = getIntent().getExtras();
	    if(args !=null)
	    	bookId = args.getInt("bookId");
	    
	    book = bookManager.getBook(bookId);
	    
	    //exit if there is no proper book selected
	    if (book == null)
	    	finish();
        
        //set values
        ((EditText) findViewById(R.id.author)).setText(book.getAuthor(),TextView.BufferType.EDITABLE);
	    title = (EditText) findViewById(R.id.title);
	    title.setText(book.getTitle(),TextView.BufferType.EDITABLE);
	    ((EditText) findViewById(R.id.course)).setText(book.getCourse(),TextView.BufferType.EDITABLE);
	    ((EditText) findViewById(R.id.isbn)).setText(book.getIsbn(),TextView.BufferType.EDITABLE); 
	    ((EditText) findViewById(R.id.price)).setText(book.getPrice()+"",TextView.BufferType.EDITABLE);
	    
	    //set text validators
	    title.addTextChangedListener(new TextValidator(title) {
	        @Override
	        public void validate(TextView textView, String text) {
	           if (text.length() < 1)
	           {
	        	   textView.setError( "Title is required" );
	        	   inputValid = false;
	           }
	           else
	           {
	        	   textView.setError(null);
	        	   inputValid = true;
	           }
	        }
	    });
	    
	    // setup save button listener
	    ((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				;
			}
	    });
	      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_book_edit, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
