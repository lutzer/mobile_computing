package com.ausloeser.views;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Utils {

	public static int inRange(int value, int min, int max) {
		if (value>max)
			return max;
		if (value<min)
			return min;
		return value;
	}
	
	public static void applyFonts(final View v, Typeface fontToSet)
	{
	    try {
	        if (v instanceof ViewGroup) {
	            ViewGroup vg = (ViewGroup) v;
	            for (int i = 0; i < vg.getChildCount(); i++) {
	                View child = vg.getChildAt(i);
	                applyFonts(child, fontToSet);
	            }
	        } else if (v instanceof TextView) {
	            ((TextView)v).setTypeface(fontToSet);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // ignore
	    }
	}
}
