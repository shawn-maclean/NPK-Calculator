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
	Double nitrogenReqValue, cropLengthModifier, organicMatterModifier, plantingDateModifier, cropStandModifier;
	EditText manureAmount;
	RadioGroup cropLengthRadioGroup, organicMatterRadioGroup, cropStandRadioGroup;
	Spinner cropSelectionSpn, prevCropSpn, phosphateSpn, potashSpn, manureTypeSpn;
	TextView manureTypePrompt, manureAmountPrompt, nitrogenRequirement, phosphateRequirement, potashRequirement, 
			 nitrogenUnits, phosphateUnits, potashUnits;
	Boolean convertUnitsBln = false;
	
	int checkedCropLengthBtn, checkedCropStandBtn, checkedOrganicMatterBtn;
	int selectedCrop, selectedPrevCrop, selectedBaseN, baseValueN;
	
	int[] baseValueNitrogen = {50, 40, 60, 50, 150, 150, 35, 150, 100, 150, 150, 60, 150, 35, 130, 120, 130, 60, 75, 50, 20, 20, 100,
							  110, 75, 75, 20, 20, 20, 20, 150, 75, 40, 50, 50, 30, 120, 100, 80, 155, 155, 155, 155, 155,
							  155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 155, 80,
							  60, 135, 150, 45, 50, 20, 100, 80, 150, 100, 60, 60, 20};
	
	int[][] barleyPK = {{100, 75, 50, 50, 25, 25}, {100, 100, 75, 50, 25, 0}};
	int[][] beanPK = {{100, 60, 45, 45, 30, 0}, {100, 75, 50, 40, 25, 0}};
	int[][] beetPK = {{300, 200, 150, 125, 100, 50}, {225, 175, 125, 100, 75, 25}};
			
	double[] poorStand = {0, 0, 0, 8.9, 0};
	double[] fairStand = {35.6, 17.8, 8.9, 8.9, 0};
	double[] goodStand = {71.2, 35.6, 17.8, 8.9, -13.35};	
	    
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
			selectedCrop = cropSelectionSpn.getSelectedItemPosition();
			calculateNitrogenRequirement();
			calculatePhosphateRequirement();
			calculatePotashRequirement();
		}
		
		if (v == convertUnitsBox) {
			convertUnits();
		}
		
	}
	
	public void calculateNitrogenRequirement() {
		getCropLength();
		getPlantingDate();
		getCropStand();
		getOrganicMatter();
		
		baseValueN = baseValueNitrogen[selectedCrop];
		
		nitrogenReqValue = baseValueN * cropLengthModifier - plantingDateModifier - cropStandModifier - organicMatterModifier;
				
		if (convertUnitsBox.isChecked())
			nitrogenReqValue = nitrogenReqValue / 0.89;			
				
		nitrogenRequirement.setText(formattedString(nitrogenReqValue));		
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
			plantingDateModifier = 0.0;
		} else if (cropSownDatePicker.getMonth() >= 5 && cropSownDatePicker.getDayOfMonth() >= 9) {
			plantingDateModifier = 29.37;
		} else if (cropSownDatePicker.getMonth() == 5) {
			if (cropSownDatePicker.getDayOfMonth() >= 2 && cropSownDatePicker.getDayOfMonth() <= 8)	{
				plantingDateModifier = 19.58;
			} else plantingDateModifier = 9.79;
		} else if (cropSownDatePicker.getMonth() == 4) {
			if (cropSownDatePicker.getDayOfMonth() >= 26)
				plantingDateModifier = 9.79;
		} else plantingDateModifier = 0.0;
	}
	
	public void getCropStand() {		
		switch (cropStandRadioGroup.getCheckedRadioButtonId()) {
		  case R.id.poorBtn : cropStandModifier = poorStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  case R.id.fairBtn : cropStandModifier = fairStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  case R.id.goodBtn : cropStandModifier = goodStand[prevCropSpn.getSelectedItemPosition()];
		  	break;
		  default : cropStandModifier = 0.0;
		  	break;
		}		
	}
	
	public void getOrganicMatter() {		
		switch (organicMatterRadioGroup.getCheckedRadioButtonId()) {
		  case R.id.lessOrganicBtn : organicMatterModifier = 0.0;
		  	break;
		  case R.id.moreOrganicBtn : organicMatterModifier = 13.35;
		  	break;
		  default : organicMatterModifier = 0.0;
		  	break;
		}
	}

	private void calculatePhosphateRequirement() {	
		double phosphateReqValue = getPhosphate();
		
		if (convertUnitsBox.isChecked())
			phosphateReqValue = phosphateReqValue / 0.89;			
				
		phosphateRequirement.setText(formattedString(phosphateReqValue));
		
	}
	
	private double getPhosphate() {
		double phosphateValue = 0;
		
		switch (selectedCrop) {
		case 0: phosphateValue = (double) barleyPK[0][phosphateSpn.getSelectedItemPosition()];
			break;
		case 1: phosphateValue = (double) beanPK[0][phosphateSpn.getSelectedItemPosition()];
			break;
		case 2: phosphateValue = (double) beetPK[0][phosphateSpn.getSelectedItemPosition()];
			break;
		}
		
		return phosphateValue;
	}
	
	private void calculatePotashRequirement() {
		double potashReqValue = getPotash();
		
		if (convertUnitsBox.isChecked())
			potashReqValue = potashReqValue / 0.89;
		
		potashRequirement.setText(formattedString(potashReqValue));
	}
	
	private double getPotash() {
		double potash = 0;
		
		switch (selectedCrop) {
		case 0: potash = (double) barleyPK[1][potashSpn.getSelectedItemPosition()];
			break;
		case 1: potash = (double) beanPK[1][potashSpn.getSelectedItemPosition()];
			break;
		case 2: potash = (double) beetPK[1][potashSpn.getSelectedItemPosition()];
			break;
		}
		
		return potash;
	}
	
	private void convertUnits() {
		if (convertUnitsBox.isChecked()) {
			calculateNitrogenRequirement();
			calculatePhosphateRequirement();
			calculatePotashRequirement();
			nitrogenRequirement.setText(formattedString(nitrogenReqValue));
			nitrogenUnits.setText("kg/ha");
			phosphateUnits.setText("kg/ha");
			potashUnits.setText("kg/ha");			
		} else {
			calculateNitrogenRequirement();
			calculatePhosphateRequirement();
			calculatePotashRequirement();
			nitrogenRequirement.setText(formattedString(nitrogenReqValue));
			nitrogenUnits.setText("lb/ac");
			phosphateUnits.setText("lb/ac");
			potashUnits.setText("lb/ac");
		}						
	}
	
	private String formattedString (double d) {
		return String.format("%.1f", d);		
	}
    
}
