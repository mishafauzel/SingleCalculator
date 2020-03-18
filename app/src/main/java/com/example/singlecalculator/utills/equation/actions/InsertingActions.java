package com.example.singlecalculator.utills.equation.actions;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.ArrayList;

public class InsertingActions extends ActionsResult {
    public static final String INSERTING_OPENING_BRANCH="(";
    public static final String INSERTING_CLOSING_BRANCH=")";
    public final boolean newInsertionInString;
    public final String insertingString;
    public final int insertingPosition;
    public final boolean newInsertionInTreeSet;
    public final ElementOfEquation[] element;

    public InsertingActions(boolean newInsertionInString,String insertingString, int insertingPosition, boolean newInsertionInTreeSet, ElementOfEquation[] element) {
        super(StateOfInsertingValues.INSERTING);
        this.newInsertionInString=newInsertionInString;
        this.insertingString = insertingString;
        this.insertingPosition = insertingPosition;
        this.newInsertionInTreeSet = newInsertionInTreeSet;
        this.element = element;
    }
    public static class InsertingBuilder extends Builder
    {
        public boolean newInsertionInString=false;
        public String insertingString;
        public int insertingPosition;
        public boolean newInsertionInTreeSet=false;
        public ArrayList<ElementOfEquation> elements=null;
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

        public InsertingBuilder addElement(ElementOfEquation elementOfEquation)
        {
            if(elements==null)
            {
                elements=new ArrayList<>();
            }
            newInsertionInTreeSet=true;
            elements.add(elementOfEquation);
            return this;

        }
        public InsertingBuilder deleteLastElement()
        {
            if(elements!=null)
            {
                if(elements.size()!=0)
                elements.remove(elements.size()-1);
            }
            if(elements.size()==0)
                newInsertionInTreeSet=false;
            return this;
        }
        @Override
        public ActionsResult build()
        {
            ElementOfEquation[] elementsOfEquation=null;
            if(newInsertionInTreeSet)
                elementsOfEquation=elements.toArray(new ElementOfEquation[elements.size()]);
            return new InsertingActions(newInsertionInString,insertingString,insertingPosition,newInsertionInTreeSet,elementsOfEquation);
        }


    }
}
