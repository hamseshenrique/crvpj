package com.software.hms.projeto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.hms.projeto.ItemObjects;
import com.software.hms.projeto.R;
import com.software.hms.projeto.holders.MenuHolders;

import java.util.List;

/**
 * Created by root on 25/07/16.
 */
public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<MenuHolders> {

    private List<ItemObjects> itemList;

    public SolventRecyclerViewAdapter(List<ItemObjects> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MenuHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, null);
        MenuHolders rcv = new MenuHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MenuHolders holder, int position) {
        holder.numMenu.setText(itemList.get(position).getNumMenu());
        holder.descMenu.setText(itemList.get(position).getMenuName());
        holder.imgMenu.setImageResource(itemList.get(position).getMenuImage());
        holder.onClick(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
