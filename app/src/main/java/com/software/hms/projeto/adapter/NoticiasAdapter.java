package com.software.hms.projeto.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
        holder.txtBody.setText(list.get(position).getCabecalho());
        if(!TextUtils.isEmpty(list.get(position).getImg())){
            byte[] bty = Base64.decode(list.get(position).getImg(),Base64.URL_SAFE);
            holder.img.setImageBitmap(BitmapFactory.decodeByteArray(bty, 0, bty.length));
        }
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
