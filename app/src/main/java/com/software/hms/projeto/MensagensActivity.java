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

import com.software.hms.projeto.adapter.MensagemAdapter;
import com.software.hms.projeto.async.HmsRest;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.TipoMensagemEnum;
import com.software.hms.projeto.interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;

public class MensagensActivity extends AppCompatActivity
        implements ObserverInterface{

    private RecyclerView recyclerView;
    private MensagemAdapter mensagemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<MensagemDTO> list = new ArrayList<MensagemDTO>();

        mensagemAdapter = new MensagemAdapter(list);
        recyclerView.setAdapter(mensagemAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(HmsStatics.getEmail(),null);

        HmsRest hmsRest = new HmsRest(this);
        hmsRest.loadMensagens(token,TipoMensagemEnum.MENSAGEM_USUARIO);

        ImageView imageView = (ImageView) findViewById(R.id.info);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InfoActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public void atualizar(RetornoDTO retornoDTO) {
        mensagemAdapter.clear();
        mensagemAdapter.addAll(retornoDTO.getListaMensagem());
        mensagemAdapter.notifyDataSetChanged();
    }
}
