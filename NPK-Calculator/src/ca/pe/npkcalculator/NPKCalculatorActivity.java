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
	CheckBox manureCredits, convertUnitsBox;
	DatePicker cropSownDatePicker;
	Double nitrogenReqValue, cropLengthModifier;
	EditText manureAmount;
	RadioGroup cropLengthRadioGroup, organicMatterRadioGroup, cropStandRadioGroup;
	Spinner cropSelectionSpn, prevCropSpn, phosphateSpn, potashSpn, manureTypeSpn;
	TextView manureTypePrompt, manureAmountPrompt, nitrogenRequirement, phosphateRequirement, potashRequirement, 
			 nitrogenUnits, phosphateUnits, potashUnits;
	Boolean convertUnitsBln = false;
	
	int checkedCropLengthBtn, checkedCropStandBtn, checkedOrganicMatterBtn, plantingDateModifier, organicMatterModifier;
	int selectedPrevCrop, selectedBaseN, baseValueN, cropStandModifier;
	
	int[] baseValueNitrogen = {56, 45, 67, 56, 168, 168, 39, 168, 112, 168, 168, 67, 168, 39, 146, 134, 146, 67, 84, 56, 22, 22, 112,
							  123, 84, 84, 22, 22, 22, 22, 168, 84, 45, 56, 56, 34, 134, 112, 90, 174, 174, 174, 174, 174,
							  174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 174, 90,
							  67, 151, 168, 50, 56, 22, 112, 90, 168, 112, 67, 67, 22};
	
	int[] poorStand = {0, 0, 0, 10, 0};
	int[] fairStand = {40, 20, 10, 10, 0};
	int[] goodStand = {80, 40, 20, 10, -15};	
	    
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
        convertUnitsBox = (CheckBox) findViewById(R.id.convertUnitsBox);
        nitrogenUnits = (TextView) findViewById(R.id.nitrogenUnits);
        phosphateUnits = (TextView) findViewById(R.id.phosphateUnits);
        potashUnits = (TextView) findViewById(R.id.potashUnits);        
        		
        manureCredits.setOnClickListener(this);
        calculateBtn.setOnClickListener(this);
        convertUnitsBox.setOnClickListener(this);
        
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
		
		if (v == convertUnitsBox) {
			convertUnits();
		}
		
	}
	
	public void calculateNitrogenRequirement() {
		baseValueN = baseValueNitrogen[cropSelectionSpn.getSelectedItemPosition()];
		getCropLength();
		getPlantingDate();
		getCropStand();
		getOrganicMatter();
		
		nitrogenReqValue = baseValueN * cropLengthModifier - plantingDateModifier - cropStandModifier - organicMatterModifier;
				
		if (convertUnitsBox.isChecked())
			nitrogenReqValue = nitrogenReqValue * 0.89;			
				
		nitrogenRequirement.setText(formattedString(nitrogenReqValue));
		//convertUnits();
	}		
	
	public void getCropLength() {		
		switch (cropLengthRadioGroup.getCheckedRadioButtonId()) {
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
		switch (cropStandRadioGroup.getCheckedRadioButtonId()) {
		  case R.id.poorBtn : cropStandModifier = poorStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  case R.id.fairBtn : cropStandModifier = fairStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  case R.id.goodBtn : cropStandModifier = goodStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  default : cropStandModifier = 0;
		  	break;
		}		
	}
	
	public void getOrganicMatter() {		
		switch (organicMatterRadioGroup.getCheckedRadioButtonId()) {
		  case R.id.lessOrganicBtn : organicMatterModifier = 0;
		  	break;
		  case R.id.moreOrganicBtn : organicMatterModifier = 15;
		  	break;
		  default : organicMatterModifier = 0;
		  	break;
		}
	}
	
	public void calculatePhosphateRequirement() {
		
	}
	
	public void convertUnits() {
		if (convertUnitsBox.isChecked()) {
			calculateNitrogenRequirement();
			nitrogenRequirement.setText(formattedString(nitrogenReqValue));
			nitrogenUnits.setText("lb/ac");
			phosphateUnits.setText("lb/ac");
			potashUnits.setText("lb/ac");
		} else {
			calculateNitrogenRequirement();
			nitrogenRequirement.setText(formattedString(nitrogenReqValue));
			nitrogenUnits.setText("kg/ha");
			phosphateUnits.setText("kg/ha");
			potashUnits.setText("kg/ha");
		}						
	}
	
	private String formattedString (double d) {
		return String.format("%.1f", d);		
	}
    
}
