package com.example.singlecalculator.utills.equation.actions;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.ArrayList;

public class InsertingActions extends ActionsResult {
    public static final String INSERTING_OPENING_BRANCH="(";
    public static final String INSERTING_CLOSING_BRANCH=")";
    public final boolean newInsertionInString;
    public final String insertingString;



    public InsertingActions(boolean newInsertionInString,String insertingString, int insertingPosition) {
        super(StateOfInsertingValues.INSERTING);
        this.newInsertionInString=newInsertionInString;
        this.insertingString = insertingString;
        this.insertingPosition = insertingPosition;

    }
    public static class InsertingBuilder extends Builder
    {
        public boolean newInsertionInString=false;
        public String insertingString;
        public int insertingPosition;

        public InsertingBuilder() {
            super(StateOfInsertingValues.INSERTING);
        }

        public InsertingBuilder setString(String insertingString,int insertingPosition)
        {
            newInsertionInString=true;
            this.insertingString=insertingString;
            this.insertingPosition=insertingPosition;
            return this;
        }



        @Override
        public ActionsResult build()
        {
            numberOfAction++;
            return new InsertingActions(newInsertionInString,insertingString,insertingPosition);
        }


    }
}
