package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Numbers;

public class CursorWithinNumber extends CursorState implements CalculateInterface {
    @Override
    public InsertingValues addAction(ButtonsTag tag) {
        Numbers newNumber=((Numbers)closestElements[0]).separateNumber(cursorPosition);
        newNumber.increasePosition(1);
        Action action=new Action(cursorPosition,String.valueOf(tag.getText()));
        return null;
    }

    @Override
    public InsertingValues addDigits(ButtonsTag tag) {
        return null;
    }

    @Override
    public InsertingValues addBranches() {
        return null;
    }

    @Override
    public InsertingValues changeSign() {
        return null;
    }


    @Override
    public void calculateTreeSet() {

    }

    @Override
    public InsertingValues addDot() {
        return null;
    }


    @Override
    public void clearAll() {

    }

    @Override
    public boolean executePercentCalculation() {
        return false;
    }
}
