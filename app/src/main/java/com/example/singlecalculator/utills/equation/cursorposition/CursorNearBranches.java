package com.example.singlecalculator.utills.equation.cursorposition;

import com.example.singlecalculator.utills.ButtonsTag;

public class CursorNearBranches extends CursorState {
    @Override
    public boolean addAction(ButtonsTag tag) {
        return false;
    }

    @Override
    public boolean addDigits(ButtonsTag tag) {
        return false;
    }

    @Override
    public boolean addBranches() {
        return false;
    }

    @Override
    public boolean changeSign() {
        return false;
    }
}
