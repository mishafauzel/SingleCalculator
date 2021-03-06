package com.example.singlecalculator.utills.equation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.actions.DeletingActions;
import com.example.singlecalculator.utills.equation.actions.InsertingActions;
import com.example.singlecalculator.utills.equation.cursorposition.CalculateInterface;
import com.example.singlecalculator.utills.equation.cursorposition.CursorState;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.equation.exceptions.UserInputExceptionsFactory;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Number;
import com.example.singlecalculator.utills.strategiesInterdaces.ActionsResultLiveDataOwner;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;

public class EquationTreeSetManager implements ActionsResultLiveDataOwner,CalculateInterface  {
    private static EquationTreeSetManager instance;
    private TreeSet<ElementOfEquation> equationTreeSet = new TreeSet<>();
    private TreeSet<Branch> branches=new TreeSet<>();
    protected int unclosedBranchNumber=0;
    private int cursorPosition=0;
    private CursorStateController stateController=new CursorStateController();
    private MutableLiveData<ActionsResult[]> resultLiveData=new MutableLiveData<>();
    private MediatorLiveData<ActionsResult[]> resultsOfEquations=new MediatorLiveData<>();
    private Calculator calculator;
    private ObserverForMediatorLiveData observer=new ObserverForMediatorLiveData();
    public static EquationTreeSetManager getInstance()
    {
        if(instance==null)
            instance=new EquationTreeSetManager();
        return instance;
    }
    private EquationTreeSetManager()
    {
        this.calculator=Calculator.getInstance();
        resultsOfEquations.addSource(resultLiveData,observer);
        resultsOfEquations.addSource(((ActionsResultLiveDataOwner)calculator).getLiveData(),observer);

    }

    public CursorStateController getStateController() {
        return stateController;
    }

    public void setCursorPosition(int cursorPosition)
    {
     this.cursorPosition=cursorPosition;
     ElementOfEquation[] nearestElements=findNearestElements();
     stateController.defineCurrentState(nearestElements);
    }
    public ElementOfEquation[] findNearestElements()
    {
        ElementOfEquation[] nearestElements=new ElementOfEquation[2];
        ElementOfEquation elementChecker=new ElementOfEquation(cursorPosition);
        ElementOfEquation previousElement=equationTreeSet.floor(elementChecker);
        if(previousElement.getType()== ElementOfEquation.TypeOfElement.Number&&previousElement.getLastPosition()>=cursorPosition)
        {
            nearestElements[0]=previousElement;
            nearestElements[1]=null;
            return nearestElements;
        }
        else
        {
            nearestElements[0]=previousElement;
            nearestElements[1]=equationTreeSet.ceiling(elementChecker);
            return nearestElements;
        }
    }


