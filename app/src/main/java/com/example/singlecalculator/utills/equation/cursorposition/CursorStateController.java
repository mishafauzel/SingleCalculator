package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public class CursorStateController extends CursorState implements CalculateInterface {


    CalculateInterface currentState;
    CalculateInterface cursorBetweenNumberAndAction;
    CalculateInterface cursorWithinNumber;
    CalculateInterface cursorNearBranches;
    CalculateInterface userInputIsEmpty;
    public CursorStateController() {
        cursorBetweenNumberAndAction=new CursorBetweenNumberAndAction();
        cursorWithinNumber=new CursorWithinNumber();
        cursorNearBranches=new CursorNearBranches();
        userInputIsEmpty=new UserInputIsEmpty();
        currentState=userInputIsEmpty;

    }

    @Override
    public InsertingValues addAction(ButtonsTag tag) {
        return currentState.addAction(tag);
    }

    @Override
    public InsertingValues addDigits(ButtonsTag tag) {
        return currentState.addDigits(tag);
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

    public void defineCurrentState(ElementOfEquation[]nearestElements)
    {

        if(nearestElements[1]==null)
        {
            switch (nearestElements[0].getType())
            {
                case Branch: {
                    setCursorNearBranchesState();
                    break;
                }
                case Number:
                {
                    setCursorWithinNumberState();
                    break;
                }
            }
        }
        else
        {
            if(nearestElements[0].getType()== ElementOfEquation.TypeOfElement.Action||nearestElements[1].getType()== ElementOfEquation.TypeOfElement.Action)
                setCursorBetweenNumberAndActionState();
            else
                setCursorNearBranchesState();
        }
    }
    public void setCursorWithinNumberState()
    {
        currentState=cursorWithinNumber;
    }
    public void setCursorBetweenNumberAndActionState()
    {
        currentState=cursorBetweenNumberAndAction;
    }
    public void setCursorNearBranchesState()
    {
        currentState=cursorNearBranches;
    }
    public void setUserInputsIsEmtyState()
    {
        currentState=userInputIsEmpty;
    }
}
