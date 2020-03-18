package com.example.singlecalculator.utills.equation.actions;

public class MovingAction extends ActionsResult {
    public final int fromPosition;
    public  final int toPosition;

    public MovingAction(int fromPosition, int toPosition) {
        super(StateOfInsertingValues.MOVING);
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }
    public static class MovingActionsBuilder extends Builder
    {
        int fromPosition;
        int toPosition;
        public MovingActionsBuilder()
        {
            super(StateOfInsertingValues.DELETING);
        }
        public MovingActionsBuilder setFromPosition(int fromPosition)
        {
            this.fromPosition=fromPosition;
            return this;
        }
        public  MovingActionsBuilder setToPosition(int toPosition)
        {
            this.toPosition=toPosition;
            return this;
        }
        @Override
        public ActionsResult build(){
            return new MovingAction(fromPosition,toPosition);
        }
    }
}
