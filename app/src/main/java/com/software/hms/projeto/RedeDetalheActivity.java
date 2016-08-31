package com.software.hms.projeto;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.widget.TextView;

import com.software.hms.projeto.dto.LugaresDTO;

public class RedeDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rede_detalhe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final LugaresDTO lugaresDTO = (LugaresDTO) extras.getSerializable("detalhe");
            byte[] bty = Base64.decode(lugaresDTO.getLogo(),Base64.URL_SAFE);

            AppCompatImageView imageView = (AppCompatImageView)findViewById(R.id.logo);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bty, 0, bty.length));

            final TextView textView = (TextView) findViewById(R.id.estado);
            textView.setText(lugaresDTO.getCidadeEstado());

            final TextView txtDetalhe = (TextView) findViewById(R.id.txtDetalhe);
            txtDetalhe.setText(lugaresDTO.getDetalhe());

        }
    }
}
