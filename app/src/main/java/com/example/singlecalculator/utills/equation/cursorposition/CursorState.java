package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public abstract class CursorState {


    protected static ElementOfEquation[] closestElements=new ElementOfEquation[2];
    protected static int cursorPosition;


    public abstract boolean addAction(ButtonsTag tag);
    public abstract boolean addDigits(ButtonsTag tag);
    public abstract boolean addBranches();
    public abstract boolean changeSign();
    public static int getCursorPosition() {
        return cursorPosition;
    }

    public static void setCursorPosition(int cursorPosition) {
        CursorState.cursorPosition = cursorPosition;
    }

}
