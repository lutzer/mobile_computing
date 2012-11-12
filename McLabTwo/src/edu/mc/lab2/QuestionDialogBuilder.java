package edu.mc.lab2;

import edu.mc.lab2.BookListFragment.OnBookSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class QuestionDialogBuilder extends AlertDialog.Builder {
	
	public enum Buttons {
		YesNo,
		OkCancel
	}
	
	OnDialogResultListener mCallback;

    public interface OnDialogResultListener {
        public void OnDialogResultListener(boolean result);
    }
	
	public QuestionDialogBuilder(Context arg0, String title, String message, Buttons buttons) {
		super(arg0);
		
		this.setTitle(title);
		this.setMessage(message);
		
		if (buttons == Buttons.YesNo) {
			this.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	
				@Override
		        public void onClick(DialogInterface dialog, int which) {
					if (mCallback != null)
						mCallback.OnDialogResultListener(true);
		            dialog.dismiss();
		        }
		    });

			this.setNegativeButton("No", new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	if (mCallback != null)
						mCallback.OnDialogResultListener(false);
		            dialog.dismiss();
		        }
			});
		}
	    else if (buttons == Buttons.OkCancel) {
	    	this.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    		
				@Override
		        public void onClick(DialogInterface dialog, int which) {
					if (mCallback != null)
						mCallback.OnDialogResultListener(true);
		            dialog.dismiss();
		        }
		    });

			this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	if (mCallback != null)
						mCallback.OnDialogResultListener(false);
		            dialog.dismiss();
		        }
			});
	    }
	}
	
	/*
	 * sets the listener which is called upon a dialogs button press
	 */
	public void SetDialogResultListener(OnDialogResultListener listener) {
		mCallback = listener;
	}

}
