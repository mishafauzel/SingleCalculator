package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.utills.Action;
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
    protected  Branch getLastUnclosedBranch(int cursorPosition)
    {
        Branch previousBranch=null;
        for (Branch branch :
                branches) {
            if (branch.getPosition() < cursorPosition)
            if(branch.isClosed())
                previousBranch=branch;
            if(branch.getPosition()>cursorPosition)
                break;
            }
        return previousBranch;
    }
    protected Branch  findSmallestPairOfBranchArrounCursor(int cursorPosition)
    {


        Branch closestClosedPair=null;
        for (Branch branch :
                branches) {
            if (branch.isClosed())
                if (branch.getPairBranch().getPosition() > cursorPosition) {
                    closestClosedPair = branch;
                }
            if (branch.getPosition() > cursorPosition)
                break;

        }
        return closestClosedPair;

    }
    protected void insertNewBranch(Branch branch)
    {
        branches.add(branch);
        increaseNumberOfUnclosedBranches();

    }


    protected static void setClosestElements(ElementOfEquation... elements) {
      closestElements[0]=elements.length==0?null:elements[0];
      closestElements[1]=elements.length==2?elements[1]:null;
    }
    protected ElementOfEquation getNumberFromClosestElements()
    {
        for (ElementOfEquation number :
                closestElements) {
            if (number.getType() == ElementOfEquation.TypeOfElement.Number)
                return number;
        }
        return null;
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
