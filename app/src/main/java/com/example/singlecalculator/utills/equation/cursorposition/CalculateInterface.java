package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;

public interface CalculateInterface {
     ActionsResult addAction(ButtonsTag tag);
     ActionsResult addDigits(ButtonsTag tag);
     ActionsResult addBranches();
     ActionsResult changeSign();
     void calculateTreeSet();
     ActionsResult addDot();
     void clearAll();
     ActionsResult delete();
     ActionsResult[] executePercentCalculation();

}
