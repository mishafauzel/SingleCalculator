package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.exceptions.NumbersToBigForUniting;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Numbers;

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
        return currentState.addBranches();
    }

    @Override
    public InsertingValues changeSign() {
        return currentState.changeSign();
    }


    @Override
    public void calculateTreeSet() {

    }

    @Override
    public InsertingValues addDot() {
        return currentState.addDot();
    }


    @Override
    public void clearAll() {

    }

    @Override
    public InsertingValues delete() {
        return currentState.delete();
    }

    @Override
    public InsertingValues executePercentCalculation() {
        return currentState.executePercentCalculation();
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
    private void setCursorWithinNumberState()
    {
        currentState=cursorWithinNumber;
    }
    private void setCursorBetweenNumberAndActionState()
    {
        currentState=cursorBetweenNumberAndAction;
    }
    private void setCursorNearBranchesState()
    {
        currentState=cursorNearBranches;
    }
    private void setUserInputsIsEmtyState()
    {
        currentState=userInputIsEmpty;
    }

    public class CursorBetweenNumberAndAction extends CursorState implements CalculateInterface {
        @Override
        public InsertingValues addAction(ButtonsTag tag) {
             return new InsertingValues.Builder().build();
        }

        @Override
        public InsertingValues addDigits(ButtonsTag tag)  {
            Numbers number=(Numbers)getNumberFromClosestElements();
            if(number.increaseNumberOfDigits(1))
            {
                increaseCursorPosition(1);
                return createInsertingValuesBuilder(true,true,false, InsertingValues.StateOfInsertingValues.INSERTING,String.valueOf(tag),cursorPosition,null).build();
            }
            else{ return new InsertingValues.Builder().build();}
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
        public InsertingValues delete() {
            return null;
        }

        @Override
        public InsertingValues executePercentCalculation() {
            return null;
        }
    }
    public class CursorNearBranches extends CursorState implements CalculateInterface {
        @Override
        public InsertingValues addAction(ButtonsTag tag) {
            return null;
        }

        @Override
        public InsertingValues addDigits(ButtonsTag tag) {
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
        public InsertingValues delete() {
            return null;
        }

        @Override
        public InsertingValues executePercentCalculation() {
            return null;
        }
    }
    public class CursorWithinNumber extends CursorState implements CalculateInterface {
        @Override
        public InsertingValues addAction(ButtonsTag tag) {
           Numbers oldNumber=(Numbers) closestElements[0];
            if(oldNumber.getPosition()!=cursorPosition)
            {
                Action newAction=new Action(cursorPosition,String.valueOf(tag.getText()));
                Numbers newNumber=oldNumber.separateNumber(cursorPosition);
                newNumber.increasePosition(1);
                InsertingValues.Builder builder=new InsertingValues.Builder(true)
                        .setState(InsertingValues.StateOfInsertingValues.INSERTING)
                        .insertingPosition(cursorPosition)
                        .insertingString(String.valueOf(tag))
                        .addNewInsertingElement(newAction)
                        .addNewInsertingElement(newNumber);
                setCursorBetweenNumberAndActionState();
                CursorStateController.setClosestElements(newAction,newNumber);
                increaseCursorPosition(1);
                return builder.build();

            }
            else
            {
                InsertingValues.Builder builder=new InsertingValues.Builder(false);
                return builder.build();
            }
        }

        @Override
        public InsertingValues addDigits(ButtonsTag tag) {

            if(((Numbers)closestElements[0]).increaseNumberOfDigits(1));
            InsertingValues.Builder builder=new InsertingValues.Builder(true)
                    .setState(InsertingValues.StateOfInsertingValues.INSERTING)
                    .insertingPosition(cursorPosition)
                    .insertingString(String.valueOf(tag));
            increaseCursorPosition(1);
            return builder.build();
        }

        @Override
        public InsertingValues addBranches() {
            Numbers oldNumber=(Numbers)closestElements[0];
            if(oldNumber.getPosition()!=cursorPosition)
            {
                Branch newBranch;
                if(CursorStateController.hasUnclosedBranches())
                {

                }
            }
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
        public InsertingValues delete() {
            return null;
        }

        @Override
        public InsertingValues executePercentCalculation() {
            return false;
        }
    }
    public class UserInputIsEmpty extends CursorState implements CalculateInterface {
        @Override
        public InsertingValues addAction(ButtonsTag tag) {
            return new InsertingValues.Builder().build();
        }

        @Override
        public InsertingValues addDigits(ButtonsTag tag)
        {
            Numbers newElement=new Numbers(cursorPosition);
            newElement.setNumberOfDigits(1);
            InsertingValues.Builder builder=createInsertingValuesBuilder(true,true,true, InsertingValues.StateOfInsertingValues.INSERTING,
                    String.valueOf(tag),cursorPosition,newElement);
            CursorStateController.this.setCursorWithinNumberState();
            CursorStateController.setClosestElements(newElement);
            CursorStateController.this.increaseCursorPosition(1);
            return builder.build();



        }

        @Override
        public InsertingValues addBranches() {
            Branch newElement=new Branch(cursorPosition);
            newElement.setOpening(true);
            newElement.setClosed(false);
            branches.add(newElement);

            InsertingValues.Builder builder=createInsertingValuesBuilder(true,true,true, InsertingValues.StateOfInsertingValues.INSERTING,
                    "(",cursorPosition,newElement);
            CursorStateController.this.setCursorNearBranchesState();
            CursorStateController.setClosestElements(newElement);
            CursorStateController.this.increaseCursorPosition(1);
            return builder.build();



        }

        @Override
        public InsertingValues changeSign() {
            return new InsertingValues.Builder().build();
        }


        @Override
        public void calculateTreeSet() {

        }

        @Override
        public InsertingValues addDot() {
            return new InsertingValues.Builder().build();
        }


        @Override
        public void clearAll() {

        }

        @Override
        public InsertingValues delete() {
            return new InsertingValues.Builder().build();
        }

        @Override
        public InsertingValues executePercentCalculation() {
            return new InsertingValues.Builder().build();
        }
    }
    private InsertingValues.Builder createInsertingValuesBuilder(boolean hasNewInsertion, boolean hasNewStringInsertion, boolean newInsertingTreeSet, InsertingValues.StateOfInsertingValues stateOfInserting,String insertingString,int insertingPosition,ElementOfEquation... elementOfEquation)
    {
        if(hasNewInsertion) {
            InsertingValues.Builder builder = new InsertingValues.Builder(hasNewStringInsertion, newInsertingTreeSet, stateOfInserting);
            builder.setInsertingValues(insertingString,insertingPosition,elementOfEquation);
            return builder;
        }
        else  return new InsertingValues.Builder();
        }



}
