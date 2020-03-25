package com.example.singlecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.singlecalculator.utills.FirstPanelStrategy;
import com.example.singlecalculator.utills.calculations.Calculator;
import com.example.singlecalculator.utills.equation.exceptions.UserInputException;
import com.example.singlecalculator.utills.strategiesInterdaces.StrategyOwner;

import java.util.Iterator;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements StrategyOwner {
    private static final String TAG = "MainActivity";
    private FirstPanelStrategy strategy;
    private LinkedList<String> linkList=new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strategy=FirstPanelStrategy.getInstance();
        strategy.setStrategyOwner(this);
        strategy.bindTextViews();
        linkList.add("1");
        linkList.add("2");
        linkList.add("3");
        Iterator<String> iterator=linkList.iterator();
       // iterator.remove();
        Log.d(TAG, "onCreate: "+iterator.next());

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
        return this.findViewById(R.id.equation);
    }

    @Override
    public void showErrorToast(UserInputException exception) {
        Toast.makeText(this,exception.getIdOfStrRes(),Toast.LENGTH_LONG).show();
        
    }
}
