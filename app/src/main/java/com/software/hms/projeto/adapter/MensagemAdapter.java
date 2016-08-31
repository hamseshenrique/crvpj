package com.software.hms.projeto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.hms.projeto.R;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.holders.MensagemHolder;

import java.util.List;

/**
 * Created by root on 26/08/16.
 */
public class MensagemAdapter extends RecyclerView.Adapter<MensagemHolder>{

    private List<MensagemDTO> mensagemDTOs;

    public MensagemAdapter(final List<MensagemDTO> mensagemDTOs){
        this.mensagemDTOs = mensagemDTOs;
    }

    @Override
    public MensagemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout,parent,false);
        MensagemHolder mensagemHolder = new MensagemHolder(layoutView);
        return mensagemHolder;
    }

    @Override
    public void onBindViewHolder(MensagemHolder holder, int position) {
        holder.textData.setText(mensagemDTOs.get(position).getData());
        holder.textDesc.setText(mensagemDTOs.get(position).getDescricao());
        holder.onClick(mensagemDTOs.get(position));
    }

    public void clear(){
        mensagemDTOs.clear();
    }

    public void addAll(final List<MensagemDTO> list){
        this.mensagemDTOs.addAll(list);
    }

    @Override
    public int getItemCount() {
        return this.mensagemDTOs.size();
    }
}
