package com.software.hms.projeto.dto;

import com.mercadopago.model.Payment;
import com.software.hms.projeto.enuns.RetornoEnum;

import java.io.Serializable;
import java.util.List;


public class RetornoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private RetornoEnum retornoEnum;
	private String descricao;
	private String token;
	private List<CategoriaDTO> listCategoria;
	private List<MensagemDTO> listaMensagem;
	private DetalheMensagemDTO detalheMensagemDTO;
	private UsuarioDTO usuarioDTO;
	private PagamentoDTO pagamentoDTO;
	private MensagemDTO mensagemDTO;
	private List<EstadoDTO> listEstado;

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

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public PagamentoDTO getPagamentoDTO() {
		return pagamentoDTO;
	}

	public void setPagamentoDTO(PagamentoDTO pagamentoDTO) {
		this.pagamentoDTO = pagamentoDTO;
	}

	public MensagemDTO getMensagemDTO() {
		return mensagemDTO;
	}

	public void setMensagemDTO(MensagemDTO mensagemDTO) {
		this.mensagemDTO = mensagemDTO;
	}

	public List<EstadoDTO> getListEstado() {
		return listEstado;
	}

	public void setListEstado(List<EstadoDTO> listEstado) {
		this.listEstado = listEstado;
	}
}