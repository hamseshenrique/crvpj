package com.software.hms.projeto.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.hms.projeto.R;
import com.software.hms.projeto.async.DetalheMensagemAsync;
import com.software.hms.projeto.dto.MensagemDTO;

/**
 * Created by root on 28/08/16.
 */
public class ProjetoHolder extends RecyclerView.ViewHolder{

    public TextView txtDesc;
    public ImageView img;

    public ProjetoHolder(final View itemView){
        super(itemView);
        txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
        img = (ImageView) itemView.findViewById(R.id.img);
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
