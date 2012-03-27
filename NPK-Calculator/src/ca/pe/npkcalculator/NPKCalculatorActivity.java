package ca.pe.npkcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NPKCalculatorActivity extends Activity implements OnClickListener {
	
	CheckBox manureCredits;
	EditText manureAmount;
	Spinner prevCropSpn, phosphateSpn, potashSpn, manureTypeSpn;
	TextView manureTypePrompt, manureAmountPrompt;
	
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Create crop selection spinner
        Spinner cropSelectionSpn = (Spinner) findViewById(R.id.cropSelectionSpn);
        
        // Create adapter for crop selection string array
        ArrayAdapter<CharSequence> cropSelectionAdapter = ArrayAdapter.createFromResource(this, R.array.cropsToSelect, android.R.layout.simple_spinner_item);
        cropSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        // Set adapter on crop selection spinner
        cropSelectionSpn.setAdapter(cropSelectionAdapter);        
     
        prevCropSpn = (Spinner) findViewById(R.id.prevCropSpn);        
        ArrayAdapter<CharSequence> prevCropAdapter = ArrayAdapter.createFromResource(this, R.array.prevCrops, android.R.layout.simple_spinner_item);
        prevCropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        prevCropSpn.setAdapter(prevCropAdapter);
        
        phosphateSpn = (Spinner) findViewById(R.id.phosphateSpn);        
        ArrayAdapter<CharSequence> phosphateAdapter = ArrayAdapter.createFromResource(this, R.array.soilTestRatings, android.R.layout.simple_spinner_item);
        phosphateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        phosphateSpn.setAdapter(phosphateAdapter);
        
        potashSpn = (Spinner) findViewById(R.id.potashSpn);        
        ArrayAdapter<CharSequence> potashAdapter = ArrayAdapter.createFromResource(this, R.array.soilTestRatings, android.R.layout.simple_spinner_item);
        potashAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        potashSpn.setAdapter(potashAdapter);
        
        manureTypeSpn = (Spinner) findViewById(R.id.manureTypeSpn);        
        ArrayAdapter<CharSequence> manureTypeAdapter = ArrayAdapter.createFromResource(this, R.array.manureTypes, android.R.layout.simple_spinner_item);
        manureTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        manureTypeSpn.setAdapter(manureTypeAdapter);
        
        manureCredits = (CheckBox) findViewById(R.id.manureCreditsCheckBox);        
        manureTypePrompt = (TextView) findViewById(R.id.manureTypePrompt);
        manureAmountPrompt = (TextView) findViewById(R.id.manureTypePrompt);
        manureAmount = (EditText) findViewById(R.id.manureAmount);
        
        
        manureCredits.setOnClickListener(this);
    }    

	public void onClick(View v) {
		
		if (v == manureCredits) {
			if (manureCredits.isChecked()) {    		
	    		manureTypePrompt.setVisibility(View.VISIBLE);
	    		manureTypeSpn.setVisibility(View.VISIBLE);
	    		manureAmountPrompt.setVisibility(View.VISIBLE);
	    		manureAmount.setVisibility(View.VISIBLE);
	    		
	    	} else {
	    		manureTypePrompt.setVisibility(View.GONE);
	    		manureTypeSpn.setVisibility(View.GONE);
	    		manureAmountPrompt.setVisibility(View.GONE);
	    		manureAmount.setVisibility(View.GONE);
	    	}
	    		
		}		
		
	}    
    
}
