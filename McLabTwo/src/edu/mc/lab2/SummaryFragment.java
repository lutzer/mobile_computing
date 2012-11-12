package edu.mc.lab2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SummaryFragment extends SherlockFragment {

	SimpleBookManager bookManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
        bookManager = SimpleBookManager.getInstance();
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_summary, container, false);
        
        //set texts
        ((TextView) view.findViewById(R.id.number_of_books)).setText("There are " + bookManager.count() + " books in your library.");
        ((TextView) view.findViewById(R.id.total_costs)).setText("" + bookManager.getTotalCost());
        ((TextView) view.findViewById(R.id.min_price)).setText("" + bookManager.getMinPrice());
        ((TextView) view.findViewById(R.id.max_price)).setText("" + bookManager.getMaxPrice());
        ((TextView) view.findViewById(R.id.avg_price)).setText("" + bookManager.getMeanPrice());
        
        return view;
    }
}
