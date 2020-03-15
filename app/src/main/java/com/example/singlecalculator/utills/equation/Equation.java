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



    public void addAction(ButtonsTag tag) {
        InsertingValues insertingValues=cursorStateController.addAction(tag);
        executeInsertingValues(insertingValues);
    }


    public void addDigits(ButtonsTag tag) {
        InsertingValues insertingValues=cursorStateController.addDigits(tag);
        executeInsertingValues(insertingValues);



    }


    public void addBranches() {
        InsertingValues insertingValues=cursorStateController.addBranches();
        executeInsertingValues(insertingValues);

    }


    public void changeSign() {
        InsertingValues insertingValues=cursorStateController.changeSign();
        executeInsertingValues(insertingValues);

    }



    public void calculateTreeSet() {

    }


    public void addDot() {
        InsertingValues insertingValues=cursorStateController.addDot();
        executeInsertingValues(insertingValues);
    }



    public void clearAll() {
        cursorStateController.clearAll();
        clearUserInput();
    }


    public void executePercentCalculation() {
        InsertingValues insertingValues=cursorStateController.executePercentCalculation();
        executeInsertingValues(insertingValues);
    }

    private void insertToUserInputInsertingValues(InsertingValues insertingValues)
    {

    }
    private void clearUserInput()
    {
        userInput.getText().clear();
    }
    private void executeInsertingValues(InsertingValues insertingValues)
    {
        
    }

}
