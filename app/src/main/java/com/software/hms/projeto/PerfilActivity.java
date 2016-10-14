package com.software.hms.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.software.hms.projeto.async.AlterarUsuario;
import com.software.hms.projeto.async.RegistrarAsync;
import com.software.hms.projeto.componentes.HmsMask;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.UsuarioDTO;
import com.software.hms.projeto.security.TokenService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private static final String TEMP_IMAGE_NAME = "tempImage";
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
    private ImageView imgPer;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private static final String VALOR_DEF_ESTADO = "Estado";
    private static final String VALOR_DEF_SEXO = "Sexo";
    private String senha;
    private Bitmap bitmapImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        context = this;

        Bundle extras = getIntent().getExtras();
        nome = (AutoCompleteTextView) findViewById(R.id.nome);

        autoCpf = (AutoCompleteTextView) findViewById(R.id.cpf);
        autoCpf.addTextChangedListener(new HmsMask("###.###.###-##"));

        autoData = (AutoCompleteTextView) findViewById(R.id.dataNas);
        autoData.addTextChangedListener(new HmsMask("##/##/####"));

        estado = (Spinner) findViewById(R.id.estado);
        imgPer = (ImageView) findViewById(R.id.imgPer);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this,
                R.array.adapterEstado,android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado.setAdapter(adapterEstado);
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
        ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter.createFromResource(this,
                R.array.adapterSexo,android.R.layout.simple_spinner_item);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapterSexo);
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

        if(extras != null){
            UsuarioDTO usuarioDTO = (UsuarioDTO) extras.get("usuario");

            nome.setText(usuarioDTO.getNome());
            autoCpf.setText(usuarioDTO.getCpf());
            autoData.setText(usuarioDTO.getDateNascimento());
            cidade.setText(usuarioDTO.getCidade());
            complemento.setText(usuarioDTO.getComplemento());
            password.setText(usuarioDTO.getSenha());
            email.setText(usuarioDTO.getEmail());
            confirmaPassword.setText(usuarioDTO.getSenha());
            senha = usuarioDTO.getSenha();

            if(!TextUtils.isEmpty(usuarioDTO.getUf())){
                estado.setSelection(adapterEstado.getPosition(usuarioDTO.getUf()));
            }
            if(!TextUtils.isEmpty(usuarioDTO.getSexo())){
                sexo.setSelection(adapterSexo.getPosition(usuarioDTO.getSexo()));
            }

            if(!TextUtils.isEmpty(HmsStatics.getFotoUsu())){
                byte[] bty = Base64.decode(HmsStatics.getFotoUsu(),Base64.URL_SAFE);
                bitmapImg = BitmapFactory.decodeByteArray(bty, 0, bty.length);
                imgPer.setImageBitmap(bitmapImg);
                imgPer.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }

        final CardView tirarFoto = (CardView) findViewById(R.id.tirarFoto);
        tirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Intent> intentList = new ArrayList<Intent>();
                final Context context = view.getContext();

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoIntent.putExtra("return-data", true);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)));
                intentList = addIntentsToList(context, intentList, pickIntent);
                intentList = addIntentsToList(context, intentList, takePhotoIntent);

                if (intentList.size() > 0) {
                    Intent chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                            "Foto do Perfil");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
                    startActivityForResult(chooserIntent,REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        final Button button = (Button) findViewById(R.id.confirma);
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
                    usuarioDTO.setCidade(cidade.getText().toString());
                    usuarioDTO.setComplemento(complemento.getText().toString());

                    if(!senha.equals(password.getText().toString())){
                        final TokenService tokenService = new TokenService();
                        usuarioDTO.setSenha(tokenService.criptSenha(password.getText().toString()));
                    }else{
                        usuarioDTO.setSenha(senha);
                    }
                    if(bitmapImg != null){
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmapImg.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();

                        usuarioDTO.setFoto(Base64.encodeToString(byteArray, Base64.NO_WRAP));
                    }

                    AlterarUsuario alterarUsuario = new AlterarUsuario(context);
                    alterarUsuario.execute(usuarioDTO);
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

    private File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        imageFile.getParentFile().mkdirs();

        return imageFile;
    }

    private List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try{
                if(data == null || data.getData() == null){
                    setImage(Uri.fromFile(getTempFile(context)));
                }else{
                    setImage(data.getData());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void setImage(final Uri uri) throws IOException {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");

        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        options.inSampleSize = calculoImage(options, 100, 100);
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        Matrix matrix = new Matrix();
        matrix.postRotate(getRotationFromCamera(uri));
        Bitmap bmOut = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        parcelFileDescriptor.close();

        imgPer.setImageBitmap(bmOut);
        bitmapImg = bmOut;
        imgPer.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

    private int calculoImage(final BitmapFactory.Options options,
                             final int reqWidth,final int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        while ((halfHeight / inSampleSize) >= reqHeight
                && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2;
        }

        return inSampleSize;
    }

    private int getRotationFromCamera(Uri imageFile) {
        int orientation = 0;
        try {
            Cursor cursor = context.getContentResolver().query(imageFile,
                    new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orientation;
    }


}
