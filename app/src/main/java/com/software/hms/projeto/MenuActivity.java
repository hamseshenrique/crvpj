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
import android.widget.LinearLayout;

import com.software.hms.projeto.async.ObterUsuarioAsync;
import com.software.hms.projeto.async.RedeDescontoAsync;
import com.software.hms.projeto.componentes.Rodape;

public class MenuActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        LinearLayout lnNoticias = (LinearLayout) findViewById(R.id.lnNoticias);
        lnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NoticiasActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        LinearLayout lnMensagens = (LinearLayout) findViewById(R.id.lnMensagens);
        lnMensagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MensagensActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayout lnProjetos = (LinearLayout) findViewById(R.id.lnProjetos);
        lnProjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProjetoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayout lnDescontos = (LinearLayout) findViewById(R.id.lnRede);
        lnDescontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedeDescontoAsync redeDescontoAsync = new RedeDescontoAsync(view.getContext());
                redeDescontoAsync.execute();
            }
        });
        LinearLayout lnAmigo = (LinearLayout) findViewById(R.id.lnCartaoAmigo);
        lnAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObterUsuarioAsync obterUsuarioAsync = new ObterUsuarioAsync(view.getContext(),Boolean.FALSE);
                obterUsuarioAsync.execute();
            }
        });
        LinearLayout lnSejaAmigo = (LinearLayout) findViewById(R.id.lnSejaAmigo);
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
