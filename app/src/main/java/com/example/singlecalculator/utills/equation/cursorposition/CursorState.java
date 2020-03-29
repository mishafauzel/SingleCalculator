package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.TreeSet;

public abstract class CursorState {
    protected static ElementOfEquation[] closestElements=new ElementOfEquation[2];


    public static ElementOfEquation[] getClosestElements() {
        return closestElements;
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







}
