package com.example.singlecalculator.utills.onClickListeners;

import android.util.Log;
import android.view.View;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.FirstPanelStrategy;
import com.example.singlecalculator.utills.equation.Equation;
import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;

public class DiggitsClickListener implements View.OnClickListener {
    private static final String TAG = "DiggitsClickListener";

    private Equation equation;
    private FirstPanelStrategy strategy;
    public void setStrategy(FirstPanelStrategy strategy)
    {
        this.strategy=strategy;
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
            case digit: {
                try {
                    equation.addDigits(tag);
                } catch (UserInputException ex) {
                    ex.printStackTrace();
                    strategy.handleUserInputException(ex);
                }
                break;
            }
            case action: {
                try {
                    equation.addAction(tag);
                }
                catch (UserInputException ex)
                {
                    ex.printStackTrace();
                    strategy.handleUserInputException(ex);
                }
                break;
            }
            case delete:
                equation.clearAll();
                break;
            case equals:
               equation.calculateTreeSet();
                break;
            case percent:
                try {
                    equation.executePercentCalculation();
                } catch (UserInputException e) {
                    e.printStackTrace();
                    strategy.handleUserInputException(e);
                }
                break;
            case branches:
                try {
                    equation.addBranches();
                } catch (UserInputException e) {
                    e.printStackTrace();
                    strategy.handleUserInputException(e);
                }
                break;
            case chengeSign:
                try {
                    equation.changeSign();
                } catch (UserInputException e) {
                    e.printStackTrace();
                    strategy.handleUserInputException(e);
                }
                break;
            case dot:
                try {
                    equation.addDot();
                } catch (UserInputException e) {
                    e.printStackTrace();
                    strategy.handleUserInputException(e);
                }
                break;

        }

    }
}
