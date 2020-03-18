package com.example.singlecalculator.utills.strategiesInterdaces;

import android.widget.EditText;
import android.widget.TextView;

import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;

public interface StrategyOwner {
    TextView[] getButtons(int[] idOfButtons);
    EditText getEquation();
    void showErrorToast(UserInputException exception);
}
