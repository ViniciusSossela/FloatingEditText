package com.vsossella.floatingedittextsample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vsossella.materialcomponents.FloatingEditText;
import com.vsossella.materialcomponents.ObservableFloating;

/**
 * Created by vsossella on 29/12/17.
 */

public class FragmentText extends Fragment {

    ObservableFloating renda;
    FloatingEditText floatingEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text, container, false);


        floatingEditText = ((FloatingEditText) view.findViewById(R.id.floating));

        renda = new ObservableFloating(floatingEditText);

        ((FloatingEditText) view.findViewById(R.id.floating)).addTextChangedListener(new TextWatcher() {
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

        final Button button = (Button) view.findViewById(R.id.button_test);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                load();
            }
        });

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        floatingEditText.setEditTextValue("50");
    }

    public void load() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new FragmentTextTwo())
                .addToBackStack(null)
                .commit();


    }


}
