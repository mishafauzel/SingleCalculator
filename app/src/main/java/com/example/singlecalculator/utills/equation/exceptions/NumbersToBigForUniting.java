package com.example.singlecalculator.utills.equation.exceptions;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.singlecalculator.utills.equation.utills.Numbers;

public class NumbersToBigForUniting extends Exception {
    Numbers numbers1;
    Numbers number2;
    public NumbersToBigForUniting(Numbers numbers1,Numbers numbers2) {
        super(new StringBuilder("Numbers ").append(numbers1).append(" and ").append(numbers2).append(" are to big for uniting").toString());
        this.numbers1=numbers1;
        this.number2=numbers2;

    }
}
