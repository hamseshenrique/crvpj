package com.software.hms.projeto.componentes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by root on 13/08/16.
 */
public class HmsMask implements TextWatcher {

    private static final String CARACTER = "#";
    private String mask;
    private String charAtual;
    private Editable editable;
    private Boolean formatou = Boolean.FALSE;

    public HmsMask(final String mask){
        this.mask = mask;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        charAtual = String.valueOf(mask.charAt(i));
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(!charAtual.equals(CARACTER) && !formatou){
            CharSequence charSequence1 = charAtual;
            formatou = Boolean.TRUE;
            editable.insert(i,charSequence1);
        }else if(charAtual.equals(CARACTER)){
            formatou = Boolean.FALSE;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        this.editable = editable;
    }
}