package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public class CursorStateController extends CursorState {


    EquationTreeSetManager treeSetManager;
    CursorState currentState;
    CursorState cursorBetweenNumberAndAction;
    CursorState cursorWithinNumber;
    CursorState cursorNearBranches;
    CursorState userInputIsEmpty;
    public CursorStateController() {
        cursorBetweenNumberAndAction=new CursorBetweenNumberAndAction();
        cursorWithinNumber=new CursorWithinNumber();
        cursorNearBranches=new CursorNearBranches();
        userInputIsEmpty=new UserInputIsEmpty();
        currentState=userInputIsEmpty;

    }

    @Override
    public boolean addAction(ButtonsTag tag) {
        return currentState.addAction(tag);
    }

    @Override
    public boolean addDigits(ButtonsTag tag) {
        return currentState.addDigits(tag);
    }

    @Override
    public boolean addBranches() {
        return currentState.addBranches();
    }

    @Override
    public boolean changeSign() {
        return currentState.changeSign();
    }
    public void defineCurrentState(ElementOfEquation[] nearestElements)
    {
        if(nearestElements[1]==null)
            setCursorWithinNumberState();
        else
        {

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
