package com.software.hms.projeto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoriaDTO.
 */
public class CategoriaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Integer id;
	
	/** The descricao. */
	private String descricao;
	
	private List<LugaresDTO> listLugares;
	
	/**
	 * Instantiates a new categoria DTO.
	 */
	public CategoriaDTO(){}

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
	 * @return the listLugares
	 */
	public List<LugaresDTO> getListLugares() {
		if(listLugares == null){
			listLugares = new ArrayList<LugaresDTO>();
		}
		return listLugares;
	}

	/**
	 * @param listLugares the listLugares to set
	 */
	public void setListLugares(List<LugaresDTO> listLugares) {
		this.listLugares = listLugares;
	}	
}