package com.software.hms.projeto.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.software.hms.projeto.MenuActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.dto.UsuarioDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.interfaces.Usuario;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 04/09/16.
 */
public class EsqueceuSenhaAsync extends AsyncTask<UsuarioDTO,Void,RetornoDTO>{
    private ProgressDialog dialog;
    private Context context;

    public EsqueceuSenhaAsync(final Context context){
        this.context = context;
    }

    @Override
    protected RetornoDTO doInBackground(UsuarioDTO... usuarioDTOs) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try {
            UsuarioDTO usuario = usuarioDTOs[0];

            final SharedPreferences sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(usuario.getLogin(), null);


            final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    "http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080").client(OkHttpBasicAuth.createHead(token)).
                    addConverterFactory(GsonConverterFactory.create()).build();

            final Usuario usuarioRest = retrofit.create(Usuario.class);
            final Call<RetornoDTO> call = usuarioRest.esqueceuSenha(usuario);
            retrofit2.Response<RetornoDTO> response = call.execute();
            if(response.isSuccessful()){
                retornoDTO = response.body();
            }

        }catch(Exception e){
            Log.e("CRUZVERMELHA",e.getMessage(),e);
        }
        return retornoDTO;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.aguarde));
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        dialog.dismiss();
        if(retornoDTO.getRetornoEnum().equals(RetornoEnum.SUCESSO)){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(R.string.senhaEnviada);
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
