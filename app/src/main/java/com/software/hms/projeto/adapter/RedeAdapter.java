package com.software.hms.projeto.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.software.hms.projeto.R;
import com.software.hms.projeto.dto.CategoriaDTO;
import com.software.hms.projeto.dto.LugaresDTO;

import java.util.List;

/**
 * Created by root on 28/08/16.
 */
public class RedeAdapter extends BaseExpandableListAdapter {

    private List<CategoriaDTO> list;
    private Context context;

    public RedeAdapter(final Context context, final List<CategoriaDTO> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getListLugares().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int itemPosition) {
        return list.get(groupPosition).getListLugares().get(itemPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int itemPosition) {
        return itemPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {

        final CategoriaDTO categoriaDTO = (CategoriaDTO) getGroup(groupPosition);
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_rede_layout,viewGroup,false);
        }

        final TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(categoriaDTO.getDescricao());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int itemPosition, boolean b, View view, ViewGroup viewGroup) {

        final LugaresDTO lugaresDTO = (LugaresDTO) getChild(groupPosition,itemPosition);
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_rede_layout,viewGroup,false);
        }

        byte[] bty = Base64.decode(lugaresDTO.getLogo(),Base64.URL_SAFE);

        AppCompatImageView imageView = (AppCompatImageView)view.findViewById(R.id.logo);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bty, 0, bty.length));

        final TextView textView = (TextView) view.findViewById(R.id.itemDesc);
        textView.setText(lugaresDTO.getDescricao());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int itemPosition) {
        return true;
    }
}
