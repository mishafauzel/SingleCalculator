package com.example.singlecalculator.utills.equation;


import android.os.Build;
import android.service.autofill.AutofillService;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.singlecalculator.R;
import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.equation.cursorposition.CursorStateController;
import com.example.singlecalculator.utills.equation.utills.Action;
import com.example.singlecalculator.utills.equation.utills.Branch;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;
import com.example.singlecalculator.utills.equation.utills.Numbers;

import java.util.SortedSet;
import java.util.TreeSet;

public class Equation {
    private static final String TAG = "Equation";
    private EditText userInput;
    private EquationTreeSetManager equationTreeSetManager=new EquationTreeSetManager();
    private CursorStateController cursorStateController=new CursorStateController();


    private static Equation instance;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position=userInput.getSelectionStart();

        }
    };

    private Equation() {

    }

    public static Equation getInstance(EditText equation) {

        if (instance == null) {
            instance = new Equation();
        }
        instance.userInput = equation;
        instance.userInput.setOnClickListener(instance.onClickListener);
        instance.userInput.getSelectionStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            instance.userInput.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            instance.userInput.setTextIsSelectable(true);
        }
        return instance;
    }









}
