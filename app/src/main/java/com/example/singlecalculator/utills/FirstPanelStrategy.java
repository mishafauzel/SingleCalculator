package com.example.singlecalculator.utills;

import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.singlecalculator.R;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.onClickListeners.DiggitsClickListener;
import com.example.singlecalculator.utills.strategiesInterdaces.ActionsResultLiveDataOwner;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategiesInterface;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

import java.lang.ref.WeakReference;

public class FirstPanelStrategy extends ViewModel implements StrategiesInterface, ActionsResultLiveDataOwner{
    private final int[] idsOfButtons=new int[]{
            R.id.delete_button, R.id.branches,R.id.equals, R.id.changeSign,R.id.percent,R.id.divide,
            R.id.multiply, R.id.minus,R.id.plus,R.id.dot
            ,R.id.number7,R.id.number8,R.id.number9, R.id.number6,
            R.id.number5,R.id.number4, R.id.number3,R.id.number2,R.id.number1,R.id.number0

    };
    private ButtonsTag[] buttonTypes=new ButtonsTag[20];


    MediatorLiveData<ActionsResult[]> resultOfActionsLD;
    private DiggitsClickListener onClicListener;


    public FirstPanelStrategy()
    {
        onClicListener=new DiggitsClickListener();
        createButtonTypeArray();
        resultOfActionsLD.addSource(((ActionsResultLiveDataOwner) onClicListener).getLiveData(), new Observer<ActionsResult[]>() {
            @Override
            public void onChanged(ActionsResult[] actionsResult) {
                resultOfActionsLD.setValue(actionsResult);
            }
        });
    }




    @Override
    public void bindTextViews(StrategyOwner owner) {

        TextView[] buttons=owner.getButtons(idsOfButtons);
        if(onClicListener==null)
        {
            onClicListener=new DiggitsClickListener();
        }

      for(int i = 0; i < buttons.length; i++)
      {
          buttons[i].setTag(buttonTypes[i].setText(buttons[i].getText().toString().charAt(0)));
          buttons[i].setOnClickListener(onClicListener);

      }
      EditText equation=owner.getEquation();
      equation.setOnClickListener(onClicListener);
      equation.setTag(new ButtonsTag(ButtonsTag.ButtonType.equation));



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
    public LiveData<ActionsResult[]> getLiveData() {
        return resultOfActionsLD;
    }
}
