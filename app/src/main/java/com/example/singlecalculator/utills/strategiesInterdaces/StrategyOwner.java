package com.example.singlecalculator.utills.strategiesInterdaces;

import android.widget.EditText;
import android.widget.TextView;

import com.example.singlecalculator.utills.calculations.Calculator;

public interface StrategyOwner {
    TextView[] getButtons(int[] idOfButtons);
    EditText getEquation();
}
