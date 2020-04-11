package com.example.singlecalculator.utills.equation.utills;


import androidx.annotation.NonNull;

/**
 * Elements of equation represents number in edit text.
 *Note:
 *
 */

public class Number extends ElementOfEquation {
    private static final String TAG = "Numbers";
    public static final int MAXIMUM_NUMBER_SIZE =15;
    private boolean isMinus=false;
    private int dotPosition=-2;
    private boolean hasDot=false;
    private int numberOfDigits=0;
    public enum PreviousSymbol {digit,dot,sign}

    public Number(int position) {
        super(position,TypeOfElement.Number);
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

    public boolean decreaseNumberOfDiggits(int sizeOfDecreasing) {
        if(numberOfDigits>0)
            numberOfDigits=numberOfDigits-sizeOfDecreasing;
        return numberOfDigits!=0;
    }



    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public boolean increaseNumberOfDigits(int increasingSize)
    {
    if(numberOfDigits< MAXIMUM_NUMBER_SIZE ||increasingSize<0)
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
        return position+getSizeOfStringRepresentation();
    }

    @Override
    public int getSizeOfStringRepresentation() {
        int sizeOfDot=hasDot?1:0;
        int sizeOfMinus=isMinus?1:0;
        return numberOfDigits+sizeOfDot+sizeOfMinus;
    }









    public Number separateNumber(int cursorPosition) {
       Number newNumber=new Number(cursorPosition);
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

    public int defineCursorPositionRelativeToStartPosition(int cursorPosition) {
        return getPosition()-cursorPosition;
    }
    public int defineCursorPositionRelativeToFirstDigit(int cursorPosition)
    {
        return cursorPosition-defineFirstDigitPositionRelativeToStartOfString();
    }

    public void moveDotPosition(int i)
    {
        this.dotPosition=this.dotPosition+i;
    }
    public int defineFirstDigitPositionRelativeToStartOfString()
    {
        return getPosition()+getFirstDigitPosition();
    }
    public int getFirstDigitPosition()
    {
        return isMinus()?1:0;
    }

    public int calculateDotPosRelativeToStartOfString() {
        return this.getPosition()+this.getDotPosition();
    }

    public PreviousSymbol defineSymbolBeforeCursor(int cursorPosition) {
        int positionRelativeToStart=defineCursorPositionRelativeToStartPosition(cursorPosition);
        if(this.isHasDot() && positionRelativeToStart==getDotPosition()+1)
        {
            return PreviousSymbol.dot;
        }
        if(this.isMinus() && positionRelativeToStart==1)
            return PreviousSymbol.sign;
        return PreviousSymbol.digit;
    }
}
