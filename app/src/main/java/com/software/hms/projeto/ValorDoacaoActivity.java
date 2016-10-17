package com.software.hms.projeto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.software.hms.projeto.componentes.HmsMask;

public class ValorDoacaoActivity extends AppCompatActivity {

    private Integer parcelas;
    private Spinner cbParcelas;
    private Spinner cbValor;
    private static final String OUTRO = "Outro Valor";
    private String valor;
    private LinearLayout otrValor;
    private EditText txtOtrValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor_doacao);

        otrValor = (LinearLayout) findViewById(R.id.otrValor);

        txtOtrValor = (EditText) findViewById(R.id.txtOtrValor);
        txtOtrValor.addTextChangedListener(new HmsMask("##.###.###,##"));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterValor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbValor = (Spinner)findViewById(R.id.cbValor);
        cbValor.setAdapter(adapter);
        cbValor.setSelection(0);
        cbValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                valor = textView.getText().toString();
                if(valor.equals(OUTRO)){
                    otrValor.setVisibility(View.VISIBLE);
                    valor = "";
                }else{
                    otrValor.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapterParcelas = ArrayAdapter.createFromResource(this,
                R.array.adapterParcelas, android.R.layout.simple_spinner_item);
        adapterParcelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbParcelas = (Spinner) findViewById(R.id.cbParcelas);
        cbParcelas.setAdapter(adapterParcelas);
        cbParcelas.setSelection(0);
        cbParcelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                if(!TextUtils.isEmpty(textView.getText().toString())){
                    parcelas = Integer.valueOf(textView.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button continuar = (Button) findViewById(R.id.continuar);
        final Activity activity = this;
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForm()){
                    Intent intent = new Intent(activity,FormaPagamentoActivity.class);
                    intent.putExtra("parcela",parcelas);
                    intent.putExtra("valor",valor);
                    activity.startActivity(intent);
                }else{
                    Toast.makeText(activity,"Informe os Dados para Doação",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private Boolean validarForm(){
        Boolean result = Boolean.TRUE;

        if(TextUtils.isEmpty(valor)){
            result = false;
        }else if(valor.equals(OUTRO) && TextUtils.isEmpty(txtOtrValor.getText().toString())){
            txtOtrValor.setError("Informe um Valor");
            txtOtrValor.requestFocus();
            result = false;
        }else{
            txtOtrValor.setError(null);
        }

        if(parcelas == null){
            result = false;
        }

        return result;
    }
}