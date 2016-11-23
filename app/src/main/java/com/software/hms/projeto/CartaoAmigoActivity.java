package com.software.hms.projeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.UsuarioDTO;

import org.w3c.dom.Text;

public class CartaoAmigoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_amigo);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            UsuarioDTO usuarioDTO = (UsuarioDTO) extras.get("usuario");
            ImageView imgPer = (ImageView) findViewById(R.id.imgPer);
            TextView textView = (TextView) findViewById(R.id.nome);
            TextView txtCodigo = (TextView) findViewById(R.id.codigo);
            TextView txtValidade = (TextView) findViewById(R.id.txtValidade);

            txtCodigo.setText(usuarioDTO.getCodigo());
            textView.setText(usuarioDTO.getNome());

            if(!TextUtils.isEmpty(usuarioDTO.getValidade())){
                txtValidade.setText(usuarioDTO.getValidade());
            }


            if(!TextUtils.isEmpty(HmsStatics.getFotoUsu())){
                byte[] bty = Base64.decode(HmsStatics.getFotoUsu(),Base64.URL_SAFE);
                Bitmap bitmapImg = BitmapFactory.decodeByteArray(bty, 0, bty.length);
                imgPer.setImageBitmap(bitmapImg);
                imgPer.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
