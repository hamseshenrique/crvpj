package com.software.hms.projeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Window;
import android.view.WindowManager;

import com.software.hms.projeto.adapter.SolventRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ItemObjects> gaggeredList = getListItemData();

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(gaggeredList);
        recyclerView.setAdapter(rcAdapter);

    }

    private List<ItemObjects> getListItemData(){
        List<ItemObjects> listViewItems = new ArrayList<ItemObjects>();

        listViewItems.add(new ItemObjects("01","Noticias", R.drawable.icon_noticias));
        listViewItems.add(new ItemObjects("02","Mensagens", R.drawable.icon_menssagens));
        listViewItems.add(new ItemObjects("03","Projetos", R.drawable.icon_project));
        listViewItems.add(new ItemObjects("04","Rede de Descontos",R.drawable.icon_rede_descontos));
        listViewItems.add(new ItemObjects("05","Cartão Amigo", R.drawable.icon_card));
        listViewItems.add(new ItemObjects("06","Seja um Amigo", R.drawable.icon_donation));


        return listViewItems;
    }
}
