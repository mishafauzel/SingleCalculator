package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;


public interface CalculateInterface {
     void addAction(ButtonsTag tag);
     void addDigits(ButtonsTag tag);
     void addBranches();
     void changeSign();

     void addDot();

     void delete();
     void executePercentCalculation();

}
