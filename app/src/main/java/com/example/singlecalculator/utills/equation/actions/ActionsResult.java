package com.example.singlecalculator.utills.equation.actions;

import android.provider.Contacts;

import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;


import java.util.ArrayList;
import java.util.Arrays;

public class ActionsResult {


    public final StateOfInsertingValues state;


    public enum StateOfInsertingValues{INSERTING,DELETING,ERROR,MOVING}

    protected ActionsResult(StateOfInsertingValues state) {
        this.state=state;
    }


    public static class Builder
    {

        private StateOfInsertingValues state;




        public Builder(StateOfInsertingValues state) {
            this.state = state;
        }
        public static ActionWithError.ActionWithErrorBuilder createActionWithErrorBuilder()
        {
            return new ActionWithError.ActionWithErrorBuilder();
        }
        public static DeletingActions.DeletingActionsBuilder createDeletingActionBuilder()
        {
            return new DeletingActions.DeletingActionsBuilder();
        }
        public static InsertingActions.InsertingBuilder createInsertingBuilder()
        {
            return new InsertingActions.InsertingBuilder();
        }
        public static MovingAction.MovingActionsBuilder createMovingActionBuilder()
        {
            return new MovingAction.MovingActionsBuilder();
        }
        public ActionsResult build()
        {
            return new ActionsResult(state);
        }


    }
}
