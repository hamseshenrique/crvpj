package com.software.hms.projeto.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.software.hms.projeto.DetalheMsgActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.MensagemDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 28/08/16.
 */
public class DetalheMensagemAsync extends AsyncTask<MensagemDTO,Void,RetornoDTO> {

    private ProgressDialog dialog;
    private Context context;
    private MensagemDTO mensagemDTO;

    public DetalheMensagemAsync(final Context context){
        this.context = context;
    }
    @Override
    protected RetornoDTO doInBackground(MensagemDTO... mensagemDTOs) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try{
            mensagemDTO = mensagemDTOs[0];

            final SharedPreferences sharedPreferences = context.getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(HmsStatics.getEmail(),null);

            final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    HmsStatics.SERVER).client(OkHttpBasicAuth.createHead(token)).
                    addConverterFactory(GsonConverterFactory.create()).build();

            final CruzVermelhaRest cruzVermelhaRest = retrofit.create(CruzVermelhaRest.class);
            final Call<RetornoDTO> call = cruzVermelhaRest.detalheMsg(mensagemDTO.getId());
            retrofit2.Response<RetornoDTO> response = call.execute();
            if(response.isSuccessful()){
                retornoDTO = response.body();
            }
        }catch (Exception e){
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
            Bundle bundle = new Bundle();
            if(TextUtils.isEmpty(mensagemDTO.getCabecalho())){
                bundle.putString("title",mensagemDTO.getDescricao());
            }else{
                bundle.putString("title",mensagemDTO.getCabecalho());
            }

            bundle.putSerializable("detalhe",retornoDTO.getDetalheMensagemDTO());
            Intent intent = new Intent(context, DetalheMsgActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

}
