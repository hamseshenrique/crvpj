package com.software.hms.projeto.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.software.hms.projeto.PerfilActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.dto.UsuarioDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.Usuario;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 22/09/16.
 */
public class AlterarUsuario extends AsyncTask<UsuarioDTO,Void,RetornoDTO> {

    private ProgressDialog dialog;
    private Context context;
    private SharedPreferences sharedPreferences;

    public AlterarUsuario(final Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.aguarde));
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected RetornoDTO doInBackground(UsuarioDTO... usuarioDTOs) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try{
            sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString(HmsStatics.getEmail(),null);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    "http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080").client(OkHttpBasicAuth.createHead(token)).
                    addConverterFactory(GsonConverterFactory.create()).build();

            Usuario usuario = retrofit.create(Usuario.class);
            UsuarioDTO usuarioDTO = usuarioDTOs[0];

            Call<RetornoDTO> call = usuario.alterarUsuario(usuarioDTO);
            retrofit2.Response<RetornoDTO> response = call.execute();

            if(response.isSuccessful()){
                retornoDTO = response.body();
            }
        }catch(Exception e){
            Log.e("CRUZVERMELHA",e.getMessage(),e);
            e.printStackTrace();
        }

        return retornoDTO;
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        dialog.dismiss();

        if(retornoDTO.getRetornoEnum().equals(RetornoEnum.SUCESSO)){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            UsuarioDTO usuarioDTO = retornoDTO.getUsuarioDTO();
            Log.i("CRUZVEMELHA",usuarioDTO.getLogin());
            Log.i("CRUZVEMELHA",usuarioDTO.getToken());
            editor.putString(usuarioDTO.getLogin(),usuarioDTO.getToken());
            editor.putString("LOGADO",usuarioDTO.getLogin());
            HmsStatics.setEmail(usuarioDTO.getLogin());

            editor.commit();

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Cadastro alterado com sucesso !");
            alertDialogBuilder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            alertDialogBuilder.create().show();
        }else{
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Erro ao alterar Perfil Usuario !");
            alertDialogBuilder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            alertDialogBuilder.create().show();
        }
    }
}
