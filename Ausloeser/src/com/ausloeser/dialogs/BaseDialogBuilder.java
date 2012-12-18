package com.ausloeser.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/*
 * creates a dialog, and puts two buttons on the bottom
 */
public class BaseDialogBuilder extends AlertDialog.Builder {
	
	public enum Buttons {
		YesNo,
		OkCancel
	}
	
	OnDialogResultListener mCallback;

    public interface OnDialogResultListener {
        public void OnDialogResultListener(boolean result);
    }
	
	public BaseDialogBuilder(Context arg0, String title, Buttons buttons) {
		super(arg0);
		
		this.setTitle(title);
		
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
