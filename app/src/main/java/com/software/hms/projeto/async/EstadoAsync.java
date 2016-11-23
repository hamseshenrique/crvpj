package com.software.hms.projeto.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.software.hms.projeto.R;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.interfaces.ObserverInterface;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hms on 12/11/16.
 */

public class EstadoAsync extends AsyncTask<String, Void, RetornoDTO> {

    private ProgressDialog dialog;
    private Context context;
    private ObserverInterface observerInterface;

    public EstadoAsync(final Context context, final ObserverInterface observerInterface) {
        this.context = context;
        this.observerInterface = observerInterface;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.aguarde));
        dialog.show();
        super.onPreExecute();
    }


    @Override
    protected RetornoDTO doInBackground(String... uf) {
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);
        try {
            final okhttp3.OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request original = chain.request();

                    RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);

                    Request request = original.newBuilder()
                            .header("Content-Type","application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            }).readTimeout(80000, TimeUnit.MILLISECONDS).build();


            final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                    HmsStatics.SERVER).client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();

            final CruzVermelhaRest cruzVermelhaRest = retrofit.create(CruzVermelhaRest.class);
            final Call<RetornoDTO> call = cruzVermelhaRest.obterMunicipios(uf[0]);
            retrofit2.Response<RetornoDTO> response = call.execute();
            if (response.isSuccessful()) {
                retornoDTO = response.body();
            }
        } catch (Exception e) {
            Log.e("CRUZVERMELHA", e.getMessage(), e);
        }


        return retornoDTO;
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {
        dialog.dismiss();
        if(retornoDTO.getRetornoEnum().equals(RetornoEnum.SUCESSO)){
            observerInterface.atualizar(retornoDTO);
        }
    }


}