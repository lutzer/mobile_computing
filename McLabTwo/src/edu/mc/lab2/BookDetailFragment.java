package edu.mc.lab2;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailFragment extends SherlockFragment {

	SimpleBookManager bookManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        bookManager = SimpleBookManager.getInstance();
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
	    
	    //get book index
	    int bookId = -1;
	    Bundle args = getArguments();
	    if(args !=null)
	    	bookId = args.getInt("bookId");
	    
	    Book book = bookManager.getBook(bookId);
	    
	    //TODO: display error if there is no proper book selected
	    if (book == null)
	    	;
	    
	    //set texts
	    ((TextView) view.findViewById(R.id.author)).setText(book.getAuthor());
	    ((TextView) view.findViewById(R.id.title)).setText(book.getTitle());
	    ((TextView) view.findViewById(R.id.course)).setText(book.getCourse());
	    ((TextView) view.findViewById(R.id.price)).setText(book.getPrice() + " Û");
	    ((TextView) view.findViewById(R.id.isbn)).setText(book.getIsbn());
	    
	    return view;
    }
}
