package com.software.hms.projeto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.hms.projeto.ItemObjects;
import com.software.hms.projeto.R;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.holders.NoticiasHolder;

import java.util.List;

/**
 * Created by root on 26/08/16.
 */
public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasHolder>{

    private List<MensagemDTO> list;

    public NoticiasAdapter(final List<MensagemDTO> list){
        this.list = list;
    }
    @Override
    public NoticiasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticias_layout, parent,false);
        NoticiasHolder noticiasHolder = new NoticiasHolder(layoutView);
        return noticiasHolder;
    }

    @Override
    public void onBindViewHolder(NoticiasHolder holder, int position) {
        holder.txtHead.setText(list.get(position).getCabecalho());
        holder.txtBody.setText(list.get(position).getDescricao());
        holder.onClick(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear(){
        list.clear();
    }

    public void addAll(final List<MensagemDTO> list){
        this.list.addAll(list);
    }
}
