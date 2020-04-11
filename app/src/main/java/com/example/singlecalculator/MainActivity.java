package com.example.singlecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.singlecalculator.utills.FirstPanelStrategy;
import com.example.singlecalculator.utills.equation.actions.ActionsResult;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements StrategyOwner {
    private static final String TAG = "MainActivity";
    private FirstPanelStrategy strategy;
    private EditText equation;
    private TextView equationResult;
    private ActionsResultsHandler resultsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equation=findViewById(R.id.equation);
        equationResult=findViewById(R.id.result);
        resultsHandler=new ActionsResultsHandler(equation,equationResult);

        strategy= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(FirstPanelStrategy.class);
        strategy.bindTextViews(this);
        strategy.getLiveData().observe(this,new ObserverForActionResults());


    }




    @Override
    public TextView[] getButtons(int[] idOfButtons) {
        TextView[] textViews=new TextView[idOfButtons.length];
        for(int i=0;i<idOfButtons.length;i++)
            textViews[i]=findViewById(idOfButtons[i]);
        return textViews;
    }

    @Override
    public EditText getEquation() {
        return this.equation;
    }
    private class ObserverForActionResults implements Observer<ActionsResult[]>
    {

        @Override
        public void onChanged(ActionsResult[] actionsResult) {

        }
    }
    private class ActionsResultsHandler
    {
        private EditText equation;
        private TextView resultOfEquation;

        public ActionsResultsHandler(EditText equation, TextView resultOfEquation) {
            this.equation = equation;
            this.resultOfEquation = resultOfEquation;
        }

        void handleActionResults(TreeSet<ActionsResult> actionsResults)
        {}
    }


}
