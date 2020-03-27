package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.actions.DeletingActions;
import com.example.singlecalculator.utills.equation.actions.InsertingActions;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
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
    public ActionsResult addDot()  {
        return currentState.addDot();
    }




    @Override
    public ActionsResult delete() {
        return currentState.delete();
    }

    @Override
    public ActionsResult[] executePercentCalculation()  {
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
            if(nearestElements[0].getType()== ElementOfEquation.TypeOfElement.Branch||nearestElements[1].getType()== ElementOfEquation.TypeOfElement.Branch)
                setCursorNearBranchesState();
            else
            setCursorBetweenNumberAndActionState();

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
             return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build());
        }

        @Override
        public ActionsResult addDigits(ButtonsTag tag)  {
            Number number=(Number)getNumberFromClosestElements();
            if(number.increaseNumberOfDigits(1))
            {
                ActionsResult result=ActionsResult.Builder.createInsertingBuilder().setString(String.valueOf(tag.getText()),cursorPosition).build();

                increaseCursorPosition(1);
                if(closestElements[1].getType()== ElementOfEquation.TypeOfElement.Number)
                    setCursorWithinNumberState();
                return result;
            }
            else{ return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getNumberToBigForInsertion()).build()}
        }

        @Override
        public ActionsResult addBranches() {
            Branch branch=null;

            Branch openingBranch;
            if(closestElements[0].getType()== ElementOfEquation.TypeOfElement.Action) {
                branch = new Branch(cursorPosition);
                branch.setOpening(true);
                Branch oldBranchesPair=findSmallestPairOfBranchArrounCursor(cursorPosition);
                if(oldBranchesPair!=null) {
                    branch.setPairBranch(oldBranchesPair.getPairBranch());
                    branch.getPairBranch().setPairBranch(branch);
                    oldBranchesPair.setPairBranch(null);
                }
                }
            else {
                 openingBranch= getLastUnclosedBranch(cursorPosition);
                 if(openingBranch != null) {
                     branch=new Branch(cursorPosition);
                     branch.setOpening(false);
                     branch.setPairBranch(openingBranch);
                     openingBranch.setPairBranch(branch);
                 }
                 }
            ActionsResult actionsResult;
            if(branch!=null)
            {
                String addingString=branch.isOpening()?"(":")";
                actionsResult=ActionsResult.Builder.createInsertingBuilder().setString(addingString,cursorPosition).addElement(branch).build();
                increaseCursorPosition(addingString.length());
            }
            actionsResult=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.AddingBranchImpossibleInThisPosition()).build();
                return actionsResult;
        }

        @Override
        public ActionsResult changeSign() {
            Number number=(Number)getNumberFromClosestElements();
            Branch branch;
            ActionsResult result;
            number.setMinus(!number.isMinus());
            if(number.isMinus())
            {
                branch=new Branch(number.getPosition());
                branch.setOpening(true);
                Branch oldBranchPair=findSmallestPairOfBranchArrounCursor(branch.getPosition());
                branch.setPairBranch(oldBranchPair.getPairBranch());
                branch.getPairBranch().setPairBranch(branch);
                branch.setClosed(true);
                oldBranchPair.setPairBranch(nu);
                number.increasePosition(1);
                result=ActionsResult.Builder.createInsertingBuilder().setString("-",number.getPosition()).build();
            }


            return null;
        }



        @Override
        public ActionsResult addDot() {
            return null;
        }



        @Override
        public ActionsResult delete() {
            return null;
        }

        @Override
        public ActionsResult[] executePercentCalculation() {
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
            if(oldNumber.defineFirstDigitPositionRelativeToStartOfString()!=cursorPosition)
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
            Number closestNumber = (Number)closestElements[0];
            if(cursorPosition != closestNumber.getFirstDigitPosition())
            {
                if(unclosedBranchNumber==0)
                {
                    Branch newBranch=new Branch(cursorPosition,true);
                    Action action=new Action(cursorPosition-1,"X");
                    Number newNumber=((Number)closestElements[0]).separateNumber(cursorPosition);
                    newNumber.increasePosition(2);
                    ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString("X(",cursorPosition)
                    .addElement(newBranch).addElement(action).addElement(newNumber);
                    insertNewBranch(newBranch);
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
                            insertNewBranch(newBranchPair);
                            return builder.build();



                        }
                    }
            }
            else
            {
                Branch newBranch=new Branch(cursorPosition);
                InsertingActions.InsertingBuilder insertingBuilder=ActionsResult.Builder.createInsertingBuilder().setString("(",cursorPosition).addElement(newBranch);
                insertNewBranch(newBranch);
                increaseCursorPosition(1);
                return insertingBuilder.build();
            }

        }

        @Override
        public ActionsResult changeSign() {
            Number number=(Number)closestElements[0];
            ActionsResult actionResult;
            if(number.isMinus())
            {
                actionResult=ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(number.getPosition()).
                        setToPosition(number.defineFirstDigitPositionRelativeToStartOfString()).build();
            }
            else
            {
               actionResult=ActionsResult.Builder.createInsertingBuilder().setString("-",number.getPosition()).build();
            }
            number.setMinus(!number.isMinus());
            return actionResult;
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
                    if (cursorPosition == number.getFirstDigitPosition()) {
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
        public ActionsResult delete() {
            Number number=(Number)closestElements[0];
            Number.PreviousSymbol previousSymbol=number.defineSymbolBeforeCursor(cursorPosition);
            ActionsResult result=null;
            switch (previousSymbol)
            {
                case sign:
                {

                    result=ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(number.getPosition()).setToPosition(number.getFirstDigitPosition()).build();
                    number.setMinus(false);
                    break;
                }
                case digit:
                {
                   DeletingActions.DeletingActionsBuilder resultBuilder=ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(cursorPosition-1).setToPosition(cursorPosition);
                    boolean hasRemainingDigits=number.decreaseNumberOfDiggits(1);
                    if(!hasRemainingDigits)
                        resultBuilder.addDeletingElement(number);
                    result=resultBuilder.build();
                    break ;
                }
                case dot:
                {
                    result=ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(number.getDotPosition()).
                            setToPosition(number.getDotPosition()+1).build();
                    number.setHasDot(false);
                    number.setDotPosition(-1);
                    break;
                }

            }
            increaseCursorPosition(-1);
            return result;
        }

        /**
         * executing by moving dot position to left
         * @return actions which will be executed to keep String and TreeSet representation of equation the same
         */
        @Override
        public ActionsResult[] executePercentCalculation() {
            Number number=(Number)closestElements[0];
            /**
             * if number has dot, just move it to left, and add digits if required
             */
            if(number.isHasDot())
            {
                /**
                 * number has dot and has enough digits for moving
                 */
                if(number.getDotPosition()>2)
                {
                    number.moveDotPosition(2);
                    int dotPosition;
                    return new ActionsResult[]{ActionsResult.Builder.createMovingActionBuilder().setFromPosition(dotPosition=number.calculateDotPosRelativeToStartOfString()).
                            setToPosition(dotPosition-2).build()};
                }
                /**
                 * number has dot but has not enough digits for moving
                 */
                else
                {
                    int oldNumberOfDiggits=number.getNumberOfDigits();
                    int numberOfAddingDigits=3-number.getDotPosition();
                    /**
                     * check that number will not exceed maximum size after inserting,return ActionResult with error if number exceed
                     */
                    if(!number.increaseNumberOfDigits(numberOfAddingDigits))
                    {
                     return new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getNumberToBigForInsertion()).build()};
                    }
                    /**
                     * add digits to number
                     */
                    else
                    {
                        number.moveDotPosition(numberOfAddingDigits);
                        int newDotPosition=number.calculateDotPosRelativeToStartOfString();
                        StringBuilder addingInString=new StringBuilder();
                        for(int i=0;i<numberOfAddingDigits;i++)
                            addingInString.append("0");
                        return new ActionsResult[]{ActionsResult.Builder.createInsertingBuilder().setString(addingInString.toString(),
                                number.getPosition()).build(),ActionsResult.Builder.createMovingActionBuilder().setFromPosition(number.getDotPosition()).
                        setToPosition(number.getDotPosition()-2).build()};
                    }
                }
            }
            /**
             * number doesn't have dot so add it, if number hasn't got enough digits,add some extra "0"
             */
            else
            {
                number.setHasDot(true);
                if(number.getNumberOfDigits()>=3)
                {
                    int positionOfInsertingDot=number.getLastPosition()-1;
                    return new ActionsResult[]{ActionsResult.Builder.createInsertingBuilder().setString(".",positionOfInsertingDot).build()};
                }
                else
                {
                    number.increaseNumberOfDigits(3-number.getNumberOfDigits());
                    return new ActionsResult[2];
                }
            }

        }
    }
    public class UserInputIsEmpty extends CursorState implements CalculateInterface {
        /**
         *
         * @param tag
         * @return
         */
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
        public ActionsResult addDot() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }




        @Override
        public ActionsResult delete() {
            return ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
        }

        @Override
        public ActionsResult[] executePercentCalculation() {
            return new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build()};
        }
    }




}
