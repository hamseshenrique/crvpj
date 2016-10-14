package com.software.hms.projeto.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mercadopago.util.LayoutUtil;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.PagamentoDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hms on 11/10/16.
 */

public class PagamentoAsync extends AsyncTask<PagamentoDTO,Void,RetornoDTO> {
    private Activity context;
    private String token;

    public PagamentoAsync(final Activity context, final String token){
        this.context = context;
        this.token = token;
    }

    @Override
    protected RetornoDTO doInBackground(PagamentoDTO... pagamentoDTOs) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try{
            final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    HmsStatics.SERVER).client(OkHttpBasicAuth.createHead(token)).
                    addConverterFactory(GsonConverterFactory.create()).build();

            final CruzVermelhaRest cruzVermelhaRest = retrofit.create(CruzVermelhaRest.class);

            final Call<RetornoDTO> call = cruzVermelhaRest.checkout(pagamentoDTOs[0]);
            retrofit2.Response<RetornoDTO> retorno = call.execute();

            if(retorno.isSuccessful()){
                retornoDTO = retorno.body();
            }
        }catch(Exception e){

        }

        return retornoDTO;
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        LayoutUtil.showRegularLayout(context);
    }
}
