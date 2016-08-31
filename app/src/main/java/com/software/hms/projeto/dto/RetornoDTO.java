package com.software.hms.projeto.dto;

import com.software.hms.projeto.enuns.RetornoEnum;

import java.io.Serializable;
import java.util.List;


public class RetornoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RetornoEnum retornoEnum;
	private String descricao;
	private String token;
	private List<CategoriaDTO> listCategoria;
	private List<MensagemDTO> listaMensagem;
	private DetalheMensagemDTO detalheMensagemDTO;

	public RetornoDTO(final RetornoEnum retornoEnum){
		this.retornoEnum = retornoEnum;
	}
	
	public RetornoDTO(final RetornoEnum retornoEnum,final String descricao){
		this.retornoEnum = retornoEnum;
		this.descricao = descricao;
	}
	
	public RetornoDTO(){}

	/**
	 * @return the retornoEnum
	 */
	public RetornoEnum getRetornoEnum() {
		return retornoEnum;
	}

	/**
	 * @param retornoEnum the retornoEnum to set
	 */
	public void setRetornoEnum(RetornoEnum retornoEnum) {
		this.retornoEnum = retornoEnum;
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


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<CategoriaDTO> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<CategoriaDTO> listCategoria) {
		this.listCategoria = listCategoria;
	}

	public List<MensagemDTO> getListaMensagem() {
		return listaMensagem;
	}

	public void setListaMensagem(List<MensagemDTO> listaMensagem) {
		this.listaMensagem = listaMensagem;
	}

	public DetalheMensagemDTO getDetalheMensagemDTO() {
		return detalheMensagemDTO;
	}

	public void setDetalheMensagemDTO(DetalheMensagemDTO detalheMensagemDTO) {
		this.detalheMensagemDTO = detalheMensagemDTO;
	}
}