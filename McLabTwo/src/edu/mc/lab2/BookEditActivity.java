package edu.mc.lab2;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookEditActivity extends SherlockActivity {

	SimpleBookManager bookManager;
	Book book;
	int bookId;
	boolean addNewBook;
	
	//edit fields
	EditText title,author,price,isbn,course;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        bookManager = SimpleBookManager.getInstance();
        
        //get book
	    Bundle args = getIntent().getExtras();
	    if(args !=null)
	    	if (args.getBoolean("addBook")) {
	    		bookId = bookManager.count();
	    		book = new Book();
	    		addNewBook = true;
	    	} else {
	    		bookId = args.getInt("bookId");
	    		book = bookManager.getBook(bookId);
	    		addNewBook = false;
	    	}
	    
	    
	    
	    //exit if there is no proper book selected
	    if (book == null)
	    	finish();
        
        //set values
	    author = (EditText) findViewById(R.id.author);
	    author.setText(book.getAuthor(),TextView.BufferType.EDITABLE);
	    title = (EditText) findViewById(R.id.title);
	    title.setText(book.getTitle(),TextView.BufferType.EDITABLE);
	    course = (EditText) findViewById(R.id.course);
	    course.setText(book.getCourse(),TextView.BufferType.EDITABLE);
	    isbn = (EditText) findViewById(R.id.isbn);
	    isbn.setText(book.getIsbn(),TextView.BufferType.EDITABLE); 
	    price = (EditText) findViewById(R.id.price);
	    price.setText(book.getPrice()+"",TextView.BufferType.EDITABLE);
	    
	    /*//set text validators
	    title.addTextChangedListener(new TextValidator(title) {
	        @Override
	        public void validate(TextView textView, String text) {
	           if (text.length() < 1) {
	        	   textView.setError( "Title is required" );
	           } else {
	        	   textView.setError(null); 
	           }
	        }
	    });*/
	    
	    // setup save button listener
	    ((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (validateInput()) {
					
					//create new Book if needed
					if (addNewBook) {
						book = bookManager.createBook();
					}
					
					//save book
					book.setAuthor(author.getText().toString());
					book.setTitle(title.getText().toString());
					book.setCourse(course.getText().toString());
					book.setPrice(Float.valueOf(price.getText().toString()));
					book.setIsbn(isbn.getText().toString());
					
					// quit activity
					Intent intent = new Intent().putExtra("bookId", bookId);
					setResult(1,intent);
					finish();
				}
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
    
    /*
     * validate the input fields
     */
    private boolean validateInput() {
    	if (title.getText().length() < 1) {
    		title.setError( "Title is required" );
    		return false;
    	}
    	return true;
    }

}
