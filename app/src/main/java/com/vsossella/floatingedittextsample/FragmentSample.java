package com.vsossella.floatingedittextsample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsossella.materialcomponents.FloatingEditText;

/**
 * Created by vsossella on 29/12/17.
 */

public class FragmentSample extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sample, container, false);

        //Example of how to add a editext changed listener
        ((FloatingEditText) view.findViewById(R.id.valor_floating_edit_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Example of how to set text value
        ((FloatingEditText) view.findViewById(R.id.valor_floating_edit_text)).setEditTextValue("50");
    }

}
