package com.example.singlecalculator.utills.equation;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.Set;
import java.util.TreeSet;

public class EquationTreeSetManager {
    private TreeSet<ElementOfEquation> equationTreeSet = new TreeSet<>();
    public ElementOfEquation[] findNearestElements(int cursorPosition)
    {
        ElementOfEquation[] nearestElements=new ElementOfEquation[2];
        for (ElementOfEquation elementOfEquation :equationTreeSet
             ) {
          if(elementOfEquation.getPosition()<=cursorPosition)
          {
            if(elementOfEquation.getLastPosition()>=cursorPosition)
            {
                int insertingPosition=nearestElements[0]==null?0:1;
                nearestElements[insertingPosition]=elementOfEquation;
            }
          }
          else
              break;
        }
        return nearestElements;
    }
    public boolean addElementOfEquation(ElementOfEquation elementOfEquation)
    {
        return false;
    }
    private void increaseNextElementsPositions(ElementOfEquation elementOfEquation,int increasinSize)
    {
       Set<ElementOfEquation> tailSet= equationTreeSet.tailSet(elementOfEquation,true);
        for (ElementOfEquation element :
                tailSet) {
            element.increasePosition(increasinSize);
        }
    }
}
