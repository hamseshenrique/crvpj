package com.software.hms.projeto.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.software.hms.projeto.R;
import com.software.hms.projeto.async.DetalheMensagemAsync;
import com.software.hms.projeto.dto.MensagemDTO;

import org.w3c.dom.Text;

/**
 * Created by root on 26/08/16.
 */
public class NoticiasHolder extends RecyclerView.ViewHolder{

    public TextView txtBody;

    public NoticiasHolder(View item){
        super(item);

        txtBody = (TextView) item.findViewById(R.id.txtDesc);
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
