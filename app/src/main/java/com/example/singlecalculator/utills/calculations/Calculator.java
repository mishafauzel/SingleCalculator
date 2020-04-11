package com.example.singlecalculator.utills.calculations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.strategiesInterdaces.ActionsResultLiveDataOwner;

import java.util.TreeSet;

public class Calculator implements ActionsResultLiveDataOwner {
    private LiveData<ActionsResult> resultLiveData = new MutableLiveData<>();
    private static Calculator instanse;


    public static Calculator getInstance()
    {
        if(instanse==null)
            instanse=new Calculator();
        return instanse;
    }

    public void calculate(TreeSet<ElementOfEquation> equation) {

    }

    @Override
    public LiveData<ActionsResult> getLiveData() {
        return null;
    }

    public void stopAllCalculation() {

    }

    public void addNewCalculation(TreeSet<ElementOfEquation> equationTreeSet) {

    }
}
