package com.software.hms.projeto.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.software.hms.projeto.R;
import com.software.hms.projeto.async.DetalheMensagemAsync;
import com.software.hms.projeto.dto.MensagemDTO;

/**
 * Created by root on 26/08/16.
 */
public class MensagemHolder extends RecyclerView.ViewHolder{

    public TextView textData;
    public TextView textDesc;
    private View itemView;


    public MensagemHolder(View itemView){
        super(itemView);

        textData = (TextView)itemView.findViewById(R.id.txtData);
        textDesc = (TextView)itemView.findViewById(R.id.txtDesc);
        this.itemView = itemView;
    }

    public void onClick(final MensagemDTO mensagemDTO){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetalheMensagemAsync detalheMensagemAsync = new DetalheMensagemAsync(itemView.getContext());
                detalheMensagemAsync.execute(mensagemDTO);
            }
        });
    }
}
