package com.software.hms.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.software.hms.projeto.adapter.NoticiasAdapter;
import com.software.hms.projeto.async.HmsRest;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.componentes.Rodape;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.TipoMensagemEnum;
import com.software.hms.projeto.interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class NoticiasActivity extends AppCompatActivity implements ObserverInterface{

    private NoticiasAdapter noticiasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noticiasAdapter = new NoticiasAdapter(new ArrayList<MensagemDTO>());
        recyclerView.setAdapter(noticiasAdapter);

        HmsRest hmsRest = new HmsRest(this);
        SharedPreferences sharedPreferences = getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(HmsStatics.getEmail(),null);
        hmsRest.loadMensagens(token, TipoMensagemEnum.MENSAGEM_NOTICIA);
        Rodape rodape = new Rodape();
        rodape.onClickButtons(this);
    }

    @Override
    public void atualizar(RetornoDTO retornoDTO) {
        noticiasAdapter.clear();
        noticiasAdapter.addAll(retornoDTO.getListaMensagem());
        noticiasAdapter.notifyDataSetChanged();
    }
}
