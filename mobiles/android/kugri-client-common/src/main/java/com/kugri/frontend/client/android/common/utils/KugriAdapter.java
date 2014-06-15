package com.kugri.frontend.client.android.common.utils;

import java.util.ArrayList;

import com.kugri.frontend.client.android.common.Context;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class KugriAdapter  extends ArrayAdapter<KugriObject>{
	private Activity context;
    ArrayList<KugriObject> data = null;

	public KugriAdapter(Activity context, int textViewResourceId,
			ArrayList<KugriObject> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);   
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        View row = convertView;
        KugriObject item = data.get(position);
        if(row == null)
        {
        	Log.i("TRANSPORT_ADAPTER", "ROW IS NULL");
            LayoutInflater inflater = context.getLayoutInflater();
//            if(item.getScreen() == 0.75){//ldpi
//            	row = inflater.inflate(R.layout.transport_item_small, parent, false);
//			}else if(item.getScreen() == 1.0){//mdpi
//				row = inflater.inflate(R.layout.transport_item_medium, parent, false);
//			}else if(item.getScreen() == 2.0){//hdpi
//				row = inflater.inflate(R.layout.transport_item_large, parent, false);
//			}else if(item.getScreen() == 3.0){//xhdpi
//				row = inflater.inflate(R.layout.transport_item_xlarge, parent, false);
//			}
            row = inflater.inflate(item.getLayout(), parent, false);
        }

        //Transport item = data.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.
        	for(KugriField field: item.getFields()){
        		if(field.getFieldClass().getName().equals(TextView.class.getName())){
        			TextView fieldView = (TextView) row.findViewById(field.getFieldId());
        			fieldView.setText(field.getFieldValue());
        		}else if(field.getFieldClass().getName().equals(EditText.class.getName())){
        			EditText fieldView = (EditText) row.findViewById(field.getFieldId());
        			fieldView.setHint(field.getFieldValue());
        		}else if(field.getFieldClass().getName().equals(CheckedTextView.class.getName())){
        			CheckedTextView fieldView = (CheckedTextView) row.findViewById(field.getFieldId());
        			fieldView.setText(field.getFieldValue());
        		}else if(field.getFieldClass().getName().equals(AutoCompleteTextView.class.getName())){
        			AutoCompleteTextView fieldView = (AutoCompleteTextView) row.findViewById(field.getFieldId());
        			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, field.getFieldId(), field.getFieldValue().split("#"));
        			fieldView.setAdapter(adapter);
        		}else if(field.getFieldClass().getName().equals(ImageView.class.getName())){
        			ImageView fieldView = (ImageView) row.findViewById(field.getFieldId());
        			fieldView.setBackgroundResource(Integer.parseInt(field.getFieldValue()));
        		}else if(field.getFieldClass().getName().equals(ImageButton.class.getName())){
        			ImageButton fieldView = (ImageButton) row.findViewById(field.getFieldId());
        			fieldView.setBackgroundResource(Integer.parseInt(field.getFieldValue()));
        		}else if(field.getFieldClass().getName().equals(CheckBox.class.getName())){
        			CheckBox fieldView = (CheckBox) row.findViewById(field.getFieldId());
        			fieldView.setChecked(Boolean.parseBoolean(field.getFieldValue()));
        		}else if(field.getFieldClass().getName().equals(ProgressBar.class.getName())){
        			ProgressBar fieldView = (ProgressBar) row.findViewById(field.getFieldId());
        			fieldView.setProgress(Integer.parseInt(field.getFieldValue()));
        		}else if(field.getFieldClass().getName().equals(TimePicker.class.getName())){
        			TimePicker fieldView = (TimePicker) row.findViewById(field.getFieldId());
        			String time[] = field.getFieldValue().split("#");
        			fieldView.setIs24HourView(Boolean.parseBoolean(time[0]));;
        			fieldView.setCurrentHour(Integer.parseInt(time[1]));
        			fieldView.setCurrentMinute(Integer.parseInt(time[2]));
        		}else if(field.getFieldClass().getName().equals(Spinner.class.getName())){
        			Spinner fieldView = (Spinner) row.findViewById(field.getFieldId());
        			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, field.getFieldId(), field.getFieldValue().split("#"));
        			fieldView.setAdapter(adapter);
        		}else if(field.getFieldClass().getName().equals(Chronometer.class.getName())){
        			Chronometer fieldView = (Chronometer) row.findViewById(field.getFieldId());
        			fieldView.setBase(Integer.parseInt(field.getFieldValue()));
        		}else{
        			//View fieldView = (View) row.findViewById(field.getFieldId());
        			Context.LOGGER.pushErrors(new Exception(), "This view is not not being handled yet by KugriAdapter.");
        		}
        	}
        }
        return row;
    }
}
