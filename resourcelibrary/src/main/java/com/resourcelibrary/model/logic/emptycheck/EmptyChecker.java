package com.resourcelibrary.model.logic.emptycheck;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EmptyChecker {
    private ArrayList<String> edittextMessage;
    private ArrayList<EditText> edittextGroup;
    private ArrayList<OnNoValueAction> edittextActions;

    private ArrayList<OnNoValueAction> actionAfterCheck;
    private ArrayList<View> viewAfterCheck;

    public EmptyChecker() {
        edittextMessage = new ArrayList<>();
        edittextGroup = new ArrayList<>();
        edittextActions = new ArrayList<>();
    }

    public void put(String message, EditText input) {
        put(message, input, null);
    }

    public void put(String message, EditText input, OnNoValueAction noValueAction) {
        edittextMessage.add(message);
        edittextGroup.add(input);
        if (noValueAction != null) {
            edittextActions.add(noValueAction);
        } else {
            edittextActions.add(noAction);
        }
    }

    public ArrayList<String> checkEmpty() {
        actionAfterCheck = new ArrayList<>();
        viewAfterCheck = new ArrayList<>();
        ArrayList<String> EmptyInputNames = new ArrayList<>();
        for (int i = 0; i < edittextMessage.size(); i++) {
            if (checkEditText(edittextGroup.get(i))) {
                EmptyInputNames.add(edittextMessage.get(i));
                actionAfterCheck.add(edittextActions.get(i));
                viewAfterCheck.add(edittextGroup.get(i));
            }
        }
        return EmptyInputNames;
    }

    public void executeNoValueViewAction(int index) {
        if (actionAfterCheck == null) return;

        OnNoValueAction action = actionAfterCheck.get(index);
        View v = viewAfterCheck.get(index);
        action.onAction(v);
    }

    private Boolean checkEditText(EditText input) {
        return input.getText().toString().equals("");
    }

    private OnNoValueAction noAction = new OnNoValueAction() {
        @Override
        public void onAction(View v) {

        }
    };
}
