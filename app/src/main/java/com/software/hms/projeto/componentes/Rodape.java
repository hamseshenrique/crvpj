package com.software.hms.projeto.componentes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
                HmsStatics.createDialogSair(context);
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

        final ImageView compartilhar = (ImageView) context.findViewById(R.id.compartilhar);
        compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, HmsStatics.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Conheça o App da Cruz Vermelha para você ! ");
                intent.putExtra(Intent.EXTRA_TEXT,context.getString(R.string.indicar));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(Intent.createChooser(intent, "Quero Indicar"));
                }
            }
        });
    }
}
