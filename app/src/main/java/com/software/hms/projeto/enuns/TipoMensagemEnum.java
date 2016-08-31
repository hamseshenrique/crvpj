package com.software.hms.projeto.enuns;

/**
 * Created by root on 28/08/16.
 */
public enum TipoMensagemEnum {

    MENSAGEM_USUARIO(1,"Mensagem Usuario"),
    MENSAGEM_PROJETO(2,"Mensagem Projeto"),
    MENSAGEM_NOTICIA(3,"Mensagem Noticia");

    private Integer id;
    private String desc;

    private TipoMensagemEnum(final Integer id,final String desc){
        this.id = id;
        this.desc = desc;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
}

