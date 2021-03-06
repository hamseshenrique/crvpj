package com.software.hms.projeto.interfaces;

import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.dto.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * @author Hamses Henrique
 */
public interface Usuario {

    @POST("/cruzvermelha/usuario/registrar")
    Call<RetornoDTO> registrar(@Body UsuarioDTO usuarioDTO);

    @POST("/cruzvermelha/api/alterarUsuario")
    Call<RetornoDTO> alterarUsuario(@Body UsuarioDTO usuarioDTO);

    @POST("/cruzvermelha/api/login")
    Call<RetornoDTO> login(@Body UsuarioDTO usuarioDTO);

    @POST("/cruzvermelha/api/esqueceuSenha")
    Call<RetornoDTO> esqueceuSenha(@Body UsuarioDTO usuarioDTO);

    @POST("/cruzvermelha/api/obterUsuario")
    Call<RetornoDTO> obterUsuario(@Body UsuarioDTO usuarioDTO);
}