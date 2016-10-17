package com.software.hms.projeto.componentes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.software.hms.projeto.LoginActivity;
import com.software.hms.projeto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 28/08/16.
 */
public class HmsStatics {

    private static String email;
    private static String fotoUsu;
    public static final String SERVER = "http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080";
//    public static final String SERVER = "http://192.168.100.13:8080";
    //public static final String YOUR_TOKEN = "APP_USR-46294dde-90eb-4e0e-bd8f-437391748e9d";
    public static final String YOUR_TOKEN = "TEST-293b3f85-122c-4d38-b066-b22a21b98a0c";

    public HmsStatics(){}

    public static String getEmail(){
        return email;
    }

    public static void setEmail(final String email){
        HmsStatics.email = email;
    }

    public static String getFotoUsu() {
        return fotoUsu;
    }

    public static void setFotoUsu(String fotoUsu) {
        HmsStatics.fotoUsu = fotoUsu;
    }

    public static Date formatDate(final String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return simpleDateFormat.parse(date);
    }

    /**
     *
     * @param context
     */
    public static void createDialogSair(final Activity context){
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final LayoutInflater inflater = context.getLayoutInflater();
        final View infView = inflater.inflate(R.layout.dialog_sair_layout,null);
        alert.setView(infView);

        final AlertDialog alertDialog = alert.create();

        final LinearLayout btnSair = (LinearLayout) infView.findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                HmsStatics.setEmail("");
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
        final LinearLayout btnCancelar = (LinearLayout) infView.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
