package com.example.singlecalculator.utills.equation.insertingvalues;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;


import java.util.ArrayList;
import java.util.Arrays;

public class InsertingValues {
    public final boolean hasNewInsertions;
    public final boolean newInsertionInString;
    public final String insertingString;
    public final int insertingPosition;
    public final boolean newInsertionInTreeSet;
    public final ElementOfEquation[] element;
    public final StateOfInsertingValues state;
    public boolean hasError;

    public enum StateOfInsertingValues{INSERTING,DELETING}

    public InsertingValues(boolean hasNewInsertions, boolean newInsertionInString, String insertingString, int insertingPosition, boolean newInsertionInTreeSet, ElementOfEquation[] element, StateOfInsertingValues state) {
        this.hasNewInsertions = hasNewInsertions;
        this.newInsertionInString = newInsertionInString;
        this.insertingString = insertingString;
        this.insertingPosition = insertingPosition;
        this.newInsertionInTreeSet = newInsertionInTreeSet;
        this.element = element;
        this.state=state;
    }
    public static class Builder
    {
        private boolean hasNewInsertion=false;
        private boolean hasNewInsertionInString=false;
        private String insertingString;
        private int insertingPosition;
        private boolean hasNewInsertionInTreeSet=false;
        private ArrayList<ElementOfEquation> elements;
        private StateOfInsertingValues state;


        public Builder() {
        }

        public Builder(boolean newInsertionInString, boolean newInsertionInTreeSet, StateOfInsertingValues state) {
            this.hasNewInsertion = true;
            this.hasNewInsertionInString = newInsertionInString;
            this.hasNewInsertionInTreeSet = newInsertionInTreeSet;
            if(newInsertionInTreeSet)
            {
                elements =new ArrayList<>();
            }
            this.state=state;
        }
        private InsertingValues.Builder setInsertingString(String insertingString)
        {
            this.insertingString=insertingString;
            return this;
        }

        private InsertingValues.Builder setInsertingPosition(int insertingPosition)
        {
            this.insertingPosition=insertingPosition;
            return this;
        }

        private InsertingValues.Builder addNewInsertingElement(ElementOfEquation... elements)
        {
            this.elements.addAll(Arrays.asList(elements));
            return this;
        }

        private InsertingValues.Builder deleteLastInsertingElement()
        {
            if(this.elements.size()!=0)
            {
                this.elements.remove(elements.size()-1);
            }
            return this;
        }

        public InsertingValues.Builder setInsertingValues(String insertinStrValue,int insertingPosition,ElementOfEquation... elementsOfEquation)
        {
            if(hasNewInsertion)
            {
                if(hasNewInsertionInString)
                {
                    setInsertingString(insertinStrValue);
                    setInsertingPosition(insertingPosition);
                }
                if(hasNewInsertionInTreeSet)
                {addNewInsertingElement(elementsOfEquation);}
            }
            return this;

        }

        public InsertingValues build()
        {
            ElementOfEquation[] addingElements=this.elements ==null?null:new ElementOfEquation[this.elements.size()-1];
            return new InsertingValues(this.hasNewInsertion, this.hasNewInsertionInString,this.insertingString,this.insertingPosition, this.hasNewInsertionInTreeSet, addingElements,state);
        }

    }
}
