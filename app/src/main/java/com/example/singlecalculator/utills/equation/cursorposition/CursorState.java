package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public abstract class CursorState {

    public enum statesOfCursor{UserInputIsEmpty,CursorBetweenNumberAndAction,CursorNearBranches,CursorWithinNumber}
    protected static ElementOfEquation[] closestElements=new ElementOfEquation[2];
    protected static int cursorPosition;



    public static int getCursorPosition() {
        return cursorPosition;
    }

    public static void setCursorPosition(int cursorPosition) {
        CursorState.cursorPosition = cursorPosition;
    }

    public void increaseCursorPosition(int i) {
        cursorPosition=cursorPosition+i;
    }
}
