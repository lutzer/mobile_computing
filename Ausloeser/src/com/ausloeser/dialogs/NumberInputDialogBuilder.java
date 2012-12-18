package com.ausloeser.dialogs;

import com.ausloeser.screens.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NumberInputDialogBuilder extends BaseDialogBuilder {
	
	EditText numberInput;
	float minValue, maxValue;
	
	public NumberInputDialogBuilder(Context arg0, String title, float value) {
		super(arg0, title, Buttons.OkCancel);
		
		init(arg0);
		
		numberInput.setText(String.valueOf(value));
		minValue = 0.0F;
		maxValue = Float.MAX_VALUE;
	}

	public NumberInputDialogBuilder(Context arg0, String title, float value, float minValue, float maxValue) {
		super(arg0, title, Buttons.OkCancel);
		
		init(arg0);
		
		numberInput.setText(String.valueOf(value));
		this.minValue = minValue;
		this.maxValue = maxValue;
		
	}
	
	private void init(Context context) {
		// set up view
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_number_input, null);
		this.setView(view);
		
		numberInput = (EditText) view.findViewById(R.id.EditTextInput);
	}
	
	public float getValue() {
		float value = -1; 
		try {
			value = Float.valueOf(numberInput.getText().toString());
		} catch (Exception e) {
			value = -1;
		}
		value = Math.max(minValue, Math.min(maxValue, value));
		return value;
	}

}
