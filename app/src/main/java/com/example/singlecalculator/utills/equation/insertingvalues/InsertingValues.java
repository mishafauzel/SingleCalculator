package com.example.singlecalculator.utills.equation.insertingvalues;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;


import java.util.ArrayList;

public class InsertingValues {
    public final boolean newInsertion;
    public final String insertingString;
    public final int insertingPosition;
    public final ElementOfEquation[] element;
    public final StateOfInsertingValues state;
    public enum StateOfInsertingValues{INSERTING,DELETING}

    public InsertingValues(boolean newInsertion, String insertingString, int insertingPosition, ElementOfEquation[] element,StateOfInsertingValues state) {
        this.newInsertion = newInsertion;
        this.insertingString = insertingString;
        this.insertingPosition = insertingPosition;
        this.element = element;
        this.state=state;
    }
    public static class Builder
    {
        private boolean newInsertion;
        private String insertingString;
        private int insertingPosition;
        private ArrayList<ElementOfEquation> element;
        private StateOfInsertingValues state;

        public Builder(boolean newInsertion) {
            this.newInsertion = newInsertion;
            if(newInsertion)
            {
                element=new ArrayList<>();
            }
        }

        public InsertingValues.Builder newInsertion(boolean hasNewInsertion)
        {
            this.newInsertion=newInsertion;
            return this;
        }
        public InsertingValues.Builder setState(StateOfInsertingValues state)
        {
            this.state=state;
            return this;
        }
        public InsertingValues.Builder insertingString(String insertingString)
        {
            this.insertingString=insertingString;
            return this;
        }
        public InsertingValues.Builder insertingPosition(int insertingPosition)
        {
            this.insertingPosition=insertingPosition;
            return this;
        }
        public InsertingValues.Builder addNewInsertingElement(ElementOfEquation element)
        {
            this.element.add(element);
            return this;
        }
        public InsertingValues.Builder deleteLastInsertingElement()
        {
            if(this.element.size()!=0)
            {
                this.element.remove(element.size()-1);
            }
            return this;
        }
        public InsertingValues build()
        {
            return new InsertingValues(this.newInsertion,this.insertingString,this.insertingPosition,this.element.toArray(new ElementOfEquation[this.element.size()]),state);
        }

    }
}
