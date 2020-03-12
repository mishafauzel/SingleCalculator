package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;

public class CursorNearBranches extends CursorState implements CalculateInterface {
    @Override
    public InsertingValues addAction(ButtonsTag tag) {
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
