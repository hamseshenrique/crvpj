package com.software.hms.projeto.enuns;

public enum RetornoEnum {
	
	SUCESSO(1,"SUCESSO"),
	ERRO(500,"ERRO"),
	USUARIO_NAO_CADASTRADO(2,"ERRO");
	
	private Integer codigo;
	private String descricao;
	
	RetornoEnum(Integer codigo,String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}