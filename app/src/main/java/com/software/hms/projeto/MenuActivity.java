package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.software.hms.projeto.async.RedeDescontoAsync;
import com.software.hms.projeto.componentes.Rodape;

public class MenuActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        ImageView lnNoticias = (ImageView) findViewById(R.id.lnNoticias);
        lnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NoticiasActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        ImageView lnMensagens = (ImageView) findViewById(R.id.lnMensagens);
        lnMensagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MensagensActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        ImageView lnProjetos = (ImageView) findViewById(R.id.lnProjetos);
        lnProjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProjetoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        ImageView lnDescontos = (ImageView) findViewById(R.id.lnDescontos);
        lnDescontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedeDescontoAsync redeDescontoAsync = new RedeDescontoAsync(view.getContext());
                redeDescontoAsync.execute();
            }
        });
        ImageView lnAmigo = (ImageView) findViewById(R.id.lnAmigo);
        lnAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CartaoAmigoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        ImageView lnSejaAmigo = (ImageView) findViewById(R.id.lnSejaAmigo);
        lnSejaAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),AmigoActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        Rodape rodape = new Rodape();
        rodape.onClickButtons(this);
    }
}
