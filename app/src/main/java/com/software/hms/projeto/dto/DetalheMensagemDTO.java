package com.software.hms.projeto.dto;

import java.io.Serializable;

/**
 * Created by root on 28/08/16.
 */
public class DetalheMensagemDTO implements Serializable{

    private String descricao;
    private String img;

    public DetalheMensagemDTO(){
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(String img) {
        this.img = img;
    }

}
