package com.software.hms.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.software.hms.projeto.async.EstadoAsync;
import com.software.hms.projeto.async.NotificacaoAsync;
import com.software.hms.projeto.async.RegistrarAsync;
import com.software.hms.projeto.componentes.HmsMask;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.EstadoDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.dto.UsuarioDTO;
import com.software.hms.projeto.interfaces.ObserverInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistreActivity extends AppCompatActivity implements ObserverInterface{

    private AutoCompleteTextView autoData;
    private AutoCompleteTextView autoCpf;
    private AutoCompleteTextView nome;
    private Spinner estado;
    private Spinner sexo;
    private Spinner cidade;
    private AutoCompleteTextView complemento;
    private EditText password;
    private Context context;
    private AutoCompleteTextView email;
    private EditText confirmaPassword;
    private String valorEstado;
    private String valorSexo;
    private String valorCidade;
    private int REQUEST_IMAGE_CAPTURE = 1;

    private static final String VALOR_DEF_ESTADO = "UF";
    private static final String VALOR_DEF_SEXO = "Sexo";
    private static final String VALOR_DEF_CIDADE = "Cidade";
    private SharedPreferences.Editor editor;
    private ObserverInterface observerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        this.context = this;
        this.observerInterface = this;

        nome = (AutoCompleteTextView) findViewById(R.id.nome);

        autoCpf = (AutoCompleteTextView) findViewById(R.id.cpf);
        autoCpf.addTextChangedListener(new HmsMask("###.###.###-##"));

        autoData = (AutoCompleteTextView) findViewById(R.id.dataNas);
        autoData.addTextChangedListener(new HmsMask("##/##/####"));

        estado = (Spinner) findViewById(R.id.estado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterEstado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado.setAdapter(adapter);
        estado.setSelection(0);
        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                valorEstado = textView.getText().toString();
                if(!VALOR_DEF_ESTADO.equals(valorEstado)){
                    EstadoAsync estadoAsync = new EstadoAsync(context,observerInterface);
                    estadoAsync.execute(valorEstado);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sexo = (Spinner) findViewById(R.id.sexo);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterSexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapter);
        sexo.setSelection(0);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                valorSexo = textView.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        cidade = (Spinner) findViewById(R.id.cidade);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterCidade, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidade.setAdapter(adapter);
        cidade.setSelection(0);

        cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                if(textView != null && textView.getText() != null){
                    valorCidade = textView.getText().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        complemento = (AutoCompleteTextView) findViewById(R.id.completeEndereco);
        password = (EditText) findViewById(R.id.password);
        confirmaPassword = (EditText) findViewById(R.id.confirmaPassword);
        email = (AutoCompleteTextView) findViewById(R.id.email);

        Button button = (Button) findViewById(R.id.registre);

        final SharedPreferences sharedPref = getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {

                    final UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setEmail(email.getText().toString());
                    usuarioDTO.setLogin(email.getText().toString());
                    usuarioDTO.setNome(nome.getText().toString());
                    usuarioDTO.setCpf(autoCpf.getText().toString());
                    usuarioDTO.setDateNascimento(autoData.getText().toString());
                    if (!VALOR_DEF_ESTADO.equals(valorEstado)) {
                        usuarioDTO.setUf(valorEstado);
                    }
                    if (!VALOR_DEF_SEXO.equals(valorSexo)) {
                        usuarioDTO.setSexo(valorSexo);
                    }
                    if (!VALOR_DEF_CIDADE.equals(valorCidade)) {
                        usuarioDTO.setCidade(valorCidade);
                    }
                    usuarioDTO.setComplemento(complemento.getText().toString());
                    usuarioDTO.setSenha(password.getText().toString());

                    final RegistrarAsync registrarAsync = new RegistrarAsync(context, editor);
                    registrarAsync.execute(usuarioDTO);
                }
            }
        });
    }


    private Boolean validarCampos(){
        Boolean isValido = Boolean.TRUE;
        if(TextUtils.isEmpty(nome.getText().toString())){
            nome.setError(getString(R.string.error_field_required));
            isValido = Boolean.FALSE;
        }
        if(TextUtils.isEmpty(email.getText().toString())
                && !email.getText().toString().contains("@")){
            email.setError(getString(R.string.error_field_required));
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError(getString(R.string.error_field_required));
            isValido = Boolean.FALSE;
        }else if(TextUtils.isEmpty(confirmaPassword.getText().toString())){
            confirmaPassword.setError(getString(R.string.error_field_required));
            isValido = Boolean.FALSE;
        }else if(!password.getText().toString().equals(confirmaPassword.getText().toString())){
            confirmaPassword.setError(getString(R.string.senhaDif));
            isValido = Boolean.FALSE;
        }

        return isValido;
    }

    @Override
    public void atualizar(RetornoDTO retornoDTO) {
        if(retornoDTO.getListEstado() != null &&
                !retornoDTO.getListEstado().isEmpty()){

            final ArrayAdapter<String> municipios = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);

            for (EstadoDTO estadoDTO:retornoDTO.getListEstado()) {
                municipios.add(estadoDTO.getMunicipio());
            }
            municipios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cidade.setAdapter(municipios);
            municipios.notifyDataSetChanged();
            cidade.setSelection(0);

        }
    }
}