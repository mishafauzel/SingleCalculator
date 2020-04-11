package com.example.singlecalculator.utills.equation.actions;

import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.util.ArrayList;

public class DeletingActions extends ActionsResult {

    public final int toPosition;


    public DeletingActions(int fromPosition, int toPosition) {
        super(StateOfInsertingValues.DELETING);
        this.formPosition = fromPosition;
        this.toPosition = toPosition;

        }
    public static class DeletingActionsBuilder extends Builder
    {
        int fromPosition;
        int toPosition;

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


        @Override
        public ActionsResult build(){
            numberOfAction++;
            return new DeletingActions(fromPosition,toPosition);
        }
    }
}
