package com.example.singlecalculator.utills.onClickListeners;

import android.media.audiofx.DynamicsProcessing;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.singlecalculator.utills.ButtonsTag;
import com.example.singlecalculator.utills.FirstPanelStrategy;
import com.example.singlecalculator.utills.equation.EquationTreeSetManager;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.strategiesInterdaces.ActionsResultLiveDataOwner;

public class DiggitsClickListener implements View.OnClickListener, ActionsResultLiveDataOwner {
    private static final String TAG = "DiggitsClickListener";

    private EquationTreeSetManager manager;

    private MediatorLiveData<ActionsResult[]> resultMediatorLiveData;

    public DiggitsClickListener() {
        manager=EquationTreeSetManager.getInstance();

        resultMediatorLiveData=new MediatorLiveData<>();
        resultMediatorLiveData.addSource(((ActionsResultLiveDataOwner) manager).getLiveData(), new Observer<ActionsResult[]>() {
            @Override
            public void onChanged(ActionsResult[] actionsResult) {
                resultMediatorLiveData.setValue(actionsResult);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        Log.d(TAG, "onClick: "+view.getTag().toString());
        ButtonsTag tag=((ButtonsTag)view.getTag());
        switch ( tag.buttonType)
        {
            case digit: {

                    manager.addDigits(tag);


                break;
            }
            case action: {

                    manager.addAction(tag);

                break;
            }
            case delete:
                manager.clearAll();
                break;
            case equals:
               manager.calculateTreeSet();
                break;
            case percent:

                    manager.executePercentCalculation();

                break;
            case branches:

                    manager.addBranches();

                break;
            case chengeSign:
            {
                    manager.changeSign();

                break;}
            case dot:
            { manager.addDot();
                break;}
            case equation:
            {
               int chosenPosition = ((EditText)view).getSelectionStart();
               manager.setCursorPosition(chosenPosition);
            }
        }

    }

    @Override
    public LiveData<ActionsResult[]> getLiveData() {
        return resultMediatorLiveData;
    }
}
