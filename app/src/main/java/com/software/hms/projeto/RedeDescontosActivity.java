package com.software.hms.projeto;

import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.software.hms.projeto.adapter.RedeAdapter;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.componentes.Rodape;
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

        Button indicar = (Button) findViewById(R.id.indicar);
        indicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","leonardo@acaocruzvermelha.com.br", null));
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto","contato@amigocvb.com.br", null));
                intent.putExtra(Intent.EXTRA_EMAIL,HmsStatics.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Quero indicar um parceiro para CVB");

                StringBuilder str = new StringBuilder();
                str.append("Envie os dados do estabelecimento para que possamos entrar em contato!");
                str.append("\n");
                str.append("\n");
                str.append("Nome:");
                str.append("\n");
                str.append("Tel:");
                str.append("\n");
                str.append("Email:");
                str.append("\n");

                str.append("Endereço:");

                intent.putExtra(Intent.EXTRA_TEXT,str.toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Quero Indicar um Parceiro"));
                }
            }
        });

        Rodape rodape = new Rodape();
        rodape.onClickButtons(this);
    }
}
