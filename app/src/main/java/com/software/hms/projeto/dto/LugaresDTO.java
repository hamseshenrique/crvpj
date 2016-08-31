package com.software.hms.projeto.dto;

// TODO: Auto-generated Javadoc

import java.io.Serializable;

/**
 * The Class LugaresDTO.
 */
public class LugaresDTO implements Serializable {
	
	/** The id. */
	private Integer id;
	
	private Integer idCategoria;
	
	/** The descricao. */
	private String descricao;

	private String logo;

	private String detalhe;


	private String cidadeEstado;
	
	/**
	 * Instantiates a new lugares DTO.
	 */
	public LugaresDTO(){}

	public LugaresDTO(final Integer id,final Integer idCategoria,final String descricao){
		this.id = id;
		this.idCategoria = idCategoria;
		this.descricao = descricao;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the descricao.
	 *
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Sets the descricao.
	 *
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	/** The logo. */
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	/** The detalhe. */
	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	/** The cidade estado. */
	public String getCidadeEstado() {
		return cidadeEstado;
	}

	public void setCidadeEstado(String cidadeEstado) {
		this.cidadeEstado = cidadeEstado;
	}
}