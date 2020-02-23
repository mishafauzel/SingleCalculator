package com.example.singlecalculator.utills.equation.utills;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.singlecalculator.utills.equation.exceptions.NumbersToBigForUniting;
import com.example.singlecalculator.utills.equation.utills.ElementOfEquation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Numbers extends ElementOfEquation {
    private static final String TAG = "Numbers";
    public Numbers(int position) {
        super(position,TypeOfElement.Number);
    }
    private Numbers(int position,ArrayList<Character> arrayOfDiggets){
        super(position,TypeOfElement.Number);
        this.arrayOfDiggits=arrayOfDiggets;
    }
    private ArrayList<Character> arrayOfDiggits=new ArrayList<>(15);
    private boolean hasDot=false;

    private boolean isMinus=false;
    private int dotPosition;

    public boolean insertNewDiggits(char newDgt,int insertPosition)
    {
        Log.d(TAG, "insertNewDiggits: firstPositionOfNumber"+position);
        Log.d(TAG, "insertNewDiggits: cursorPosition"+insertPosition);
        if(arrayOfDiggits.size()<15)
        {
            arrayOfDiggits.add(insertPosition-position,newDgt);
            return true;

        }
        else return false;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        String minus="";
        minus=isMinus?"-":"";
        sb.append(minus);

        for (Character diggit :
                arrayOfDiggits) {
            sb.append(diggit);
        }
        if(hasDot)
            sb.insert(dotPosition,".");
            return sb.toString();
    }

    public int getLastPosition()
    {
        int minusSize=isMinus?1:0;
        int dotSize=hasDot?1:0;
        int sizrOfDiggits=arrayOfDiggits.size()==0?0:arrayOfDiggits.size()-1;
        return position+sizrOfDiggits+minusSize+dotSize;
    }

    public boolean isHasDot() {
        return hasDot;
    }

    public void setHasDot(boolean hasDot) {
        this.hasDot = hasDot;
    }

    public boolean isMinus() {
        return isMinus;
    }

    public void setMinus(boolean minus) {
        isMinus = minus;
    }

    public int getDotPosition() {
        return dotPosition;
    }

    public void setDotPosition(int dotPosition) {
        this.dotPosition = dotPosition;
    }


    @Override
    public String toDocumentationString() {
        return new StringBuilder().append("number").append(" ").append(position).append(" ").append(getLastPosition()).append("\n").toString();
    }
    public int getSize()
    {
       return arrayOfDiggits.size();
    }

    public Numbers separateNumber(int cursorPosition) {
        ArrayList<Character> newNumberDiggits=new ArrayList<>();
        Log.d(TAG, "separateNumber: "+arrayOfDiggits);
        Log.d(TAG, "separateNumber: "+(cursorPosition-position));
        for(int i=cursorPosition-position;i<arrayOfDiggits.size();i++)
        {
            Log.d(TAG, "separateNumber: "+i+" "+arrayOfDiggits.get(i));
            newNumberDiggits.add(arrayOfDiggits.get(i));

        }
        arrayOfDiggits.removeAll(newNumberDiggits);
        return new Numbers(cursorPosition,newNumberDiggits);
    }
    public Numbers uniteNumber(Numbers numbers) throws NumbersToBigForUniting
    {
        if(this.arrayOfDiggits.size()+numbers.arrayOfDiggits.size()>15)
            throw new NumbersToBigForUniting(this,numbers);
        else
        {

            for (Character character :
                this.arrayOfDiggits) {
                numbers.arrayOfDiggits.add(character);
            }
            this.arrayOfDiggits.clear();
        }
        return numbers;
    }

    public boolean addDot(int cursorPosition) {
        if(!hasDot)
        {

            dotPosition=cursorPosition-position;
            hasDot=true;
            return true;
        }
        else
        {
            return false;
        }


    }
    public void deleteDot()
    {

    }
}
