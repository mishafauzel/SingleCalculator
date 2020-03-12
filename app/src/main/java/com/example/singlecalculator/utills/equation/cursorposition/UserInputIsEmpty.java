package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;
import com.example.singlecalculator.utills.equation.utills.Numbers;

public class UserInputIsEmpty extends CursorState implements CalculateInterface {
    @Override
    public InsertingValues addAction(ButtonsTag tag) {
        return null;
    }

    @Override
    public InsertingValues addDigits(ButtonsTag tag)
    {
        Numbers newNumber=new Numbers(0);
        InsertingValues insertingValue=new InsertingValues();
        insertingValue.newInsertion=true;
        insertingValue.insertingPosition=0;
        insertingValue.insertingString=String.valueOf(tag.getText());
        insertingValue.element=newNumber;

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
