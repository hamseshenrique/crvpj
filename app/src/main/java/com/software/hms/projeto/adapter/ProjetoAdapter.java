package com.software.hms.projeto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.hms.projeto.R;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.dto.ProjetoDTO;
import com.software.hms.projeto.holders.ProjetoHolder;

import java.util.List;

/**
 * Created by root on 28/08/16.
 */
public class ProjetoAdapter extends RecyclerView.Adapter<ProjetoHolder>{

    private List<MensagemDTO> list;

    public ProjetoAdapter(final List<MensagemDTO> list){
        this.list = list;
    }

    @Override
    public ProjetoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_layout, parent,false);
        ProjetoHolder projetoHolder = new ProjetoHolder(layoutView);
        return projetoHolder;
    }

    @Override
    public void onBindViewHolder(ProjetoHolder holder, int position) {
        holder.txtDesc.setText(list.get(position).getDescricao());
        holder.onClick(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear(){
        this.list.clear();
    }

    public void addAll(final List<MensagemDTO> list){
        this.list.addAll(list);
    }
}
