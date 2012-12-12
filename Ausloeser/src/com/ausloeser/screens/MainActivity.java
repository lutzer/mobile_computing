package com.ausloeser.screens;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.mc.lab2.Book;
import edu.mc.lab2.R;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ActionBar actionBar = getSupportActionBar();
	}
	
	private void populateList() {
		//ListView listView =  ((ListView) view.findViewById(R.id.listView));
    	//listView.setAdapter(adapter);
		
	}

	public boolean onOptionsItemSelected(MenuItem item)
    {
         /*switch (item.getItemId())
         {
         case R.id.Remove:
        	 
        	 /;
        	 return true;
         case R.id.Edit:
        	 ;
        	 return true;
         case android.R.id.home:
        	 ;
             return true;
        default:
            return super.onOptionsItemSelected(item);
        }*/
		return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);;
        return super.onCreateOptionsMenu(menu);
    }
}