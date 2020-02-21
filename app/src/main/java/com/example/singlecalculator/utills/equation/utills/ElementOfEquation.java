package com.example.singlecalculator.utills.equation.utills;

public class ElementOfEquation implements Comparable<ElementOfEquation> {
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

    int position;
    TypeOfElement type;
    public ElementOfEquation(int position,TypeOfElement typeOfElement) {
        this.position = position;
        this.type=typeOfElement;
    }
    public void increasePosition(int increasingSize)
    {
        position=position+increasingSize;
    }
    @Override
    public int compareTo(ElementOfEquation elementOfEquation) {

        return this.position-elementOfEquation.position;
    }
    public String toDocumentationString()
    {
        return "I am just stub";
    }
    public enum TypeOfElement{Action,Number,Branch}
}
