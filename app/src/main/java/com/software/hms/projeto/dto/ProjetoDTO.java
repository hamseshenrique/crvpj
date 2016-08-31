package com.software.hms.projeto.dto;

// TODO: Auto-generated Javadoc

import java.io.Serializable;

/**
 * The Class ProjetoDTO.
 */
public class ProjetoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;
	
	/** The descricao. */
	private String descricao;
	
	/**
	 * Instantiates a new projeto DTO.
	 */
	public ProjetoDTO(){}
	
	/**
	 * Instantiates a new projeto DTO.
	 *
	 * @param id the id
	 * @param descricao the descricao
	 */
	public ProjetoDTO(final Integer id,final String descricao){
		this.id = id;
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
}
