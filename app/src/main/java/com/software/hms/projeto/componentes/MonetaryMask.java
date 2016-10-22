package com.software.hms.projeto.componentes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by hms on 19/10/16.
 */

public class MonetaryMask implements TextWatcher {

    private boolean mUpdating;
    private EditText mEditText;
    private NumberFormat mNF = NumberFormat.getCurrencyInstance();

    public MonetaryMask(final EditText editText){
        mEditText = editText;
    }

    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int after) {
        if (this.mUpdating) {
            this.mUpdating = false;
            return;
        }

        this.mUpdating = true;
        String formattedValue = cs.toString().replaceAll("[^\\d.]", "");

        try {
            double value = (Double.parseDouble(formattedValue) / 100);
            formattedValue = this.mNF.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            formattedValue = this.mNF.format(0.0D);
        }

        this.mEditText.setText(formattedValue);
        this.mEditText.setSelection(formattedValue.length());
    }

    @Override
    public void beforeTextChanged(CharSequence cs, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable e) {
    }
}
