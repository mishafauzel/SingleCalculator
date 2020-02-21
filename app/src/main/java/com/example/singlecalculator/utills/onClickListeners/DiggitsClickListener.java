package com.example.singlecalculator.utills.onClickListeners;

import android.util.Log;
import android.view.View;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.Equation;
import com.example.singlecalculator.utills.calculations.Calculator;

public class DiggitsClickListener implements View.OnClickListener {
    private static final String TAG = "DiggitsClickListener";
    private Calculator calculator;
    private Equation equation;
    public DiggitsClickListener(Calculator calculator) {
        this.calculator=calculator;
    }
    public void setEquation(Equation equation) {
        this.equation = equation;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        Log.d(TAG, "onClick: "+view.getTag().toString());
        ButtonsTag tag=((ButtonsTag)view.getTag());
        switch ( tag.buttonType)
        {
            case digit:
                equation.addDigits(tag);
                break;
            case action:
                equation.addAction(tag);
                break;
            case delete:
                equation.clearAll();
                break;
            case equals:
                calculator.calculate(equation);
                break;
            case percent:
                equation.executePercentCalculation();
                break;
            case branches:
                equation.addBranches();
                break;
            case chengeSign:
                equation.changeSign();
                break;
            case dot:
                equation.addDot();
                break;

        }

    }
}
