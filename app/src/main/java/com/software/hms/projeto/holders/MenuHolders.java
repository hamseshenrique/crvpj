package com.software.hms.projeto.holders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.hms.projeto.AmigoActivity;
import com.software.hms.projeto.CartaoAmigoActivity;
import com.software.hms.projeto.ItemObjects;
import com.software.hms.projeto.MensagensActivity;
import com.software.hms.projeto.NoticiasActivity;
import com.software.hms.projeto.ProjetoActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.RedeDescontosActivity;
import com.software.hms.projeto.async.RedeDescontoAsync;
import com.software.hms.projeto.interfaces.ItemClickListener;

/**
 * Created by root on 25/07/16.
 */
public class MenuHolders extends RecyclerView.ViewHolder {

    public TextView numMenu;
    public TextView descMenu;
    public ImageView imgMenu;
    private View itemView;

    public MenuHolders(View itemView) {
        super(itemView);

        numMenu = (TextView) itemView.findViewById(R.id.numMenu);
        descMenu = (TextView) itemView.findViewById(R.id.descMenu);
        imgMenu = (ImageView) itemView.findViewById(R.id.imgMenu);
        this.itemView = itemView;
    }

    public void onClick(final ItemObjects itemList){
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemList.getNumMenu().toString().equals("01")) {
                    Intent intent = new Intent(view.getContext(),NoticiasActivity.class);
                    view.getContext().startActivity(intent);
                }else if(itemList.getNumMenu().toString().equals("02")) {
                    Intent intent = new Intent(view.getContext(), MensagensActivity.class);
                    view.getContext().startActivity(intent);
                }else if(itemList.getNumMenu().toString().equals("03")){
                    Intent intent = new Intent(view.getContext(), ProjetoActivity.class);
                    view.getContext().startActivity(intent);
                }else if(itemList.getNumMenu().toString().equals("04")){
                    RedeDescontoAsync redeDescontoAsync = new RedeDescontoAsync(view.getContext());
                    redeDescontoAsync.execute();
                }else if(itemList.getNumMenu().toString().equals("05")){
                    Intent intent = new Intent(view.getContext(),CartaoAmigoActivity.class);
                    view.getContext().startActivity(intent);
                }else if(itemList.getNumMenu().toString().equals("06")){
                    Intent intent = new Intent(view.getContext(),AmigoActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

}
