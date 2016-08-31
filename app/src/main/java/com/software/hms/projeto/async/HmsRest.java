package com.software.hms.projeto.async;

import android.util.Log;

import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.enuns.TipoMensagemEnum;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.interfaces.ObserverInterface;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 28/08/16.
 */
public class HmsRest {

    private ObserverInterface observerInterface;

    public HmsRest(ObserverInterface observerInterface){
        this.observerInterface = observerInterface;
    }

    public void loadMensagens(final String token, final TipoMensagemEnum tipoMensagemEnum){
        try{
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpBasicAuth.createHead(token)).build();

            CruzVermelhaRest cruzVermelhaRest = retrofit.create(CruzVermelhaRest.class);
            Log.i("CRUZVERMELHA",tipoMensagemEnum.getId().toString());
            Observable<RetornoDTO> obsRet = cruzVermelhaRest.obterMensagem(tipoMensagemEnum.getId());
            obsRet.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(retornoDTO ->{
                        observerInterface.atualizar(retornoDTO);
                    });
        }catch(Exception e){
            Log.i("CRUZVERMELHA",e.getMessage(),e);
        }
    }
}
