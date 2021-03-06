package edu.mc.lab2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

/*
 * class can validate input while typing (not used)
 */
public abstract class TextValidator implements TextWatcher {
    private TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { 
    	
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { 
    	
    }
}