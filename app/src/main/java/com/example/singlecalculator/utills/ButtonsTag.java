package com.example.singlecalculator.utills;

public class ButtonsTag {
    public String text;
    public ButtonType buttonType;

    public ButtonsTag(ButtonType buttonType) {
        this.buttonType = buttonType;
    }
    public ButtonsTag setText(String text) {

        this.text=text;

        return this;
    }

    @Override
    public String toString() {
        return "ButtonsTag{" +
                "text='" + text + '\'' +
                ", buttonType=" + buttonType +
                '}';
    }

    public enum ButtonType{digit,action,delete,branches,equals,chengeSign,percent};



}
