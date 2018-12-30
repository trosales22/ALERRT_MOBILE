package com.capstone.alerrt_app.classes;

import android.widget.EditText;

public class Validation {
    public static boolean validateEditText(EditText[] fields){
        boolean isEmpty = false;

        for(int i=0; i<fields.length; i++){
            EditText currentField=fields[i];
            if(currentField.getText().toString().trim().length()<=0){
                currentField.setError("This item or field is required.");
                currentField.requestFocus();
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    public static boolean validateSingleEditText(EditText field){
        boolean isEmpty = false;

        if(field.getText().toString().trim().length()<=0){
            field.setError("This item or field is required.");
            field.requestFocus();
            isEmpty = true;
        }

        return isEmpty;
    }
}
