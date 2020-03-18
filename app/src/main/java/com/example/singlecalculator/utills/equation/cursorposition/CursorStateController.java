package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.actions.InsertingActions;
import com.example.singlecalculator.utills.equation.exceptions.UserInputExceptionsFactory;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Number;

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
    public ActionsResult addAction(ButtonsTag tag)  {
        return currentState.addAction(tag);
    }

    @Override
    public ActionsResult addDigits(ButtonsTag tag)
    {
        return currentState.addDigits(tag);
    }

    @Override
    public ActionsResult addBranches()  {
        return currentState.addBranches();
    }

    @Override
    public ActionsResult changeSign()  {
        return currentState.changeSign();
    }


    @Override
    public void calculateTreeSet() {

    }

    @Override
    public ActionsResult addDot()  {
        return currentState.addDot();
    }


    @Override
    public void clearAll() {

    }

    @Override
    public ActionsResult delete() {
        return currentState.delete();
    }

    @Override
    public ActionsResult executePercentCalculation()  {
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
        public ActionsResult addAction(ButtonsTag tag) {
             return new ActionsResult.Builder().build();
        }

        @Override
        public ActionsResult addDigits(ButtonsTag tag)  {
            Number number=(Number)getNumberFromClosestElements();
            if(number.increaseNumberOfDigits(1))
            {
                increaseCursorPosition(1);
                return createInsertingValuesBuilder(true,true,false, ActionsResult.StateOfInsertingValues.INSERTING,String.valueOf(tag),cursorPosition,null).build();
            }
            else{ return new ActionsResult.Builder().build();}
        }

        @Override
        public ActionsResult addBranches() {
            return null;
        }

        @Override
        public ActionsResult changeSign() {
            return null;
        }

        @Override
        public void calculateTreeSet() {

        }

        @Override
        public ActionsResult addDot() {
            return null;
        }

        @Override
        public void clearAll() {

        }

        @Override
        public ActionsResult delete() {
            return null;
        }

        @Override
        public ActionsResult executePercentCalculation() {
            return null;
        }
    }
    public class CursorNearBranches extends CursorState implements CalculateInterface {
        @Override
        public ActionsResult addAction(ButtonsTag tag) {
            return null;
        }

        @Override
        public ActionsResult addDigits(ButtonsTag tag) {
            return null;
        }

        @Override
        public ActionsResult addBranches() {
            return null;
        }

        @Override
        public ActionsResult changeSign() {
            return null;
        }


        @Override
        public void calculateTreeSet() {

        }

        @Override
        public ActionsResult addDot() {
            return null;
        }


        @Override
        public void clearAll() {

        }

        @Override
        public ActionsResult delete() {
            return null;
        }

        @Override
        public ActionsResult executePercentCalculation() {
            return null;
        }
    }
    public class CursorWithinNumber extends CursorState implements CalculateInterface {
        @Override
        public ActionsResult addAction(ButtonsTag tag) {
           Number oldNumber=(Number) closestElements[0];
            if(oldNumber.getPosition()!=cursorPosition)
            {
                Action newAction=new Action(cursorPosition,String.valueOf(tag.getText()));
                Number newNumber=oldNumber.separateNumber(cursorPosition);
                newNumber.increasePosition(1);
                ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().addElement(newAction).addElement(newNumber)
                        .setString(String.valueOf(tag.getText()),cursorPosition);
                setCursorBetweenNumberAndActionState();
                CursorStateController.setClosestElements(newAction,newNumber);
                increaseCursorPosition(1);
                return builder.build();

            }
            else
            {
                ActionsResult.Builder builder=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                        .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName()));
                return builder.build();
            }
        }

        @Override
        public ActionsResult addDigits(ButtonsTag tag) {

            if(((Number)closestElements[0]).increaseNumberOfDigits(1));
            ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString(String.valueOf(tag),cursorPosition);
            increaseCursorPosition(1);
            return builder.build();
        }

        @Override
        public ActionsResult addBranches() {
            if(cursorPosition!=closestElements[0].getPosition())
            {
                if(unclosedBranchNumber==0)
                {
                    Branch newBranch=new Branch(cursorPosition,true);
                    Action action=new Action(cursorPosition-1,"X");
                    Number newNumber=((Number)closestElements[0]).separateNumber(cursorPosition);
                    newNumber.increasePosition(2);
                    ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString("X(",cursorPosition)
                    .addElement(newBranch).addElement(action).addElement(newNumber);
                    increaseNumberOfUnclosedBranch();
                    increaseCursorPosition(2);
                    return builder.build();

                }
                else
                    {
                    Branch branchRequiredClosed;
                    if((branchRequiredClosed = getLastUnclosedBranch(cursorPosition))!=null)
                        {
                        Branch closedBranch=new Branch(cursorPosition,false);
                        branchRequiredClosed.setClosed(true);
                        branchRequiredClosed.setPairBranch(closedBranch);
                        Action action=new Action(cursorPosition+1,"X");
                        Number newNumber=((Number)closestElements[0]).separateNumber(cursorPosition);
                        newNumber.increasePosition(2);
                        ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString(")X",cursorPosition)
                                .addElement(closedBranch).addElement(action).addElement(newNumber);
                        increaseCursorPosition(2);
                        decreaseNumberOfUnclosedBranches();
                        return builder.build();
                        }
                    else
                        {
                            Branch oldBranch= findSmallestPairOfBranchArrounCursor(cursorPosition);
                            Branch newBranchPair=new Branch(cursorPosition,true);
                            newBranchPair.setPairBranch(oldBranch.getPairBranch());
                            oldBranch.setPairBranch(null);
                            Action newAction=new Action(cursorPosition-1,"X");
                            Number newNumber=((Number)closestElements[0]).separateNumber(cursorPosition);
                            ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString("X(",cursorPosition)
                               .addElement(newBranchPair).addElement(newAction).addElement(newNumber);
                            increaseCursorPosition(2);
                            setClosestElements(newBranchPair,newNumber);
                            setCursorNearBranchesState();
                            increaseNumberOfUnclosedBranch();
                            return builder.build();



                        }
                    }
            }
            else
            {
                Branch newBranch=new Branch(cursorPosition);
                InsertingActions.InsertingBuilder insertingBuilder=ActionsResult.Builder.createInsertingBuilder().setString("(",cursorPosition).addElement(newBranch);
                increaseNumberOfUnclosedBranch();
                increaseCursorPosition(1);
                return insertingBuilder.build();
            }

        }

        @Override
        public ActionsResult changeSign() {
            if(((Number)closestElements[0]).isMinus())
            return null;
        }


        @Override
        public void calculateTreeSet() {

        }

        @Override
        public ActionsResult addDot() {
            Number number = (Number) closestElements[0];
            if (number.isHasDot())
                return ActionsResult.Builder.createActionWithErrorBuilder()
                        .setException(UserInputExceptionsFactory.getNumberAlreadyHasDot()).build();
            else {
                {
                    String insertionInEditText = ".";
                    if (cursorPosition == number.getPosition()) {
                        if (!(number.increaseNumberOfDigits(1)))
                            return ActionsResult.Builder.createActionWithErrorBuilder()
                                    .setException(UserInputExceptionsFactory.getNumberToBigForInsertion()).build();

                        else {
                            insertionInEditText = "0.";

                        }
                    }

                    number.defineCursorPositionRelativeToStartPosition(cursorPosition);
                    number.setDotPosition(cursorPosition);
                    number.setHasDot(true);
                    return ActionsResult.Builder.createInsertingBuilder().setString(insertionInEditText,cursorPosition).build();


                }

            }
        }



        @Override
        public void clearAll() {

        }

        @Override
        public ActionsResult delete() {
            return null;
        }

        @Override
        public ActionsResult executePercentCalculation() {
            Number number=(Number)closestElements[0];
            if(number.isHasDot())
            {

            }
            else
            {
                number.setHasDot(true);
                if(number.getNumberOfDigits()>=3)
                {
                    int positionOfInsertingDot=number.getLastPosition()-1;
                    return ActionsResult.Builder.createInsertingBuilder().setString(".",positionOfInsertingDot).build();
                }
                else
                {
                    number.increaseNumberOfDigits(3-number.getNumberOfDigits());

                }
            }
            return false;
        }
    }
    public class UserInputIsEmpty extends CursorState implements CalculateInterface {
        @Override
        public ActionsResult addAction(ButtonsTag tag) {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }

        @Override
        public ActionsResult addDigits(ButtonsTag tag)
        {
            Number newElement=new Number(cursorPosition);
            newElement.setNumberOfDigits(1);
            ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().addElement(newElement)
                    .setString(String.valueOf(tag.getText()),cursorPosition);
            CursorStateController.this.setCursorWithinNumberState();
            CursorStateController.setClosestElements(newElement);
            CursorStateController.this.increaseCursorPosition(1);
            return builder.build();



        }

        @Override
        public ActionsResult addBranches() {
            Branch newElement=new Branch(cursorPosition);
            newElement.setOpening(true);
            newElement.setClosed(false);
            branches.add(newElement);
            ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString("(",cursorPosition)
                    .addElement(newElement);
            CursorStateController.this.setCursorNearBranchesState();
            CursorStateController.setClosestElements(newElement);
            CursorStateController.this.increaseCursorPosition(1);
            return builder.build();



        }

        @Override
        public ActionsResult changeSign() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }


        @Override
        public void calculateTreeSet() {

        }

        @Override
        public ActionsResult addDot() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }


        @Override
        public void clearAll() {

        }

        @Override
        public ActionsResult delete() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }

        @Override
        public ActionsResult executePercentCalculation() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }
    }




}
