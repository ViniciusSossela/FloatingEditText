package com.vsossella.materialcomponents;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by vsossella on 30/10/17.
 */

public class ObservableFloating {


    String value;

    private FloatingEditText floatingEditText;

    public ObservableFloating(FloatingEditText floatingEditText) {
        this.floatingEditText = floatingEditText;
        this.floatingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public String get() {
        return value;
    }

    public void set(String value) {
        this.value = value;
        this.floatingEditText.setEditTextValue(value);
    }

}
