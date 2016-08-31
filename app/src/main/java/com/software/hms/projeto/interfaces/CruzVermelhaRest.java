package com.software.hms.projeto.interfaces;

import com.software.hms.projeto.dto.RetornoDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by root on 13/08/16.
 */
public interface CruzVermelhaRest {

    @GET("/cruzvermelha/api/mensagem/obterMensagem/{tipoMensagem}")
    Observable<RetornoDTO> obterMensagem(@Path("tipoMensagem") Integer tipoMensagem);

    @GET("/cruzvermelha/api/categoria/obterCategorias")
    Call<RetornoDTO> obterCategorias();

    @GET("/cruzvermelha/api/mensagem/detalheMsg/{idMensagem}")
    Call<RetornoDTO> detalheMsg(@Path("idMensagem") Integer idMensagem);

}