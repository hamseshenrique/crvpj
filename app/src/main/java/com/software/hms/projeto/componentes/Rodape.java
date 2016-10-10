package com.software.hms.projeto.componentes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.software.hms.projeto.InfoActivity;
import com.software.hms.projeto.LoginActivity;
import com.software.hms.projeto.PerfilActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.async.ObterUsuarioAsync;

/**
 * Created by root on 19/09/16.
 */
public class Rodape {

    public void onClickButtons(final Activity context){
        final ImageView imageView = (ImageView) context.findViewById(R.id.info);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),InfoActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        final ImageView logout = (ImageView)context.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(context.getString(R.string.sair_app));
                alertDialogBuilder.setNegativeButton(context.getString(R.string.nao),new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                });
                alertDialogBuilder.setPositiveButton(context.getString(R.string.sim),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                HmsStatics.setEmail("");
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                });

                alertDialogBuilder.create().show();
            }
        });

        final ImageView perfil  = (ImageView) context.findViewById(R.id.perfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObterUsuarioAsync obterUsuarioAsync = new ObterUsuarioAsync(context,Boolean.TRUE);
                obterUsuarioAsync.execute();
            }
        });
    }
}
