package com.software.hms.projeto.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.mercadopago.core.MercadoPago;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.software.hms.projeto.AprovadoActivity;
import com.software.hms.projeto.CardActivity;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.PagamentoDTO;
import com.software.hms.projeto.dto.PayerDTO;
import com.software.hms.projeto.dto.Response;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hms on 11/10/16.
 */

public class PagamentoAsync extends AsyncTask<PagamentoDTO,Void,RetornoDTO> {
    private Activity context;
    private String token;
    private PaymentMethod paymentMethod;
    private String valor;


    public PagamentoAsync(final Activity context, final String token,
                          final PaymentMethod paymentMethod,final String valor){
        this.context = context;
        this.token = token;
        this.paymentMethod = paymentMethod;
        this.valor = valor;
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
            e.printStackTrace();
        }

        return retornoDTO;
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        LayoutUtil.showRegularLayout(context);
        if(RetornoEnum.SUCESSO.equals(retornoDTO.getRetornoEnum())){
            final PagamentoDTO pagamentoDTO = retornoDTO.getPagamentoDTO();
            final Response response = pagamentoDTO.getResponse();
            if(response.getStatus().equals("approved")){
                try {
                    if(paymentMethod.getName().equals("Boleto")){

                    }else{
                        Intent intent = new Intent(context,AprovadoActivity.class);
                        intent.putExtra("paymentMethod", JsonUtil.getInstance().toJson(paymentMethod));
                        intent.putExtra("payment", pagamentoDTO);
                        intent.putExtra("valor",valor);
                        context.startActivity(intent);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
