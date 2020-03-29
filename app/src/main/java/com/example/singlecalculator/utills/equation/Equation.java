package com.example.singlecalculator.utills.equation;


import android.os.Build;
import android.view.View;
import android.widget.EditText;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.actions.ActionWithError;
import com.example.singlecalculator.utills.equation.actions.DeletingActions;
import com.example.singlecalculator.utills.equation.actions.InsertingActions;
import com.example.singlecalculator.utills.equation.actions.MovingAction;
import com.example.singlecalculator.utills.equation.cursorposition.CursorStateController;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.exceptions.UserInputExceptionsFactory;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public class Equation  {
    private static final String TAG = "Equation";
    private EditText userInput;
    private EquationTreeSetManager treeSetManager=new EquationTreeSetManager();



    private static Equation instance;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position=userInput.getSelectionStart();
            treeSetManager.setCursorPosition(position);


        }
    };

    private Equation() {

    }

    public static Equation getInstance(EditText equation) {

        if (instance == null) {
            instance = new Equation();
        }
        instance.userInput = equation;
        instance.userInput.setOnClickListener(instance.onClickListener);
        instance.userInput.getSelectionStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            instance.userInput.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            instance.userInput.setTextIsSelectable(true);
        }
        return instance;
    }



    public void addAction(ButtonsTag tag) throws UserInputException {
        ActionsResult insertingValues=cursorStateController.addAction(tag);
        executeInsertingValues(insertingValues);
    }


    public void addDigits(ButtonsTag tag) throws UserInputException{
        ActionsResult insertingValues=cursorStateController.addDigits(tag);
        executeInsertingValues(insertingValues);



    }


    public void addBranches() throws UserInputException{
        ActionsResult insertingValues=cursorStateController.addBranches();
        executeInsertingValues(insertingValues);

    }


    public void changeSign() throws UserInputException {
        ActionsResult insertingValues=cursorStateController.changeSign();
        executeInsertingValues(insertingValues);

    }



    public void calculateTreeSet() {

    }


    public void addDot() throws UserInputException {
        ActionsResult insertingValues=cursorStateController.addDot();
        executeInsertingValues(insertingValues);
    }



    public void clearAll() {
        treeSetManager.clearAll();
        clearUserInput();
    }


    public void executePercentCalculation() throws UserInputException {
        ActionsResult[] insertingValues=cursorStateController.executePercentCalculation();

        executeInsertingValues(insertingValues);
    }

    private void insertToUserInputInsertingValues(ActionsResult insertingValues)
    {

    }
    private void clearUserInput()
    {
        userInput.getText().clear();
    }
    private void executeInsertingValues(ActionsResult... insertingValues) throws UserInputException
    {
        for (ActionsResult actionResult :
                insertingValues) {

            switch (actionResult.state)
            {
                case ERROR:
                    throw ((ActionWithError)actionResult).exception;
                case MOVING:
                {
                    MovingAction movingAction=(MovingAction)actionResult;
                    int fromPosition=movingAction.fromPosition;
                    int toPosition=movingAction.toPosition;
                    moveInsertionInUserInput(fromPosition,toPosition);

                    break;
                }
                case DELETING:
                {
                    DeletingActions deletingActions=((DeletingActions)actionResult);
                    if(deletingActions.formPosition!=-1)
                        deleteFromUserInput(deletingActions.formPosition,deletingActions.toPosition);
                    if(deletingActions.deletingOftreeSetRequired) {
                        treeSetManager.deleteElements(deletingActions.elementOfEquation);
                        treeSetManager.checkTreeSetElementsCorrect();
                        redefineCursorState(CursorStateController.getCursorPosition());
                    }
                    break;

                }
                case INSERTING:
                {
                    InsertingActions insertingAction=(InsertingActions)actionResult;
                    if(insertingAction.newInsertionInString)
                    {
                        insertToUserInput(insertingAction.insertingPosition,insertingAction.insertingString);
                    }
                    if(insertingAction.newInsertionInTreeSet)
                    {
                        insertNewValueToTreeSet(insertingAction.element);
                    }
                }
            }
        }
    }

    private void insertNewValueToTreeSet(ElementOfEquation[] element) {
        treeSetManager.insertNewValueToTreeSet(element);
    }

    private void redefineCursorState(int cursorPosition) {
        ElementOfEquation[] elementsOfEquation=treeSetManager.findNearestElements(cursorPosition);
        cursorStateController.defineCurrentState(elementsOfEquation);
    }

    private void moveInsertionInUserInput(int fromPosition,int toPosition)
    {
        String movingString=String.valueOf(userInput.getText().charAt(fromPosition));
        userInput.getText().insert(toPosition,movingString);
        userInput.getText().delete(fromPosition+1,fromPosition+2);
    }
    private void deleteFromUserInput(int from, int to)
    {
        userInput.getText().delete(from,to);
    }
    private void insertToUserInput(int insertingPosition, String insertingString) throws UserInputException
    {
        if(userInput.getText().length()<100){
        userInput.getText().insert(insertingPosition,insertingString);
        treeSetManager.increasePositionOfElements(insertingPosition,insertingString.length());}
        else
            throw UserInputExceptionsFactory.getUserInputHasTooManySymbols();
    }


}
