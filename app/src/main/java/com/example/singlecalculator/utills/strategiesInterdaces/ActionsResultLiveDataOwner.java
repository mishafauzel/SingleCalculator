package com.example.singlecalculator.utills.strategiesInterdaces;

import androidx.lifecycle.LiveData;

import com.example.singlecalculator.utills.equation.actions.ActionsResult;

public interface ActionsResultLiveDataOwner {
    LiveData<ActionsResult[]> getLiveData();
}
