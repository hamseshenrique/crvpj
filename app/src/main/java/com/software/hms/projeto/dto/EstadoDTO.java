package com.software.hms.projeto.dto;

import java.io.Serializable;

/**
 * Created by hms on 12/11/16.
 */

public class EstadoDTO implements Serializable{

    private String uf;
    private String estado;
    private String municipio;

    public EstadoDTO(){}


    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
