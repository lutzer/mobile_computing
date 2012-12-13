package com.ausloeser.screens;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SimpleCableRemoteFragment extends SherlockFragment {

	/*@Override
	protected void onCreateView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_simple_cable_remote, container, false);
	
	    return view;
	}
}
