package com.example.singlecalculator.utills.equation;

import androidx.annotation.NonNull;

import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class EquationTreeSetManager implements Iterable<ElementOfEquation> {
    private TreeSet<ElementOfEquation> equationTreeSet = new TreeSet<>();
    private boolean isAscending=true;
    @NonNull
    @Override
    public Iterator<ElementOfEquation> iterator() {
        Iterator<ElementOfEquation> retIterator=equationTreeSet.iterator();
        if(!isAscending)
            retIterator=equationTreeSet.descendingIterator();
        return retIterator;
    }
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


    public void clearAll()
    {
        equationTreeSet.clear();
    }
    public ActionsResult[] checkTreeSetElementsCorrect()
    {
        boolean hasPreviousDeletion=false;
        int deletingSize=0;
        ArrayList<ActionsResult> arrayListOfActionsResult=new ArrayList<>();
        ElementOfEquation previousElement=null;
        isAscending=true;
        for (ElementOfEquation element :
                this) {
            if(previousElement==null)
            {previousElement=element;
            continue;}
            if(hasPreviousDeletion)
            {
                element.increasePosition(deletingSize);
            }
            switch(element.getType())
            {
                case Action:
                {
                    if(previousElement.getType() == ElementOfEquation.TypeOfElement.Action)
                    {
                        arrayListOfActionsResult.add(ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(previousElement.getPosition()).setToPosition(previousElement.getLastPosition()).build());
                        equationTreeSet.remove(previousElement);
                        hasPreviousDeletion=true;
                        deletingSize--;
                    }
                    if(previousElement.getType() == ElementOfEquation.TypeOfElement.Branch && ((Branch)previousElement).isOpening())
                    {
                        arrayListOfActionsResult.add(ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(element.getPosition()).setToPosition(element.getLastPosition()).build());
                        equationTreeSet.remove(element);
                        hasPreviousDeletion=true;
                        deletingSize--;
                    }
                }
            }
        }
        return arrayListOfActionsResult.toArray(new ActionsResult[arrayListOfActionsResult.size()]);
    }

    public void deleteElements(ElementOfEquation[] elementsOfEquation) {

        equationTreeSet.removeAll(Arrays.asList(elementsOfEquation));
        int[] dataForIncreasing=calculateMinPositionAndSizeChange(elementsOfEquation,false);
        increasePositionOfElements(dataForIncreasing[0],dataForIncreasing[1]);
    }

    public void insertNewValueToTreeSet(ElementOfEquation[] elementsOfEquation) {
        int[] dataForIncreasing=calculateMinPositionAndSizeChange(elementsOfEquation,false);
        increasePositionOfElements(dataForIncreasing[0],dataForIncreasing[1]);
        equationTreeSet.addAll(Arrays.asList(elementsOfEquation));

    }

    public void increasePositionOfElements(int insertingPosition, int sizeOfIncreasing) {
        isAscending=false;
        for (ElementOfEquation element :
                this) {
            if (element.getPosition() > insertingPosition)
                element.increasePosition(sizeOfIncreasing);
            else break;
        }
    }
    private int[] calculateMinPositionAndSizeChange(ElementOfEquation[] elementsOfEquation,boolean isInserting)
    {
        int minPos=101;
        int sizeOfChange=0;
        int multiplier=isInserting?1:-1;
        for (ElementOfEquation element :
                elementsOfEquation) {
            minPos=minPos>element.getPosition()?element.getPosition():minPos;
            sizeOfChange=sizeOfChange+element.getSizeOfStringRepresentation()*multiplier;

        }
        return new int[]{minPos,sizeOfChange};
    }
}
