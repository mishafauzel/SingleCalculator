package com.example.singlecalculator.utills.equation;


import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.singlecalculator.R;
import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.cursorposition.CalculateInterface;
import com.example.singlecalculator.utills.equation.cursorposition.CursorStateController;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

public class Equation  {
    private static final String TAG = "Equation";
    private EditText userInput;
    private EquationTreeSetManager treeSetManager=new EquationTreeSetManager();
    private CursorStateController cursorStateController=new CursorStateController();


    private static Equation instance;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position=userInput.getSelectionStart();
            cursorStateController.setCursorPosition(position);
            ElementOfEquation[] nearestElements=treeSetManager.findNearestElements(position);
            cursorStateController.defineCurrentState(nearestElements);


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



    public boolean addAction(ButtonsTag tag) {

         return false;
    }


    public boolean addDigits(ButtonsTag tag) {
        InsertingValues insertingValues;


        return false;
    }


    public InsertingValues addBranches() {
        InsertingValues insertingValues;
        if((insertingValues=cursorStateController.addBranches())!=null)
        {
            insertToUserInputInsertingValues(insertingValues);
        }
        return null;
    }


    public InsertingValues changeSign() {
        InsertingValues insertingValues;
        if((insertingValues=cursorStateController.changeSign())!=null)
        {
            insertToUserInputInsertingValues(insertingValues);
        }
        return null;
    }



    public void calculateTreeSet() {

    }


    public InsertingValues addDot() {
        InsertingValues insertingValues;
        if((insertingValues=cursorStateController.addDot())!=null)
        {
            insertToUserInputInsertingValues(insertingValues);
        }
        return null;
    }



    public void clearAll() {
        cursorStateController.clearAll();
        clearUserInput();
    }


    public boolean executePercentCalculation() {
        return false;
    }
    private void insertToUserInput(char addingChar,int position)
    {
        userInput.getText().insert(position,String.valueOf(addingChar));
    }
    private void insertToUserInputInsertingValues(InsertingValues insertingValues)
    {

    }
    private void clearUserInput()
    {
        userInput.getText().clear();
    }
}
