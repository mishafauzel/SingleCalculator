package com.example.singlecalculator.utills.equation.exceptions;

public class UserInputException extends Exception {
     protected int idOfStrRes;
     protected String message;

    public UserInputException() {
    }

    public UserInputException(String message, int idOfStrRes)
    {
        super(message);
        this.idOfStrRes=idOfStrRes;
    }
    protected static UserInputException getExceptionInstance(String message,int idOfStrRes)
    {
        return new UserInputException(message,idOfStrRes);
    }
    public int getIdOfStrRes() {
        return idOfStrRes;
    }

}
