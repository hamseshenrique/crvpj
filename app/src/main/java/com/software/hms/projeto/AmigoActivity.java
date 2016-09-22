package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.software.hms.projeto.componentes.Rodape;

public class AmigoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo);

        Rodape rodape = new Rodape();
        rodape.onClickButtons(this);
    }
}
