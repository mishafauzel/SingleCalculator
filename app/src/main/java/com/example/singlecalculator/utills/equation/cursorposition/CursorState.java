package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.TreeSet;

public abstract class CursorState {
    protected static ElementOfEquation[] closestElements=new ElementOfEquation[2];
    protected static int cursorPosition;
    protected static int unclosedBranchNumber=0;
    protected static TreeSet<Branch> branches=new TreeSet<>();
    public static ElementOfEquation[] getClosestElements() {
        return closestElements;
    }
    protected static Branch getLa

    public static void setClosestElements(ElementOfEquation... elements) {
      closestElements[0]=elements.length==0?null:elements[0];
      closestElements[1]=elements.length==2?elements[1]:null;
    }
    public static boolean hasUnclosedBranches()
    {
        return unclosedBranchNumber==0;
    }
    public static void increaseNumberOfUnclosedBranches()
    {
        unclosedBranchNumber++;
    }
    public static void decreaseNumberOfUnclosedBranches()
    {
        unclosedBranchNumber=unclosedBranchNumber==0?0:unclosedBranchNumber++;
    }






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
