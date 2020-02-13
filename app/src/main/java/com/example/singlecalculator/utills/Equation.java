package com.example.singlecalculator.utills;


import android.media.audiofx.DynamicsProcessing;
import android.widget.EditText;

public class Equation  {
    private EditText equation;
    private static Equation instance;

    private Equation() {

    }
    public static Equation getInstance(EditText equation)
    {
        if(instance==null)
        {
            instance=new Equation();
        }
        instance.equation=equation;
        return instance;
    }

    public void addDigits(ButtonsTag tag) {
        
    }

    public void addAction(ButtonsTag tag) {
    }

    public void delete() {
    }

    public void executePercentCalculation() {
    }

    public void addBranches() {
    }

    public void changeSign() {
    }
}
