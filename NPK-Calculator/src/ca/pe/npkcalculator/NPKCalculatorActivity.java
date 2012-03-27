package ca.pe.npkcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NPKCalculatorActivity extends Activity {
    
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
     
        Spinner prevCropSpn = (Spinner) findViewById(R.id.prevCropSpn);        
        ArrayAdapter<CharSequence> prevCropAdapter = ArrayAdapter.createFromResource(this, R.array.prevCrops, android.R.layout.simple_spinner_item);
        prevCropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        prevCropSpn.setAdapter(prevCropAdapter);
        
        Spinner phosphateSpn = (Spinner) findViewById(R.id.phosphateSpn);        
        ArrayAdapter<CharSequence> phosphateAdapter = ArrayAdapter.createFromResource(this, R.array.soilTestRatings, android.R.layout.simple_spinner_item);
        phosphateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        phosphateSpn.setAdapter(phosphateAdapter);
        
        Spinner potashSpn = (Spinner) findViewById(R.id.potashSpn);        
        ArrayAdapter<CharSequence> potashAdapter = ArrayAdapter.createFromResource(this, R.array.soilTestRatings, android.R.layout.simple_spinner_item);
        potashAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        potashSpn.setAdapter(potashAdapter);
        
    }
}
