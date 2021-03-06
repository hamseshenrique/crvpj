package com.software.hms.projeto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.hms.projeto.componentes.Rodape;
import com.software.hms.projeto.dto.DetalheMensagemDTO;

public class DetalheMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_msg);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView txtTitle = (TextView) findViewById(R.id.txtTitulo);
            txtTitle.setText((String)extras.get("title"));


            DetalheMensagemDTO detalheMensagemDTO = (DetalheMensagemDTO) extras.get("detalhe");
            TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
            txtDesc.setText(detalheMensagemDTO.getDescricao());

            if(!TextUtils.isEmpty(detalheMensagemDTO.getImg())){
                byte[] bty = Base64.decode(detalheMensagemDTO.getImg(),Base64.URL_SAFE);
                AppCompatImageView imageView = (AppCompatImageView)findViewById(R.id.imgMsg);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(bty, 0, bty.length));
                imageView.setVisibility(View.VISIBLE);
            }
        }

        Rodape rodape = new Rodape();
        rodape.onClickButtons(this);
    }
}
