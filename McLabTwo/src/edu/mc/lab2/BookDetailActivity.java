package edu.mc.lab2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BookDetailActivity extends SherlockActivity {

	SimpleBookManager bookManager;
	Book book;
	int bookId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);  //enable home button
        
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
	    
	    //set texts
	    ((TextView) findViewById(R.id.author)).setText(book.getAuthor());
	    ((TextView) findViewById(R.id.title)).setText(book.getTitle());
	    ((TextView) findViewById(R.id.course)).setText(book.getCourse());
	    ((TextView) findViewById(R.id.price)).setText(book.getPrice() + " Û");
	    ((TextView) findViewById(R.id.isbn)).setText(book.getIsbn());
	    
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
         switch (item.getItemId())
         {
         case R.id.Remove:
        	 QuestionDialogBuilder builder = new QuestionDialogBuilder(this,"Remove Book",
        			 "Do you really want to remove " +book.getTitle()+ " from the library?",
        			 QuestionDialogBuilder.Buttons.YesNo);
        	 builder.SetDialogResultListener(new QuestionDialogBuilder.OnDialogResultListener() {

				@Override
				public void OnDialogResultListener(boolean result) {
					if (result) {
						bookManager.removeBook(book);
						finish(); //end activity
					}
				}
        		 
        	 });
        	 AlertDialog dialog = builder.create();
        	 dialog.show();
        	 return true;
         case R.id.Edit:
        	 Intent intent = new Intent(getApplicationContext(), BookEditActivity.class);
        	 intent.putExtra("bookId", bookId);
        	 startActivity(intent);
        	 return true;
         case android.R.id.home:
        	 finish();
             return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getSupportMenuInflater().inflate(R.menu.activity_book_detail, menu);;
        return super.onCreateOptionsMenu(menu);
    }
}
