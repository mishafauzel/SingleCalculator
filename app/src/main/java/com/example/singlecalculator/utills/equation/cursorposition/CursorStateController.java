package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
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
            return new InsertingValues.Builder(false).build();
        }

        @Override
        public InsertingValues addDigits(ButtonsTag tag)
        {
            Numbers newNumber=new Numbers(cursorPosition);
            newNumber.setNumberOfDigits(1);
            InsertingValues.Builder builder=new InsertingValues.Builder(true).setState(InsertingValues.StateOfInsertingValues.INSERTING)
            .insertingPosition(0)
            .addNewInsertingElement(newNumber)
            .insertingString(String.valueOf(tag.getText()));
            CursorStateController.this.setCursorWithinNumberState();
            CursorStateController.setClosestElements(newNumber);
            CursorStateController.this.increaseCursorPosition(1);
            return builder.build();



        }

        @Override
        public InsertingValues addBranches() {
            Branch newBranch=new Branch(cursorPosition);
            newBranch.setOpening(true);
            newBranch.setClosed(false);
            branches.add(newBranch);
            InsertingValues.Builder builder=new InsertingValues.Builder(true)
            .insertingPosition(0).setState(InsertingValues.StateOfInsertingValues.INSERTING)
                    .addNewInsertingElement(newBranch)
                    .insertingString("(");
            CursorStateController.setClosestElements(newBranch);
            CursorStateController.this.increaseCursorPosition(1);
            CursorStateController.this.setCursorNearBranchesState();
            return builder.build();
        }

        @Override
        public InsertingValues changeSign() {
            return new InsertingValues.Builder(false).build();
        }


        @Override
        public void calculateTreeSet() {

        }

        @Override
        public InsertingValues addDot() {
            return new InsertingValues.Builder(false).build();
        }


        @Override
        public void clearAll() {

        }

        @Override
        public InsertingValues delete() {
            return new InsertingValues.Builder(false).build();
        }

        @Override
        public InsertingValues executePercentCalculation() {
            return new InsertingValues.Builder(false).build();
        }
    }



}
