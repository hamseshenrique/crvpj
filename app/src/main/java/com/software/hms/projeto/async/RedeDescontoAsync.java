package com.software.hms.projeto.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.software.hms.projeto.R;
import com.software.hms.projeto.RedeDescontosActivity;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 30/08/16.
 */
public class RedeDescontoAsync extends AsyncTask<Void,Void,RetornoDTO> {

    private ProgressDialog dialog;
    private Context context;

    public RedeDescontoAsync(final Context context){
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
    protected RetornoDTO doInBackground(Void... voids) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try{
            final SharedPreferences sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(HmsStatics.getEmail(),null);

            final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    HmsStatics.SERVER).client(OkHttpBasicAuth.createHead(token)).
                    addConverterFactory(GsonConverterFactory.create()).build();

            final CruzVermelhaRest cruzVermelhaRest = retrofit.create(CruzVermelhaRest.class);
            final Call<RetornoDTO> call = cruzVermelhaRest.obterCategorias();
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
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        dialog.dismiss();

        if(retornoDTO.getRetornoEnum().equals(RetornoEnum.SUCESSO)){
            Intent intent = new Intent(context,RedeDescontosActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("retorno",retornoDTO);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
