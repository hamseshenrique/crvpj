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

public class MenuActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        LinearLayoutCompat lnNoticias = (LinearLayoutCompat)findViewById(R.id.lnNoticias);
        lnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),NoticiasActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        LinearLayoutCompat lnMensagens = (LinearLayoutCompat)findViewById(R.id.lnMensagens);
        lnMensagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MensagensActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayoutCompat lnProjetos = (LinearLayoutCompat)findViewById(R.id.lnProjetos);
        lnProjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProjetoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayoutCompat lnDescontos = (LinearLayoutCompat)findViewById(R.id.lnDescontos);
        lnDescontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedeDescontoAsync redeDescontoAsync = new RedeDescontoAsync(view.getContext());
                redeDescontoAsync.execute();
            }
        });
        LinearLayoutCompat lnAmigo = (LinearLayoutCompat)findViewById(R.id.lnAmigo);
        lnAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CartaoAmigoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        LinearLayoutCompat lnSejaAmigo = (LinearLayoutCompat)findViewById(R.id.lnSejaAmigo);
        lnSejaAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),AmigoActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.info);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InfoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
