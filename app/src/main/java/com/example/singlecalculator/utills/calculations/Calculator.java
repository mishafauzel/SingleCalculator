package com.example.singlecalculator.utills.calculations;

import com.example.singlecalculator.utills.Equation;

public class Calculator {
    private static Calculator instanse;
    public static Calculator getInstance()
    {
        if(instanse==null)
            instanse=new Calculator();
        return instanse;
    }

    public void calculate(Equation equation) {

    }
}
