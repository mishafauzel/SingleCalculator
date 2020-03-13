package com.example.singlecalculator.utills.equation.cursorposition;

import android.provider.ContactsContract;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.insertingvalues.InsertingValues;

public interface CalculateInterface {
     InsertingValues addAction(ButtonsTag tag);
     InsertingValues addDigits(ButtonsTag tag);
     InsertingValues addBranches();
     InsertingValues changeSign();
     void calculateTreeSet();
     InsertingValues addDot();
     void clearAll();
     InsertingValues delete();
     InsertingValues executePercentCalculation();

}
