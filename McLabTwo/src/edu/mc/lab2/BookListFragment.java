package edu.mc.lab2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public class BookListFragment extends SherlockFragment {
	
	OnBookSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnBookSelectedListener {
        public void onBookSelected(int position);
    }
	
	/*
	 *  Class holds the books for the listview and defines the row layout
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
	                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	BookAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		bookManager = SimpleBookManager.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_book_list, container, false);
		
		ArrayList<Book> books = bookManager.getAllBooks();
		adapter = new BookAdapter(this.getActivity(),books);	
	   	    
	    ListView listView =  ((ListView) view.findViewById(R.id.listView));
    	listView.setAdapter(adapter);
    	
    	// link list with detail activity
        listView.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		        // Send the event to the host activity
		        mCallback.onBookSelected(position);
			}
        });
	    
	    return view;
	}
	
	 
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnBookSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBookSelectedListener");
        }
    }
    
    @Override
    public void onResume() {
    	adapter.notifyDataSetChanged();
    	super.onResume();
    }
   
}
