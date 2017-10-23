package com.vsossella.materialcomponents;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by vsossella on 23/10/17.
 */

public class FloatingEditText extends RelativeLayout {

    LayoutInflater mInflater;
    View layoutView;

    public FloatingEditText(Context context) {
        super(context);
    }

    public FloatingEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);

        init();
        loadCustomAttributeSet(context, attrs);
    }

    private TextView getFloatingTextView() {
        return (TextView) layoutView.findViewById(R.id.text_view_hint_material);
    }

    private EditText getEditText() {
        return (EditText) layoutView.findViewById(R.id.edit_text_material);
    }

    private ImageView getIconImageView() {
        return (ImageView) layoutView.findViewById(R.id.image_view_material_edit_text);
    }

    private void init() {
        layoutView = mInflater.inflate(R.layout.floating_edit_text, this, true);
        addAnimation();
    }

    private void addAnimation() {
        final TextView floatingLabel = getFloatingTextView();
        final EditText editText = getEditText();
        final float customInitialYTextView = floatingLabel.getY() - 5f;
        floatingLabel.setY(customInitialYTextView);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1 && before == 0) {

                    ObjectAnimator moveUp = ObjectAnimator.ofFloat(floatingLabel, "y", customInitialYTextView);
                    ObjectAnimator showAnim = ObjectAnimator.ofFloat(floatingLabel, "alpha", 100f);
                    showAnim.setDuration(100);
                    moveUp.setDuration(100);
                    AnimatorSet animationFadein = new AnimatorSet();
                    animationFadein.play(moveUp).with(showAnim);
                    animationFadein.start();
                }

                if(start == 0 && count == 0) {
                    ObjectAnimator moveDown = ObjectAnimator.ofFloat(floatingLabel, "y", editText.getY() - 10f);
                    ObjectAnimator hideAnim = ObjectAnimator.ofFloat(floatingLabel, "alpha", 0f);
                    hideAnim.setDuration(100);
                    moveDown.setDuration(100);
                    AnimatorSet animationFadeout = new AnimatorSet();
                    animationFadeout.play(moveDown).with(hideAnim);
                    animationFadeout.start();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void loadCustomAttributeSet(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingEditText,
                0, 0);
        try {
            String floatingLabelText = attributes.getString(R.styleable.FloatingEditText_floating_label_text);
            getFloatingTextView().setText(floatingLabelText);
            getEditText().setHint(floatingLabelText);


            float editTextFontSize = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_edit_text_font_size, 16);
            getEditText().setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextFontSize);

            String editTextValue = attributes.getString(R.styleable.FloatingEditText_edit_text_value);
            getEditText().setText(editTextValue);


            float floatingLabelTextSize = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_floating_label_font_size, 10);
            getFloatingTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, floatingLabelTextSize);

            int imageResourceId = attributes.getResourceId(R.styleable.FloatingEditText_icon_left, 0);
            getIconImageView().setImageResource(imageResourceId);
        } finally {
            attributes.recycle();
        }
    }

}
