package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.software.hms.projeto.adapter.RedeAdapter;
import com.software.hms.projeto.dto.CategoriaDTO;
import com.software.hms.projeto.dto.LugaresDTO;
import com.software.hms.projeto.dto.RetornoDTO;

import java.util.ArrayList;
import java.util.List;

public class RedeDescontosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rede_descontos);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            RetornoDTO retornoDTO = (RetornoDTO) extras.get("retorno");
            final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            final List<CategoriaDTO> listCategoria = retornoDTO.getListCategoria();

            expandableListView.setAdapter(new RedeAdapter(this,listCategoria));
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition,
                                            int childPosition, long id) {
                    final Intent intent = new Intent(view.getContext(),RedeDetalheActivity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putSerializable("detalhe",listCategoria.get(groupPosition)
                            .getListLugares().get(childPosition));
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                    return false;
                }
            });
        }

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
