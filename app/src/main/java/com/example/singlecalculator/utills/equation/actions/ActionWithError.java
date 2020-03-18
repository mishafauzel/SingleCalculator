package com.example.singlecalculator.utills.equation.actions;

import com.example.singlecalculator.utills.equation.exceptions.UserInputException;


public class ActionWithError extends ActionsResult {
    public final UserInputException exception;

    public ActionWithError(UserInputException exception) {
        super(StateOfInsertingValues.ERROR);
        this.exception = exception;
    }
    public static class ActionWithErrorBuilder extends ActionsResult.Builder
    {
        private UserInputException exception;
        public ActionWithErrorBuilder() {
            super(StateOfInsertingValues.ERROR);
        }
        public ActionWithErrorBuilder setException(UserInputException exception)
        {
            this.exception=exception;
            return this;
        }
        @Override
        public ActionsResult build()
        {
            return new ActionWithError(exception);
        }
    }
}
