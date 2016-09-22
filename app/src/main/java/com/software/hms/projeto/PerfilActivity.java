package com.software.hms.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.software.hms.projeto.componentes.HmsMask;
import com.software.hms.projeto.dto.UsuarioDTO;

import java.io.File;
import java.io.FileNotFoundException;
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

    private void setImage(final Uri uri) throws FileNotFoundException {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(),null,bmOptions);

        int rotation = getRotationFromCamera(uri);
        if(rotation != 0){
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap bmOut = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            imgPer.setImageBitmap(bmOut);
        }else{
            imgPer.setImageBitmap(bitmap);
        }

        imgPer.setScaleType(ImageView.ScaleType.CENTER_CROP);



    }

    private int getRotationFromCamera(Uri imageFile) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageFile, null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }


}
