package com.ausloeser.views;

import java.util.ArrayList;

import com.ausloeser.screens.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderSwitch extends FrameLayout {
	
	public interface OnSliderSwitchChangeListener {       
		abstract void onProgressChanged(int snappedPosition);
		abstract void onStartTrackingTouch(int snappedPosition);
		abstract void onStopTrackingTouch(int snappedPosition);
    }
	
	ImageButton sliderKnob;
	
	LinearLayout sliderLayout,labelLayout;
	RelativeLayout relLayout;
	
	int knobPosition = 0;
	int knobWidth = 0;
	int sliderWidth = 0;
	int sliderStart = 0;
	
	int numberOfElements = 2;
	int spaceBetweenElements = 1;
	
	ArrayList<TextView> labels = new ArrayList<TextView>();
	
	ArrayList<OnSliderSwitchChangeListener> switchListeners = new ArrayList<OnSliderSwitchChangeListener>();
	int currentSliderPosition = 0;

	public SliderSwitch(Context context) {
		super(context);
		init(context);
	}

	public SliderSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// get attributes
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.SliderSwitch,
				0, 0);

		try {
			numberOfElements = a.getInteger(R.styleable.SliderSwitch_number_of_elements, 2);
		} finally {
			a.recycle();
		}
		
		init(context);
		
	}

	
	private void init(Context context)
	{
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.slider_switch,this);
		
		sliderKnob = (ImageButton) findViewById(R.id.sliderKnob);

		sliderLayout = (LinearLayout) findViewById(R.id.sliderLayout);
		labelLayout = (LinearLayout) findViewById(R.id.labelLayout);
		relLayout = (RelativeLayout) findViewById(R.id.relLayout);
		
		// make the slider knob be dragable
		sliderKnob.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sliderKnob.getWidth(),sliderKnob.getHeight());
            	
				switch(event.getAction())
                {
	                case MotionEvent.ACTION_DOWN:
	                	for (OnSliderSwitchChangeListener listener : switchListeners) 
	                	{
	                	   listener.onStartTrackingTouch(currentSliderPosition);
	                	}
	                	break;
	                case MotionEvent.ACTION_MOVE:
	                	knobPosition += event.getX()-knobWidth/2;
	                	
	                	// snap to wholes when the knob is 1/4th close to one
	                	if (knobPosition % spaceBetweenElements < spaceBetweenElements/4 || knobPosition % spaceBetweenElements > spaceBetweenElements*3/4 ) {
	                		int newSliderPosition = (int)Math.round((float)knobPosition/spaceBetweenElements);
		                	knobPosition = spaceBetweenElements * newSliderPosition;
		                	if (newSliderPosition != currentSliderPosition) {
		                		currentSliderPosition = newSliderPosition;
		                		for (OnSliderSwitchChangeListener listener : switchListeners) 
			                	{
			                	   listener.onProgressChanged(currentSliderPosition);
			                	}
		                	}
	                	}
	                	
	                	// stay in range of the slider
	                	knobPosition = Utils.inRange(knobPosition, 0, sliderWidth-knobWidth);
	             	
	                	//update pos
	                	params.setMargins(knobPosition, 0, 0, 0);
	                	sliderKnob.setLayoutParams(params);
	                	break;
	                case MotionEvent.ACTION_UP:
	                	//put slider to closest whole
	                	int newSliderPosition = (int)Math.round((float)knobPosition/spaceBetweenElements);
	                	knobPosition = spaceBetweenElements * newSliderPosition;
	                	knobPosition = Utils.inRange(knobPosition, 0, sliderWidth-knobWidth);
	                	params.setMargins(knobPosition, 0, 0, 0);
	    				sliderKnob.setLayoutParams(params);
	    				for (OnSliderSwitchChangeListener listener : switchListeners) 
	                	{
	                	   listener.onStopTrackingTouch(newSliderPosition);
	                	   if (currentSliderPosition != newSliderPosition)
	                		   listener.onProgressChanged(newSliderPosition);
	                	}
	    				currentSliderPosition = newSliderPosition;
	                	break;
	                default:
	                	break;
	                }
	                return true;
			}

		});
		
		//add a spacer in the front to get to first hole
		View spacer = new View(context);
		spacer.setLayoutParams(new LayoutParams(21,LayoutParams.MATCH_PARENT));
		sliderLayout.addView(spacer);

		// add one spacer
		spacer = new View(context);
		spacer.setLayoutParams(new LinearLayout.LayoutParams(1,LayoutParams.WRAP_CONTENT,1.0F));
		sliderLayout.addView(spacer);
		
		// setup the view depending on how many elements there are
		for (int i=1;i<numberOfElements-1;i++) {
			ImageView lineDot = (ImageView) inflater.inflate(R.layout.slider_switch_line_dot, null);
			sliderLayout.addView(lineDot);

			spacer = new View(context);
			spacer.setLayoutParams(new LinearLayout.LayoutParams(1,LayoutParams.WRAP_CONTENT,1.0F));
			//spacer.setLayoutParams(new LinearLayout.LayoutParams(1,LayoutParams.WRAP_CONTENT,1.0F));
			sliderLayout.addView(spacer);
		}
		
		// add one spacer in the end to fill gap
		spacer = new View(context);
		spacer.setLayoutParams(new LayoutParams(21,LayoutParams.MATCH_PARENT));
		sliderLayout.addView(spacer);
		
		// setup the text labels
		for (int i=0;i<numberOfElements;i++) {
			TextView label = (TextView) inflater.inflate(R.layout.slider_switch_label, null);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1,LayoutParams.WRAP_CONTENT,1.0F);
			label.setLayoutParams(params);
			
			labels.add(label);
			labelLayout.addView(label);
			
		}
		
		//set fonts
		Utils.applyFonts(labelLayout,Typeface.createFromAsset(context.getAssets(),"fonts/eurostile.ttf"));
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	 
	    //only get this value the first time
	    if (knobWidth==0)
	    	knobWidth = sliderKnob.getWidth();
	    
	    sliderWidth = ((RelativeLayout) findViewById(R.id.relLayout)).getWidth();
	    spaceBetweenElements = (sliderWidth-knobWidth)/(numberOfElements-1);
	    
	    /*for (int i=0;i<labels.size();i++) {
	    	labels.get(i).setLayoutParams(new LinearLayout.LayoutParams(10,LayoutParams.WRAP_CONTENT,0.0F));
	    }*/
	    
	}


	@Override
	protected void onDraw(Canvas canvas)
	{
	    super.onDraw(canvas);
	}
	
	/*
	 * Sets the text of the labels
	 */
	public void setLabelTexts(String[] labelNames) {
		for (int i=0;i<labelNames.length;i++) {
			labels.get(i).setText(labelNames[i]);
		}
	}
	
	public void setOnSliderSwitchChangeListener(OnSliderSwitchChangeListener listener) {
		switchListeners.add(listener);
	}
	
	

}
