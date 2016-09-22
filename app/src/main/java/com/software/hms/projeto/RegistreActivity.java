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

import com.software.hms.projeto.async.RegistrarAsync;
import com.software.hms.projeto.componentes.HmsMask;
import com.software.hms.projeto.dto.UsuarioDTO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistreActivity extends AppCompatActivity {

    private AutoCompleteTextView autoData;
    private AutoCompleteTextView autoCpf;
    private AutoCompleteTextView nome;
    private Spinner estado;
    private Spinner sexo;
    private AutoCompleteTextView cidade;
    private AutoCompleteTextView complemento;
    private EditText password;
    private Context context;
    private AutoCompleteTextView email;
    private EditText confirmaPassword;
    private String valorEstado;
    private String valorSexo;
    private int REQUEST_IMAGE_CAPTURE = 1;

    private static final String VALOR_DEF_ESTADO = "Estado";
    private static final String VALOR_DEF_SEXO = "Sexo";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        this.context = this;

        nome = (AutoCompleteTextView) findViewById(R.id.nome);

        autoCpf = (AutoCompleteTextView) findViewById(R.id.cpf);
        autoCpf.addTextChangedListener(new HmsMask("###.###.###-##"));

        autoData = (AutoCompleteTextView) findViewById(R.id.dataNas);
        autoData.addTextChangedListener(new HmsMask("##/##/####"));

        estado = (Spinner) findViewById(R.id.estado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterEstado,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado.setAdapter(adapter);
        estado.setSelection(0);
        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                valorEstado = textView.getText().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sexo = (Spinner) findViewById(R.id.sexo);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterSexo,android.R.layout.simple_spinner_item);
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

        cidade = (AutoCompleteTextView) findViewById(R.id.cidade);
        complemento = (AutoCompleteTextView) findViewById(R.id.completeEndereco);
        password = (EditText) findViewById(R.id.password);
        confirmaPassword = (EditText) findViewById(R.id.confirmaPassword);
        email = (AutoCompleteTextView) findViewById(R.id.email);

        Button button = (Button)findViewById(R.id.registre);

        final SharedPreferences sharedPref = getSharedPreferences("CRUZHMSVERMELHA",Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()){

                    final UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setEmail(email.getText().toString());
                    usuarioDTO.setLogin(email.getText().toString());
                    usuarioDTO.setNome(nome.getText().toString());
                    usuarioDTO.setCpf(autoCpf.getText().toString());
                    usuarioDTO.setDateNascimento(autoData.getText().toString());
                    if(!VALOR_DEF_ESTADO.equals(valorEstado)){
                        usuarioDTO.setUf(valorEstado);
                    }
                    if(!VALOR_DEF_SEXO.equals(valorSexo)){
                        usuarioDTO.setSexo(valorSexo);
                    }
                    usuarioDTO.setCidade(cidade.getText().toString());
                    usuarioDTO.setComplemento(complemento.getText().toString());
                    usuarioDTO.setSenha(password.getText().toString());

                    final RegistrarAsync registrarAsync = new RegistrarAsync(context,editor);
                    registrarAsync.execute(usuarioDTO);

                }
            }
        });

       /* Button btnFoto = (Button) findViewById(R.id.foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try{
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(imageFileName,".jpg",storageDir);

                editor.putString("FOTO"+email.getText().toString(),image.getAbsolutePath());
                editor.commit();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
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
}