    public void clearAll()
    {
        resultLiveData.setValue(new ActionsResult[]{DeletingActions.Builder.createDeletingActionBuilder().setFromPosition(0).setToPosition(equationTreeSet.last().getLastPosition()).build()});
        equationTreeSet.clear();
        branches.clear();
        unclosedBranchNumber=0;
        cursorPosition=0;
        calculator.stopAllCalculation();
        stateController.setUserInputsIsEmptyState();

    }
    public TreeSet<ActionsResult> createActionsResultTreeSet()
    {
     return new TreeSet<>();
    }
    public ActionsResult[] checkTreeSetElementsCorrect()
    {
        boolean hasPreviousDeletion=false;
        int deletingSize=0;
        ArrayList<ActionsResult> arrayListOfActionsResult=new ArrayList<>();
        ElementOfEquation previousElement=null;

        for (ElementOfEquation element :
               equationTreeSet) {
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
    public TreeSet<ActionsResult> insertNewValueToTreeSet(,TreeSet<ActionsResult> results,TreeSet<ElementOfEquation> elementsOfEquation, String insertingString) {
        if(equationTreeSet.last().getLastPosition()+insertingString.length()<100) {
            increasePositionOfElements(elementsOfEquation.first(), insertingString.length());
            cursorPosition = cursorPosition + insertingString.length();
            ActionsResult.Builder actionResult= ActionsResult.Builder.createInsertingBuilder().setString(insertingString,elementsOfEquation.first().getPosition());
            equationTreeSet.addAll(elementsOfEquation);

            return new ActionsResult[]{ actionResult.build()};
        }
        else return new ActionsResult[] {ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getUserInputHasTooManySymbols()).build()};

    }
    public ActionsResult[] insertNewDigitInNumber(Number number, String insertingValue)
    {
        if(number.getNumberOfDigits()<Number.MAXIMUM_NUMBER_SIZE&&equationTreeSet.last().getLastPosition()+1<=100)
        {
            number.increaseNumberOfDigits(insertingValue.length());
            increasePositionOfElements(number,insertingValue.length());
            ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString(insertingValue,cursorPosition);
            cursorPosition=cursorPosition+insertingValue.length();
            return new ActionsResult[]{builder.build()};
        }
        UserInputException exception;
        if(equationTreeSet.last().getLastPosition()+insertingValue.length()>100)
            exception = UserInputExceptionsFactory.getUserInputHasTooManySymbols();
        else
            exception=UserInputExceptionsFactory.getNumberToBigForInsertion();
        return new ActionsResult[]{ ActionsResult.Builder.createActionWithErrorBuilder().setException(exception).build()};
    }
    private  void increasePositionOfElements(ElementOfEquation smalestElement,int increasingSize) {
        SortedSet<ElementOfEquation> increasingElements=equationTreeSet.tailSet(smalestElement);
        for (ElementOfEquation element :
                increasingElements) {
            element.increasePosition(increasingSize);
        }


    }
    public ActionsResult[] deleteElements(TreeSet<ElementOfEquation> elementsOfEquation,int size) {

        equationTreeSet.removeAll(elementsOfEquation);
        increasePositionOfElements(elementsOfEquation.first(),size);
        checkTreeSetElementsCorrect();
        cursorPosition=cursorPosition-size;
        for (ElementOfEquation elemnts :
                elementsOfEquation) {
            
        }
        return 
    }




    private void addBranchesToBranchTreeSet(Branch branch)
    {
        String inseringString;
        if(branch.isOpening()) {
            unclosedBranchNumber++;
            inseringString="(";
        }
        else {
            unclosedBranchNumber--;
            inseringString=")";
        }
        TreeSet<ElementOfEquation> addingBranch = new TreeSet<ElementOfEquation>();
        addingBranch.add(branch);
        insertNewValueToTreeSet(addingBranch,inseringString);
        branches.add(branch);
        recalculateBranch();
    }
    private void recalculateBranch()
    {
        LinkedList<Branch> openingBranches=new LinkedList<>();
        for (Branch branch :
                branches) {
            if (branch.isOpening())
                openingBranches.offer(branch);
            else {
                Branch openingBranch=openingBranches.pollLast();
                openingBranch.setPairBranch(branch);
                branch.setPairBranch(openingBranch);
            }
        }
    }
    private Branch getPreviousOpeningBranches()
    {
     Branch testBranch=new Branch(cursorPosition);
     SortedSet<Branch> subSet=branches.tailSet(testBranch);
      TreeSet<Branch> subsetTree=new TreeSet<>(subSet);
      Iterator<Branch> iterator=subsetTree.descendingIterator();
      while (iterator.hasNext())
      {
          Branch branch=iterator.next();
          if(branch.isOpening()&&!branch.isClosed())
              return branch;


      }
      return null;

    }

    @Override
    public LiveData<ActionsResult[]> getLiveData() {
        return resultsOfEquations;
    }

    @Override
    public void addAction(ButtonsTag tag) {
        stateController.addAction(tag);
    }

    @Override
    public void addDigits(ButtonsTag tag) {
        stateController.addAction(tag);
    }

    @Override
    public void addBranches() {
        stateController.addBranches();
    }

    @Override
    public void changeSign() {
        stateController.changeSign();
    }

    @Override
    public void addDot() {
         stateController.addDot();
    }

    @Override
    public void delete() {
        stateController.delete();
    }

    @Override
    public void executePercentCalculation() {
       stateController.executePercentCalculation();
    }

    public void calculateTreeSet() {
        calculator.addNewCalculation(equationTreeSet);

    }


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
        public void addAction(ButtonsTag tag)  {
             currentState.addAction(tag);
        }

        @Override
        public void addDigits(ButtonsTag tag)
        {
             currentState.addDigits(tag);
        }

        @Override
        public void addBranches()  {
             currentState.addBranches();
        }

        @Override
        public void changeSign()  {
            currentState.changeSign();
        }




        @Override
        public void addDot()  {
             currentState.addDot();
        }




        @Override
        public void delete() {
             currentState.delete();
        }

        @Override
        public void executePercentCalculation()  {
            currentState.executePercentCalculation();
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
        private void setUserInputsIsEmptyState()
        {
            currentState=userInputIsEmpty;
        }

        public void clearAll() {
            int lastPosition=equationTreeSet.last().getLastPosition();
            resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createDeletingActionBuilder().setFromPosition(0).setToPosition(lastPosition).build()});
            equationTreeSet.clear();
            branches.clear();
            cursorPosition=0;
            unclosedBranchNumber=0;
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
            public void addAction(ButtonsTag tag) {
                Number oldNumber=(Number) closestElements[0];
                if(oldNumber.defineFirstDigitPositionRelativeToStartOfString()!=cursorPosition)
                {
                    Action newAction=new Action(cursorPosition,String.valueOf(tag.getText()));
                    Number newNumber=oldNumber.separateNumber(cursorPosition);
                    String insertingString;
                    int increasingSizeOfPosition=0;
                    if(newNumber.getDotPosition()==0)
                    {
                        newNumber.increaseNumberOfDigits(1);
                        insertingString=tag.getText()+"0";
                        increasingSizeOfPosition=2;
                    }
                    else {
                        insertingString = tag.getText().toString();
                        increasingSizeOfPosition=1;
                    }
                    newNumber.increasePosition(1);
                    ActionsResult.Builder builder;
                    TreeSet<ElementOfEquation> insertingValues=new TreeSet<>();
                    insertingValues.add(newAction);
                    insertingValues.add(newNumber);
                    if(insertNewValueToTreeSet(insertingValues,increasingSizeOfPosition)) {
                        builder = ActionsResult.Builder.createInsertingBuilder()
                                .setString(insertingString, newAction.getPosition());
                        setCursorBetweenNumberAndActionState();
                        CursorStateController.setClosestElements(newAction,newNumber);
                    }
                    else

                        builder=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getUserInputHasTooManySymbols());



                    resultLiveData.setValue(new ActionsResult[]{builder.build()});

                }
                else
                {
                    ActionsResult.Builder builder=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                            .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName()));
                    resultLiveData.setValue(new ActionsResult[]{builder.build()});
                }
            }

            @Override
            public void addDigits(ButtonsTag tag) {
                ActionsResult.Builder builder;
                if(((Number)closestElements[0]).increaseNumberOfDigits(1))
                {builder=ActionsResult.Builder.createInsertingBuilder().setString(String.valueOf(tag),cursorPosition);
                cursorPosition++;
                increasePositionOfElements(closestElements[0],1);}
                else
                {
                    builder=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getUserInputHasTooManySymbols());
                }
                resultLiveData.setValue(new ActionsResult[]{builder.build()});

            }

            @Override
            public void addBranches() {
                Number closestNumber = (Number)closestElements[0];
                if(cursorPosition != closestNumber.getFirstDigitPosition())
                {

                    if(unclosedBranchNumber==0)
                    {
                        Branch newBranch=new Branch(cursorPosition,true);
                        Action action=new Action(cursorPosition-1,"X");
                        Number newNumber=((Number)closestElements[0]).separateNumber(cursorPosition);
                        newNumber.increasePosition(2);


                        addBranchesToBranchTreeSet(newBranch);
                        TreeSet<ElementOfEquation> newInsertion=new TreeSet<>();
                        newInsertion.add(action);
                        newInsertion.add(newNumber);
                        ActionsResult.Builder builder;
                        if(insertNewValueToTreeSet(newInsertion,1))
                            builder=ActionsResult.Builder.createInsertingBuilder().setString("X(",action.getPosition());
                        else
                            builder=ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getUserInputHasTooManySymbols());
                        resultLiveData.setValue(new ActionsResult[]{builder.build()});
                        return;

                    }
                    else
                    {
                        Branch openingBranch= getPreviousOpeningBranches();

                            Branch newBranch=new Branch(cursorPosition,false);
                            openingBranch.setPairBranch(newBranch);
                            newBranch.setPairBranch(openingBranch);
                            Action action=new Action(cursorPosition+1,"x");
                            Number newNumber=closestNumber.separateNumber(cursorPosition);
                            addBranchesToBranchTreeSet(newBranch);
                            TreeSet<ElementOfEquation> addingElemnt=new TreeSet<>();
                            addingElemnt.add(action);
                            addingElemnt.add(newNumber);
                            insertNewValueToTreeSet(addingElemnt,1);
                            resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createInsertingBuilder().setString(")X",newBranch.getPosition()).build()});


                    }
                }
                else
                {
                    resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.AddingBranchImpossibleInThisPosition()).build()});
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

                    actionResult=ActionsResult.Builder.createInsertingBuilder().setString("(-",number.getPosition()).build();
                }
                number.setMinus(!number.isMinus());
                return actionResult;
            }




            @Override
            public ActionsResult addDot() {
                Number number = (Number) closestElements[0];
                int increasingSize=0;
                if (number.isHasDot())
                    return ActionsResult.Builder.createActionWithErrorBuilder()
                            .setException(UserInputExceptionsFactory.getNumberAlreadyHasDot()).build();
                else {
                    {
                        increasingSize=1;
                        String insertionInEditText = ".";
                        if (cursorPosition == number.getFirstDigitPosition()) {
                            if (!(number.increaseNumberOfDigits(1)))
                                return ActionsResult.Builder.createActionWithErrorBuilder()
                                        .setException(UserInputExceptionsFactory.getNumberToBigForInsertion()).build();

                            else {
                                increasingSize=2;
                                insertionInEditText = "0.";
                                number.increaseNumberOfDigits(1);

                            }
                        }
                        increasePositionOfElements(number,increasingSize);
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
            public void addAction(ButtonsTag tag) {
                resultLiveData.setValue(ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory.getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build();
            }

            @Override
            public void addDigits(ButtonsTag tag)
            {
                Number newElement=new Number(cursorPosition);
                newElement.setNumberOfDigits(1);
                ActionsResult.Builder builder;
                TreeSet<ElementOfEquation> newTreeSet=new TreeSet<>();
                newTreeSet.add(newElement);
                ActionsResult.Builder builder=EquationTreeSetManager.this.insertNewValueToTreeSet(newTreeSet,1);


                CursorStateController.this.setCursorWithinNumberState();
                CursorStateController.setClosestElements(newElement);

                resultLiveData.setValue(new ActionsResult[]{builder.build()});



            }

            @Override
            public void addBranches() {
                Branch newElement=new Branch(cursorPosition);
                newElement.setOpening(true);
                newElement.setClosed(false);
                branches.add(newElement);
                EquationTreeSetManager.this.addBranchesToBranchTreeSet(newElement);
                ActionsResult.Builder builder=ActionsResult.Builder.createInsertingBuilder().setString("(",cursorPosition);

                CursorStateController.this.setCursorNearBranchesState();
                CursorStateController.setClosestElements(newElement);
                resultLiveData.setValue(new ActionsResult[]{ builder.build()});



            }

            @Override
            public void changeSign() {
                resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                        .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build()});
            }




            @Override
            public void addDot() {
                resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                        .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build()});
            }




            @Override
            public void delete() {
                resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                        .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build()});
            }

            @Override
            public void executePercentCalculation() {
                resultLiveData.setValue(new ActionsResult[]{ActionsResult.Builder.createActionWithErrorBuilder().setException(UserInputExceptionsFactory
                        .getActionImpossibleInCurrentState(this.getClass().getEnclosingMethod().getName(),this.getClass().getSimpleName().toString())).build()});
            }
        }




    }
    private class ObserverForMediatorLiveData implements Observer<ActionsResult[]>
    {


        @Override
        public void onChanged(ActionsResult[] actionsResult) {
            resultsOfEquations.setValue(actionsResult);
        }
    }
}
