package ca.pe.npkcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class NPKCalculatorActivity extends Activity implements OnClickListener {
	
	Button calculateBtn;
	CheckBox manureCredits;
	DatePicker cropSownDatePicker;
	Double nitrogenReqValue, cropLengthModifier;
	EditText manureAmount;
	RadioGroup cropLengthRadioGroup, organicMatterRadioGroup, cropStandRadioGroup;
	Spinner cropSelectionSpn, prevCropSpn, phosphateSpn, potashSpn, manureTypeSpn;
	TextView manureTypePrompt, manureAmountPrompt, nitrogenRequirement, phosphateRequirement, potashRequirement;
	
	int checkedCropLengthBtn, checkedCropStandBtn, checkedOrganicMatterBtn, plantingDateModifier, organicMatterModifier;
	    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        cropSelectionSpn = (Spinner) findViewById(R.id.cropSelectionSpn);        
        ArrayAdapter<CharSequence> cropSelectionAdapter = ArrayAdapter.createFromResource(this, R.array.cropsToSelect, android.R.layout.simple_spinner_item);
        cropSelectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
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
        
        cropLengthRadioGroup = (RadioGroup) findViewById(R.id.cropLengthRadioGroup);
        cropSownDatePicker = (DatePicker) findViewById(R.id.cropSownDatePicker);
        manureCredits = (CheckBox) findViewById(R.id.manureCreditsCheckBox);        
        manureTypePrompt = (TextView) findViewById(R.id.manureTypePrompt);
        manureAmountPrompt = (TextView) findViewById(R.id.manureAmountPrompt);
        manureAmount = (EditText) findViewById(R.id.manureAmount);
        cropStandRadioGroup = (RadioGroup) findViewById(R.id.cropStandRadioGroup);
        organicMatterRadioGroup = (RadioGroup) findViewById(R.id.organicMatterRadioGroup);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        nitrogenRequirement = (TextView) findViewById(R.id.nitrogenRequirement);
        phosphateRequirement = (TextView) findViewById(R.id.phosphateRequirement);
        potashRequirement = (TextView) findViewById(R.id.potashRequirement);
        
        manureCredits.setOnClickListener(this);
        calculateBtn.setOnClickListener(this);        
        
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
		
		if (v == calculateBtn) {			
			calculateNitrogenRequirement();			
		}
		
	}
	
	public void calculateNitrogenRequirement() {
		getCropLength();
		getPlantingDate();
		getCropStand();
		getOrganicMatter();
		
		nitrogenReqValue = cropLengthModifier - plantingDateModifier - organicMatterModifier;
		
		String nitrogenReqFS = String.format("%.1f", nitrogenReqValue);
		
		nitrogenRequirement.setText(nitrogenReqFS);		
		//nitrogenRequirement.setText("" + cropSownDatePicker.getMonth());
		//nitrogenRequirement.setText("" + organicMatterModifier);		
	}
	
	public void getCropLength() {
		checkedCropLengthBtn = cropLengthRadioGroup.getCheckedRadioButtonId();
		
		switch (checkedCropLengthBtn) {
		  case R.id.cropLengthEarly : cropLengthModifier = 0.9;
		  	break;
		  case R.id.cropLengthFull : cropLengthModifier = 1.0;
			break;
		  default : cropLengthModifier = 0.0;
			break;
		}
	}
	
	public void getPlantingDate() {
		if (cropSownDatePicker.getMonth() <= 4 && cropSownDatePicker.getDayOfMonth() <= 25) {
			plantingDateModifier = 0;
		} else if (cropSownDatePicker.getMonth() >= 5 && cropSownDatePicker.getDayOfMonth() >= 9) {
			plantingDateModifier = 33;
		} else if (cropSownDatePicker.getMonth() == 5) {
			if (cropSownDatePicker.getDayOfMonth() >= 2 && cropSownDatePicker.getDayOfMonth() <= 8)	{
				plantingDateModifier = 22;
			} else plantingDateModifier = 11;
		} else if (cropSownDatePicker.getMonth() == 4) {
			if (cropSownDatePicker.getDayOfMonth() >= 26)
				plantingDateModifier = 11;
		} else plantingDateModifier = 0;
	}
	
	public void getCropStand() {
		checkedCropStandBtn = cropStandRadioGroup.getCheckedRadioButtonId();
		
		// TO DO
		
	}
	
	public void getOrganicMatter() {
		checkedOrganicMatterBtn = organicMatterRadioGroup.getCheckedRadioButtonId();
		
		switch (checkedOrganicMatterBtn) {
		  case R.id.lessOrganicBtn : organicMatterModifier = 0;
		  	break;
		  case R.id.moreOrganicBtn : organicMatterModifier = 15;
		  	break;
		  default : organicMatterModifier = 0;
		  	break;
		}
	}
    
}
