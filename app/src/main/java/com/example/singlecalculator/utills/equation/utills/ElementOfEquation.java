package com.example.singlecalculator.utills.equation.utills;

public class ElementOfEquation implements Comparable<ElementOfEquation> {
    int position;
    TypeOfElement type;

    public ElementOfEquation(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TypeOfElement getType() {
        return type;
    }

    public void setType(TypeOfElement type) {
        this.type = type;
    }


    public ElementOfEquation(int position,TypeOfElement typeOfElement) {
        this.position = position;
        this.type=typeOfElement;
    }
    public void increasePosition(int increasingSize)
    {
        position=position+increasingSize;
    }
    public int getLastPosition()
    {return -1;}
    public  int getSizeOfStringRepresentation()
    {return -1;}
    @Override
    public int compareTo(ElementOfEquation elementOfEquation) {

        return this.position-elementOfEquation.position;
    }

    public enum TypeOfElement{Action,Number,Branch}
}
