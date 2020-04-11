package com.example.singlecalculator.utills;

public class ButtonsTag {
    private Character text;
    public ButtonType buttonType;

    public ButtonsTag(ButtonType buttonType) {
        this.buttonType = buttonType;
    }
    public ButtonsTag setText(Character text) {

        this.text=text;

        return this;
    }

    public Character getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ButtonsTag{" +
                "text='" + text + '\'' +
                ", buttonType=" + buttonType +
                '}';
    }

    public enum ButtonType{digit,action,delete,branches,equals,chengeSign,percent,dot,equation}



}
