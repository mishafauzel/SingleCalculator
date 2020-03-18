package com.example.singlecalculator.utills;

import android.widget.TextView;

import com.example.singlecalculator.R;
import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.equation.Equation;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.onClickListeners.DiggitsClickListener;
import com.example.singlecalculator.utills.strategiesInterdaces.ExceptionHandlerForUserInput;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategiesInterface;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

public class FirstPanelStrategy implements StrategiesInterface, ExceptionHandlerForUserInput {
    private final int[] idsOfButtons=new int[]{
            R.id.delete_button, R.id.branches,R.id.equals, R.id.changeSign,R.id.percent,R.id.divide,
            R.id.multiply, R.id.minus,R.id.plus,R.id.dot
            ,R.id.number7,R.id.number8,R.id.number9, R.id.number6,
            R.id.number5,R.id.number4, R.id.number3,R.id.number2,R.id.number1,R.id.number0

    };
    private ButtonsTag[] buttonTypes=new ButtonsTag[20];
    private TextView[] buttons;
    private StrategyOwner owner;
    private Calculator calculator;
    private DiggitsClickListener onClicListener;
    private Equation equation;
    private static FirstPanelStrategy instance;
    private FirstPanelStrategy()
    {
        calculator=Calculator.getInstance();
        onClicListener=new DiggitsClickListener();
        createButtonTypeArray();
    }


    public static FirstPanelStrategy getInstance()
    {
        if(instance==null)
            instance=new FirstPanelStrategy();
        return instance;
    }

    @Override
    public void setStrategyOwner(StrategyOwner owner) {
        this.owner=owner;
        equation=Equation.getInstance(this.owner.getEquation());
    }

    @Override
    public void bindTextViews() {

        buttons=owner.getButtons(idsOfButtons);
        if(onClicListener==null)
        {
            onClicListener=new DiggitsClickListener();
        }
        onClicListener.setEquation(equation);
      for(int i = 0; i < buttons.length; i++)
      {
          buttons[i].setTag(buttonTypes[i].setText(buttons[i].getText().toString().charAt(0)));
          buttons[i].setOnClickListener(onClicListener);

    }
}

    private void createButtonTypeArray()
    {
        buttonTypes[0]= new ButtonsTag( ButtonsTag.ButtonType.delete);
        buttonTypes[1]= new ButtonsTag(ButtonsTag.ButtonType.branches);
        buttonTypes[2]= new ButtonsTag(ButtonsTag.ButtonType.equals);
        buttonTypes[3]= new ButtonsTag(ButtonsTag.ButtonType.chengeSign);
        buttonTypes[4]= new ButtonsTag(ButtonsTag.ButtonType.percent);
        for(int i = 5; i < 9; i++ )
            buttonTypes[i]= new ButtonsTag(ButtonsTag.ButtonType.action);
        buttonTypes[9]=new ButtonsTag(ButtonsTag.ButtonType.dot);
        for(int i = 10; i < buttonTypes.length; i++)
            buttonTypes[i]= new ButtonsTag(ButtonsTag.ButtonType.digit);

    }

    @Override
    public void handleUserInputException(UserInputException exception) {
        owner.showErrorToast(exception);
    }
}
