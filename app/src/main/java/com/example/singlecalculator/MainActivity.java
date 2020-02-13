package com.example.singlecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.singlecalculator.utills.FirstPanelStrategy;
import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

public class MainActivity extends AppCompatActivity implements StrategyOwner {

    private FirstPanelStrategy strategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strategy=FirstPanelStrategy.getInstance();
        strategy.setStrategyOwner(this);
        strategy.bindTextViews();

    }




    @Override
    public TextView[] getButtons(int[] idOfButtons) {
        TextView[] textViews=new TextView[idOfButtons.length];
        for(int i=0;i<idOfButtons.length;i++)
            textViews=findViewById(idOfButtons[i]);
        return textViews;
    }

    @Override
    public EditText getEquation() {
        return this.findViewById(R.id.equation);
    }
}
