package com.example.singlecalculator.utills.equation.utills;


import androidx.annotation.NonNull;

public class Numbers extends ElementOfEquation {
    private static final String TAG = "Numbers";
    private static final int NAXIMUM_NUMBER_SIZE=15;
    private boolean isMinus=false;
    private int dotPosition;
    private boolean hasDot=false;
    private int numberOfDigits=0;
    public Numbers(int position) {
        super(position,TypeOfElement.Number);
    }
    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public boolean increaseNumberOfDigits(int increasingSize)
    {
    if(numberOfDigits<NAXIMUM_NUMBER_SIZE||increasingSize<0)
    {
        numberOfDigits=numberOfDigits+increasingSize;
        return true;
    }
    return false;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("startPosition");
        sb.append(this.position);
        sb.append("endPosition");
        sb.append(this.getLastPosition());
            return sb.toString();
    }
    @Override
    public int getLastPosition()
    {
        int sizeOfDot=hasDot?1:0;
        int minusSize=isMinus?1:0;

        return position+getNumberOfFields()+sizeOfDot+minusSize;
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
        if(isMinus)
            dotPosition++;
        else
            dotPosition--;
    }

    public int getDotPosition() {
        return dotPosition;
    }

    public void setDotPosition(int dotPosition) {
        this.dotPosition = dotPosition;
    }




    public int getNumberOfFields()
    {
        int minusSize=isMinus?1:0;
        int dotSize=hasDot?1:0;
        return minusSize+dotSize+getNumberOfDigits();
    }


    public Numbers separateNumber(int cursorPosition) {
       Numbers newNumber=new Numbers(cursorPosition);
       int numberOfFieldsInFirstNumber=cursorPosition-this.position;
       if(this.isMinus())
           numberOfFieldsInFirstNumber=numberOfFieldsInFirstNumber--;
       if(this.isHasDot())
       {
           if(this.position+dotPosition>=cursorPosition)
           {
               newNumber.setHasDot(true);
               newNumber.setDotPosition(this.position+dotPosition-cursorPosition);
               this.setHasDot(false);
           }
           else
           {
               numberOfFieldsInFirstNumber=numberOfFieldsInFirstNumber--;
           }
       }
       newNumber.setNumberOfDigits(this.getNumberOfDigits()-numberOfFieldsInFirstNumber);
       this.setNumberOfDigits(numberOfFieldsInFirstNumber);

        return newNumber;
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
