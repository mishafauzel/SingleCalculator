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
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Numbers;

import java.lang.reflect.Array;
import java.util.SortedSet;
import java.util.TreeSet;

public class Equation  {
    private static final String TAG = "Equation";
    private EditText equation;
    private int cursorPosition;
    private Numbers closestNumber;

    private TreeSet<ElementOfEquation> equationTreeSet=new TreeSet<>();
    private static Equation instance;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Equation.this.cursorPosition = ((EditText)view).getSelectionStart();
            Equation.this.closestNumber = Equation.this.findClosestNumberToCursor(cursorPosition);
            Log.d(TAG, "onClick: "+Equation.this.cursorPosition);
            Log.d(TAG, "onClick: "+Equation.this.closestNumber);
        }
    };

    private Equation() {

    }
    public static Equation getInstance(EditText equation)
    {

        if(instance==null)
        {
            instance=new Equation();
        }
        instance.equation=equation;
        equation.setOnClickListener(instance.onClickListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            equation.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            equation.setTextIsSelectable(true);
        }
        return instance;
    }

    public void addDigits(ButtonsTag tag) {

        Character text=tag.getText();
        if(closestNumber==null)
            equationTreeSet.add(closestNumber=new Numbers(0));
        Log.d(TAG, "addDigits: "+equation.getSelectionStart());
        if(closestNumber.insertNewDiggits(text,cursorPosition=equation.getSelectionStart()))
        {equation.getText().insert(equation.getSelectionStart(),text.toString());
        cursorPosition++;}
        else
            Toast.makeText(equation.getContext(), R.string.maxLength,Toast.LENGTH_LONG).show();
        Log.d(TAG, "addDigits: "+closestNumber.toString());
        Log.d(TAG, "addDigits: "+cursorPosition);
        Log.d(TAG, "addDigits: "+equation.length());
    }


    public void addAction(ButtonsTag tag) {

        Log.d(TAG, "addAction: positionOfCursor="+cursorPosition);
        Log.d(TAG, "addAction: "+cursorPosition);

        if(cursorPosition!=0)
        {
            if(cursorPosition==equation.length())
            {boolean[]areNeightborsActions=checkAreNeightboorsActions(cursorPosition);
            if(!areNeightborsActions[0])
            {
                equation.getText().insert(cursorPosition,tag.getText().toString());
                equationTreeSet.add(new Action(cursorPosition,tag.getText().toString()));
                cursorPosition++;
                prinOutActions();
            }

            }
            else
            {
                boolean[] isNeightboorsAction=checkAreNeightboorsActions(cursorPosition);
                Log.d(TAG, "addAction: "+isNeightboorsAction[0]+" "+isNeightboorsAction[1]);
                if(!(isNeightboorsAction[0]||isNeightboorsAction[1]))
                {
                    if(cursorPosition<=closestNumber.getLastPosition())
                    {
                        Numbers newNumber=closestNumber.separateNumber(cursorPosition);
                        Log.d(TAG, "addAction: newNumber"+newNumber.toString());
                        Log.d(TAG, "addAction: sizeOfEquationSet"+equationTreeSet.size());
                        equationTreeSet.add(newNumber);
                        Log.d(TAG, "addAction: sizeOfEquationSet"+equationTreeSet.size());
                        equation.getText().insert(cursorPosition,tag.getText().toString());
                        equationTreeSet.add(new Action(cursorPosition,tag.getText().toString()));
                        closestNumber=newNumber;
                        increasePosition(closestNumber,1);
                        Log.d(TAG, "addAction: "+equationTreeSet.add(new Action(cursorPosition,tag.getText().toString())));
                        Log.d(TAG, "addAction: sizeOfEquationSet"+equationTreeSet.size());

                        closestNumber=newNumber;
                        prinOutActions();
                    }
                }

            }

        }
    }

    private Numbers findClosestNumberToCursor(int cursorPosition)
    {
        for (ElementOfEquation element :
                equationTreeSet) {
            if (element.getType() == ElementOfEquation.TypeOfElement.Number)
            {
                Log.d(TAG, "findClosestNumberToCursor: "+element.toDocumentationString());
                if(element.getPosition()<=cursorPosition) {
                    Log.d(TAG, "findClosestNumberToCursor: "+element.toDocumentationString());
                    if (((Numbers) element).getLastPosition() >= cursorPosition) {
                        return (Numbers) element;
                    }
                }
                else return null;
            }
        }
        return null;
    }
    private void increasePosition(ElementOfEquation element,int increasedSize)
    {
        if(equationTreeSet.size()>1) {
            SortedSet<ElementOfEquation> subSet = equationTreeSet.tailSet(element);
            Log.d(TAG, "increasePosition: " + subSet.size());
            for (ElementOfEquation nextElement :
                    subSet) {
                nextElement.increasePosition(increasedSize);
            }
        }
    }
    private boolean[] checkAreNeightboorsActions(int positionOfCursor) {
        boolean isBeforeAction=false;
        boolean isAfterAction=false;
        if (!equationTreeSet.isEmpty())
            for (ElementOfEquation action :
                    equationTreeSet) {
                if(positionOfCursor-action.getPosition()==1)
                    isBeforeAction=action.getType()== ElementOfEquation.TypeOfElement.Action;
                if(positionOfCursor-action.getPosition()==0)
                {
                    isAfterAction=action.getType()==ElementOfEquation.TypeOfElement.Action;
                }
                if(positionOfCursor-action.getPosition()<0)
                break;

            }
        Log.d(TAG, "checkAreNeightboorsActions: B:"+isBeforeAction+", A:"+isAfterAction);
        return new boolean[]{isBeforeAction,isAfterAction};


    }

    public void clearAll()
    {
        equation.getText().delete(0,equation.getText().length()-1);
       equationTreeSet.clear();
    }

    public void delete() {
    }

    public void executePercentCalculation() {
        int position=equation.getSelectionStart();
        findClosestNumberToCursor(position);

    }

    public void addBranches() {
        int positionOfCursor=equation.getSelectionStart();
        boolean[] areActions = checkAreNeightboorsActions(positionOfCursor);

    }

    public void changeSign() {


        int increasingSize=-1;
        Log.d(TAG, "changeSign: position"+closestNumber.getPosition());

        if(!closestNumber.isMinus())
        {
            equation.getText().insert(closestNumber.getPosition(),"-");
            increasingSize=1;
        }
        else
        {
            equation.getText().delete(closestNumber.getPosition(),1);
            increasingSize=-1;
        }
        closestNumber.setMinus(!closestNumber.isMinus());

        Log.d(TAG, "changeSign: "+closestNumber.isMinus());
        increasePosition(closestNumber,increasingSize);
    }

    public void addDot() {
    }
    private void prinOutActions()
    {
        StringBuilder builder=new StringBuilder();
        for (ElementOfEquation element :
                equationTreeSet) {
            builder.append(element.toString());
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
