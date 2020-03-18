package com.example.singlecalculator.utills.equation.actions;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.ArrayList;

public class DeletingActions extends ActionsResult {
    public final int formPosition;
    public final int toPosition;
    public final boolean deletingOftreeSetRequired;
    public final ElementOfEquation[] elementOfEquation;

    public DeletingActions(int fromPosition, int toPosition,ElementOfEquation[] elementOfEquation) {
        super(StateOfInsertingValues.DELETING);
        this.formPosition = fromPosition;
        this.toPosition = toPosition;
        this.elementOfEquation = elementOfEquation;
        this.deletingOftreeSetRequired=elementOfEquation!=null;
        }
    public static class DeletingActionsBuilder extends Builder
    {
        int fromPosition;
        int toPosition;
        ArrayList<ElementOfEquation> deletingElements=null;
        public DeletingActionsBuilder()
        {
            super(StateOfInsertingValues.DELETING);
        }
        public DeletingActionsBuilder setFromPosition(int fromPosition)
        {
            this.fromPosition=fromPosition;
            return this;
        }
        public DeletingActionsBuilder setToPosition(int toPosition)
        {
            this.toPosition=toPosition;
            return this;
        }
        public DeletingActionsBuilder addDeletingElement(ElementOfEquation elementOfEquation)
        {
            if(deletingElements==null)
                deletingElements=new ArrayList<>();
            deletingElements.add(elementOfEquation);
            return this;
        }
        public DeletingActionsBuilder removeLastDeletingElement()
        {
            if(deletingElements!=null)
                deletingElements.remove(deletingElements.size()-1);
            if(deletingElements.size()==0)
                deletingElements=null;
            return this;
        }

        @Override
        public ActionsResult build(){
            ElementOfEquation[] deletingElementsArray=null;
            if(deletingElements!=null)
                deletingElementsArray=deletingElements.toArray(new ElementOfEquation[deletingElements.size()]);
            return new DeletingActions(fromPosition,toPosition,deletingElementsArray);
        }
    }
}
