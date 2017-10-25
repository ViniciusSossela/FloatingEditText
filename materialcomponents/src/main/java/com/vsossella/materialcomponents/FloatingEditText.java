package com.vsossella.materialcomponents;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;


/**
 * Created by vsossella on 23/10/17.
 */

public class FloatingEditText extends RelativeLayout {

    LayoutInflater mInflater;
    View layoutView;
    boolean animateUp = true;
    boolean isFirstTime = true;
    boolean shouldAnimateOnEnterText = true;
    boolean shouldAnimateOnCleanText = true;
    boolean applyDecimalMask = false;


    public void addMensagemErro(String mensagemErro) {

        getTextViewError().setText(mensagemErro);

        if (mensagemErro == null || mensagemErro.isEmpty()) {
            getTextViewError().setVisibility(INVISIBLE);
            getUnderlineEditText().setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            getTextViewError().setVisibility(VISIBLE);
            getUnderlineEditText().setBackgroundColor(getResources().getColor(R.color.red));
        }

    }

    public void clearMensagemErro() {
        getTextViewError().setText("");
        getTextViewError().setVisibility(INVISIBLE);
        getUnderlineEditText().setBackgroundColor(getResources().getColor(R.color.white));
    }

    TextView floatingLabel;
    EditText editText;
    View underlineEditText;

    public FloatingEditText(Context context) {
        super(context);
    }

    public FloatingEditText(Context context, AttributeSet attrs) {

        super(context, attrs);
        mInflater = LayoutInflater.from(context);

        init();
        loadCustomAttributeSet(context, attrs);
        addAnimation();
    }

    private TextView getFloatingTextView() {
        return (TextView) layoutView.findViewById(R.id.text_view_hint_material);
    }

    private TextView getTextViewError() {
        return (TextView) layoutView.findViewById(R.id.text_view_error);
    }

    private EditText getEditText() {
        return (EditText) layoutView.findViewById(R.id.edit_text_material);
    }

    private ImageView getIconImageView() {
        return (ImageView) layoutView.findViewById(R.id.image_view_material_edit_text);
    }

    private View getUnderlineEditText() {
        return layoutView.findViewById(R.id.edit_text_under_line);
    }


    private void init() {
        layoutView = mInflater.inflate(R.layout.floating_edit_text, this, true);
        floatingLabel = getFloatingTextView();
        editText = getEditText();
        underlineEditText = getUnderlineEditText();
    }

    private void addAnimation() {
        getEditText().addTextChangedListener(animationFloatingTextViewWatcher);

        if (applyDecimalMask)
            getEditText().addTextChangedListener(currencyFormatWatcher);
    }

    private TextWatcher currencyFormatWatcher = new TextWatcher() {
        private String current = "";


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!s.toString().equals(current)) {
                clearMensagemErro();
                getEditText().removeTextChangedListener(this);

                String cleanString = s.toString().replaceAll("[$,.]", "");

                double parsed = Double.parseDouble(cleanString);
                String formatted = parsed == 0 ? "" : getNumberFormat().format((parsed / 100));

                current = formatted;
                getEditText().setText(formatted);
                getEditText().setSelection(formatted.length());

                getEditText().addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher animationFloatingTextViewWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (isFirstTime) {
                isFirstTime = false;
                return;
            }

            if (s.length() > 0 && shouldAnimateOnEnterText) {
                shouldAnimateOnEnterText = false;
                shouldAnimateOnCleanText = true;
                if (animateUp) {
                    animateField(editText.getY() - 30f, 100f);
                } else {
                    setupRightYPosition(underlineEditText.getY() - 10f);
                    animateField(underlineEditText.getY() + 10f, 100f);
                }
            }


            if (s.length() == 0 && shouldAnimateOnCleanText) {
                shouldAnimateOnCleanText = false;
                shouldAnimateOnEnterText = true;
                if (animateUp) {
                    animateField(floatingLabel.getY() + 10f, 0f);
                } else {
                    animateField(underlineEditText.getY() - 10f, 0f);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void setupRightYPosition(float yPosition) {
        getFloatingTextView().setY(yPosition);
    }

    private void animateField(float yPosition, float alpha) {
        final TextView floatingLabel = getFloatingTextView();

        ObjectAnimator moveUp = ObjectAnimator.ofFloat(floatingLabel, "y", yPosition);
        ObjectAnimator showAnim = ObjectAnimator.ofFloat(floatingLabel, "alpha", alpha);
        showAnim.setDuration(100);
        moveUp.setDuration(100);
        AnimatorSet animationFadein = new AnimatorSet();
        animationFadein.play(moveUp).with(showAnim);
        animationFadein.start();
    }


    private NumberFormat getNumberFormat() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setGroupingSeparator('.');
        dfs.setMonetaryDecimalSeparator(',');
        ((DecimalFormat) formatter).setNegativePrefix("-");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(dfs);
        return formatter;
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

            String editTextError = attributes.getString(R.styleable.FloatingEditText_edit_text_error);
            addMensagemErro(editTextError);

            animateUp = attributes.getBoolean(R.styleable.FloatingEditText_edit_text_animate_up, false);
            applyDecimalMask = attributes.getBoolean(R.styleable.FloatingEditText_edit_text_decimal_mask, false);
            float editTextFontSize = attributes.getDimensionPixelSize(R.styleable.FloatingEditText_edit_text_font_size, 16);
            getEditText().setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextFontSize);

            int editTextMaxLength = attributes.getInteger(R.styleable.FloatingEditText_edit_text_max_length, 150);
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(editTextMaxLength);
            getEditText().setFilters(filterArray);


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
