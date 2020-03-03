package com.example.singlecalculator.utills.equation;


import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.singlecalculator.R;
import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Numbers;

import java.util.SortedSet;
import java.util.TreeSet;

public class Equation {
    private static final String TAG = "Equation";
    private EditText equation;
    private int cursorPosition;
    private Numbers closestNumber;

    private TreeSet<ElementOfEquation> equationTreeSet = new TreeSet<>();
    private static Equation instance;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Equation.this.cursorPosition = ((EditText) view).getSelectionStart();
            Equation.this.closestNumber = Equation.this.findClosestNumberToCursor(cursorPosition);
            Log.d(TAG, "onClick: " + Equation.this.cursorPosition);
            Log.d(TAG, "onClick: " + Equation.this.closestNumber);
        }
    };

    private Equation() {
        cursorPosition = 0;
    }

    public static Equation getInstance(EditText equation) {

        if (instance == null) {
            instance = new Equation();
        }
        instance.equation = equation;
        equation.setOnClickListener(instance.onClickListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            equation.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            equation.setTextIsSelectable(true);
        }
        return instance;
    }
    private void addSymbolToEditTextAndIncreaseCursorPosition(Character addingChar, int sizeOfAdding)
    {
        equation.getText().insert(cursorPosition, addingChar.toString());
        cursorPosition++;
    }
    public void addDigits(ButtonsTag tag) {

        Character text = tag.getText();
        if (closestNumber == null) {
            equationTreeSet.add(closestNumber = new Numbers(0));
            closestNumber.increaseNumberOfDigits(1);
            addSymbolToEditTextAndIncreaseCursorPosition(text,1);
            printOutNumbers();
            return;
        }
        Log.d(TAG, "addDigits: " + equation.getSelectionStart());
        if (closestNumber.increaseNumberOfDigits(1)) {
            addSymbolToEditTextAndIncreaseCursorPosition(text,1);
            increasePosition(closestNumber,1,false);
        } else
            Toast.makeText(equation.getContext(), R.string.maxLength, Toast.LENGTH_LONG).show();
        Log.d(TAG, "addDigits: " + closestNumber.toString());
        Log.d(TAG, "addDigits: " + cursorPosition);
        Log.d(TAG, "addDigits: " + equation.length());
        printOutNumbers();
    }


    public void addAction(ButtonsTag tag) {
        if (cursorPosition != 0) {
            if (cursorPosition == equation.length()) {
                boolean[] areNeightborsActions = checkAreNeightboorsActions(cursorPosition);
                if (!areNeightborsActions[0]) {

                    equationTreeSet.add(new Action(cursorPosition, tag.getText().toString()));
                    addSymbolToEditTextAndIncreaseCursorPosition(tag.getText(),1);
                    equationTreeSet.add(closestNumber = new Numbers(cursorPosition));
                    //prinOutActions();
                }

            } else {
                boolean[] isNeightboorsAction = checkAreNeightboorsActions(cursorPosition);

                if (!(isNeightboorsAction[0] || isNeightboorsAction[1])) {
                    if (cursorPosition <= closestNumber.getLastPosition()) {
                        Numbers newNumber = closestNumber.separateNumber(cursorPosition);
                        equationTreeSet.add(new Action(cursorPosition, tag.getText().toString()));
                        newNumber.setPosition(newNumber.getPosition()+1);
                        equationTreeSet.add(newNumber);

                      addSymbolToEditTextAndIncreaseCursorPosition(tag.getText(),1);

                        closestNumber = newNumber;
                        increasePosition(closestNumber, 1,false);
                        Log.d(TAG, "addAction: " + equationTreeSet.add(new Action(cursorPosition, tag.getText().toString())));
                        Log.d(TAG, "addAction: sizeOfEquationSet" + equationTreeSet.size());

                        closestNumber = newNumber;
                        //prinOutActions();
                    }
                }

            }
            //prinOutActions();
            printOutNumbers();

        }
    }

    private Numbers findClosestNumberToCursor(int cursorPosition) {
        for (ElementOfEquation element :
                equationTreeSet) {
            if (element.getType() == ElementOfEquation.TypeOfElement.Number) {
                Log.d(TAG, "findClosestNumberToCursor: " + element.toDocumentationString()+" cursorPosition is "+cursorPosition );
                if (element.getPosition() <= cursorPosition) {
                    Log.d(TAG, "findClosestNumberToCursor: " + element.toDocumentationString()+" cursor position is"+cursorPosition);
                    if (((Numbers) element).getLastPosition() >= cursorPosition ) {
                        return (Numbers) element;
                    }
                } else return null;
            }
        }
        return null;
    }

    private void increasePosition(ElementOfEquation element, int increasedSize,boolean includeClosestNumber) {

        SortedSet<ElementOfEquation> subSet = equationTreeSet.tailSet(element, includeClosestNumber);
        Log.d(TAG, "increasePosition: " + subSet.size());
        for (ElementOfEquation nextElement :
                subSet) {
            nextElement.increasePosition(increasedSize);
        }

    }

    private boolean[] checkAreNeightboorsActions(int positionOfCursor) {
        boolean isBeforeAction = false;
        boolean isAfterAction = false;
        if (!equationTreeSet.isEmpty())
            for (ElementOfEquation action :
                    equationTreeSet) {
                if (positionOfCursor - action.getPosition() == 1)
                    isBeforeAction = action.getType() == ElementOfEquation.TypeOfElement.Action;
                if (positionOfCursor - action.getPosition() == 0) {
                    isAfterAction = action.getType() == ElementOfEquation.TypeOfElement.Action;
                }
                if (positionOfCursor - action.getPosition() < 0)
                    break;

            }
        Log.d(TAG, "checkAreNeightboorsActions: B:" + isBeforeAction + ", A:" + isAfterAction);
        return new boolean[]{isBeforeAction, isAfterAction};


    }

    public void clearAll() {
        equation.getText().delete(0, equation.getText().length());
        equationTreeSet.clear();
        Log.d(TAG, "clearAll: size Of tree" + equationTreeSet.size());
        cursorPosition = 0;
        closestNumber = null;

    }

    public void delete() {
    }

    public void executePercentCalculation() {
        int position = equation.getSelectionStart();
        findClosestNumberToCursor(position);

    }

    public void addBranches() {
        


    }

    private void addBranchesArroundNumberWithMinus(Numbers number)
    {
        Log.d(TAG, "addBranchesArroundNumberWithMinus: "+number.toDocumentationString());
        Editable text=equation.getText();
        if(number.isMinus())
        {
        text.insert(number.getPosition(),"(");
        number.increasePosition(1);
        Branch openingBranch=new Branch(number.getPosition()-1,true);
        equationTreeSet.add(openingBranch);
            cursorPosition=cursorPosition+2;
        }
        else
        {
            int firstPosition=number.getPosition();
            text.delete(firstPosition-1,firstPosition);
            number.increasePosition(-1);
            cursorPosition=cursorPosition>=2?cursorPosition-2:0;
        }
    }


    public void changeSign() {


        int increasingSize=-1;
        Log.d(TAG, "changeSign: position"+closestNumber.getPosition());
        if (closestNumber!=null)
        if(closestNumber.getNumberOfDigits()!=0) {
            if (!closestNumber.isMinus()) {
                equation.getText().insert(closestNumber.getPosition(), "-");

                increasingSize =2;
            } else {

                equation.getText().delete(closestNumber.getPosition(), closestNumber.getPosition()+1);

                increasingSize = -2;
            }
            closestNumber.setMinus(!closestNumber.isMinus());
            addBranchesArroundNumberWithMinus(closestNumber);
            Log.d(TAG, "changeSign: " + closestNumber.toDocumentationString());
            increasePosition(closestNumber, increasingSize,false);
            Log.d(TAG, "changeSign: " + closestNumber.toDocumentationString());
        }
        printOutNumbers();
    }

    public void addDot() {
        if(closestNumber.addDot(cursorPosition))
        {
            equation.getText().insert(cursorPosition,".");
            cursorPosition++;
        }


    }
    private void printOutNumbers()
    {
        for (ElementOfEquation elementOfEquation:
                this.equationTreeSet) {
            switch (elementOfEquation.getType())
            {
                case Action:
                {
                    Log.d(TAG, "printOutNumbers: "+elementOfEquation.getPosition());
                    Log.d(TAG, "printOutNumbers: action - actionType "+((Action)elementOfEquation).getType()+"\n"+"value in screen="
                            +equation.getText().subSequence(elementOfEquation.getPosition(),elementOfEquation.getPosition()+1));
                    break;
                }
                case Branch:
                {
                    Log.d(TAG, "printOutNumbers: branch "+elementOfEquation.toString()+"  \n value in screen="
                            +equation.getText().subSequence(elementOfEquation.getPosition(),elementOfEquation.getPosition()+1));
                    break;
                }
                case Number:
                {
                    Log.d(TAG, "printOutNumbers: last pos "+(((Numbers)elementOfEquation).getLastPosition()));

                    Log.d(TAG, "printOutNumbers: hasDot="+((Numbers)elementOfEquation).isHasDot());
                    Log.d(TAG, "printOutNumbers: dotPosition ="+((Numbers)elementOfEquation).getDotPosition());
                    Log.d(TAG, "printOutNumbers: isMinus"+((Numbers)elementOfEquation).isMinus());
                    Log.d(TAG, "printOutNumbers: nimberOfFields"+(((Numbers)elementOfEquation).getLastPosition()-
                            ((Numbers)elementOfEquation).getPosition()));
                    Log.d(TAG, "printOutNumbers: number "+elementOfEquation.toString()+"\n value on screen ="+
                            equation.getText().subSequence(elementOfEquation.getPosition(),((Numbers)elementOfEquation).getLastPosition()));
                    break;
                }
            }

        }
    }
    private void prinOutActions()
    {
        StringBuilder builder=new StringBuilder();
        for (ElementOfEquation element :
                equationTreeSet) {
            builder.append(element.toString());
            builder.append("\n");
        }

        Log.d(TAG, "prinOutActions: "+builder.toString());
        builder.delete(0,builder.length());
        for (ElementOfEquation element :
                equationTreeSet) {
          builder.append(element.toDocumentationString());
        }
        Log.d(TAG, "prinOutActions: "+"\n"+builder.toString());
    }

}
