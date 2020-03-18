package com.example.singlecalculator.utills.equation.exceptions;

import com.example.singlecalculator.R;

public class UserInputExceptionsFactory {
    public static UserInputException getActionImpossibleInCurrentState(String nameOfMethod,String state)
    {
        return new UserInputException("Error: call of "+nameOfMethod+" method in "+state+" state is impossible", R.string.cant_execute_action);
    }
    public static UserInputException getNumberToBigForMerging()
    {
        return  new UserInputException("Error: sum of two merging numbers' lengths must be less then 15",R.string.merging_length_too_big);
    }
    public static UserInputException getNumberToBigForInsertion()
    {
        return new UserInputException("Error: number's length must not exceed 15 ",R.string.max_length_of_number);
    }
    public static UserInputException getNumberAlreadyHasDot()
    {
        return new UserInputException("Error: number already has dot",R.string.number_already_has_dot);
    }
}
